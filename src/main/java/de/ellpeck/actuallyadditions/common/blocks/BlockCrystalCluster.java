package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.common.util.IColorProvidingBlock;
import de.ellpeck.actuallyadditions.common.util.IColorProvidingItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockCrystalCluster extends Block implements IColorProvidingBlock, IColorProvidingItem {

    private final TheCrystals crystal;

    public BlockCrystalCluster(TheCrystals crystal) {
        super(Properties.create(Material.GLASS)
                .hardnessAndResistance(0.25f, 1.0f)
                .sound(SoundType.GLASS)
                .lightValue(7));

        this.crystal = crystal;
    }
    
    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos){
        return 1;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){
        return super.getStateForPlacement(context).with(BlockStateProperties.FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.FACING);
    }
    
    @Override
    public BlockState rotate(BlockState state, Rotation rot){
        return state.with(BlockStateProperties.FACING, rot.rotate(state.get(BlockStateProperties.FACING)));
    }
    
    @Override
    public BlockState mirror(BlockState state, Mirror mirror){
        return this.rotate(state, mirror.toRotation(state.get(BlockStateProperties.FACING)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor() {
        return (state, world, pos, tintIndex) -> BlockCrystalCluster.this.crystal.clusterColor;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        return (stack, tintIndex) -> BlockCrystalCluster.this.crystal.clusterColor;
    }

    /* todo canitzp: realise block drops with loot tables
    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return InitItems.itemCrystalShard;
    }
    
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder){
        return Collections.singletonList(new ItemStack(InitItems.itemCrystalShard, new Random().nextInt(5) + 2));
    }
    
    @Override
    public int damageDropped(BlockState state) {
        return ArrayUtils.indexOf(WorldGenLushCaves.CRYSTAL_CLUSTERS, this);
    }
    
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player){
        super.onBlockHarvested(worldIn, pos, state, player);
    }
    
    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }*/
}