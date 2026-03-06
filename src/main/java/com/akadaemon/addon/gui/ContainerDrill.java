package com.akadaemon.addon.gui;

import com.akadaemon.addon.blocks.TileEntityTitanDrill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDrill extends Container {
    private TileEntityTitanDrill tile;

    public ContainerDrill(InventoryPlayer invPlayer, TileEntityTitanDrill tile) {
        this.tile = tile;

        addSlotToContainer(new Slot(tile, 0, 134, 53));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return tile.isUseableByPlayer(player); }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIdx) { return null; }
}