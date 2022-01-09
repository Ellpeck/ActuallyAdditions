/*
 * This file ("DefaultFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultFarmerBehavior implements IFarmerBehavior {

    public static boolean defaultPlant(World world, BlockPos pos, BlockState toPlant, IFarmer farmer, int use) {
        if (toPlant != null) {
            BlockPos farmland = pos.below();
            Block farmlandBlock = world.getBlockState(farmland).getBlock();
            if (Tags.Blocks.DIRT.contains(farmlandBlock) || farmlandBlock == Blocks.GRASS) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                useHoeAt(world, farmland);
                world.playSound(null, farmland, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                farmer.extractEnergy(use);
            }

            if (tryPlant(toPlant, world, pos)) {
                farmer.extractEnergy(use);
                return true;
            }
        }
        return false;
    }

    private static boolean tryPlant(BlockState toPlant, World world, BlockPos pos) {
        if (toPlant.canSurvive(world, pos)) {
            world.setBlockAndUpdate(pos, toPlant);
            return true;
        }
        return false;
    }

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 350;
        if (farmer.getEnergy() >= use * 2) {
            if (defaultPlant(world, pos, this.getPlantablePlantFromStack(seed, world, pos), farmer, use)) {
                return FarmerResult.SUCCESS;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(ServerWorld world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof CropsBlock) {
                if (((CropsBlock) block).isMaxAge(state)) {
                    return this.doFarmerStuff(state, world, pos, farmer);
                }
            }
            else if (state.hasProperty(BlockStateProperties.AGE_7)) {
                if (state.getValue(BlockStateProperties.AGE_7) >= 7 && !(block instanceof StemBlock)) {
                    return this.doFarmerStuff(state, world, pos, farmer);
                }
            }
        }
        return FarmerResult.FAIL;
    }

    private FarmerResult doFarmerStuff(BlockState state, ServerWorld world, BlockPos pos, IFarmer farmer) {
        List<ItemStack> seeds = new ArrayList<>();
        List<ItemStack> other = new ArrayList<>();
        List<ItemStack> drops = state.getDrops(new LootContext.Builder(world)
            .withParameter(LootParameters.ORIGIN, new Vector3d(pos.getX(), pos.getY(), pos.getZ()))
            .withParameter(LootParameters.TOOL, ItemStack.EMPTY));
        if (drops.isEmpty())
            return FarmerResult.FAIL;
        for (ItemStack stack : drops) {
            if (this.getPlantableFromStack(stack) != null) {
                seeds.add(stack);
            } else {
                other.add(stack);
            }
        }

        boolean addSeeds = true;
        if (!farmer.canAddToSeeds(seeds)) {
            other.addAll(seeds);
            addSeeds = false;
        }

        if (farmer.canAddToOutput(other)) {
            farmer.addToOutput(other);

            if (addSeeds) {
                farmer.addToSeeds(seeds);
            }

            world.levelEvent(2001, pos, Block.getId(state));
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            farmer.extractEnergy(250);
            return FarmerResult.SUCCESS;
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    private BlockState getPlantablePlantFromStack(ItemStack stack, World world, BlockPos pos) {
        if (StackUtil.isValid(stack)) {
            IPlantable plantable = this.getPlantableFromStack(stack);
            if (plantable != null) {
                BlockState state = plantable.getPlant(world, pos);
                if (state != null && state.getBlock() instanceof IGrowable) {
                    return state;
                }
            }
        }
        return null;
    }

    private IPlantable getPlantableFromStack(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IPlantable) {
            return (IPlantable) item;
        } else if (item instanceof BlockItem) {
            Block block = Block.byItem(item);
            if (block instanceof IPlantable) {
                return (IPlantable) block;
            }
        }
        return null;
    }

    private static ItemStack hoe = ItemStack.EMPTY;

    private static ItemStack getHoeStack() {
        if (hoe.isEmpty()) {
            hoe = new ItemStack(Items.DIAMOND_HOE);
        }
        return hoe;
    }

    public static ActionResultType useHoeAt(World world, BlockPos pos) {

        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world); //TODO we need our own fakeplayer for the mod.

        ItemStack itemstack = getHoeStack();

        if (!player.mayUseItemAt(pos.relative(Direction.UP), Direction.UP, itemstack)) {
            return ActionResultType.FAIL;
        } else {
            ItemUseContext dummyContext = new ItemUseContext(world, player, Hand.MAIN_HAND, itemstack, new BlockRayTraceResult(new Vector3d(0.5, 0.5, 0.5), Direction.UP, pos, false));
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(dummyContext);
            if (hook != 0) {
                return hook > 0
                    ? ActionResultType.SUCCESS
                    : ActionResultType.FAIL;
            }

            if (world.isEmptyBlock(pos.above())) {
                Block block = world.getBlockState(pos).getBlock();
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                    world.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
                    return ActionResultType.SUCCESS;
                }

                if (Tags.Blocks.DIRT.contains(block)) {
                    world.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.PASS;
        }
    }
}
