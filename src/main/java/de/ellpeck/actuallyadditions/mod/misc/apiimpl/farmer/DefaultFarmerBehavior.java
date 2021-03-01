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
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultFarmerBehavior implements IFarmerBehavior {

    public static boolean defaultPlant(World world, BlockPos pos, BlockState toPlant, IFarmer farmer, int use) {
        if (toPlant != null) {
            BlockPos farmland = pos.down();
            Block farmlandBlock = world.getBlockState(farmland).getBlock();
            if (Tags.Blocks.DIRT.contains(farmlandBlock) || farmlandBlock == Blocks.GRASS) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                useHoeAt(world, farmland);
                world.playSound(null, farmland, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
        if (toPlant.isValidPosition(world, pos)) {
            world.setBlockState(pos, toPlant);
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
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof CropsBlock) {
                if (((CropsBlock) block).isMaxAge(state)) {
                    return this.doFarmerStuff(state, world, pos, farmer);
                }
            }
            // TODO: [port] come back and see what this is actually doing
            //            else if (CropsBlock.AGE.equals(block.getBlockState().getProperty("age"))) {
            //                if (state.get(BlockStateProperties.AGE_0_7) >= 7 && !(block instanceof StemBlock)) {
            //                    return this.doFarmerStuff(state, world, pos, farmer);
            //                }
            //            }
        }
        return FarmerResult.FAIL;
    }

    private FarmerResult doFarmerStuff(BlockState state, World world, BlockPos pos, IFarmer farmer) {
        List<ItemStack> seeds = new ArrayList<>();
        List<ItemStack> other = new ArrayList<>();
        NonNullList<ItemStack> drops = NonNullList.create();
        state.getBlock().getDrops(drops, world, pos, state, 0);
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

            world.playEvent(2001, pos, Block.getStateId(state));
            world.setBlockState(pos, Blocks.AIR.getDefaultState());

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
            Block block = Block.getBlockFromItem(item);
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

        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world);

        ItemStack itemstack = getHoeStack();

        if (!player.canPlayerEdit(pos.offset(Direction.UP), Direction.UP, itemstack)) {
            return ActionResultType.FAIL;
        } else {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(itemstack, player, world, pos);
            if (hook != 0) {
                return hook > 0
                    ? ActionResultType.SUCCESS
                    : ActionResultType.FAIL;
            }

            BlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (world.isAirBlock(pos.up())) {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                    return ActionResultType.SUCCESS;
                }

                if (block == Blocks.DIRT) {
                    switch (iblockstate.get(BlockDirt.VARIANT)) {
                        case DIRT:
                            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                            return ActionResultType.SUCCESS;
                        case COARSE_DIRT:
                            world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return ActionResultType.SUCCESS;
                        default:
                    }
                }
            }

            return ActionResultType.PASS;
        }
    }
}
