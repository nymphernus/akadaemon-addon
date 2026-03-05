package com.akadaemon.addon;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import java.util.List;

public class ItemMinerBelt extends Item implements IBauble {
    public ItemMinerBelt() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName("miner_belt");
        this.setTextureName(AkadaemonAddon.MODID + ":miner_belt");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            boolean state = !stack.getTagCompound().getBoolean("MagnetOn");
            stack.getTagCompound().setBoolean("MagnetOn", state);
            if (!world.isRemote) {
                String status = state ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF";
                player.addChatMessage(new ChatComponentText("Magnet: " + status));
            }
        }
        return stack;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;

            p.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 40, 1, true));

            if (itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("MagnetOn")) {
                List<EntityItem> items = p.worldObj.getEntitiesWithinAABB(EntityItem.class, p.boundingBox.expand(7.0D, 7.0D, 7.0D));
                for (EntityItem item : items) {
                    if (!item.isDead) {
                        item.onCollideWithPlayer(p);
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("tooltip.miner_belt.desc"));
        list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.miner_belt.extra"));

        if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("MagnetOn")) {
            list.add(EnumChatFormatting.GREEN + "Magnet: ACTIVE");
        } else {
            list.add(EnumChatFormatting.RED + "Magnet: INACTIVE");
        }
    }

    @Override public BaubleType getBaubleType(ItemStack itemstack) { return BaubleType.BELT; }
    @Override public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
    @Override public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
    @Override public boolean canEquip(ItemStack itemstack, EntityLivingBase player) { return true; }
    @Override public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) { return true; }
}