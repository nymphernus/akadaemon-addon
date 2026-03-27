package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockContainer {
    private final int tier;

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconSide;

    public BlockSolarPanel(int tier) {
        super(Material.iron);
        this.tier = tier;
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
        this.setHardness(3.0F);
        this.setStepSound(soundTypeMetal);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.iconSide = reg.registerIcon(AkadaemonAddon.MODID + ":thaum_transformer_side");

        this.iconTop = reg.registerIcon(AkadaemonAddon.MODID + ":solar_top_tier" + tier);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1) return this.iconTop;
        return this.iconSide;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSolarPanel(this.tier);
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public int getRenderType() { return 0; }

    @Override
    public boolean isOpaqueCube() { return true; }
}