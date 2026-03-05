package com.akadaemon.addon;

import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public class TileAmberFiber extends TileEntity implements IAspectContainer {

    @Override
    public void updateEntity() {
        if (this.worldObj == null || this.worldObj.isRemote) return;
        if (this.worldObj.getTotalWorldTime() % 5 != 0) return;

        TileEntity device = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);

        if (device instanceof IAspectContainer) {
            IAspectContainer container = (IAspectContainer) device;
            AspectList aspects = container.getAspects();

            if (aspects != null && aspects.size() > 0) {
                Aspect aspect = aspects.getAspects()[0];

                if (aspects.getAmount(aspect) > 0) {
                    if (transferEssentia(aspect)) {
                        container.takeFromContainer(aspect, 1);
                    }
                }
            }
        }
    }

    private boolean transferEssentia(Aspect aspect) {
        int radius = 8;
        IAspectContainer emptyJar = null;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x == 0 && z == 0 && (y == 0 || y == -1)) continue;

                    TileEntity tile = this.worldObj.getTileEntity(this.xCoord + x, this.yCoord + y, this.zCoord + z);

                    if (tile instanceof IAspectContainer) {
                        IAspectContainer jar = (IAspectContainer) tile;

                        if (jar.getAspects().getAmount(aspect) > 0 && jar.doesContainerAccept(aspect)) {
                            if (jar.addToContainer(aspect, 1) == 0) return true;
                        }

                        if (emptyJar == null && jar.getAspects().size() == 0 && jar.doesContainerAccept(aspect)) {
                            emptyJar = jar;
                        }
                    }
                }
            }
        }

        if (emptyJar != null) {
            return emptyJar.addToContainer(aspect, 1) == 0;
        }

        return false;
    }

    @Override
    public AspectList getAspects() { return new AspectList(); }

    @Override
    public void setAspects(AspectList aspects) {}

    @Override
    public boolean doesContainerAccept(Aspect tag) { return true; }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        if (transferEssentia(tag)) return 0;
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) { return false; }

    @Override
    public boolean takeFromContainer(AspectList ot) { return false; }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) { return false; }

    @Override
    public boolean doesContainerContain(AspectList ot) { return false; }

    @Override
    public int containerContains(Aspect tag) {
        return 0;
    }
    public boolean containerContainsAmount(Aspect tag, int amount) {
        return false;
    }
}