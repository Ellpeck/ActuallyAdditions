/*
 * This file ("EmpowererHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public final class EmpowererHandler{

    public static final ArrayList<EmpowererRecipe> MAIN_PAGE_RECIPES = new ArrayList<EmpowererRecipe>();

    public static void init(){
        addCrystalEmpowering(TheCrystals.REDSTONE, new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.NETHERBRICK), new ItemStack(Items.REDSTONE), new ItemStack(Items.BRICK));
        addCrystalEmpowering(TheCrystals.LAPIS, new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage()), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD));
        addCrystalEmpowering(TheCrystals.DIAMOND, new ItemStack(Items.DYE, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Blocks.CLAY));
        addCrystalEmpowering(TheCrystals.IRON, new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage()), new ItemStack(Items.SNOWBALL), new ItemStack(Blocks.STONE_BUTTON), new ItemStack(Blocks.COBBLESTONE));

        addCrystalEmpowering(TheCrystals.COAL, new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()), new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.FLINT), new ItemStack(Blocks.STONE));
        addCrystalEmpowering(TheCrystals.COAL, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BLACK_DYE.ordinal()), new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.FLINT), new ItemStack(Blocks.STONE));

        List<ItemStack> balls = OreDictionary.getOres("slimeball");
        for(ItemStack ball : balls){
            addCrystalEmpowering(TheCrystals.EMERALD, new ItemStack(Items.DYE, 1, EnumDyeColor.LIME.getDyeDamage()), new ItemStack(Blocks.TALLGRASS, 1, 1), new ItemStack(Blocks.SAPLING), ball.copy());
        }
    }

    private static void addCrystalEmpowering(TheCrystals type, ItemStack modifier1, ItemStack modifier2, ItemStack modifier3, ItemStack modifier4){
        float[] color = type.conversionColorParticles;
        ActuallyAdditionsAPI.addEmpowererRecipe(new ItemStack(InitItems.itemCrystal, 1, type.ordinal()), new ItemStack(InitItems.itemCrystalEmpowered, 1, type.ordinal()), modifier1, modifier2, modifier3, modifier4, 5000, 50, color);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastEmpowererRecipe());
        ActuallyAdditionsAPI.addEmpowererRecipe(new ItemStack(InitBlocks.blockCrystal, 1, type.ordinal()), new ItemStack(InitBlocks.blockCrystalEmpowered, 1, type.ordinal()), modifier1, modifier2, modifier3, modifier4, 50000, 500, color);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastEmpowererRecipe());
    }
}
