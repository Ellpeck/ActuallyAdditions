/*
 * This file ("BlockPlant.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import java.util.List;
import java.util.Random;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockPlant extends BlockCrops{

    public Item seedItem;
    public Item returnItem;
    public int returnMeta;
    private String name;
    private int minDropAmount;
    private int addDropAmount;

    public BlockPlant(String name, int minDropAmount, int addDropAmount){
        this.name = name;
        this.minDropAmount = minDropAmount;
        this.addDropAmount = addDropAmount;
        this.register();
    }
    public boolean onBlockActivated(World w, BlockPos p, IBlockState s, EntityPlayer ep, EnumFacing f, float hitX, float hitY, float hitZ){
    	if(getMetaFromState(s)>=7){
    		if(w.isRemote)return true;
    		List<ItemStack> isa = getDrops(w, p, s, 0);
    		for(ItemStack i:isa){
    			if(i!=null&&i.getItem()==this.getSeed()){
    				i.stackSize--;
    			}
    			EntityItem ei = new EntityItem(w, p.getX()+.5,p.getY()+.5,p.getZ()+.5,i);
    			w.spawnEntityInWorld(ei);
    		}
    		w.setBlockState(p, getStateFromMeta(0));
    		return true;
    	}
    	return false;
    }
    private void register(){
        this.setUnlocalizedName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerBlock(this, this.getItemBlock(), this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }

        this.registerRendering();
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), new ResourceLocation(ModUtil.MOD_ID_LOWER, this.getBaseName()));
    }

    protected String getBaseName(){
        return this.name;
    }

    protected Class<? extends ItemBlockBase> getItemBlock(){
        return ItemBlockBase.class;
    }

    public boolean shouldAddCreative(){
        return false;
    }

    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
        return EnumPlantType.Crop;
    }

    @Override
    public Item getSeed(){
        return this.seedItem;
    }

    @Override
    public Item getCrop(){
        return this.returnItem;
    }
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3){
        return this.getMetaFromState(state) >= 7 ? this.getCrop() : this.getSeed();
    }

    @Override
    public int damageDropped(IBlockState state){
        return this.getMetaFromState(state) >= 7 ? this.returnMeta : 0;
    }

    @Override
    public int getDamageValue(World world, BlockPos pos){
        return 0;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random){
        return this.getMetaFromState(state) >= 7 ? random.nextInt(addDropAmount)+minDropAmount : super.quantityDropped(state, fortune, random);
    }
}