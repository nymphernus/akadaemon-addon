package com.akadaemon.addon;

import com.akadaemon.addon.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import tconstruct.library.modifier.ItemModifier;

public class ModInfiniteDurability extends ItemModifier {

    public ModInfiniteDurability(ItemStack[] items) {
        super(items, 174, "CompositeModifier");
    }

    @Override
    public boolean matches(ItemStack[] recipe, ItemStack tool) {
        boolean hasItem = false;
        for (ItemStack stack : recipe) {
            if (stack != null && stack.getItem() == ModItems.compositeMod) {
                hasItem = true;
                break;
            }
        }
        if (!hasItem || tool == null || !tool.hasTagCompound()) return false;
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        return tags.getInteger("Modifiers") > 0 && tags.getInteger("Reinforced") < 10;
    }

    @Override
    public void modify(ItemStack[] recipe, ItemStack tool) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        tags.setInteger("Reinforced", 10);
        tags.setInteger("Damage", 0);
        tags.setBoolean("Broken", false);

        int m = tags.getInteger("Modifiers");
        if (m > 0) {
            tags.setInteger("Modifiers", m - 1);
        }

        addToolTip(tool,
                EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.composite.name"),
                EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.composite.extra")
        );
    }
}