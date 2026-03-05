package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBucketBase extends ItemBucket {
    private final EnumChatFormatting color;

    public ItemBucketBase(Block fluidBlock, String name, EnumChatFormatting color) {
        super(fluidBlock);
        this.setUnlocalizedName(name);
        this.setTextureName(AkadaemonAddon.MODID + ":" + name);
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
        this.setContainerItem(net.minecraft.init.Items.bucket);
        this.color = color;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return color.toString() + super.getItemStackDisplayName(stack);
    }
}