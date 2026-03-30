package com.akadaemon.addon;

import com.akadaemon.addon.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import tconstruct.library.modifier.ItemModifier;

public class ModExtraSlot extends ItemModifier {

    public ModExtraSlot(ItemStack[] items) {
        super(items, 175, "Expansion");
    }

    @Override
    public boolean matches(ItemStack[] recipe, ItemStack tool) {
        boolean hasItem = false;
        for (ItemStack stack : recipe) {
            if (stack != null && stack.getItem() == ModItems.expansionChip) {
                hasItem = true;
                break;
            }
        }

        if (!hasItem || tool == null || !tool.hasTagCompound()) return false;

        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        return tags.getInteger("Modifiers") < 10;
    }

    @Override
    public void modify(ItemStack[] recipe, ItemStack tool) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int currentModifiers = tags.getInteger("Modifiers");

        tags.setInteger("Modifiers", currentModifiers + 1);

        addToolTip(tool,
                EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.expansion.name"),
                EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.expansion.extra")
        );
    }
}