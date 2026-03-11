package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemFoodBase extends ItemFood {

    private final EnumChatFormatting nameColor;
    private final EnumChatFormatting nameStyle;

    public ItemFoodBase(int amount, float saturation, boolean isWolfFood, EnumChatFormatting color, EnumChatFormatting style) {
        super(amount, saturation, isWolfFood);
        this.nameColor = color;
        this.nameStyle = style;
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String style = (nameStyle == EnumChatFormatting.RESET) ? "" : nameStyle.toString();
        return nameColor.toString() + style + super.getItemStackDisplayName(stack);
    }
}