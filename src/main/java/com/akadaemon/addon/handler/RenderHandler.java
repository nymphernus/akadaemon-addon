package com.akadaemon.addon.handler;

import com.akadaemon.addon.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import java.util.List;

public class RenderHandler {

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null) return;
        ItemStack helmet = player.getCurrentArmor(3);

        if (helmet != null && helmet.getItem() == ModItems.neuralInterface) {
            if (helmet.getItemDamage() < helmet.getMaxDamage()) {
                double radius = 36.0D;
                List entities = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                        player.boundingBox.copy().expand(radius, radius, radius));

                for (Object obj : entities) {
                    if (obj instanceof IMob) {
                        renderEntityESP((EntityLivingBase) obj, event.partialTicks);
                    }
                }
            }
        }
    }

    private void renderEntityESP(EntityLivingBase entity, float partialTicks) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - net.minecraft.client.renderer.entity.RenderManager.renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - net.minecraft.client.renderer.entity.RenderManager.renderPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - net.minecraft.client.renderer.entity.RenderManager.renderPosZ;

        double centerY = y + (entity.height / 2.0F);

        GL11.glPushMatrix();
        GL11.glTranslated(x, centerY, z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        float scale = 0.15F; // Размер шарика
        GL11.glColor4f(0.0F, 1.0F, 0.2F, 0.6F);

        Sphere sphere = new Sphere();
        sphere.setDrawStyle(GLU.GLU_SILHOUETTE);
        sphere.draw(scale, 8, 8);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
        sphere.setDrawStyle(GLU.GLU_FILL);
        sphere.draw(0.03F, 4, 4);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}