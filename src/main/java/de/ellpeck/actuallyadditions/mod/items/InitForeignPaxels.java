/*
 * This file ("InitForeignPaxels.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.crafting.ToolCrafting;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.items.base.ItemAllToolAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class InitForeignPaxels{

    public static final int[] MT_COLORS = new int[]{4166, 2248890, 8882649, 12410135, 11451392, 3684412};
    public static final String[] MT_NAMES = new String[]{"Obsidian", "LapisLazuli", "Osmium", "Bronze", "Glowstone", "Steel"};
    public static final int[] TF_COLORS = new int[]{13332762, 5407943, 5407895, 5394789, 12960613, 12960653, 12410135, 2999795, 10143162};
    public static final int[] SO_COLORS = new int[]{9409450, 2040021, 5714944, 526344, 545032};
    //MekanismTools
    private static final String MEKANISM_TOOLS = "MekanismTools";
    private static final String[] MT_REPAIR_NAMES = new String[]{"ingotRefinedObsidian", "gemLapis", "ingotOsmium", "ingotBronze", "ingotRefinedGlowstone", "ingotSteel"};
    //ThermalFoundation
    private static final String THERMAL_FOUNDATION = "ThermalFoundation";
    private static final String[] TF_NAMES = new String[]{"Copper", "Tin", "Silver", "Lead", "Nickel", "Electrum", "Bronze", "Platinum", "Invar"};
    //SimpleOres
    private static final String SIMPLE_ORES = "simpleores";
    private static final String[] SO_NAMES = new String[]{"tin", "mythril", "copper", "onyx", "adamantium"};
    private static final String[] SO_REPAIR_NAMES = new String[]{"ingotTin", "ingotMythril", "ingotCopper", "gemOnyx", "ingotAdamantium"};
    public static Item[] tfPaxels = new Item[9];
    public static Item[] soPaxels = new Item[5];
    private static Item[] mtPaxels = new Item[6];

    public static void init(){
        //SimpleOres
        if(ConfigBoolValues.SO_PAXELS.isEnabled()){
            if(Loader.isModLoaded(SIMPLE_ORES)){
                ModUtil.LOGGER.info("Initializing "+SIMPLE_ORES+" AIOTs...");

                for(int i = 0; i < soPaxels.length; i++){
                    Item axe = ItemUtil.getItemFromName(SIMPLE_ORES+":"+SO_NAMES[i]+"_axe");
                    Item pickaxe = ItemUtil.getItemFromName(SIMPLE_ORES+":"+SO_NAMES[i]+"_pickaxe");
                    Item hoe = ItemUtil.getItemFromName(SIMPLE_ORES+":"+SO_NAMES[i]+"_hoe");
                    Item sword = ItemUtil.getItemFromName(SIMPLE_ORES+":"+SO_NAMES[i]+"_sword");
                    Item shovel = ItemUtil.getItemFromName(SIMPLE_ORES+":"+SO_NAMES[i]+"_shovel");

                    if(axe != null && pickaxe != null && hoe != null && sword != null && shovel != null && axe instanceof ItemTool){
                        Item.ToolMaterial material = ((ItemTool)axe).func_150913_i();
                        soPaxels[i] = new ItemAllToolAA(material, SO_REPAIR_NAMES[i], "paxelSO"+SO_NAMES[i], EnumRarity.rare, SO_COLORS[i]);

                        if(ConfigCrafting.PAXELS.isEnabled()){
                            GameRegistry.addRecipe(new ShapelessOreRecipe(soPaxels[i], axe, pickaxe, hoe, sword, shovel));
                            ToolCrafting.recipesPaxels.add(Util.GetRecipes.lastIRecipe());
                        }
                    }
                }
            }
            else{
                ModUtil.LOGGER.info(SIMPLE_ORES+" not loaded, can't initialize Special AIOTs.");
            }
        }

        //MekanismTools
        if(ConfigBoolValues.MT_PAXELS.isEnabled()){
            if(Loader.isModLoaded(MEKANISM_TOOLS)){
                ModUtil.LOGGER.info("Initializing "+MEKANISM_TOOLS+" AIOTs...");

                for(int i = 0; i < mtPaxels.length; i++){
                    Item axe = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+MT_NAMES[i]+"Axe");
                    Item pickaxe = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+MT_NAMES[i]+"Pickaxe");
                    Item hoe = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+MT_NAMES[i]+"Hoe");
                    Item sword = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+MT_NAMES[i]+"Sword");
                    Item shovel = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+MT_NAMES[i]+"Shovel");

                    if(axe != null && pickaxe != null && hoe != null && sword != null && shovel != null && axe instanceof ItemTool){
                        Item.ToolMaterial material = ((ItemTool)axe).func_150913_i();
                        mtPaxels[i] = new ItemAllToolAA(material, MT_REPAIR_NAMES[i], "paxelMT"+MT_NAMES[i], EnumRarity.rare, MT_COLORS[i]);

                        if(ConfigCrafting.PAXELS.isEnabled()){
                            GameRegistry.addRecipe(new ShapelessOreRecipe(mtPaxels[i], axe, pickaxe, hoe, sword, shovel));
                            ToolCrafting.recipesPaxels.add(Util.GetRecipes.lastIRecipe());
                        }
                    }
                }
            }
            else{
                ModUtil.LOGGER.info(MEKANISM_TOOLS+" not loaded, can't initialize Special AIOTs.");
            }
        }

        //ThermalFoundation
        if(ConfigBoolValues.TF_PAXELS.isEnabled()){
            if(Loader.isModLoaded(THERMAL_FOUNDATION)){
                ModUtil.LOGGER.info("Initializing "+THERMAL_FOUNDATION+" AIOTs...");

                for(int i = 0; i < tfPaxels.length; i++){
                    Item axe = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.axe"+TF_NAMES[i]);
                    Item pickaxe = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.pickaxe"+TF_NAMES[i]);
                    Item hoe = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.hoe"+TF_NAMES[i]);
                    Item sword = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.sword"+TF_NAMES[i]);
                    Item shovel = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.shovel"+TF_NAMES[i]);

                    if(axe != null && pickaxe != null && hoe != null && sword != null && shovel != null && axe instanceof ItemTool){
                        Item.ToolMaterial material = ((ItemTool)axe).func_150913_i();
                        tfPaxels[i] = new ItemAllToolAA(material, "ingot"+TF_NAMES[i], "paxelTF"+TF_NAMES[i], EnumRarity.rare, TF_COLORS[i]);

                        if(ConfigCrafting.PAXELS.isEnabled()){
                            GameRegistry.addRecipe(new ShapelessOreRecipe(tfPaxels[i], axe, pickaxe, hoe, sword, shovel));
                            ToolCrafting.recipesPaxels.add(Util.GetRecipes.lastIRecipe());
                        }
                    }
                }
            }
            else{
                ModUtil.LOGGER.info(THERMAL_FOUNDATION+" not loaded, can't initialize Special AIOTs.");
            }
        }
    }

    public static void addToCreativeTab(){
        for(Item item : tfPaxels){
            if(item != null){
                CreativeTab.instance.add(item);
            }
        }
        for(Item item : mtPaxels){
            if(item != null){
                CreativeTab.instance.add(item);
            }
        }
        for(Item item : soPaxels){
            if(item != null){
                CreativeTab.instance.add(item);
            }
        }
    }
}
