package de.ellpeck.actuallyadditions.mod.block;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class TinyTorchBlock extends Block {
    
    public static final DirectionProperty FACING_EXCEPT_DOWN = DirectionProperty.create("facing", direction -> direction != Direction.DOWN);
    
    private static final Properties PROPERTIES = Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0).lightValue(11).sound(SoundType.WOOD).tickRandomly();
    private static final VoxelShape STANDING_SHAPE = VoxelShapes.create(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.3125D, 0.5625D);
    private static final VoxelShape TORCH_NORTH_SHAPE = VoxelShapes.create(0.4375D, 0.25D, 0.8125D, 0.5625D, 0.5625D, 1.0D);
    private static final VoxelShape TORCH_SOUTH_SHAPE = VoxelShapes.create(0.4375D, 0.25D, 0.0D, 0.5625D, 0.5625D, 0.1875D);
    private static final VoxelShape TORCH_WEST_SHAPE = VoxelShapes.create(0.8125D, 0.25D, 0.4375D, 1.0D, 0.5625D, 0.5625D);
    private static final VoxelShape TORCH_EAST_SHAPE = VoxelShapes.create(0.0D, 0.25D, 0.4375D, 0.1875D, 0.5625D, 0.5625D);
    
    public TinyTorchBlock(){
        super(PROPERTIES);
        
        this.setRegistryName(ActuallyAdditions.MODID, "tiny_torch");
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING_EXCEPT_DOWN, Direction.UP));
    }
    
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context){
        switch(state.get(FACING_EXCEPT_DOWN)){
            case EAST:
                return TORCH_EAST_SHAPE;
            case WEST:
                return TORCH_WEST_SHAPE;
            case SOUTH:
                return TORCH_SOUTH_SHAPE;
            case NORTH:
                return TORCH_NORTH_SHAPE;
            default:
                return STANDING_SHAPE;
        }
    }
    
    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context){
        return VoxelShapes.empty();
    }
    
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer(){
        return BlockRenderLayer.CUTOUT;
    }
    
    private boolean canPlaceAt(@Nonnull IWorldReader world, @Nonnull BlockPos pos, @Nonnull Direction direction){
        BlockPos blockpos = pos.offset(direction.getOpposite());
        return (direction.getAxis().isHorizontal() && hasSolidSide(world.getBlockState(blockpos), world, blockpos, direction)) || (direction.equals(Direction.UP) && hasSolidSideOnTop(world, blockpos));
    }
    
    @Override
    public boolean isValidPosition(@Nonnull BlockState state, @Nonnull IWorldReader world, @Nonnull BlockPos pos){
        for(Direction direction : FACING_EXCEPT_DOWN.getAllowedValues()){
            if(this.canPlaceAt(world, pos, direction)){
                return true;
            }
        }
        return false;
    }
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context){
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Direction placementDirection = context.getFace();
        if(this.canPlaceAt(world, pos, placementDirection)){
            return this.getDefaultState().with(FACING_EXCEPT_DOWN, placementDirection);
        } else{
            for(Direction direction : Direction.Plane.HORIZONTAL){
                if(hasSolidSide(world.getBlockState(pos), world, pos.offset(direction.getOpposite()), direction)){
                    return this.getDefaultState().with(FACING_EXCEPT_DOWN, direction);
                }
            }
        }
        return super.getStateForPlacement(context);
    }
    
    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving){
        Direction direction = state.get(FACING_EXCEPT_DOWN);
        if(!this.canPlaceAt(world, pos, direction)){
            world.addEntity(new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(this)));
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand){
        if(rand.nextBoolean()){
            Direction direction = state.get(FACING_EXCEPT_DOWN);
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + 0.4D;
            double d2 = pos.getZ() + 0.5D;
            
            if(direction.getAxis().isHorizontal()){
                Direction direction1 = direction.getOpposite();
                world.addParticle(ParticleTypes.SMOKE, d0 + 0.35D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.35D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.FLAME, d0 + 0.35D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.35D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
            } else{
                world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @Nonnull
    @Override
    public BlockState rotate(@Nonnull BlockState state, @Nonnull Rotation rot){
        return state.with(FACING_EXCEPT_DOWN, rot.rotate(state.get(FACING_EXCEPT_DOWN)));
    }
    
    @Nonnull
    @Override
    public BlockState mirror(@Nonnull BlockState state, @Nonnull Mirror mirrorIn){
        return state.rotate(mirrorIn.toRotation(state.get(FACING_EXCEPT_DOWN)));
    }
    
    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder){
        builder.add(FACING_EXCEPT_DOWN);
    }
    
}