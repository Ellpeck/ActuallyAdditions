/*
 * This file ("BlockWildPlant.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBushBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheWildPlants;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockWildPlant extends BlockBushBase{

    public static final TheWildPlants[] ALL_WILD_PLANTS = TheWildPlants.values();
    private static final PropertyInteger META = PropertyInteger.create("meta", 0, ALL_WILD_PLANTS.length-1);

    public BlockWildPlant(String name){
        super(name);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state){
        BlockPos offset = pos.down();
        IBlockState offsetState = world.getBlockState(offset);
        Block offsetBlock = offsetState.getBlock();
        return offsetBlock.getMetaFromState(offsetState) == TheWildPlants.RICE.ordinal() ? offsetBlock.getMaterial(offsetState) == Material.WATER : offsetBlock.canSustainPlant(world.getBlockState(offset), world, offset, EnumFacing.UP, this);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
        int metadata = this.getMetaFromState(state);
        return metadata >= ALL_WILD_PLANTS.length ? null : new ItemStack(((BlockPlant)ALL_WILD_PLANTS[metadata].wildVersionOf).seedItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < ALL_WILD_PLANTS.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        int metadata = this.getMetaFromState(state);
        return metadata >= ALL_WILD_PLANTS.length ? null : ALL_WILD_PLANTS[metadata].wildVersionOf.getDrops(world, pos, ALL_WILD_PLANTS[metadata].wildVersionOf.getStateFromMeta(7), fortune);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
        return false;
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    @Override
    public boolean shouldAddCreative(){
        return false;
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < ALL_WILD_PLANTS.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), META.getName()+"="+i);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= ALL_WILD_PLANTS.length ? EnumRarity.COMMON : ALL_WILD_PLANTS[stack.getItemDamage()].rarity;
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }


        @Override
        public String getUnlocalizedName(ItemStack stack){
            return stack.getItemDamage() >= ALL_WILD_PLANTS.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+ALL_WILD_PLANTS[stack.getItemDamage()].name;
        }
    }
}
