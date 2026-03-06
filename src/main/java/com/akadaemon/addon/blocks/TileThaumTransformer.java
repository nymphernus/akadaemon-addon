package com.akadaemon.addon.blocks;

import com.akadaemon.addon.handler.ConfigHandler;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TileThaumTransformer extends TileEntity implements IEnergySink, IInventory {
    private ItemStack[] inventory = new ItemStack[1];
    private boolean isAddedToEnet = false;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;

        if (!isAddedToEnet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            isAddedToEnet = true;
        }

        ItemStack stack = getStackInSlot(0);
        if (stack == null) return;

        if (stack.getItem() instanceof ItemWandCasting) {
            if (worldObj.getTotalWorldTime() % 20 == 0) {
                drainNodesToWand((ItemWandCasting) stack.getItem(), stack);
            }
        }

        if (stack.getItem() instanceof IElectricItem) {
            IElectricItem item = (IElectricItem) stack.getItem();
            if (ElectricItem.manager.getCharge(stack) < item.getMaxCharge(stack)) {
                drainNodesToEU(stack);
            }
        }
    }

    private void drainNodesToEU(ItemStack stack) {
        int r = 15;
        for (int x = xCoord - r; x <= xCoord + r; x++) {
            for (int y = yCoord - r; y <= yCoord + r; y++) {
                for (int z = zCoord - r; z <= zCoord + r; z++) {
                    TileEntity te = worldObj.getTileEntity(x, y, z);
                    if (te instanceof INode) {
                        INode node = (INode) te;
                        for (Aspect a : Aspect.getPrimalAspects()) {
                            if (node.getAspects().getAmount(a) > 0) {
                                node.getAspects().reduce(a, 1);
                                ElectricItem.manager.charge(stack, ConfigHandler.convertEuVis, 4, false, false);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void drainNodesToWand(ItemWandCasting wand, ItemStack stack) {
        int r = 15;
        for (int x = xCoord - r; x <= xCoord + r; x++) {
            for (int y = yCoord - r; y <= yCoord + r; y++) {
                for (int z = zCoord - r; z <= zCoord + r; z++) {
                    TileEntity te = worldObj.getTileEntity(x, y, z);
                    if (te instanceof INode) {
                        INode node = (INode) te;
                        for (Aspect a : Aspect.getPrimalAspects()) {
                            if (node.getAspects().getAmount(a) > 0 && wand.getVis(stack, a) < wand.getMaxVis(stack)) {
                                node.getAspects().reduce(a, 1);
                                wand.addVis(stack, a, 1, true);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public double getDemandedEnergy() {
        ItemStack stack = getStackInSlot(0);
        if (stack != null && stack.getItem() instanceof IElectricItem) {
            return 2048;
        }
        if (stack != null && stack.getItem() instanceof ItemWandCasting) {
            return ConfigHandler.convertEuVis;
        }
        return 0;
    }

    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        ItemStack stack = getStackInSlot(0);
        if (stack == null) return amount;

        if (stack.getItem() instanceof IElectricItem) {
            double left = ElectricItem.manager.charge(stack, amount, 4, false, false);
            return amount - left;
        }

        if (stack.getItem() instanceof ItemWandCasting) {
            ItemWandCasting wand = (ItemWandCasting) stack.getItem();
            if (amount >= ConfigHandler.convertEuVis) {
                for (Aspect aspect : Aspect.getPrimalAspects()) {
                    if (wand.getVis(stack, aspect) < wand.getMaxVis(stack)) {
                        wand.addVis(stack, aspect, 1, true);
                        return amount - ConfigHandler.convertEuVis;
                    }
                }
            }
        }
        return amount;
    }

    @Override public int getSinkTier() { return 3; }
    @Override public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) { return true; }

    @Override
    public void invalidate() {
        if (isAddedToEnet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            isAddedToEnet = false;
        }
        super.invalidate();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();
        if (inventory[0] != null) {
            NBTTagCompound slot = new NBTTagCompound();
            inventory[0].writeToNBT(slot);
            list.appendTag(slot);
        }
        nbt.setTag("Items", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        if (list.tagCount() > 0) {
            this.inventory[0] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(0));
        }
    }

    @Override public int getSizeInventory() { return 1; }
    @Override public ItemStack getStackInSlot(int s) { return inventory[s]; }
    @Override public void setInventorySlotContents(int s, ItemStack st) { inventory[s] = st; }
    @Override public String getInventoryName() { return "Thaumic Transformer"; }
    @Override public boolean hasCustomInventoryName() { return false; }
    @Override public int getInventoryStackLimit() { return 1; }
    @Override public boolean isUseableByPlayer(EntityPlayer p) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
    }
    @Override public void openInventory() {}
    @Override public void closeInventory() {}
    @Override public boolean isItemValidForSlot(int s, ItemStack st) { return true; }
    @Override public ItemStack decrStackSize(int s, int amt) {
        if (inventory[s] != null) {
            ItemStack st = inventory[s]; inventory[s] = null; return st;
        } return null;
    }
    @Override public ItemStack getStackInSlotOnClosing(int s) { return null; }
}