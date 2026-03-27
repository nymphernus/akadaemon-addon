package com.akadaemon.addon.handler;

import com.akadaemon.addon.blocks.ModBlocks;
import com.akadaemon.addon.items.ModItems;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler {
    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillBucket(event);
        if (result != null) {
            event.result = result;
            event.setResult(Result.ALLOW);
        }
    }

    private ItemStack fillBucket(FillBucketEvent event) {
        MovingObjectPosition pos = event.target;
        int x = pos.blockX, y = pos.blockY, z = pos.blockZ;
        Block block = event.world.getBlock(x, y, z);

        if (block == ModBlocks.blockGlacialQuicksilver) {
            event.world.setBlockToAir(x, y, z);
            return new ItemStack(ModItems.bucketGlacialQuicksilver);
        }
        if (block == ModBlocks.blockEtherealPhoton) {
            event.world.setBlockToAir(x, y, z);
            return new ItemStack(ModItems.bucketEtherealPhoton);
        }
        return null;
    }
}