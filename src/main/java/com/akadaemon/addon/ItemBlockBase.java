package com.akadaemon.addon;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockBase extends ItemBlock {
    private final EnumChatFormatting nameColor;
    private final EnumChatFormatting nameStyle;

    public ItemBlockBase(Block block, EnumChatFormatting color, EnumChatFormatting style) {
        super(block);
        this.nameColor = color;
        this.nameStyle = style;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String style = (nameStyle == EnumChatFormatting.RESET) ? "" : nameStyle.toString();
        return nameColor.toString() + style + super.getItemStackDisplayName(stack);
    }
}