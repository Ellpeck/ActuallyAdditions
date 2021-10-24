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
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class FluidAA implements Supplier<Fluid> {
    private String name;
    private RegistryObject<ForgeFlowingFluid> source;
    private RegistryObject<ForgeFlowingFluid> flowing;
    private RegistryObject<FlowingFluidBlock> fluidBlock;
    private RegistryObject<Item> bucket;

    public String getName() {
        return name;
    }

    public FluidAA(String fluidName, String textureName) {
        name = fluidName;
        ForgeFlowingFluid.Properties props = makeProperties(textureName, fluidBlock, source, flowing, bucket);
        source = InitFluids.FLUIDS.register(name, () ->  new ForgeFlowingFluid.Source(props));
        flowing = InitFluids.FLUIDS.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(props));
        fluidBlock = ActuallyBlocks.BLOCKS.register(name, () -> new FlowingFluidBlock(source, AbstractBlock.Properties.of(Material.WATER)));
        bucket = ActuallyItems.ITEMS.register(name + "_bucket", () -> new BucketItem(source.get(), new Item.Properties().craftRemainder(Items.BUCKET).tab(ActuallyAdditions.GROUP).stacksTo(1)));
    }

    public static ForgeFlowingFluid.Properties makeProperties(String texture, Supplier<FlowingFluidBlock> blockSupplier, Supplier<ForgeFlowingFluid> stillSupplier, Supplier<ForgeFlowingFluid> flowingSupplier, Supplier<Item> bucketSupplier) {
        return new ForgeFlowingFluid.Properties(stillSupplier, flowingSupplier, FluidAttributes.builder(new ResourceLocation(ActuallyAdditions.MODID,texture), new ResourceLocation(ActuallyAdditions.MODID, texture))).bucket(bucketSupplier).block(blockSupplier);
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
}
