package de.ellpeck.actuallyadditions.mod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ColoredLampBlock extends Block {
    
    public static final EnumProperty<LampColors> COLOR = EnumProperty.create("color", LampColors.class);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    
    public ColoredLampBlock() {
        super(Properties.create(Material.REDSTONE_LIGHT).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 3.0F));
    }
    
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state.with(ACTIVE, false).with(COLOR, LampColors.WHITE), placer, stack);
    }
    
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItemMainhand();
        if (hand == Hand.MAIN_HAND && (stack == null || stack.isEmpty())) {
            world.setBlockState(pos, state.with(ACTIVE, !state.get(ACTIVE)), 3);
            return true;
        }
    
        LampColors color = LampColors.getColorFromStack(stack);
        if (color != null) {
            world.setBlockState(pos, state.with(COLOR, color), 3);
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), state, world.getBlockState(pos), 3);
            if (!player.abilities.isCreativeMode) {
                player.inventory.decrStackSize(player.inventory.currentItem, 1);
            }
        }
    
    
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }
    
    public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        return state.get(ACTIVE) ? 15 : 0;
    }
    
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(COLOR, ACTIVE);
    }
}