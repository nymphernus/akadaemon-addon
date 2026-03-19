package com.akadaemon.addon.handler;

import com.akadaemon.addon.blocks.TileEntityChunkLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import java.util.List;

public class ChunkLoaderHandler implements ForgeChunkManager.LoadingCallback {
    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        for (ForgeChunkManager.Ticket ticket : tickets) {
            int x = ticket.getModData().getInteger("loaderX");
            int y = ticket.getModData().getInteger("loaderY");
            int z = ticket.getModData().getInteger("loaderZ");

            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityChunkLoader) {
                ((TileEntityChunkLoader) te).forceChunkLoading();
            }
        }
    }
}