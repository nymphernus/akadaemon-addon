package com.akadaemon.addon.entity;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDraugr extends RenderBiped {
    private static final ResourceLocation texture = new ResourceLocation(AkadaemonAddon.MODID, "textures/entity/draugr.png");

    public RenderDraugr() {
        super(new ModelDraugr(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
