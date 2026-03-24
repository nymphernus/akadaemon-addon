package com.akadaemon.addon.handler;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.entity.EntityDraugr;
import com.akadaemon.addon.entity.RenderDraugr;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityDraugr.class, new RenderDraugr());

        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 0) {
            AkadaemonAddon.fluidIce.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_ice_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_ice_flow"));
            AkadaemonAddon.fluidSnow.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_snow_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_snow_flow"));
            AkadaemonAddon.fluidLapis.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_lapis_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_lapis_flow"));
            AkadaemonAddon.fluidQuicksilver.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_quicksilver_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_quicksilver_flow"));
            AkadaemonAddon.fluidAmber.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_amber_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_amber_flow"));
            AkadaemonAddon.fluidGlowstone.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_glowstone_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/liquid_glowstone_flow"));
            AkadaemonAddon.fluidRedstone.setIcons(event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/ruby_flux_still"), event.map.registerIcon(AkadaemonAddon.MODID + ":fluids/ruby_flux_flow"));

            AkadaemonAddon.fluidEtherealPhoton.setIcons(AkadaemonAddon.blockEtherealPhoton.getIcon(0, 0));
            AkadaemonAddon.fluidGlacialQuicksilver.setIcons(AkadaemonAddon.blockGlacialQuicksilver.getIcon(0, 0));
            AkadaemonAddon.fluidRubyFlux.setIcons(AkadaemonAddon.blockRubyFlux.getIcon(0, 0));
        }
    }
}