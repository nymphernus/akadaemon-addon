package com.akadaemon.addon.gui;

import com.akadaemon.addon.blocks.TileThaumTransformer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiThaumTransformer extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation("akadaemon", "textures/gui/gui_thaum_transformer.png");
    private TileThaumTransformer tile;

    public GuiThaumTransformer(InventoryPlayer inv, TileThaumTransformer tile) {
        super(new ContainerThaumTransformer(inv, tile));
        this.tile = tile;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}