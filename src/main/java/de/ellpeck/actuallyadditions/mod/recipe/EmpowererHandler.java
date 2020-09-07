package de.ellpeck.actuallyadditions.mod.recipe;

import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

public final class EmpowererHandler {

    public static final ArrayList<EmpowererRecipe> MAIN_PAGE_RECIPES = new ArrayList<>();
    public static EmpowererRecipe recipeEmpoweredCanolaSeed;

    public static void init() {
        addCrystalEmpowering(TheCrystals.REDSTONE, "dyeRed", Ingredient.fromItems(Items.NETHERBRICK), Ingredient.fromItems(Items.REDSTONE), Ingredient.fromItems(Items.BRICK));
        addCrystalEmpowering(TheCrystals.LAPIS, "dyeCyan", Ingredient.fromItems(Items.PRISMARINE_SHARD), Ingredient.fromItems(Items.PRISMARINE_SHARD), Ingredient.fromItems(Items.PRISMARINE_SHARD));
        addCrystalEmpowering(TheCrystals.DIAMOND, "dyeLightBlue", Ingredient.fromItems(Items.CLAY_BALL), Ingredient.fromItems(Items.CLAY_BALL), fromBlock(Blocks.CLAY));
        addCrystalEmpowering(TheCrystals.IRON, "dyeGray", Ingredient.fromItems(Items.SNOWBALL), fromBlock(Blocks.STONE_BUTTON), fromBlock(Blocks.COBBLESTONE));

        addCrystalEmpowering(TheCrystals.COAL, "dyeBlack", igd(new ItemStack(Items.COAL, 1, 1)), Ingredient.fromItems(Items.FLINT), fromBlock(Blocks.STONE));

        List<ItemStack> balls = OreDictionary.getOres("slimeball");
        for (ItemStack ball : balls) {
            addCrystalEmpowering(TheCrystals.EMERALD, "dyeLime", igd(new ItemStack(Blocks.TALLGRASS, 1, 1)), igd(new ItemStack(Blocks.SAPLING)), igd(ball.copy()));
        }

        Ingredient seed = Ingredient.fromItems(InitItems.itemCanolaSeed);
        ActuallyAdditionsAPI.addEmpowererRecipe(Ingredient.fromStacks(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CRYSTALLIZED_CANOLA_SEED.ordinal())), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.EMPOWERED_CANOLA_SEED.ordinal()), seed, seed, seed, seed, 1000, 30, new float[] { 1F, 91F / 255F, 76F / 255F });
        recipeEmpoweredCanolaSeed = RecipeUtil.lastEmpowererRecipe();
    }

    private static void addCrystalEmpowering(TheCrystals type, String dye, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3) {
        float[] color = type.conversionColorParticles;

        ActuallyAdditionsAPI.addEmpowererRecipe(Ingredient.fromStacks(new ItemStack(InitItems.itemCrystal, 1, type.ordinal())), new ItemStack(InitItems.itemCrystalEmpowered, 1, type.ordinal()), new OreIngredient(dye), modifier1, modifier2, modifier3, 5000, 50, color);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastEmpowererRecipe());
        ActuallyAdditionsAPI.addEmpowererRecipe(Ingredient.fromStacks(new ItemStack(InitBlocks.blockCrystal, 1, type.ordinal())), new ItemStack(InitBlocks.blockCrystalEmpowered, 1, type.ordinal()), new OreIngredient(dye), modifier1, modifier2, modifier3, 50000, 500, color);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastEmpowererRecipe());
    }

    private static Ingredient igd(ItemStack s) {
        return Ingredient.fromStacks(s);
    }

    private static Ingredient fromBlock(Block b) {
        return Ingredient.fromItems(Item.getItemFromBlock(b));
    }
}
