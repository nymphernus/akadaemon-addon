package com.akadaemon.addon.blocks;

import cpw.mods.fml.common.Loader;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSolarPanel extends TileEntity implements IEnergySource {
    private boolean initialized = false;
    private int tier;

    public TileSolarPanel(int tier) {
        this.tier = tier;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && !initialized) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            initialized = true;
        }
    }

    @Override
    public void invalidate() {
        if (initialized) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            initialized = false;
        }
        super.invalidate();
    }

    @Override
    public double getOfferedEnergy() {
        boolean hasASP = Loader.isModLoaded("AdvancedSolarPanel");

        if (tier == 1) {
            return hasASP ? 1024.0 : 64.0;
        } else {
            return hasASP ? 8192.0 : 512.0;
        }
    }

    @Override
    public void drawEnergy(double amount) {}

    @Override
    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    @Override
    public int getSourceTier() {
        return tier + 3;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.tier = nbt.getInteger("tier");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("tier", this.tier);
    }
}