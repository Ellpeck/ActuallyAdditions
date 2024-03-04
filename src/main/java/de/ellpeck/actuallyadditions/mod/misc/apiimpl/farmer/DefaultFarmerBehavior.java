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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultFarmerBehavior implements IFarmerBehavior {

    public static boolean defaultPlant(Level world, BlockPos pos, BlockState toPlant, IFarmer farmer, int use) {
        if (toPlant != null) {
            BlockPos farmland = pos.below();
            BlockState farmlandState = world.getBlockState(farmland);
            if (farmlandState.is(BlockTags.DIRT) || farmlandState.is(Blocks.GRASS_BLOCK)) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                useHoeAt(world, farmland);
                world.playSound(null, farmland, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                farmer.extractEnergy(use);
            }

            if (tryPlant(toPlant, world, pos)) {
                farmer.extractEnergy(use);
                return true;
            }
        }
        return false;
    }

    private static boolean tryPlant(BlockState toPlant, Level world, BlockPos pos) {
        if (toPlant.canSurvive(world, pos)) {
            world.setBlockAndUpdate(pos, toPlant);
            return true;
        }
        return false;
    }

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, Level world, BlockPos pos, IFarmer farmer) {
        int use = 350;
        if (farmer.getEnergy() >= use * 2) {
            if (defaultPlant(world, pos, this.getPlantablePlantFromStack(seed, world, pos), farmer, use)) {
                return FarmerResult.SUCCESS;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(ServerLevel world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof CropBlock) {
                if (((CropBlock) block).isMaxAge(state)) {
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

    private FarmerResult doFarmerStuff(BlockState state, ServerLevel serverLevel, BlockPos pos, IFarmer farmer) {
        List<ItemStack> seeds = new ArrayList<>();
        List<ItemStack> other = new ArrayList<>();
        List<ItemStack> drops = state.getDrops((new LootParams.Builder(serverLevel))
                .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY));
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

            serverLevel.levelEvent(2001, pos, Block.getId(state));
            serverLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            farmer.extractEnergy(250);
            return FarmerResult.SUCCESS;
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    private BlockState getPlantablePlantFromStack(ItemStack stack, Level world, BlockPos pos) {
        if (StackUtil.isValid(stack)) {
            IPlantable plantable = this.getPlantableFromStack(stack);
            if (plantable != null) {
                BlockState state = plantable.getPlant(world, pos);
                if (state != null && state.getBlock() instanceof BonemealableBlock) {
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

    public static InteractionResult useHoeAt(Level world, BlockPos pos) {

        Player player = FakePlayerFactory.getMinecraft((ServerLevel) world); //TODO we need our own fakeplayer for the mod.

        ItemStack itemstack = getHoeStack();

        if (!player.mayUseItemAt(pos.relative(Direction.UP), Direction.UP, itemstack)) {
            return InteractionResult.FAIL;
        } else {
//            UseOnContext dummyContext = new UseOnContext(world, player, InteractionHand.MAIN_HAND, itemstack, new BlockHitResult(new Vec3(0.5, 0.5, 0.5), Direction.UP, pos, false));
//            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(dummyContext);
//            if (hook != 0) {
//                return hook > 0
//                    ? InteractionResult.SUCCESS
//                    : InteractionResult.FAIL;
//            } TODO: Fire event for hoe use?

            if (world.isEmptyBlock(pos.above())) {
                BlockState state = world.getBlockState(pos);
                if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT_PATH)) {
                    world.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
                    return InteractionResult.SUCCESS;
                }

                if (state.is(BlockTags.DIRT)) {
                    world.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
    }
}
