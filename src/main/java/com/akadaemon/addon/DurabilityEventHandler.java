package com.akadaemon.addon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import tconstruct.library.tools.ToolCore;
import net.minecraft.item.ItemStack;

public class DurabilityEventHandler {

    @SubscribeEvent
    public void onToolUse(PlayerInteractEvent event) {
        ItemStack stack = event.entityPlayer.getHeldItem();

        if (stack != null && stack.getItem() instanceof ToolCore && stack.hasTagCompound()) {
            NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

            if (tags.getInteger("Reinforced") >= 10) {
                if (tags.getInteger("Damage") > 0) {
                    tags.setInteger("Damage", 0);
                    tags.setBoolean("Broken", false);
                }
            }
        }
    }
}