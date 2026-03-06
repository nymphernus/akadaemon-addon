package com.akadaemon.addon.handler;

import com.akadaemon.addon.gui.ContainerThaumTransformer;
import com.akadaemon.addon.gui.GuiThaumTransformer;
import com.akadaemon.addon.blocks.TileThaumTransformer;
import com.akadaemon.addon.gui.ContainerDrill; // Новый контейнер
import com.akadaemon.addon.gui.GuiDrill;      // Новый GUI
import com.akadaemon.addon.blocks.TileEntityTitanDrill;   // Новый Tile
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class GuiHandler implements IGuiHandler {
    public static final int THAUM_TRANSFORMER_ID = 0;
    public static final int DRILL_ID = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);

        switch (ID) {
            case THAUM_TRANSFORMER_ID:
                if (te instanceof TileThaumTransformer)
                    return new ContainerThaumTransformer(player.inventory, (TileThaumTransformer) te);
                break;
            case DRILL_ID:
                if (te instanceof TileEntityTitanDrill)
                    return new ContainerDrill(player.inventory, (TileEntityTitanDrill) te);
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);

        switch (ID) {
            case THAUM_TRANSFORMER_ID:
                if (te instanceof TileThaumTransformer)
                    return new GuiThaumTransformer(player.inventory, (TileThaumTransformer) te);
                break;
            case DRILL_ID:
                if (te instanceof TileEntityTitanDrill)
                    return new GuiDrill(player.inventory, (TileEntityTitanDrill) te);
                break;
        }
        return null;
    }
}