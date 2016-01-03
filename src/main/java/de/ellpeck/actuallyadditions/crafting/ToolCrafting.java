/*
 * This file ("ToolCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import de.ellpeck.actuallyadditions.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.items.InitItems;
import de.ellpeck.actuallyadditions.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public class ToolCrafting{

    public static ArrayList<IRecipe> recipesPaxels = new ArrayList<IRecipe>();

    public static void init(){

        if(ConfigCrafting.TOOL_EMERALD.isEnabled()){
            addToolAndArmorRecipes(new ItemStack(Items.emerald), InitItems.itemPickaxeEmerald, InitItems.itemSwordEmerald, InitItems.itemAxeEmerald, InitItems.itemShovelEmerald, InitItems.itemHoeEmerald, InitItems.itemHelmEmerald, InitItems.itemChestEmerald, InitItems.itemPantsEmerald, InitItems.itemBootsEmerald);
        }

        if(ConfigCrafting.TOOL_QUARTZ.isEnabled()){
            addToolAndArmorRecipes(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), InitItems.itemPickaxeQuartz, InitItems.itemSwordQuartz, InitItems.itemAxeQuartz, InitItems.itemShovelQuartz, InitItems.itemHoeQuartz, InitItems.itemHelmQuartz, InitItems.itemChestQuartz, InitItems.itemPantsQuartz, InitItems.itemBootsQuartz);
        }

        if(ConfigCrafting.TOOL_OBSIDIAN.isEnabled()){
            addToolAndArmorRecipes(new ItemStack(Blocks.obsidian), InitItems.itemPickaxeObsidian, InitItems.itemSwordObsidian, InitItems.itemAxeObsidian, InitItems.itemShovelObsidian, InitItems.itemHoeObsidian, InitItems.itemHelmObsidian, InitItems.itemChestObsidian, InitItems.itemPantsObsidian, InitItems.itemBootsObsidian);
        }

        if(ConfigCrafting.TOOL_CRYSTALS.isEnabled()){
            addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), InitItems.itemPickaxeCrystalRed, InitItems.itemSwordCrystalRed, InitItems.itemAxeCrystalRed, InitItems.itemShovelCrystalRed, InitItems.itemHoeCrystalRed, InitItems.itemHelmCrystalRed, InitItems.itemChestCrystalRed, InitItems.itemPantsCrystalRed, InitItems.itemBootsCrystalRed);
            addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), InitItems.itemPickaxeCrystalGreen, InitItems.itemSwordCrystalGreen, InitItems.itemAxeCrystalGreen, InitItems.itemShovelCrystalGreen, InitItems.itemHoeCrystalGreen, InitItems.itemHelmCrystalGreen, InitItems.itemChestCrystalGreen, InitItems.itemPantsCrystalGreen, InitItems.itemBootsCrystalGreen);
            addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), InitItems.itemPickaxeCrystalWhite, InitItems.itemSwordCrystalWhite, InitItems.itemAxeCrystalWhite, InitItems.itemShovelCrystalWhite, InitItems.itemHoeCrystalWhite, InitItems.itemHelmCrystalWhite, InitItems.itemChestCrystalWhite, InitItems.itemPantsCrystalWhite, InitItems.itemBootsCrystalWhite);
            addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), InitItems.itemPickaxeCrystalLightBlue, InitItems.itemSwordCrystalLightBlue, InitItems.itemAxeCrystalLightBlue, InitItems.itemShovelCrystalLightBlue, InitItems.itemHoeCrystalLightBlue, InitItems.itemHelmCrystalLightBlue, InitItems.itemChestCrystalLightBlue, InitItems.itemPantsCrystalLightBlue, InitItems.itemBootsCrystalLightBlue);
            addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), InitItems.itemPickaxeCrystalBlue, InitItems.itemSwordCrystalBlue, InitItems.itemAxeCrystalBlue, InitItems.itemShovelCrystalBlue, InitItems.itemHoeCrystalBlue, InitItems.itemHelmCrystalBlue, InitItems.itemChestCrystalBlue, InitItems.itemPantsCrystalBlue, InitItems.itemBootsCrystalBlue);
            addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), InitItems.itemPickaxeCrystalBlack, InitItems.itemSwordCrystalBlack, InitItems.itemAxeCrystalBlack, InitItems.itemShovelCrystalBlack, InitItems.itemHoeCrystalBlack, InitItems.itemHelmCrystalBlack, InitItems.itemChestCrystalBlack, InitItems.itemPantsCrystalBlack, InitItems.itemBootsCrystalBlack);
        }

        //Paxels
        if(ConfigCrafting.PAXELS.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.woodenPaxel),
                    new ItemStack(Items.wooden_axe),
                    new ItemStack(Items.wooden_pickaxe),
                    new ItemStack(Items.wooden_shovel),
                    new ItemStack(Items.wooden_sword),
                    new ItemStack(Items.wooden_hoe)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.stonePaxel),
                    new ItemStack(Items.stone_axe),
                    new ItemStack(Items.stone_pickaxe),
                    new ItemStack(Items.stone_shovel),
                    new ItemStack(Items.stone_sword),
                    new ItemStack(Items.stone_hoe)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.ironPaxel),
                    new ItemStack(Items.iron_axe),
                    new ItemStack(Items.iron_pickaxe),
                    new ItemStack(Items.iron_shovel),
                    new ItemStack(Items.iron_sword),
                    new ItemStack(Items.iron_hoe)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.goldPaxel),
                    new ItemStack(Items.golden_axe),
                    new ItemStack(Items.golden_pickaxe),
                    new ItemStack(Items.golden_shovel),
                    new ItemStack(Items.golden_sword),
                    new ItemStack(Items.golden_hoe)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.diamondPaxel),
                    new ItemStack(Items.diamond_axe),
                    new ItemStack(Items.diamond_pickaxe),
                    new ItemStack(Items.diamond_shovel),
                    new ItemStack(Items.diamond_sword),
                    new ItemStack(Items.diamond_hoe)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.emeraldPaxel),
                    new ItemStack(InitItems.itemAxeEmerald),
                    new ItemStack(InitItems.itemPickaxeEmerald),
                    new ItemStack(InitItems.itemSwordEmerald),
                    new ItemStack(InitItems.itemShovelEmerald),
                    new ItemStack(InitItems.itemHoeEmerald)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.obsidianPaxel),
                    new ItemStack(InitItems.itemAxeObsidian),
                    new ItemStack(InitItems.itemPickaxeObsidian),
                    new ItemStack(InitItems.itemSwordObsidian),
                    new ItemStack(InitItems.itemShovelObsidian),
                    new ItemStack(InitItems.itemHoeObsidian)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.quartzPaxel),
                    new ItemStack(InitItems.itemAxeQuartz),
                    new ItemStack(InitItems.itemPickaxeQuartz),
                    new ItemStack(InitItems.itemSwordQuartz),
                    new ItemStack(InitItems.itemShovelQuartz),
                    new ItemStack(InitItems.itemHoeQuartz)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());

            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalRed),
                    new ItemStack(InitItems.itemAxeCrystalRed),
                    new ItemStack(InitItems.itemPickaxeCrystalRed),
                    new ItemStack(InitItems.itemSwordCrystalRed),
                    new ItemStack(InitItems.itemShovelCrystalRed),
                    new ItemStack(InitItems.itemHoeCrystalRed)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalGreen),
                    new ItemStack(InitItems.itemAxeCrystalGreen),
                    new ItemStack(InitItems.itemPickaxeCrystalGreen),
                    new ItemStack(InitItems.itemSwordCrystalGreen),
                    new ItemStack(InitItems.itemShovelCrystalGreen),
                    new ItemStack(InitItems.itemHoeCrystalGreen)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalBlue),
                    new ItemStack(InitItems.itemAxeCrystalBlue),
                    new ItemStack(InitItems.itemPickaxeCrystalBlue),
                    new ItemStack(InitItems.itemSwordCrystalBlue),
                    new ItemStack(InitItems.itemShovelCrystalBlue),
                    new ItemStack(InitItems.itemHoeCrystalBlue)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalLightBlue),
                    new ItemStack(InitItems.itemAxeCrystalLightBlue),
                    new ItemStack(InitItems.itemPickaxeCrystalLightBlue),
                    new ItemStack(InitItems.itemSwordCrystalLightBlue),
                    new ItemStack(InitItems.itemShovelCrystalLightBlue),
                    new ItemStack(InitItems.itemHoeCrystalLightBlue)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalBlack),
                    new ItemStack(InitItems.itemAxeCrystalBlack),
                    new ItemStack(InitItems.itemPickaxeCrystalBlack),
                    new ItemStack(InitItems.itemSwordCrystalBlack),
                    new ItemStack(InitItems.itemShovelCrystalBlack),
                    new ItemStack(InitItems.itemHoeCrystalBlack)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalWhite),
                    new ItemStack(InitItems.itemAxeCrystalWhite),
                    new ItemStack(InitItems.itemPickaxeCrystalWhite),
                    new ItemStack(InitItems.itemSwordCrystalWhite),
                    new ItemStack(InitItems.itemShovelCrystalWhite),
                    new ItemStack(InitItems.itemHoeCrystalWhite)));
            recipesPaxels.add(Util.GetRecipes.lastIRecipe());
        }
    }

    public static void addToolAndArmorRecipes(ItemStack base, Item pickaxe, Item sword, Item axe, Item shovel, Item hoe, Item helm, Item chest, Item pants, Item boots){
        //Pickaxe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxe),
                "EEE", " S ", " S ",
                'E', base,
                'S', new ItemStack(Items.stick)));

        //Sword
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sword),
                "E", "E", "S",
                'E', base,
                'S', new ItemStack(Items.stick)));

        //Axe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axe),
                "EE", "ES", " S",
                'E', base,
                'S', new ItemStack(Items.stick)));

        //Shovel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovel),
                "E", "S", "S",
                'E', base,
                'S', new ItemStack(Items.stick)));

        //Hoe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoe),
                "EE", " S", " S",
                'E', base,
                'S', new ItemStack(Items.stick)));

        //Helm
        GameRegistry.addRecipe(new ItemStack(helm),
                "OOO", "O O",
                'O', base);

        //Chest
        GameRegistry.addRecipe(new ItemStack(chest),
                "O O", "OOO", "OOO",
                'O', base);

        //Legs
        GameRegistry.addRecipe(new ItemStack(pants),
                "OOO", "O O", "O O",
                'O', base);

        //Boots
        GameRegistry.addRecipe(new ItemStack(boots),
                "O O", "O O",
                'O', base);
    }
}
