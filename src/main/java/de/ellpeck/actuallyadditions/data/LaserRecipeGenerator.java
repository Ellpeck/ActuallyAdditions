package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

public class LaserRecipeGenerator extends RecipeProvider {
    public LaserRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Laser " + super.getName();
    }

//    @Override //TODO: Flanks do your RecipeOutput wrapper thingy ;)
//    protected @Nullable CompletableFuture<?> saveAdvancement(CachedOutput stack, FinishedRecipe finishedRecipe, JsonObject advancementJson) {
//        return null;
//        //Nope... maybe later...
//    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        //Crystal Blocks
        laserCrystalizeRecipe(consumer, ActuallyBlocks.RESTONIA_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_REDSTONE, 400);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.PALIS_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_LAPIS, 400);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.DIAMATINE_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_DIAMOND, 600);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.EMERADIC_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_EMERALD, 1000);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.VOID_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_COAL, 600);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.ENORI_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_IRON, 800);

        //Crystal Items
        laserCrystalizeRecipe(consumer, ActuallyItems.RESTONIA_CRYSTAL.get(), Tags.Items.DUSTS_REDSTONE, 40);
        laserCrystalizeRecipe(consumer, ActuallyItems.PALIS_CRYSTAL.get(), Tags.Items.GEMS_LAPIS, 40);
        laserCrystalizeRecipe(consumer, ActuallyItems.DIAMATINE_CRYSTAL.get(), Tags.Items.GEMS_DIAMOND, 60);
        laserCrystalizeRecipe(consumer, ActuallyItems.EMERADIC_CRYSTAL.get(), Tags.Items.GEMS_EMERALD, 100);
        laserCrystalizeRecipe(consumer, ActuallyItems.VOID_CRYSTAL.get(), ItemTags.COALS, 60);
        laserCrystalizeRecipe(consumer, ActuallyItems.ENORI_CRYSTAL.get(), Tags.Items.INGOTS_IRON, 80);

        //Lenses
        laserRecipe(consumer, ActuallyItems.LENS_OF_COLOR.get(), ActuallyItems.LENS.get(), 5000);
        laserRecipe(consumer, ActuallyItems.LENS_OF_DETONATION.get(), ActuallyItems.LENS_OF_COLOR.get(), 5000);
        laserRecipe(consumer, ActuallyItems.LENS_OF_CERTAIN_DEATH.get(), ActuallyItems.LENS_OF_DETONATION.get(), 5000);
        laserRecipe(consumer, ActuallyItems.LENS.get(), ActuallyItems.LENS_OF_CERTAIN_DEATH.get(), 5000);

        //Relays
        laserRecipe(consumer, ActuallyBlocks.LASER_RELAY_FLUIDS.getItem(), ActuallyBlocks.LASER_RELAY.getItem(), 2000);
        laserRecipe(consumer, ActuallyBlocks.LASER_RELAY_ITEM.getItem(), ActuallyBlocks.LASER_RELAY_FLUIDS.getItem(), 2000);
        laserRecipe(consumer, ActuallyBlocks.LASER_RELAY.getItem(), ActuallyBlocks.LASER_RELAY_ITEM.getItem(), 2000);

        //Misc
        laserRecipe(consumer, Items.SOUL_SAND, Tags.Items.SAND, 20000);
        laserRecipe(consumer, Items.LEATHER, Items.ROTTEN_FLESH, 20000);
        laserRecipe(consumer, Items.NETHER_WART, Items.RED_MUSHROOM, 150000);
        laserRecipe(consumer, Items.PRISMARINE_SHARD, Items.QUARTZ, 30000);
        laserRecipe(consumer, ActuallyItems.CRYSTALLIZED_CANOLA_SEED.get(), ActuallyItems.CANOLA_SEEDS.get(), 2000);
        laserRecipe(consumer, ActuallyBlocks.ETHETIC_WHITE_BLOCK.getItem(), Items.QUARTZ_BLOCK, 10);
        laserRecipe(consumer, ActuallyBlocks.ETHETIC_GREEN_BLOCK.getItem(), Items.CHISELED_QUARTZ_BLOCK, 10);

    }

    private void laserRecipe(RecipeOutput consumer, ItemLike output, Ingredient input, int energy) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "laser/" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), input, energy), null);
    }
    private void laserRecipe(RecipeOutput consumer, ItemLike output, TagKey<Item> input, int energy) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "laser/" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
    private void laserRecipe(RecipeOutput consumer, ItemLike output, ItemLike input, int energy) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "laser/" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);;
    }
    private void laserCrystalizeRecipe(RecipeOutput consumer, ItemLike output, Ingredient input, int energy) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "laser/crystalize_" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), input, energy), null);
    }
    private void laserCrystalizeRecipe(RecipeOutput consumer, ItemLike output, TagKey<Item> input, int energy) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "laser/crystalize_" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
    private void laserCrystalizeRecipe(RecipeOutput consumer, ItemLike output, ItemLike input, int energy) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, "laser/crystalize_" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
}
