package com.akadaemon.addon;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class OreDump {
    public static void init() {
        try {
            File dumpFile = new File("logs/akadaemon_oredict_dump.txt");
            PrintWriter writer = new PrintWriter(dumpFile);

            writer.println("Total Ore Names found: " + OreDictionary.getOreNames().length);
            writer.println("____________________________________________");

            String[] oreNames = OreDictionary.getOreNames();
            Arrays.sort(oreNames);

            for (String name : oreNames) {
                List<ItemStack> ores = OreDictionary.getOres(name);
                writer.print(name + " => ");

                if (ores.isEmpty()) {
                    writer.println("[EMPTY]");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (ItemStack stack : ores) {
                        if (stack.getItem() != null) {
                            sb.append(Item.itemRegistry.getNameForObject(stack.getItem()))
                                    .append(":").append(stack.getItemDamage()).append(", ");
                        }
                    }
                    writer.println(sb.toString());
                }
            }

            writer.close();
            System.out.print("Ore Dictionary dump saved to: " + dumpFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.print("Failed to dump Ore Dictionary: " + e.getMessage());
        }
    }
}
