/*
 * This file ("BlockPlant.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
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

// CROP BLOCK DEFAULTS TO 7 YEARS OF AGE.
import net.minecraft.block.AbstractBlock.Properties;

public class BlockPlant extends CropsBlock {
    public Item seedItem;

    // Stolen from potato for now
    //    PotatoBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public BlockPlant(Item seedItem) {
        super(Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        this.seedItem = seedItem;
    }

    // Remove
    @Deprecated
    public void doStuff(Item seedItem, Item returnItem, int returnMeta) {
        this.seedItem = seedItem;
        //        this.returnItem = returnItem;
        //        this.returnMeta = returnMeta;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.CROP;
    }
    //
    //    @Override
    //    public int damageDropped(BlockState state) {
    //        return this.getMetaFromState(state) >= 7
    //            ? this.returnMeta
    //            : 0;
    //    }


    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.getAge(state) < 7) {
            return ActionResultType.PASS;
        }

        if (!world.isClientSide) {
            List<ItemStack> drops = Block.getDrops(state, (ServerWorld) world, pos, null);
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

            world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, 0));
        }

        return super.use(state, world, pos, player, handIn, hit);
    }

    @Override
    protected IItemProvider getBaseSeedId() {
        return this.seedItem;
    }

    //    @Override
    //    public int quantityDropped(BlockState state, int fortune, Random random) {
    //        return this.getMetaFromState(state) >= 7
    //            ? random.nextInt(this.addDropAmount) + this.minDropAmount
    //            : super.quantityDropped(state, fortune, random);
    //    }

    // TODO: [port] move to data table

    //    @Override
    //    public Item getCrop() {
    //        return this.returnItem;
    //    }

    // TODO: [port] move to data table

    //    @Override
    //    public Item getItemDropped(BlockState state, Random rand, int par3) {
    //        return this.getMetaFromState(state) >= 7
    //            ? this.getCrop()
    //            : this.getSeed();
    //    }
}
