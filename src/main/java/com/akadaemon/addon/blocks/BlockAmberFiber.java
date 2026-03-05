package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockAmberFiber extends BlockContainer {

    public BlockAmberFiber() {
        super(Material.glass);
        this.setBlockName("amber_fiber");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGlass);
        this.setBlockTextureName(AkadaemonAddon.MODID + ":amber_fiber");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
        this.setBlockBounds(0.3125F, -0.0625F, 0.3125F, 0.6875F, 0.0625F, 0.6875F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileAmberFiber();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }
}