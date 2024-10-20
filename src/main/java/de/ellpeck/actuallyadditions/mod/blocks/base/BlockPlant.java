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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class BlockPlant extends CropBlock {
    public Supplier<Item> seedItem;

    // Stolen from potato for now
    //    PotatoBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public BlockPlant(Supplier<Item> seedItem) {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        this.seedItem = seedItem;
    }

    // Remove
    @Deprecated
    public void doStuff(Supplier<Item> seedItem, Item returnItem, int returnMeta) {
        this.seedItem = seedItem;
        //        this.returnItem = returnItem;
        //        this.returnMeta = returnMeta;
    }

/*    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CROP;
    }*/
    //
    //    @Override
    //    public int damageDropped(BlockState state) {
    //        return this.getMetaFromState(state) >= 7
    //            ? this.returnMeta
    //            : 0;
    //    }


    @Nonnull
    @Override
    protected ItemInteractionResult useItemOn(@Nonnull ItemStack pStack, @Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult pHitResult) {
        if (this.getAge(state) < 7) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!world.isClientSide) {
            List<ItemStack> drops = Block.getDrops(state, (ServerLevel) world, pos, null);
            boolean deductedSeedSize = false;
            for (ItemStack drop : drops) {
                if (!drop.isEmpty()) {
                    if (drop.getItem() == this.seedItem.get() && !deductedSeedSize) {
                        drop.shrink(1);
                        deductedSeedSize = true;
                    }
                    if (!drop.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(player, drop);
                    }
                }
            }

            world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, 0));
        }

        return super.useItemOn(pStack, state, world, pos, player, hand, pHitResult);
    }

    @Nonnull
    @Override
    protected ItemLike getBaseSeedId() {
        return this.seedItem.get();
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
