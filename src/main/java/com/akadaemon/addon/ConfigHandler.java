package com.akadaemon.addon;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

    public static String[] titanNames;
    public static String[] mythrilNames;
    public static String[] mythrilBlockNames;
    public static String[] adamantitNames;

    public static boolean enableOreDump;

    public static void init(FMLPreInitializationEvent event) {
        net.minecraftforge.common.config.Configuration config = new net.minecraftforge.common.config.Configuration(event.getSuggestedConfigurationFile());
        config.load();

        String debugCat = "Debug_Settings";
        enableOreDump = config.getBoolean("oreDictionaryDump", debugCat, false,
                "If true, creates a file in /logs/ with all registered OreDictionary names.");

        String cat = "OreDictionary_Compatibility";

        mythrilNames = config.getStringList("mythrilIngot", cat,
                new String[]{"ingotMythril", "ingotMithril"},
                "OreDictionary name list for Mythril");
        titanNames = config.getStringList("titanIngot", cat,
                new String[]{"ingotTitan", "ingotTitanium"},
                "OreDictionary name list for Titan");
        adamantitNames = config.getStringList("adamantitIngot", cat,
                new String[]{"ingotAdamantit", "ingotAdamantium"},
                "OreDictionary name list for Adamantite");

        mythrilBlockNames = config.getStringList("mythrilBlock", cat,
                new String[]{"blockMythril", "blockMithril"},
                "OreDictionary name list for Mythril Block");

        if (config.hasChanged()) {
            config.save();
        }
    }
}
