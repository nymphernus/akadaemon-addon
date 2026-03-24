package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;

public class ItemNeuralInterface extends ItemArmor implements IGoggles, IRevealer, IElectricItem {
    public ItemNeuralInterface() {
        super(ArmorMaterial.DIAMOND, 0, 0);
        this.setMaxStackSize(1);
        this.setMaxDamage(27);
        this.setUnlocalizedName("neural_interface");
        this.setTextureName(AkadaemonAddon.MODID + ":neural_interface");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return AkadaemonAddon.MODID + ":textures/models/neural_interface_layer_1.png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isRemote) {
            if (ElectricItem.manager.canUse(itemStack, 1)) {
                ElectricItem.manager.use(itemStack, 1, player);
            }
        }
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        double charge = ElectricItem.manager.getCharge(stack);
        double max = getMaxCharge(stack);
        return (int) (getMaxDamage(stack) - (charge / max) * getMaxDamage(stack));
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false; // Шлем не отдает энергию другим приборам
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return 1000000;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 2000;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) { return EnumChatFormatting.GREEN + super.getItemStackDisplayName(stack); }

    @Override
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) { return true; }

    @Override
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) { return true; }
}
