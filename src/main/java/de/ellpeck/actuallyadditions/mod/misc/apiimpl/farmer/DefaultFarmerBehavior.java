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

import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayerFactory;

public class DefaultFarmerBehavior implements IFarmerBehavior {

    public static boolean defaultPlant(World world, BlockPos pos, IBlockState toPlant, IFarmer farmer, int use) {
        if (toPlant != null) {
            BlockPos farmland = pos.down();
            Block farmlandBlock = world.getBlockState(farmland).getBlock();
            if (farmlandBlock instanceof BlockDirt || farmlandBlock instanceof BlockGrass) {
                world.setBlockToAir(pos);
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

    private static boolean tryPlant(IBlockState toPlant, World world, BlockPos pos) {
        if (toPlant.getBlock().canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, toPlant);
            return true;
        }
        return false;
    }

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 350;
        if (farmer.getEnergy() >= use * 2) {
            if (defaultPlant(world, pos, this.getPlantablePlantFromStack(seed, world, pos), farmer, use)) return FarmerResult.SUCCESS;
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof BlockCrops) {
                if (((BlockCrops) block).isMaxAge(state)) { return doFarmerStuff(state, world, pos, farmer); }
            } else if ((BlockCrops.AGE).equals(block.getBlockState().getProperty("age"))) {
                if (state.getValue(BlockCrops.AGE) >= 7 && !(block instanceof BlockStem)) return doFarmerStuff(state, world, pos, farmer);
            }
        }
        return FarmerResult.FAIL;
    }

    private FarmerResult doFarmerStuff(IBlockState state, World world, BlockPos pos, IFarmer farmer) {
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
            world.setBlockToAir(pos);

            farmer.extractEnergy(250);
            return FarmerResult.SUCCESS;
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    private IBlockState getPlantablePlantFromStack(ItemStack stack, World world, BlockPos pos) {
        if (StackUtil.isValid(stack)) {
            IPlantable plantable = this.getPlantableFromStack(stack);
            if (plantable != null) {
                IBlockState state = plantable.getPlant(world, pos);
                if (state != null && state.getBlock() instanceof IGrowable) return state;
            }
        }
        return null;
    }

    private IPlantable getPlantableFromStack(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IPlantable) {
            return (IPlantable) item;
        } else if (item instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(item);
            if (block instanceof IPlantable) return (IPlantable) block;
        }
        return null;
    }

    private static ItemStack hoe = ItemStack.EMPTY;

    private static ItemStack getHoeStack() {
        if (hoe.isEmpty()) hoe = new ItemStack(Items.DIAMOND_HOE);
        return hoe;
    }

    public static EnumActionResult useHoeAt(World world, BlockPos pos) {

        EntityPlayer player = FakePlayerFactory.getMinecraft((WorldServer) world);

        ItemStack itemstack = getHoeStack();

        if (!player.canPlayerEdit(pos.offset(EnumFacing.UP), EnumFacing.UP, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(itemstack, player, world, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (world.isAirBlock(pos.up())) {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT) {
                    switch (iblockstate.getValue(BlockDirt.VARIANT)) {
                    case DIRT:
                        world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                        return EnumActionResult.SUCCESS;
                    case COARSE_DIRT:
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                        return EnumActionResult.SUCCESS;
                    default:
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }
}
