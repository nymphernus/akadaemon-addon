package com.akadaemon.addon;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.List;

public class ItemSolarAmulet extends Item implements IBauble {
    public ItemSolarAmulet() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName("solar_amulet");
        this.setTextureName(AkadaemonAddon.MODID + ":solar_amulet");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player instanceof EntityPlayer && !player.worldObj.isRemote) {
            EntityPlayer p = (EntityPlayer) player;

            for (ItemStack stack : p.inventory.mainInventory) {
                chargeIC2(stack);
            }
            for (ItemStack stack : p.inventory.armorInventory) {
                chargeIC2(stack);
            }

            if (p.worldObj.isDaytime() && p.worldObj.canBlockSeeTheSky((int)p.posX, (int)p.posY, (int)p.posZ)) {
                repairArmor(p);
            }

            if (p.ticksExisted % 20 == 0) {
                for (ItemStack stack : p.inventory.mainInventory) {
                    if (stack != null && stack.getItem() instanceof ItemWandCasting) {
                        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
                        for (Aspect aspect : Aspect.getPrimalAspects()) {
                            wand.addVis(stack, aspect, 10, true);
                        }
                    }
                }
            }
        }
    }

    private void repairArmor(EntityPlayer player) {
        if (player.ticksExisted % 40 == 0) {
            for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                ItemStack armor = player.inventory.armorInventory[i];

                if (armor != null && armor.isItemDamaged() && !(armor.getItem() instanceof ic2.api.item.IElectricItem)) {
                    armor.setItemDamage(armor.getItemDamage() - 1);
                }
            }
        }
    }

    private void chargeIC2(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IElectricItem) {
            ElectricItem.manager.charge(stack, 1000, 4, false, false);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("tooltip.solar_amulet.desc"));
        list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.solar_amulet.extra"));
        list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.solar_amulet.goggles"));
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) { return BaubleType.AMULET; }

    @Override public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
    @Override public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
    @Override public boolean canEquip(ItemStack itemstack, EntityLivingBase player) { return true; }
    @Override public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) { return true; }
}