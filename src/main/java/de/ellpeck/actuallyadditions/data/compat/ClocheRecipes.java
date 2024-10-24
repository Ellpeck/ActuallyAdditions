package de.ellpeck.actuallyadditions.data.compat;

import blusunrize.immersiveengineering.client.utils.ClocheRenderFunctions;
import blusunrize.immersiveengineering.data.recipes.builder.ClocheRecipeBuilder;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class ClocheRecipes {
    public static void buildRecipes(RecipeOutput output) {
        ClocheRecipeBuilder.builder()
                .output(ActuallyItems.CANOLA, 2)
                .output(ActuallyItems.CANOLA_SEEDS)
                .seed(ActuallyItems.CANOLA_SEEDS)
                .soil(ItemTags.DIRT)
                .setTime(800)
                .setRender(new ClocheRenderFunctions.RenderFunctionCrop(ActuallyBlocks.CANOLA.get()))
                .build(output, ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "compat/cloche_canola"));

        ClocheRecipeBuilder.builder()
                .output(Items.STRING, 2)
                .output(ActuallyItems.FLAX_SEEDS)
                .seed(ActuallyItems.FLAX_SEEDS)
                .soil(ItemTags.DIRT)
                .setTime(800)
                .setRender(new ClocheRenderFunctions.RenderFunctionCrop(ActuallyBlocks.FLAX.get()))
                .build(output, ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "compat/cloche_flax"));

        ClocheRecipeBuilder.builder()
                .output(ActuallyItems.COFFEE_BEANS, 2)
                .output(ActuallyItems.COFFEE_BEANS)
                .seed(ActuallyItems.COFFEE_BEANS)
                .soil(ItemTags.DIRT)
                .setTime(800)
                .setRender(new ClocheRenderFunctions.RenderFunctionCrop(ActuallyBlocks.COFFEE.get()))
                .build(output, ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "compat/cloche_coffee"));

        ClocheRecipeBuilder.builder()
                .output(ActuallyItems.RICE, 2)
                .output(ActuallyItems.RICE_SEEDS)
                .seed(ActuallyItems.RICE_SEEDS)
                .soil(ItemTags.DIRT)
                .setTime(800)
                .setRender(new ClocheRenderFunctions.RenderFunctionCrop(ActuallyBlocks.RICE.get()))
                .build(output, ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "compat/cloche_rice"));
    }
}
