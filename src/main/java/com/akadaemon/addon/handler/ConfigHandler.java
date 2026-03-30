package com.akadaemon.addon.handler;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

    public static String[] titanNames;
    public static String[] mythrilNames;
    public static String[] mythrilBlockNames;
    public static String[] adamantitNames;

    public static boolean enableOreDump, ingotTransfer;

    public static int amberFiberRadius;
    public static int convertEuVis;
    public static int solarEu;
    public static int solarVis;
    public static int drillTick;
    public static int towerGeneration;

    public static double teleportSpeed, sunstrikeDistance;
    public static float teleportGravity, sunstrikeDamage;

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
                200, 50, 5000, "The amount of EU charged by the solar amulet");
        solarVis = config.getInt("solarVis", balCat,
                5, 1, 50, "The amount of Vis charged by the solar amulet");
        drillTick = config.getInt("drillTick", balCat,
                40, 10, 500, "Drill update speed in ticks");
        ingotTransfer = config.getBoolean("ingotTransfer", balCat, true,
                "If true, ores can be changed through a catalyst.");

        String genCat = "Generation_Settings";
        towerGeneration = config.getInt("towerGeneration", genCat,
                450, 100, 5000, "Draugr Tower spawn rarity. Higher value = lower spawn rate. [1 per X chunks]");

        String focusCat = "Focus_Settings";
        int speedInt = config.getInt("teleportSpeed", focusCat,
                250, 10, 1000, "Speed of the teleport pearl (Value / 100). Default 250 (2.5)");
        teleportSpeed = speedInt / 100.0D;
        int gravityInt = config.getInt("teleportGravity", focusCat,
                2, 0, 100, "Gravity of the teleport pearl (Value / 100). Default 2 (0.02). 0 = no gravity.");
        teleportGravity = gravityInt / 100.0F;
        int dmgInt = config.getInt("sunstrikeDamage", focusCat,
                12, 1, 50, "Damage dealt by Sunstrike");
        sunstrikeDamage = (float) dmgInt;
        int distInt = config.getInt("sunstrikeDistance", focusCat,
                32, 8, 256, "Max range of Sunstrike focus");
        sunstrikeDistance = (double) distInt;

        if (config.hasChanged()) {
            config.save();
        }
    }
}
