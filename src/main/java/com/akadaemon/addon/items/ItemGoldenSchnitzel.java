package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemGoldenSchnitzel extends ItemFood {
    public ItemGoldenSchnitzel() {
        super(9, 1.2F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("golden_schnitzel");
        this.setTextureName(AkadaemonAddon.MODID + ":golden_schnitzel");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2000, 1, true));
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 2, true));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("tooltip.golden_schnitzel.desc"));
        list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("tooltip.golden_schnitzel.extra"));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return EnumChatFormatting.YELLOW + super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}