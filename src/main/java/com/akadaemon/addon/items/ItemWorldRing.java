package com.akadaemon.addon.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemWorldRing extends Item implements IBauble {

    public static ItemWorldRing instance;

    public ItemWorldRing() {
        instance = this;
        this.setMaxStackSize(1);
        this.setUnlocalizedName("world_ring");
        this.setTextureName("akadaemon:world_ring");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return EnumChatFormatting.BLUE + super.getItemStackDisplayName(stack);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 200, 0, true));
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 200, 0, true));
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 200, 0, true));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("tooltip.world_ring.desc"));
        list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.world_ring.extra"));
    }

    @Override public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
    @Override public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
    @Override public boolean canEquip(ItemStack itemstack, EntityLivingBase player) { return true; }
    @Override public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) { return true; }
}