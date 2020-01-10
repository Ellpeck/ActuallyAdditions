package de.ellpeck.actuallyadditions.mod.block;

import java.util.EnumMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;


public class ColoredLampBlock extends RedstoneLampBlock {

    public static final EnumMap<LampColor, ColoredLampBlock> LAMPS = new EnumMap<>(LampColor.class);

    protected LampColor color;

    public ColoredLampBlock(LampColor color) {
        super(Properties.create(Material.REDSTONE_LIGHT).harvestTool(ToolType.PICKAXE).lightValue(15).hardnessAndResistance(0.5F, 3.0F));
        this.setDefaultState(getDefaultState().with(LIT, false));
        this.color = color;
        LAMPS.put(color, this);
    }


    @Override
    @Deprecated
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking() && stack.isEmpty()) {
            world.setBlockState(pos, state.cycle(LIT));
            return true;
        }

        LampColor color = LampColor.getColorFromStack(stack);
        if (color != null && color != this.color) {
            world.setBlockState(pos, LAMPS.get(color).getDefaultState().with(LIT, state.get(LIT)));
            if (! player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
            return true;
        }

        return false;
    }


    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block from, BlockPos fromPos, boolean isMoving) {
        if (! world.isRemote) {
            if (world.getBlockState(fromPos).canProvidePower()) {
                world.setBlockState(pos, state.with(LIT, world.isBlockPowered(pos)));
            }
        }
    }

}