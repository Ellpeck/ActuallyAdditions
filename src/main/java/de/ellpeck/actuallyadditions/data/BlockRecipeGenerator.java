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
        //Battery Box
       ShapelessRecipeBuilder.shapelessRecipe(ActuallyBlocks.BATTERY_BOX.get())
                .addIngredient(ActuallyBlocks.ENERGIZER.get())
                .addIngredient(ActuallyBlocks.ENERVATOR.get())
                .addIngredient(ActuallyItems.COIL.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer, new ResourceLocation(ActuallyAdditions.MODID, "battery_box"));

        //Farmer
       ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.FARMER.get())
                .patternLine("ISI")
                .patternLine("SCS")
                .patternLine("ISI")
                .key('I', ActuallyBlocks.CRYSTAL_ENORI.get())
                .key('C', ActuallyBlocks.IRON_CASING.get())
                .key('S', Tags.Items.SEEDS)
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer, new ResourceLocation(ActuallyAdditions.MODID, "farmer"));

       //Empowerer
       ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.EMPOWERER.get())
               .patternLine(" R ")
               .patternLine(" B ")
               .patternLine("CDC")
               .key('R', ActuallyItems.RESTONIA_CRYSTAL.get())
               .key('B', ActuallyItems.BATTERY_DOUBLE.get())
               .key('C', ActuallyBlocks.IRON_CASING.get())
               .key('D', ActuallyBlocks.DISPLAY_STAND.get())
               .addCriterion("", hasItem(Items.AIR))
               .build(consumer,new ResourceLocation(ActuallyAdditions.MODID, "empowerer"));


    }

    @Override
    protected void saveRecipeAdvancement(DirectoryCache cache, JsonObject cache2, Path advancementJson) {
        //Nope...
    }
}
