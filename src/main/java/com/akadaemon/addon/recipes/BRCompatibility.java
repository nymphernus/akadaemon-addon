package com.akadaemon.addon.recipes;

import cpw.mods.fml.common.event.FMLInterModComms;

public class BRCompatibility {
    public static void init() {
        if (cpw.mods.fml.common.Loader.isModLoaded("BigReactors")) {
            FMLInterModComms.sendMessage("BigReactors", "registerCoolant",
                    "glacial_quicksilver_block|0.95|0.85|7.0|3.5");

            FMLInterModComms.sendMessage("BigReactors", "registerCoolant",
                    "ethereal_photon_block|0.80|0.75|8.0|2.5");

            FMLInterModComms.sendMessage("BigReactors", "registerCoolant",
                    "ruby_flux|0.70|0.60|3.2|4.0");
        }
    }
}
