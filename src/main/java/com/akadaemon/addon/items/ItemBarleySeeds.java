package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemBarleySeeds extends ItemSeeds {
    public ItemBarleySeeds(Block crops) {
        super(crops, Blocks.farmland);
        this.setUnlocalizedName("barley_seeds");
        this.setTextureName(AkadaemonAddon.MODID + ":barley_seeds");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }
}