/*
 * This file ("ToolCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public final class ToolCrafting{

    public static final ArrayList<IRecipe> RECIPES_PAXELS = new ArrayList<IRecipe>();

    public static void init(){

        addToolAndArmorRecipes(new ItemStack(Items.EMERALD), InitItems.itemPickaxeEmerald, InitItems.itemSwordEmerald, InitItems.itemAxeEmerald, InitItems.itemShovelEmerald, InitItems.itemHoeEmerald, InitItems.itemHelmEmerald, InitItems.itemChestEmerald, InitItems.itemPantsEmerald, InitItems.itemBootsEmerald);
        addToolAndArmorRecipes(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), InitItems.itemPickaxeQuartz, InitItems.itemSwordQuartz, InitItems.itemAxeQuartz, InitItems.itemShovelQuartz, InitItems.itemHoeQuartz, InitItems.itemHelmQuartz, InitItems.itemChestQuartz, InitItems.itemPantsQuartz, InitItems.itemBootsQuartz);
        addToolAndArmorRecipes(new ItemStack(Blocks.OBSIDIAN), InitItems.itemPickaxeObsidian, InitItems.itemSwordObsidian, InitItems.itemAxeObsidian, InitItems.itemShovelObsidian, InitItems.itemHoeObsidian, InitItems.itemHelmObsidian, InitItems.itemChestObsidian, InitItems.itemPantsObsidian, InitItems.itemBootsObsidian);

        addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), InitItems.itemPickaxeCrystalRed, InitItems.itemSwordCrystalRed, InitItems.itemAxeCrystalRed, InitItems.itemShovelCrystalRed, InitItems.itemHoeCrystalRed, InitItems.itemHelmCrystalRed, InitItems.itemChestCrystalRed, InitItems.itemPantsCrystalRed, InitItems.itemBootsCrystalRed);
        addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), InitItems.itemPickaxeCrystalGreen, InitItems.itemSwordCrystalGreen, InitItems.itemAxeCrystalGreen, InitItems.itemShovelCrystalGreen, InitItems.itemHoeCrystalGreen, InitItems.itemHelmCrystalGreen, InitItems.itemChestCrystalGreen, InitItems.itemPantsCrystalGreen, InitItems.itemBootsCrystalGreen);
        addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), InitItems.itemPickaxeCrystalWhite, InitItems.itemSwordCrystalWhite, InitItems.itemAxeCrystalWhite, InitItems.itemShovelCrystalWhite, InitItems.itemHoeCrystalWhite, InitItems.itemHelmCrystalWhite, InitItems.itemChestCrystalWhite, InitItems.itemPantsCrystalWhite, InitItems.itemBootsCrystalWhite);
        addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), InitItems.itemPickaxeCrystalLightBlue, InitItems.itemSwordCrystalLightBlue, InitItems.itemAxeCrystalLightBlue, InitItems.itemShovelCrystalLightBlue, InitItems.itemHoeCrystalLightBlue, InitItems.itemHelmCrystalLightBlue, InitItems.itemChestCrystalLightBlue, InitItems.itemPantsCrystalLightBlue, InitItems.itemBootsCrystalLightBlue);
        addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), InitItems.itemPickaxeCrystalBlue, InitItems.itemSwordCrystalBlue, InitItems.itemAxeCrystalBlue, InitItems.itemShovelCrystalBlue, InitItems.itemHoeCrystalBlue, InitItems.itemHelmCrystalBlue, InitItems.itemChestCrystalBlue, InitItems.itemPantsCrystalBlue, InitItems.itemBootsCrystalBlue);
        addToolAndArmorRecipes(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), InitItems.itemPickaxeCrystalBlack, InitItems.itemSwordCrystalBlack, InitItems.itemAxeCrystalBlack, InitItems.itemShovelCrystalBlack, InitItems.itemHoeCrystalBlack, InitItems.itemHelmCrystalBlack, InitItems.itemChestCrystalBlack, InitItems.itemPantsCrystalBlack, InitItems.itemBootsCrystalBlack);

        //Paxels
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.woodenPaxel),
                new ItemStack(Items.WOODEN_AXE),
                new ItemStack(Items.WOODEN_PICKAXE),
                new ItemStack(Items.WOODEN_SHOVEL),
                new ItemStack(Items.WOODEN_SWORD),
                new ItemStack(Items.WOODEN_HOE)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.stonePaxel),
                new ItemStack(Items.STONE_AXE),
                new ItemStack(Items.STONE_PICKAXE),
                new ItemStack(Items.STONE_SHOVEL),
                new ItemStack(Items.STONE_SWORD),
                new ItemStack(Items.STONE_HOE)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.ironPaxel),
                new ItemStack(Items.IRON_AXE),
                new ItemStack(Items.IRON_PICKAXE),
                new ItemStack(Items.IRON_SHOVEL),
                new ItemStack(Items.IRON_SWORD),
                new ItemStack(Items.IRON_HOE)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.goldPaxel),
                new ItemStack(Items.GOLDEN_AXE),
                new ItemStack(Items.GOLDEN_PICKAXE),
                new ItemStack(Items.GOLDEN_SHOVEL),
                new ItemStack(Items.GOLDEN_SWORD),
                new ItemStack(Items.GOLDEN_HOE)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.diamondPaxel),
                new ItemStack(Items.DIAMOND_AXE),
                new ItemStack(Items.DIAMOND_PICKAXE),
                new ItemStack(Items.DIAMOND_SHOVEL),
                new ItemStack(Items.DIAMOND_SWORD),
                new ItemStack(Items.DIAMOND_HOE)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.emeraldPaxel),
                new ItemStack(InitItems.itemAxeEmerald),
                new ItemStack(InitItems.itemPickaxeEmerald),
                new ItemStack(InitItems.itemSwordEmerald),
                new ItemStack(InitItems.itemShovelEmerald),
                new ItemStack(InitItems.itemHoeEmerald)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.obsidianPaxel),
                new ItemStack(InitItems.itemAxeObsidian),
                new ItemStack(InitItems.itemPickaxeObsidian),
                new ItemStack(InitItems.itemSwordObsidian),
                new ItemStack(InitItems.itemShovelObsidian),
                new ItemStack(InitItems.itemHoeObsidian)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.quartzPaxel),
                new ItemStack(InitItems.itemAxeQuartz),
                new ItemStack(InitItems.itemPickaxeQuartz),
                new ItemStack(InitItems.itemSwordQuartz),
                new ItemStack(InitItems.itemShovelQuartz),
                new ItemStack(InitItems.itemHoeQuartz)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalRed),
                new ItemStack(InitItems.itemAxeCrystalRed),
                new ItemStack(InitItems.itemPickaxeCrystalRed),
                new ItemStack(InitItems.itemSwordCrystalRed),
                new ItemStack(InitItems.itemShovelCrystalRed),
                new ItemStack(InitItems.itemHoeCrystalRed)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalGreen),
                new ItemStack(InitItems.itemAxeCrystalGreen),
                new ItemStack(InitItems.itemPickaxeCrystalGreen),
                new ItemStack(InitItems.itemSwordCrystalGreen),
                new ItemStack(InitItems.itemShovelCrystalGreen),
                new ItemStack(InitItems.itemHoeCrystalGreen)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalBlue),
                new ItemStack(InitItems.itemAxeCrystalBlue),
                new ItemStack(InitItems.itemPickaxeCrystalBlue),
                new ItemStack(InitItems.itemSwordCrystalBlue),
                new ItemStack(InitItems.itemShovelCrystalBlue),
                new ItemStack(InitItems.itemHoeCrystalBlue)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalLightBlue),
                new ItemStack(InitItems.itemAxeCrystalLightBlue),
                new ItemStack(InitItems.itemPickaxeCrystalLightBlue),
                new ItemStack(InitItems.itemSwordCrystalLightBlue),
                new ItemStack(InitItems.itemShovelCrystalLightBlue),
                new ItemStack(InitItems.itemHoeCrystalLightBlue)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalBlack),
                new ItemStack(InitItems.itemAxeCrystalBlack),
                new ItemStack(InitItems.itemPickaxeCrystalBlack),
                new ItemStack(InitItems.itemSwordCrystalBlack),
                new ItemStack(InitItems.itemShovelCrystalBlack),
                new ItemStack(InitItems.itemHoeCrystalBlack)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemPaxelCrystalWhite),
                new ItemStack(InitItems.itemAxeCrystalWhite),
                new ItemStack(InitItems.itemPickaxeCrystalWhite),
                new ItemStack(InitItems.itemSwordCrystalWhite),
                new ItemStack(InitItems.itemShovelCrystalWhite),
                new ItemStack(InitItems.itemHoeCrystalWhite)));
        RECIPES_PAXELS.add(RecipeUtil.lastIRecipe());
    }

    public static void addToolAndArmorRecipes(ItemStack base, Item pickaxe, Item sword, Item axe, Item shovel, Item hoe, Item helm, Item chest, Item pants, Item boots){
        //Pickaxe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxe),
                "EEE", " S ", " S ",
                'E', base,
                'S', new ItemStack(Items.STICK)));

        //Sword
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sword),
                "E", "E", "S",
                'E', base,
                'S', new ItemStack(Items.STICK)));

        //Axe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axe),
                "EE", "ES", " S",
                'E', base,
                'S', new ItemStack(Items.STICK)));

        //Shovel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovel),
                "E", "S", "S",
                'E', base,
                'S', new ItemStack(Items.STICK)));

        //Hoe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoe),
                "EE", " S", " S",
                'E', base,
                'S', new ItemStack(Items.STICK)));

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
