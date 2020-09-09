package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

// todo add rarity RARE
public class BlockBatteryBox extends BlockContainerBase {

    public static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    
    public BlockBatteryBox() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestLevel(0)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }
    
    @Override
    public Rarity getRarity(){
        return Rarity.RARE;
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return SHAPE;
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityBatteryBox) {
            TileEntityBatteryBox box = (TileEntityBatteryBox) tile;
            ItemStack stack = player.getHeldItem(hand);
        
            if (StackUtil.isValid(stack)) {
                if (stack.getItem() instanceof ItemBattery && !StackUtil.isValid(box.inv.getStackInSlot(0))) {
                    box.inv.setStackInSlot(0, stack.copy());
                    player.setHeldItem(hand, StackUtil.getEmpty());
                    return ActionResultType.SUCCESS;
                }
            } else {
                ItemStack inSlot = box.inv.getStackInSlot(0);
                if (StackUtil.isValid(inSlot)) {
                    player.setHeldItem(hand, inSlot.copy());
                    box.inv.setStackInSlot(0, StackUtil.getEmpty());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new TileEntityBatteryBox();
    }
    
}
