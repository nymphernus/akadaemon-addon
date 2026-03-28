package com.akadaemon.addon.handler;

import net.minecraft.entity.Entity;
import java.lang.reflect.Field;

public class ReflectionHelper {
    private static Field fieldFire;

    static {
        fieldFire = cpw.mods.fml.relauncher.ReflectionHelper.findField(Entity.class, "isImmuneToFire", "field_70178_ae");
        fieldFire.setAccessible(true);
    }

    public static void setImmuneToFire(Entity entity, boolean value) {
        try {
            fieldFire.setBoolean(entity, value);
        } catch (Exception ignored) {}
    }
}