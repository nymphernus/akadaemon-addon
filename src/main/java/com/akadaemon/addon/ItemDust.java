package com.akadaemon.addon;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemDust extends Item {
    public ItemDust(String name) {
        super();
        this.setUnlocalizedName(name);
        this.setTextureName(AkadaemonAddon.MODID + ":" + name);
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return EnumChatFormatting.AQUA + super.getItemStackDisplayName(stack);
    }
}