package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, ActuallyAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.WALLS).add(
            ActuallyBlocks.ETHETIC_WHITE_WALL.get(),
            ActuallyBlocks.ETHETIC_GREEN_WALL.get(),
            ActuallyBlocks.BLACK_QUARTZ_WALL.get(),
            ActuallyBlocks.SMOOTH_BLACK_QUARTZ_WALL.get(),
            ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.get(),
            ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.get(),
            ActuallyBlocks.BLACK_QUARTZ_BRICK_WALL.get()
        );

        tag(BlockTags.MINEABLE_WITH_AXE).add(
                ActuallyBlocks.FERMENTING_BARREL.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ActuallyBlocks.LAMP_WHITE.get(),
                ActuallyBlocks.LAMP_ORANGE.get(),
                ActuallyBlocks.LAMP_MAGENTA.get(),
                ActuallyBlocks.LAMP_LIGHT_BLUE.get(),
                ActuallyBlocks.LAMP_YELLOW.get(),
                ActuallyBlocks.LAMP_LIME.get(),
                ActuallyBlocks.LAMP_PINK.get(),
                ActuallyBlocks.LAMP_GRAY.get(),
                ActuallyBlocks.LAMP_LIGHT_GRAY.get(),
                ActuallyBlocks.LAMP_CYAN.get(),
                ActuallyBlocks.LAMP_PURPLE.get(),
                ActuallyBlocks.LAMP_BLUE.get(),
                ActuallyBlocks.LAMP_BROWN.get(),
                ActuallyBlocks.LAMP_GREEN.get(),
                ActuallyBlocks.LAMP_RED.get(),
                ActuallyBlocks.LAMP_BLACK.get(),
                ActuallyBlocks.LEAF_GENERATOR.get(),
                ActuallyBlocks.WOOD_CASING.get(),
                ActuallyBlocks.IRON_CASING.get(),
                ActuallyBlocks.ENDER_CASING.get(),
                ActuallyBlocks.LAVA_FACTORY_CASING.get(),
                ActuallyBlocks.BLACK_QUARTZ_ORE.get(),
                ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(),
                ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(),
                ActuallyBlocks.BLACK_QUARTZ.get(),
                ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(),
                ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(),
                ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(),
                ActuallyBlocks.BLACK_QUARTZ_BRICK.get(),
                ActuallyBlocks.FEEDER.get(),
                ActuallyBlocks.CRUSHER.get(),
                ActuallyBlocks.CRUSHER_DOUBLE.get(),
                ActuallyBlocks.ENERGIZER.get(),
                ActuallyBlocks.ENERVATOR.get(),
                ActuallyBlocks.LAVA_FACTORY_CONTROLLER.get(),
                ActuallyBlocks.LAMP_CONTROLLER.get(),
                ActuallyBlocks.CANOLA_PRESS.get(),
                ActuallyBlocks.OIL_GENERATOR.get(),
                ActuallyBlocks.COAL_GENERATOR.get(),
                ActuallyBlocks.XP_SOLIDIFIER.get(),
                ActuallyBlocks.PLACER.get(),
                ActuallyBlocks.BREAKER.get(),
                ActuallyBlocks.DROPPER.get(),
                ActuallyBlocks.FLUID_COLLECTOR.get(),
                ActuallyBlocks.FARMER.get(),
                ActuallyBlocks.BIOREACTOR.get(),
                ActuallyBlocks.VERTICAL_DIGGER.get(),
                ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get(),
                ActuallyBlocks.RANGED_COLLECTOR.get(),
                ActuallyBlocks.LONG_RANGE_BREAKER.get(),
                ActuallyBlocks.COFFEE_MACHINE.get(),
                ActuallyBlocks.POWERED_FURNACE.get(),
                ActuallyBlocks.ENORI_CRYSTAL.get(),
                ActuallyBlocks.RESTONIA_CRYSTAL.get(),
                ActuallyBlocks.PALIS_CRYSTAL.get(),
                ActuallyBlocks.DIAMATINE_CRYSTAL.get(),
                ActuallyBlocks.VOID_CRYSTAL.get(),
                ActuallyBlocks.EMERADIC_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_PALIS_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_VOID_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_EMERADIC_CRYSTAL.get(),
                ActuallyBlocks.DISPLAY_STAND.get(),
                ActuallyBlocks.EMPOWERER.get(),
                ActuallyBlocks.PLAYER_INTERFACE.get(),
                ActuallyBlocks.ITEM_INTERFACE.get(),
                ActuallyBlocks.ITEM_INTERFACE_HOPPING.get(),
                ActuallyBlocks.PHANTOM_ITEMFACE.get(),
                ActuallyBlocks.PHANTOM_PLACER.get(),
                ActuallyBlocks.PHANTOM_LIQUIFACE.get(),
                ActuallyBlocks.PHANTOM_ENERGYFACE.get(),
                ActuallyBlocks.PHANTOM_REDSTONEFACE.get(),
                ActuallyBlocks.PHANTOM_BREAKER.get(),
                ActuallyBlocks.PHANTOM_BOOSTER.get(),
                ActuallyBlocks.BATTERY_BOX.get(),
                ActuallyBlocks.FIREWORK_BOX.get(),
                ActuallyBlocks.SHOCK_SUPPRESSOR.get(),
                ActuallyBlocks.HEAT_COLLECTOR.get(),
                ActuallyBlocks.LASER_RELAY.get(),
                ActuallyBlocks.LASER_RELAY_ADVANCED.get(),
                ActuallyBlocks.LASER_RELAY_EXTREME.get(),
                ActuallyBlocks.LASER_RELAY_FLUIDS.get(),
                ActuallyBlocks.LASER_RELAY_ITEM.get(),
                ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED.get(),
                ActuallyBlocks.GREENHOUSE_GLASS.get()
        );

        tag(BlockTags.NEEDS_STONE_TOOL).add(
                ActuallyBlocks.WOOD_CASING.get(),
                ActuallyBlocks.IRON_CASING.get(),
                ActuallyBlocks.ENDER_CASING.get(),
                ActuallyBlocks.LAVA_FACTORY_CASING.get(),
                ActuallyBlocks.BLACK_QUARTZ_ORE.get(),
                ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(),
                ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(),
                ActuallyBlocks.BLACK_QUARTZ.get(),
                ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(),
                ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(),
                ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(),
                ActuallyBlocks.BLACK_QUARTZ_BRICK.get(),
                ActuallyBlocks.ENORI_CRYSTAL.get(),
                ActuallyBlocks.RESTONIA_CRYSTAL.get(),
                ActuallyBlocks.PALIS_CRYSTAL.get(),
                ActuallyBlocks.DIAMATINE_CRYSTAL.get(),
                ActuallyBlocks.VOID_CRYSTAL.get(),
                ActuallyBlocks.EMERADIC_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_PALIS_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_VOID_CRYSTAL.get(),
                ActuallyBlocks.EMPOWERED_EMERADIC_CRYSTAL.get()
        );

        this.tag(ActuallyTags.Blocks.MINEABLE_WITH_DRILL).addTags(
                BlockTags.MINEABLE_WITH_SHOVEL,
                BlockTags.MINEABLE_WITH_PICKAXE
        );

        this.tag(ActuallyTags.Blocks.MINEABLE_WITH_AIO).addTags(
                BlockTags.MINEABLE_WITH_AXE,
                BlockTags.MINEABLE_WITH_HOE,
                BlockTags.MINEABLE_WITH_PICKAXE,
                BlockTags.MINEABLE_WITH_SHOVEL
        );
    }

//    /**
//     * Resolves a Path for the location to save the given tag.
//     */
//    @Override
//    protected Path getPath(ResourceLocation id) {
//        return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
//    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return "Block Tags";
    }
}
