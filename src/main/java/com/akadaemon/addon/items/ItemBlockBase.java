package com.akadaemon.addon.items;

import com.akadaemon.addon.blocks.BlockSolarPanel;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (this.field_150939_a instanceof BlockSolarPanel) {
            BlockSolarPanel solarBlock = (BlockSolarPanel) this.field_150939_a;
            int tier = solarBlock.getTier();

            boolean hasASP = Loader.isModLoaded("AdvancedSolarPanel");
            double energy;

            if (tier == 1) {
                energy = hasASP ? 1024.0 : 64.0;
            } else {
                energy = hasASP ? 8192.0 : 512.0;
            }

            list.add(StatCollector.translateToLocal("tooltip.solar_output.gen") + ": " +
                    EnumChatFormatting.GREEN + (int)energy + " EU/t");
        }
    }
}