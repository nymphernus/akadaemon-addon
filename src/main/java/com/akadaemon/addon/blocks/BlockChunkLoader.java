package com.akadaemon.addon.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import com.akadaemon.addon.AkadaemonAddon;

public class BlockChunkLoader extends BlockContainer {
    public BlockChunkLoader() {
        super(Material.iron);
        setBlockName("chunk_loader");
        setBlockTextureName(AkadaemonAddon.MODID + ":chunk_loader");
        setCreativeTab(AkadaemonAddon.tabAkadaemon);
        setHardness(5.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityChunkLoader();
    }

    @Override
    public int getRenderType() { return 0; }
}