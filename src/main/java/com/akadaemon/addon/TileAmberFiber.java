package com.akadaemon.addon;

import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public class TileAmberFiber extends TileEntity implements IAspectContainer {

    @Override
    public void updateEntity() {
        if (worldObj == null || worldObj.isRemote || worldObj.getTotalWorldTime() % 10 != 0) return;

        TileEntity device = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

        if (device instanceof IAspectContainer && !(device instanceof TileAmberFiber)) {
            IAspectContainer container = (IAspectContainer) device;
            AspectList aspects = container.getAspects();

            if (aspects != null && aspects.size() > 0) {
                for (Aspect aspect : aspects.getAspects()) {
                    if (aspects.getAmount(aspect) > 0) {
                        if (transferEssentia(aspect)) {
                            container.takeFromContainer(aspect, 1);
                            worldObj.markBlockForUpdate(xCoord, yCoord - 1, zCoord);
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean transferEssentia(Aspect aspect) {
        int radius = 10;
        for (Object obj : worldObj.loadedTileEntityList) {
            TileEntity tile = (TileEntity) obj;

            double dist = tile.getDistanceFrom(xCoord, yCoord, zCoord);
            if (dist > radius * radius) continue;

            if (tile == this || (tile.xCoord == xCoord && tile.zCoord == zCoord)) continue;

            if (tile instanceof IAspectContainer && !(tile instanceof TileAmberFiber)) {
                IAspectContainer jar = (IAspectContainer) tile;

                if (jar.doesContainerAccept(aspect)) {
                    int leftover = jar.addToContainer(aspect, 1);
                    if (leftover == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override public AspectList getAspects() { return new AspectList(); }
    @Override public void setAspects(AspectList aspects) {}
    @Override public boolean doesContainerAccept(Aspect tag) { return true; }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        if (transferEssentia(tag)) return amount - 1;
        return amount;
    }

    @Override public boolean takeFromContainer(Aspect tag, int amount) { return false; }
    @Override public boolean takeFromContainer(AspectList ot) { return false; }
    @Override public boolean doesContainerContainAmount(Aspect tag, int amount) { return false; }
    @Override public boolean doesContainerContain(AspectList ot) { return false; }
    @Override public int containerContains(Aspect tag) { return 0; }
}