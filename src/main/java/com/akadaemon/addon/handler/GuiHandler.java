package com.akadaemon.addon.handler;

import com.akadaemon.addon.gui.ContainerThaumTransformer;
import com.akadaemon.addon.gui.GuiThaumTransformer;
import com.akadaemon.addon.blocks.TileThaumTransformer;
import cpw.mods.fml.common.network.IGuiHandler; // Важно!
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileThaumTransformer) return new ContainerThaumTransformer(player.inventory, (TileThaumTransformer) te);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileThaumTransformer) return new GuiThaumTransformer(player.inventory, (TileThaumTransformer) te);
        return null;
    }
}