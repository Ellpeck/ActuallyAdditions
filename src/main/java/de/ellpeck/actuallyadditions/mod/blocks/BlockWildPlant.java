/*
 * This file ("BlockWildPlant.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBushBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheWildPlants;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockWildPlant extends BlockBushBase{

    public static final TheWildPlants[] allWildPlants = TheWildPlants.values();
    private static final PropertyInteger META = PropertyInteger.create("meta", 0, allWildPlants.length-1);

    public BlockWildPlant(String name){
        super(name);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state){
        BlockPos offset = PosUtil.offset(pos, 0, -1, 0);
        return PosUtil.getMetadata(state) == TheWildPlants.RICE.ordinal() ? PosUtil.getMaterial(offset, world) == Material.WATER : PosUtil.getBlock(offset, world).canSustainPlant(world.getBlockState(offset), world, offset, EnumFacing.UP, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
        int metadata = PosUtil.getMetadata(pos, world);
        return metadata >= allWildPlants.length ? null : new ItemStack(((BlockPlant)allWildPlants[metadata].wildVersionOf).seedItem);
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allWildPlants.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        int metadata = PosUtil.getMetadata(state);
        return metadata >= allWildPlants.length ? null : allWildPlants[metadata].wildVersionOf.getDrops(world, pos, allWildPlants[metadata].wildVersionOf.getStateFromMeta(7), fortune);
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
        for(int i = 0; i < allWildPlants.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ModelResourceLocation(this.getRegistryName(), META.getName()+"="+i));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allWildPlants.length ? EnumRarity.COMMON : allWildPlants[stack.getItemDamage()].rarity;
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
            return stack.getItemDamage() >= allWildPlants.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allWildPlants[stack.getItemDamage()].name;
        }
    }
}
