package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.init.InternalName;
import ic2.core.item.armor.ItemArmorElectric;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;

public class ItemNeuralInterface extends ItemArmorElectric implements IGoggles, IRevealer {

    public ItemNeuralInterface() {
        super(
                InternalName.itemArmorNanoHelmet,
                InternalName.itemArmorNanoHelmet,
                0,
                (double)1.0E6F,
                (double)2000.0F,
                2
        );
        this.setUnlocalizedName("neural_interface");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return (double)0.8F;
    }

    @Override
    public int getEnergyPerDamage() {
        return 800;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return AkadaemonAddon.MODID + ":textures/models/neural_interface_layer_1.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon(AkadaemonAddon.MODID + ":neural_interface");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return EnumChatFormatting.GREEN + super.getItemStackDisplayName(stack);
    }

    @Override
    public boolean showNodes(ItemStack itemstack, net.minecraft.entity.EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean showIngamePopups(ItemStack itemstack, net.minecraft.entity.EntityLivingBase player) {
        return true;
    }
}