package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.recipe.CrusherRecipeAutoRegistry;
import ellpeck.actuallyadditions.recipe.CrusherRecipeAutoRegistry.SearchCase;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CrusherCrafting{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crusher Recipes...");

        if(ConfigCrafting.REDSTONE.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10));
        if(ConfigCrafting.LAPIS.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(Items.dye, 12, 4));
        if(ConfigCrafting.COAL.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()));
        if(ConfigCrafting.COAL_ORE.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, 3));
        if(ConfigCrafting.COAL_BLOCKS.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.coal_block), new ItemStack(Items.coal, 9));

        if(ConfigCrafting.COBBLESTONE.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
        if(ConfigCrafting.GRAVEL.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
        if(ConfigCrafting.STONE.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone));
        if(ConfigCrafting.RICE_SUGAR.isEnabled()) CrusherRecipeManualRegistry.registerRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.sugar, 2));

        if(ConfigCrafting.NICKEL.isEnabled()) CrusherRecipeManualRegistry.registerRecipe("oreNickel", "dustNickel", "dustPlatinum", 15, 2, 1);
        if(ConfigCrafting.IRON.isEnabled()) CrusherRecipeManualRegistry.registerRecipe("oreIron", "dustIron", "dustGold", 20, 2, 1);

        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("oreNether", 6));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("orePoor", 4, "nugget"));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("denseore", 8));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("gem", 1));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("ingot", 1));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("ore", 2));

        CrusherRecipeAutoRegistry.registerFinally();
    }
}
