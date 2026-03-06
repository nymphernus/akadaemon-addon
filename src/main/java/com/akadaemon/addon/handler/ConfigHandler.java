package com.akadaemon.addon.handler;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

    public static String[] titanNames;
    public static String[] mythrilNames;
    public static String[] mythrilBlockNames;
    public static String[] adamantitNames;

    public static boolean enableOreDump;

    public static int amberFiberRadius;
    public static int convertEuVis;
    public static int solarEu;
    public static int solarVis;
    public static int drillTick;

    public static void init(FMLPreInitializationEvent event) {
        net.minecraftforge.common.config.Configuration config = new net.minecraftforge.common.config.Configuration(event.getSuggestedConfigurationFile());
        config.load();

        String debugCat = "Debug_Settings";
        enableOreDump = config.getBoolean("oreDictionaryDump", debugCat, false,
                "If true, creates a file in /logs/ with all registered OreDictionary names.");

        String oreCat = "OreDictionary_Compatibility";
        mythrilNames = config.getStringList("mythrilIngot", oreCat,
                new String[]{"ingotMythril", "ingotMithril"},
                "OreDictionary name list for Mythril");
        titanNames = config.getStringList("titanIngot", oreCat,
                new String[]{"ingotTitan", "ingotTitanium"},
                "OreDictionary name list for Titan");
        adamantitNames = config.getStringList("adamantitIngot", oreCat,
                new String[]{"ingotAdamantit", "ingotAdamantium"},
                "OreDictionary name list for Adamantite");

        mythrilBlockNames = config.getStringList("mythrilBlock", oreCat,
                new String[]{"blockMythril", "blockMithril"},
                "OreDictionary name list for Mythril Block");

        String balCat = "Balance_Settings";
        amberFiberRadius = config.getInt("amberFiberRadius", balCat,
                10, 5, 32, "Amber Fiber Working Radius (in blocks)");
        convertEuVis = config.getInt("convertEuVis", balCat,
                1000, 5, 5000, "The amount of EU received per 1 Vis");
        solarEu = config.getInt("solarEu", balCat,
                500, 100, 5000, "The amount of EU charged by the solar amulet");
        solarVis = config.getInt("solarVis", balCat,
                5, 1, 50, "The amount of Vis charged by the solar amulet");
        drillTick = config.getInt("drillTick", balCat,
                40, 10, 500, "Drill update speed in ticks");

        if (config.hasChanged()) {
            config.save();
        }
    }
}
