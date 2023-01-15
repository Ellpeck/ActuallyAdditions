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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

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
        super(Properties.of(Material.BUILDABLE_GLASS).strength(0.5F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        //Turning On
        if (hand == Hand.MAIN_HAND && stack.isEmpty()) {
            world.setBlock(pos, this.defaultBlockState().setValue(LIT, !state.getValue(LIT)), Constants.BlockFlags.NO_RERENDER);
            return ActionResultType.PASS;
        }

        if (StackUtil.isValid(stack) && stack.getItem() instanceof DyeItem) {
            DyeColor color = DyeColor.getColor(stack);
            if (color == null) {
                return ActionResultType.FAIL;
            }

            Block newColor = COLOR_TO_LAMP.get(color).get();
            if (!world.isClientSide) {
                world.setBlock(pos, newColor.defaultBlockState().setValue(LIT, state.getValue(LIT)), 2);
                if (!player.isCreative()) {
                    player.inventory.removeItem(player.inventory.selected, 1);
                }
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(LIT)
            ? 15
            : 0;
    }
}
