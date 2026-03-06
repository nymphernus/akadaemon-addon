package com.akadaemon.addon.blocks;

import com.akadaemon.addon.handler.ConfigHandler;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityTitanDrill extends TileEntity implements IEnergySink, IInventory {

    public boolean isActive = false;
    private ItemStack[] inventory = new ItemStack[1];
    public double energy = 0;
    public int maxEnergy = 10000;
    public int depthLimit = 5;
    public boolean silkTouch = false;
    private boolean isAddedToEnet = false;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;

        if (!isAddedToEnet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            isAddedToEnet = true;
        }

        if (worldObj.getTotalWorldTime() % ConfigHandler.drillTick == 0 && energy >= 250 && isActive) {
            runDrill();
        }
    }

    private void runDrill() {
        for (int y = yCoord - 1; y >= yCoord - depthLimit; y--) {
            Block block = worldObj.getBlock(xCoord, y, zCoord);
            int meta = worldObj.getBlockMetadata(xCoord, y, zCoord);

            if (block == null || block.isAir(worldObj, xCoord, y, zCoord) || block.getBlockHardness(worldObj, xCoord, y, zCoord) < 0) {
                continue;
            }

            ItemStack filter = inventory[0];
            if (filter != null) {
                if (Block.getBlockFromItem(filter.getItem()) != block) continue;
            }

            breakBlock(xCoord, y, zCoord, block, meta);
            if (silkTouch) {
                energy -= 500;
            } else {
                energy -= 250;
            }
            return;
        }
    }

    private void breakBlock(int x, int y, int z, Block block, int meta) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        if (silkTouch && block.canSilkHarvest(worldObj, null, x, y, z, meta)) {
            drops.add(new ItemStack(block, 1, meta));
        } else {
            drops.addAll(block.getDrops(worldObj, x, y, z, meta, 0));
        }

        worldObj.setBlockToAir(x, y, z);

        for (ItemStack drop : drops) {
            if (!addToAdjacentChest(drop)) {
                EntityItem entityItem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.1, zCoord + 0.5, drop);
                worldObj.spawnEntityInWorld(entityItem);
            }
        }
    }

    private boolean addToAdjacentChest(ItemStack stack) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (te instanceof IInventory && !(te instanceof TileEntityTitanDrill)) {
                IInventory inv = (IInventory) te;
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    if (inv.isItemValidForSlot(i, stack)) {
                        ItemStack target = inv.getStackInSlot(i);
                        if (target == null) {
                            inv.setInventorySlotContents(i, stack.copy());
                            return true;
                        } else if (target.isItemEqual(stack) && target.stackSize < target.getMaxStackSize()) {
                            int space = target.getMaxStackSize() - target.stackSize;
                            int toAdd = Math.min(space, stack.stackSize);
                            target.stackSize += toAdd;
                            stack.stackSize -= toAdd;
                            if (stack.stackSize <= 0) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setDouble("energy", energy);
        nbt.setInteger("depthLimit", depthLimit);
        nbt.setBoolean("silkTouch", silkTouch);
        nbt.setBoolean("isActive", isActive);

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
        this.energy = nbt.getDouble("energy");
        this.depthLimit = nbt.getInteger("depthLimit");
        this.silkTouch = nbt.getBoolean("silkTouch");
        this.isActive = nbt.getBoolean("isActive");

        NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        if (list.tagCount() > 0) {
            this.inventory[0] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(0));
        }
    }

    @Override
    public void invalidate() {
        if (isAddedToEnet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            isAddedToEnet = false;
        }
        super.invalidate();
    }

    @Override public ItemStack decrStackSize(int s, int amt) {
        if (this.inventory[s] != null) {
            ItemStack itemstack;
            if (this.inventory[s].stackSize <= amt) {
                itemstack = this.inventory[s];
                this.inventory[s] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[s].splitStack(amt);
                if (this.inventory[s].stackSize == 0) this.inventory[s] = null;
                this.markDirty();
                return itemstack;
            }
        }
        return null;
    }
    @Override
    public net.minecraft.network.Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new net.minecraft.network.play.server.S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override public double getDemandedEnergy() { return maxEnergy - energy; }
    @Override public double injectEnergy(ForgeDirection dir, double amt, double volt) {
        energy += amt;
        this.markDirty();
        return 0;
    }
    @Override public int getSinkTier() { return 4; }
    @Override public boolean acceptsEnergyFrom(TileEntity e, ForgeDirection d) { return true; }
    @Override
    public String getInventoryName() { return net.minecraft.util.StatCollector.translateToLocal("tile.titan_drill.name"); }
    @Override public int getSizeInventory() { return 1; }
    @Override public ItemStack getStackInSlot(int s) { return inventory[s]; }
    @Override public void setInventorySlotContents(int s, ItemStack st) { inventory[s] = st; }
    @Override public boolean hasCustomInventoryName() { return false; }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUseableByPlayer(EntityPlayer p) { return true; }
    @Override public void openInventory() {}
    @Override public void closeInventory() {}
    @Override public boolean isItemValidForSlot(int s, ItemStack st) { return true; }
    @Override public ItemStack getStackInSlotOnClosing(int s) { return null; }
}