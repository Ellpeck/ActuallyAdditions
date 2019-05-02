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

import java.util.Random;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.render.IHasModel;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.items.ItemHandlerHelper;

public class BlockPlant extends BlockCrops implements ItemBlockBase.ICustomRarity, IHasModel {

    private final String name;
    private final int minDropAmount;
    private final int addDropAmount;
    public Item seedItem;
    private Item returnItem;
    private int returnMeta;

    public BlockPlant(String name, int minDropAmount, int addDropAmount) {
        this.name = name;
        this.minDropAmount = minDropAmount;
        this.addDropAmount = addDropAmount;
        this.register();
    }

    public void doStuff(Item seedItem, Item returnItem, int returnMeta) {
        this.seedItem = seedItem;
        this.returnItem = returnItem;
        this.returnMeta = returnMeta;
    }

    private void register() {
        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());
    }

    protected String getBaseName() {
        return this.name;
    }

    protected ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }

    public boolean shouldAddCreative() {
        return false;
    }

    @Override
    public void registerRendering() {
        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state) >= 7 ? this.returnMeta : 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (this.getMetaFromState(state) >= 7) {
            if (!world.isRemote) {

                NonNullList<ItemStack> drops = NonNullList.create();
                this.getDrops(drops, world, pos, state, 0);
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

                world.setBlockState(pos, this.getStateFromMeta(0));
            }
            return true;
        }
        return false;
    }

    @Override
    public Item getSeed() {
        return this.seedItem;
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

}