package com.akadaemon.addon;

import net.minecraft.item.Item;

public class ItemDust extends Item {
    public ItemDust(String name) {
        super();
        this.setUnlocalizedName(name);
        this.setTextureName(AkadaemonAddon.MODID + ":" + name);
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }
}