package com.akadaemon.addon.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelDraugr extends ModelBiped {
    public ModelDraugr() {
        super(0.0F, 0.0F, 64, 64);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
}