/*
 * This file ("ToolCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public class ToolCrafting{

    public static ArrayList<IRecipe> recipesPaxels = new ArrayList<IRecipe>();

    public static void init(){

        if(ConfigCrafting.TOOL_EMERALD.isEnabled()){
            //Pickaxe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPickaxeEmerald),
                    "EEE", " S ", " S ",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Sword
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemSwordEmerald),
                    "E", "E", "S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Axe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemAxeEmerald),
                    "EE", "ES", " S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Shovel
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemShovelEmerald),
                    "E", "S", "S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Hoe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemHoeEmerald),
                    "EE", " S", " S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Helm
            GameRegistry.addRecipe(new ItemStack(InitItems.itemHelmEmerald),
                    "OOO", "O O",
                    'O', new ItemStack(Items.emerald));

            //Chest
            GameRegistry.addRecipe(new ItemStack(InitItems.itemChestEmerald),
                    "O O", "OOO", "OOO",
                    'O', new ItemStack(Items.emerald));

            //Legs
            GameRegistry.addRecipe(new ItemStack(InitItems.itemPantsEmerald),
                    "OOO", "O O", "O O",
                    'O', new ItemStack(Items.emerald));

            //Boots
            GameRegistry.addRecipe(new ItemStack(InitItems.itemBootsEmerald),
                    "O O", "O O",
                    'O', new ItemStack(Items.emerald));
        }

        if(ConfigCrafting.TOOL_QUARTZ.isEnabled()){
            //Pickaxe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPickaxeQuartz),
                    "EEE", " S ", " S ",
                    'E', "gemQuartzBlack",
                    'S', new ItemStack(Items.stick)));

            //Sword
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemSwordQuartz),
                    "E", "E", "S",
                    'E', "gemQuartzBlack",
                    'S', new ItemStack(Items.stick)));

            //Axe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemAxeQuartz),
                    "EE", "ES", " S",
                    'E', "gemQuartzBlack",
                    'S', new ItemStack(Items.stick)));

            //Shovel
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemShovelQuartz),
                    "E", "S", "S",
                    'E', "gemQuartzBlack",
                    'S', new ItemStack(Items.stick)));

            //Hoe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemHoeQuartz),
                    "EE", " S", " S",
                    'E', "gemQuartzBlack",
                    'S', new ItemStack(Items.stick)));

            //Helm
            GameRegistry.addRecipe(new ItemStack(InitItems.itemHelmQuartz),
                    "OOO", "O O",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));

            //Chest
            GameRegistry.addRecipe(new ItemStack(InitItems.itemChestQuartz),
                    "O O", "OOO", "OOO",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));

            //Legs
            GameRegistry.addRecipe(new ItemStack(InitItems.itemPantsQuartz),
                    "OOO", "O O", "O O",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));

            //Boots
            GameRegistry.addRecipe(new ItemStack(InitItems.itemBootsQuartz),
                    "O O", "O O",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));
        }

        if(ConfigCrafting.TOOL_OBSIDIAN.isEnabled()){
            //Pickaxe
            GameRegistry.addRecipe(new ItemStack(InitItems.itemPickaxeObsidian),
                    "EEE", " S ", " S ",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Sword
            GameRegistry.addRecipe(new ItemStack(InitItems.itemSwordObsidian),
                    "E", "E", "S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Axe
            GameRegistry.addRecipe(new ItemStack(InitItems.itemAxeObsidian),
                    "EE", "ES", " S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Shovel
            GameRegistry.addRecipe(new ItemStack(InitItems.itemShovelObsidian),
                    "E", "S", "S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Hoe
            GameRegistry.addRecipe(new ItemStack(InitItems.itemHoeObsidian),
                    "EE", " S", " S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Helm
            GameRegistry.addRecipe(new ItemStack(InitItems.itemHelmObsidian),
                    "OOO", "O O",
                    'O', new ItemStack(Blocks.obsidian));

            //Chest
            GameRegistry.addRecipe(new ItemStack(InitItems.itemChestObsidian),
                    "O O", "OOO", "OOO",
                    'O', new ItemStack(Blocks.obsidian));

            //Legs
            GameRegistry.addRecipe(new ItemStack(InitItems.itemPantsObsidian),
                    "OOO", "O O", "O O",
                    'O', new ItemStack(Blocks.obsidian));

            //Boots
            GameRegistry.addRecipe(new ItemStack(InitItems.itemBootsObsidian),
                    "O O", "O O",
                    'O', new ItemStack(Blocks.obsidian));
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
        }
    }

}
