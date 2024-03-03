/*
 * This file ("FluidAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.fluids;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluidAA implements Supplier<Fluid> {
    private String name;
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private RegistryObject<FluidType> fluidType;
    private RegistryObject<ForgeFlowingFluid> source;
    private RegistryObject<ForgeFlowingFluid> flowing;
    private RegistryObject<LiquidBlock> fluidBlock;
    private RegistryObject<Item> bucket;

    public String getName() {
        return name;
    }

    public static ForgeFlowingFluid.Properties createProperties(Supplier<FluidType> type, Supplier<ForgeFlowingFluid> still, Supplier<ForgeFlowingFluid> flowing,
                                                               RegistryObject<Item> bucket, Supplier<LiquidBlock> block) {
        return new ForgeFlowingFluid.Properties(type, still, flowing)
                .bucket(bucket).block(block);
    }

    public FluidAA(String fluidName, String textureName) {
        this.name = fluidName;
        this.stillTexture = new ResourceLocation(ActuallyAdditions.MODID,textureName + "_still");
        this.flowingTexture = new ResourceLocation(ActuallyAdditions.MODID,textureName + "_flowing");
        this.fluidType = InitFluids.FLUID_TYPES.register(name, () -> new FluidType(createTypeProperties()) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {

                    @Override
                    public ResourceLocation getStillTexture() {
                        return FluidAA.this.stillTexture;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return FluidAA.this.flowingTexture;
                    }
                });
            }
        });

        source = InitFluids.FLUIDS.register(name, () ->  new ForgeFlowingFluid.Source(createProperties(fluidType, source, flowing, bucket, fluidBlock)));
        flowing = InitFluids.FLUIDS.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(createProperties(fluidType, source, flowing, bucket, fluidBlock)));
        fluidBlock = ActuallyBlocks.BLOCKS.register(name, () -> new LiquidBlock(source, BlockBehaviour.Properties.copy(Blocks.WATER)));
        bucket = ActuallyItems.ITEMS.register(name + "_bucket", () -> new BucketItem(source, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    }

    public FluidType getFluidType() {
        return fluidType.get();
    }

    @Override
    public Fluid get() {
        return source.get();
    }

    public Block getBlock() {
        return fluidBlock.get();
    }

    public Item getBucket() {
        return bucket.get();
    }

    public FlowingFluid getFlowing() {
        return flowing.get();
    }

    public static FluidType.Properties createTypeProperties() {
        return FluidType.Properties.create()
                .canSwim(true)
                .canDrown(true)
                .pathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
    }
}
