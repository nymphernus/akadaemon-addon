package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;

public class TileEntityChunkLoader extends TileEntity {
    private ForgeChunkManager.Ticket chunkTicket;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && chunkTicket == null) {
            chunkTicket = ForgeChunkManager.requestTicket(AkadaemonAddon.instance, worldObj, ForgeChunkManager.Type.NORMAL);

            if (chunkTicket != null) {
                chunkTicket.getModData().setInteger("loaderX", xCoord);
                chunkTicket.getModData().setInteger("loaderY", yCoord);
                chunkTicket.getModData().setInteger("loaderZ", zCoord);

                forceChunkLoading();
            }
        }
    }

    public void forceChunkLoading() {
        if (chunkTicket == null) return;

        int blockChunkX = xCoord >> 4;
        int blockChunkZ = zCoord >> 4;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                ForgeChunkManager.forceChunk(chunkTicket, new ChunkCoordIntPair(blockChunkX + x, blockChunkZ + z));
            }
        }
    }

    @Override
    public void invalidate() {
        if (!worldObj.isRemote && chunkTicket != null) {
            ForgeChunkManager.releaseTicket(chunkTicket);
        }
        super.invalidate();
    }
}