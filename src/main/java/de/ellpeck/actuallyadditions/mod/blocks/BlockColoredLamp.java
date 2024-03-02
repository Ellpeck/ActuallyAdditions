/*
 * This file ("BlockColoredLamp.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.function.Supplier;

public class BlockColoredLamp extends BlockBase {
    private static final HashMap<DyeColor, Supplier<Block>> COLOR_TO_LAMP = new HashMap<DyeColor, Supplier<Block>>() {{
        this.put(DyeColor.WHITE, ActuallyBlocks.LAMP_WHITE);
        this.put(DyeColor.ORANGE, ActuallyBlocks.LAMP_ORANGE);
        this.put(DyeColor.MAGENTA, ActuallyBlocks.LAMP_MAGENTA);
        this.put(DyeColor.LIGHT_BLUE, ActuallyBlocks.LAMP_LIGHT_BLUE);
        this.put(DyeColor.YELLOW, ActuallyBlocks.LAMP_YELLOW);
        this.put(DyeColor.LIME, ActuallyBlocks.LAMP_LIME);
        this.put(DyeColor.PINK, ActuallyBlocks.LAMP_PINK);
        this.put(DyeColor.GRAY, ActuallyBlocks.LAMP_GRAY);
        this.put(DyeColor.LIGHT_GRAY, ActuallyBlocks.LAMP_LIGHT_GRAY);
        this.put(DyeColor.CYAN, ActuallyBlocks.LAMP_CYAN);
        this.put(DyeColor.PURPLE, ActuallyBlocks.LAMP_PURPLE);
        this.put(DyeColor.BLUE, ActuallyBlocks.LAMP_BLUE);
        this.put(DyeColor.BROWN, ActuallyBlocks.LAMP_BROWN);
        this.put(DyeColor.GREEN, ActuallyBlocks.LAMP_GREEN);
        this.put(DyeColor.RED, ActuallyBlocks.LAMP_RED);
        this.put(DyeColor.BLACK, ActuallyBlocks.LAMP_BLACK);
    }};

    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BlockColoredLamp() {
        super(Properties.of(Material.BUILDABLE_GLASS).strength(0.5F, 3.0F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        //Turning On
        if (hand == InteractionHand.MAIN_HAND && stack.isEmpty()) {
            world.setBlock(pos, this.defaultBlockState().setValue(LIT, !state.getValue(LIT)), Block.UPDATE_INVISIBLE);
            return InteractionResult.PASS;
        }

        if (StackUtil.isValid(stack) && stack.getItem() instanceof DyeItem) {
            DyeColor color = DyeColor.getColor(stack);
            if (color == null) {
                return InteractionResult.FAIL;
            }

            Block newColor = COLOR_TO_LAMP.get(color).get();
            if (!world.isClientSide) {
                world.setBlock(pos, newColor.defaultBlockState().setValue(LIT, state.getValue(LIT)), 2);
                if (!player.isCreative()) {
                    player.getInventory().removeItem(player.getInventory().selected, 1);
                }
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT)
            ? 15
            : 0;
    }
}
