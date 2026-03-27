package com.akadaemon.addon.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
    public static Fluid fluidGlacialQuicksilver;
    public static Fluid fluidEtherealPhoton;
    public static Fluid fluidRubyFlux;
    public static Fluid fluidIce;
    public static Fluid fluidSnow;
    public static Fluid fluidQuicksilver;
    public static Fluid fluidLapis;
    public static Fluid fluidGlowstone;
    public static Fluid fluidAmber;
    public static Fluid fluidRedstone;

    public static void init() {
        initializeFluids();
        registerFluids();
    }

    private static void initializeFluids() {
        fluidGlacialQuicksilver = new Fluid("glacial_quicksilver").setLuminosity(0).setViscosity(1000).setDensity(1000);
        fluidEtherealPhoton = new Fluid("ethereal_photon").setLuminosity(15).setViscosity(1000).setDensity(1000);
        fluidRubyFlux = new Fluid("ruby_flux").setLuminosity(5).setViscosity(1000).setDensity(1000);
        fluidIce = new Fluid("liquid_ice").setTemperature(200).setDensity(1000).setViscosity(2000);
        fluidSnow = new Fluid("liquid_snow").setTemperature(150).setDensity(1000).setViscosity(1500);
        fluidQuicksilver = new Fluid("liquid_quicksilver").setTemperature(500).setDensity(2000).setViscosity(3000);
        fluidLapis = new Fluid("liquid_lapis").setTemperature(400).setDensity(1100).setViscosity(1000);
        fluidGlowstone = new Fluid("liquid_glowstone").setTemperature(800).setDensity(2000).setViscosity(3000);
        fluidAmber = new Fluid("liquid_amber").setTemperature(700).setDensity(3000).setViscosity(1000);
        fluidRedstone = new Fluid("liquid_redstone").setTemperature(400).setDensity(1100).setViscosity(1000);

    }

    private static void registerFluids() {
        FluidRegistry.registerFluid(fluidGlacialQuicksilver);
        FluidRegistry.registerFluid(fluidEtherealPhoton);
        FluidRegistry.registerFluid(fluidRubyFlux);
        FluidRegistry.registerFluid(fluidIce);
        FluidRegistry.registerFluid(fluidSnow);
        FluidRegistry.registerFluid(fluidQuicksilver);
        FluidRegistry.registerFluid(fluidLapis);
        FluidRegistry.registerFluid(fluidGlowstone);
        FluidRegistry.registerFluid(fluidAmber);
        FluidRegistry.registerFluid(fluidRedstone);


    }
}
