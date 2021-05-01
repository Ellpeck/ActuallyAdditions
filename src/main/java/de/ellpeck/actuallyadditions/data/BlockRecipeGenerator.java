package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.nio.file.Path;
import java.util.function.Consumer;

public class BlockRecipeGenerator extends RecipeProvider {
    public BlockRecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        //        //Battery Box
        //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockBatteryBox), new ItemStack(InitBlocks.blockEnergizer), new ItemStack(InitBlocks.blockEnervator), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()));
        ShapelessRecipeBuilder.shapelessRecipe(ActuallyBlocks.blockBatteryBox.get())
                .addIngredient(ActuallyBlocks.blockEnergizer.get())
                .addIngredient(ActuallyBlocks.blockEnervator.get())
                .addIngredient(ActuallyItems.itemCoil.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer, new ResourceLocation(ActuallyAdditions.MODID, "battery_box"));

        //        //Farmer
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFarmer), "ISI", "SCS", "ISI", 'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()), 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'S', new ItemStack(Items.WHEAT_SEEDS));
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.blockFarmer.get())
                .patternLine("ISI")
                .patternLine("SCS")
                .patternLine("ISI")
                .key('I', ActuallyBlocks.CRYSTAL_ENORI.get())
                .key('C', ActuallyBlocks.blockIronCasing.get())
                .key('S', Tags.Items.SEEDS)
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer, new ResourceLocation(ActuallyAdditions.MODID, "farmer"));

    }

    @Override
    protected void saveRecipeAdvancement(DirectoryCache cache, JsonObject cache2, Path advancementJson) {
        //Nope...
    }
}
