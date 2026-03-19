package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;

public class TileEntityChunkLoader extends TileEntity {
    private ForgeChunkManager.Ticket chunkTicket;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && chunkTicket == null && worldObj.getTotalWorldTime() % 100 == 0) {
            requestTicket();
        }
    }

    private void requestTicket() {
        chunkTicket = ForgeChunkManager.requestTicket(AkadaemonAddon.instance, worldObj, ForgeChunkManager.Type.NORMAL);

        if (chunkTicket != null) {
            chunkTicket.getModData().setInteger("loaderX", xCoord);
            chunkTicket.getModData().setInteger("loaderY", yCoord);
            chunkTicket.getModData().setInteger("loaderZ", zCoord);
            forceChunkLoading();
        }
    }

    public void forceChunkLoading() {
        if (chunkTicket == null) return;

        int maxChunks = chunkTicket.getMaxChunkListDepth();
        int loadedCount = 0;

        int blockChunkX = xCoord >> 4;
        int blockChunkZ = zCoord >> 4;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (loadedCount < maxChunks) {
                    ForgeChunkManager.forceChunk(chunkTicket, new ChunkCoordIntPair(blockChunkX + x, blockChunkZ + z));
                    loadedCount++;
                }
            }
        }
    }

    @Override
    public void onChunkUnload() {
        if (chunkTicket != null) {
            chunkTicket = null;
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