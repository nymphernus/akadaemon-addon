package com.akadaemon.addon.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBase extends Item {
    private final EnumChatFormatting nameColor;
    private final EnumChatFormatting nameStyle; // Добавляем поле для стиля

    public ItemBase(EnumChatFormatting color, EnumChatFormatting style) {
        this.nameColor = color;
        this.nameStyle = style;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String style = (nameStyle == EnumChatFormatting.RESET) ? "" : nameStyle.toString();
        return nameColor.toString() + style + super.getItemStackDisplayName(stack);
    }
}