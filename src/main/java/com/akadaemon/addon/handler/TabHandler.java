package com.akadaemon.addon.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TabHandler extends Gui {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final RenderItem itemRender = new RenderItem();

    @SubscribeEvent
    public void onRenderTab(RenderGameOverlayEvent.Pre event) {
        if (event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST) {
            if (mc.thePlayer != null && mc.theWorld != null) {
                event.setCanceled(true);
                renderTab(event.resolution.getScaledWidth());
            }
        }
    }

    private void renderTab(int width) {
        NetHandlerPlayClient handler = mc.thePlayer.sendQueue;
        if (handler == null) return;

        List<GuiPlayerInfo> players = (List<GuiPlayerInfo>) handler.playerInfoList;
        if (players == null || players.isEmpty()) return;

        int xSize = 220;
        int xPos = width / 2 - xSize / 2;
        int yPos = 30;
        int rowHeight = 14;

        drawRect(xPos - 5, yPos - 20, xPos + xSize + 5, yPos + (players.size() * rowHeight) + 5, 0xAA000000);

        String dimName = mc.theWorld.provider.getDimensionName();
        long time = (mc.theWorld.getWorldTime() % 24000L);
        long hours = (time / 1000 + 6) % 24;
        long minutes = (time % 1000) * 60 / 1000;
        String timeStr = String.format("%02d:%02d", hours, minutes);

        renderItemIcon(Items.clock, xPos, yPos - 19);
        mc.fontRenderer.drawStringWithShadow(EnumChatFormatting.GOLD + dimName + EnumChatFormatting.GRAY + " | " + EnumChatFormatting.WHITE + timeStr, xPos + 18, yPos - 16, 0xFFFFFF);

        for (int i = 0; i < players.size(); i++) {
            GuiPlayerInfo info = players.get(i);
            int currentY = yPos + (i * rowHeight);

            String playerName = info.name;
            ResourceLocation skinLocation = AbstractClientPlayer.getLocationSkin(playerName);
            AbstractClientPlayer.getDownloadImageSkin(skinLocation, playerName);

            mc.getTextureManager().bindTexture(skinLocation);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            func_146110_a(xPos, currentY, 8.0F, 8.0F, 8, 8, 64.0F, 32.0F);
            func_146110_a(xPos, currentY, 40.0F, 8.0F, 8, 8, 64.0F, 32.0F);

            String name = (playerName.equals(mc.thePlayer.getCommandSenderName()) ? EnumChatFormatting.AQUA : EnumChatFormatting.WHITE) + playerName;
            mc.fontRenderer.drawStringWithShadow(name, xPos + 12, currentY, 0xFFFFFF);

            int ping = info.responseTime;
            String pingColor = (ping < 100) ? "§a" : (ping < 300) ? "§e" : "§c";
            String pingText = pingColor + ping + "ms";

            int pingWidth = mc.fontRenderer.getStringWidth(pingText);
            mc.fontRenderer.drawStringWithShadow(pingText, xPos + xSize - pingWidth, currentY, 0xFFFFFF);
        }
    }

    private void renderItemIcon(net.minecraft.item.Item item, int x, int y) {
        ItemStack stack = new ItemStack(item);

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();

        itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, x, y);

        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }
}