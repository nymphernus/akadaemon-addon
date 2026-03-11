package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockBarley extends BlockCrops {
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockBarley() {
        super();
        this.setBlockName("barley_crop");
    }

    @Override
    protected Item func_149866_i() {
        return AkadaemonAddon.barley;
    }

    @Override
    protected Item func_149865_P() {
        return AkadaemonAddon.barleySeeds;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[6];
        for (int i = 0; i < icons.length; i++) {
            this.icons[i] = register.registerIcon(AkadaemonAddon.MODID + ":barley_stage_" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > 5) meta = 5;
        return icons[meta];
    }
}