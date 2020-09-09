/*
* This file ("BlockPlant.java") is part of the Actually Additions mod for Minecraft.
* It is created and owned by Ellpeck and distributed
* under the Actually Additions License to be found at
* http://ellpeck.de/actaddlicense
* View the source code at https://github.com/Ellpeck/ActuallyAdditions
*
* © 2015-2017 Ellpeck
*/

package de.ellpeck.actuallyadditions.blocks.base;

import de.ellpeck.actuallyadditions.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class BlockPlant extends CropsBlock implements IBaseBlock {

    private final int minDropAmount;
    private final int addDropAmount;
    public Item seedItem;
    private Item returnItem;
    private int returnMeta;

    // todo: find the correct properties for crops
    public BlockPlant(int minDropAmount, int addDropAmount) {
        super(Properties.create(Material.PLANTS));
        
        this.minDropAmount = minDropAmount;
        this.addDropAmount = addDropAmount;
    }
    
    // todo: check what this is doing
    public void doStuff(Item seedItem, Item returnItem, int returnMeta) {
        this.seedItem = seedItem;
        this.returnItem = returnItem;
        this.returnMeta = returnMeta;
    }
    
    @Override
    public BlockItemBase getItemBlock(){
        return new BlockItemBase(this, new Item.Properties());
    }
    
    public boolean shouldAddCreative() {
        return false;
    }
    
    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }
    
    @Override
    protected IItemProvider getSeedsItem() {
        return this.seedItem;
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (getAge(state) > 7) {
            if (!world.isRemote) {
                List<ItemStack> drops = getDrops(state, (ServerWorld) world, pos, null);
                
                boolean deductedSeedSize = false;
                for (ItemStack drop : drops) {
                    if (StackUtil.isValid(drop)) {
                        if (drop.getItem() == this.seedItem && !deductedSeedSize) {
                            drop.shrink(1);
                            deductedSeedSize = true;
                        }
                        if (StackUtil.isValid(drop)) {
                            ItemHandlerHelper.giveItemToPlayer(player, drop);
                        }
                    }
                }
                
                world.setBlockState(pos, state.with(AGE, 0));
            }
            
            return ActionResultType.SUCCESS;
        }
        
        return super.onBlockActivated(state, world, pos, player, handIn, hit);
    }
    
    /* todo: this is likely handled by loot tables now
    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state) >= 7 ? this.returnMeta : 0;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return this.getMetaFromState(state) >= 7 ? random.nextInt(this.addDropAmount) + this.minDropAmount : super.quantityDropped(state, fortune, random);
    }

    @Override
    public Item getCrop() {
        return this.returnItem;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3) {
        return this.getMetaFromState(state) >= 7 ? this.getCrop() : this.getSeed();
    }
     */

}