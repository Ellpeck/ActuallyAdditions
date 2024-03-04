/*
 * This file ("ItemShovelAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.Sets;
import de.ellpeck.actuallyadditions.mod.items.base.ItemToolAA;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class ItemShovelAA extends ItemToolAA {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.SOUL_SAND, Blocks.DIRT_PATH);

    public ItemShovelAA(float p_i48512_1_, float p_i48512_2_, Tier p_i48512_3_, TagKey<Block> p_i48512_4_, Properties p_i48512_5_, String name, ItemStack repairItem, TagKey<Item> repairTag) {
        super(p_i48512_1_, p_i48512_2_, p_i48512_3_, p_i48512_4_, p_i48512_5_, name, repairItem, repairTag);
    }

//    public ItemShovelAA(IItemTier material) {
////        super(1.5F, -3.0F, material, this.repairItem, unlocalizedName, this.rarity, EFFECTIVE_ON);
////        this.setHarvestLevel("shovel", material.getLevel());
//    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        Block block = blockIn.getBlock();
        return block == Blocks.SNOW_BLOCK || block == Blocks.SNOW;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext useContext) {
        return Items.IRON_SHOVEL.useOn(useContext);
    }

    //@Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Collections.singleton("shovel");
    }
}
