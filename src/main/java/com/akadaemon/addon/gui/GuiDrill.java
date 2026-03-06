package com.akadaemon.addon.gui;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.blocks.TileEntityTitanDrill;
import com.akadaemon.addon.handler.PacketDrillUpdate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;

public class GuiDrill extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(AkadaemonAddon.MODID, "textures/gui/gui_drill.png");
    private TileEntityTitanDrill tile;
    private GuiTextField depthField;
    private GuiButton btnSilk;
    private GuiButton btnPower;

    public GuiDrill(InventoryPlayer invPlayer, TileEntityTitanDrill tile) {
        super(new ContainerDrill(invPlayer, tile));
        this.tile = tile;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);

        depthField = new GuiTextField(this.fontRendererObj, guiLeft + 134, guiTop + 30, 28, 12);
        depthField.setMaxStringLength(3);
        depthField.setText(String.valueOf(tile.depthLimit));
        depthField.setFocused(false);

        btnSilk = new GuiButton(1, guiLeft + 25, guiTop + 58, 30, 20, "");
        btnPower = new GuiButton(2, guiLeft + 25, guiTop + 27, 30, 20, "");

        this.buttonList.add(btnSilk);
        this.buttonList.add(btnPower);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(char c, int keyCode) {
        if (depthField.textboxKeyTyped(c, keyCode)) {
            updateDepth();
            return;
        }
        super.keyTyped(c, keyCode);
    }

    @Override
    protected void mouseClicked(int x, int y, int btn) {
        super.mouseClicked(x, y, btn);
        depthField.mouseClicked(x, y, btn);
    }

    private void updateDepth() {
        try {
            int val = Integer.parseInt(depthField.getText());
            if (val < 1) val = 1;
            if (val > 256) val = 256;

            tile.depthLimit = val;
            sendUpdatePacket();
        } catch (NumberFormatException e) {
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            tile.silkTouch = !tile.silkTouch;
            sendUpdatePacket();
        }if (button.id == 2) {
            tile.isActive = !tile.isActive;
            sendUpdatePacket();
        }

    }

    private void sendUpdatePacket() {
        AkadaemonAddon.network.sendToServer(new PacketDrillUpdate(tile.xCoord, tile.yCoord, tile.zCoord, tile.depthLimit, tile.silkTouch, tile.isActive));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = StatCollector.translateToLocal("tile.titan_drill.name");
        fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 4210752);

        fontRendererObj.drawString(StatCollector.translateToLocal("gui.titan_drill.depth"), 100, 32, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("gui.titan_drill.filter"), 100, 55, 4210752);

        String silkLabel = StatCollector.translateToLocal("gui.titan_drill.silk");
        fontRendererObj.drawString(silkLabel, 15, 49, 4210752);

        btnSilk.displayString = tile.silkTouch ?
                "§2" + StatCollector.translateToLocal("gui.titan_drill.on") :
                "§4" + StatCollector.translateToLocal("gui.titan_drill.off");

        String statusLabel = StatCollector.translateToLocal("gui.titan_drill.power");
        fontRendererObj.drawString(statusLabel, 15, 18, 4210752);

        btnPower.displayString = tile.isActive ?
                "§2" + StatCollector.translateToLocal("gui.titan_drill.on") :
                "§4" + StatCollector.translateToLocal("gui.titan_drill.off");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        depthField.drawTextBox();
    }
}