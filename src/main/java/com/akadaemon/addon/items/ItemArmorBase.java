package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.ExternalItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemArmorBase extends ItemArmor {
    private final EnumChatFormatting color;
    private final String textureName;

    public ItemArmorBase(ArmorMaterial material, int type, String textureName, EnumChatFormatting color) {
        super(material, 0, type);
        this.textureName = textureName;
        this.color = color;
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ArmorMaterial mat = this.getArmorMaterial();
        if (mat == ModItems.matManyullyn) {
            return repair.getItem() == ExternalItems.ingotManyullyn.getItem() &&
                    repair.getItemDamage() == ExternalItems.ingotManyullyn.getItemDamage();
        }
        if (mat == ModItems.matTitan) return repair.getItem() == ModItems.ingotTitan;
        if (mat == ModItems.matAdamantit) return repair.getItem() == ModItems.ingotAdamantit;
        if (mat == ModItems.matMythril) return repair.getItem() == ModItems.ingotMythril;

        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return color.toString() + super.getItemStackDisplayName(stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        String layer = (slot == 2) ? "2" : "1";
        return AkadaemonAddon.MODID + ":textures/models/" + textureName + "_layer_" + layer + ".png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isRemote) {
            if (player.isDead || player.getHealth() <= 0) {
                resetPlayerStats(player);
                return;
            }

            if (player.getCurrentArmor(2) == itemStack) {
                if (isFullSet(player)) {
                    applySetEffects(player);
                } else {
                    resetPlayerStats(player);
                }
            }
        }
    }

    private boolean isFullSet(EntityPlayer player) {
        ArmorMaterial mat = this.getArmorMaterial();
        for (int i = 1; i <= 4; i++) {
            ItemStack stack = player.getEquipmentInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemArmor) ||
                    ((ItemArmor)stack.getItem()).getArmorMaterial() != mat) return false;
        }
        return true;
    }

    private void applySetEffects(EntityPlayer player) {
        ArmorMaterial mat = this.getArmorMaterial();

        if (mat == ModItems.matAdamantit) {
            com.akadaemon.addon.handler.ReflectionHelper.setImmuneToFire(player, true);
            player.extinguish();
        }
        else if (mat == ModItems.matMythril) {
            if (player.fallDistance > 0) player.fallDistance = 0.0F;
        }
    }

    private void resetPlayerStats(EntityPlayer player) {
        ArmorMaterial mat = this.getArmorMaterial();

        if (mat == ModItems.matAdamantit) {
            if (!player.isPotionActive(net.minecraft.potion.Potion.fireResistance.id)) {
                com.akadaemon.addon.handler.ReflectionHelper.setImmuneToFire(player, false);
            }
        }
    }
}