/*
 * This file ("BlockColoredLamp.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Random;

public class BlockColoredLamp extends BlockBase{

    public static TheColoredLampColors[] allLampTypes = TheColoredLampColors.values();
    private static final PropertyInteger META = PropertyInteger.create("meta", 0, allLampTypes.length-1);
    public boolean isOn;

    public BlockColoredLamp(boolean isOn, String name){
        super(Material.redstoneLight, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(3.0F);
        this.isOn = isOn;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3){
        return Item.getItemFromBlock(InitBlocks.blockColoredLamp);
    }

    @Override
    public int damageDropped(IBlockState state){
        return this.getMetaFromState(state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        //Turning On
        if(player.isSneaking()){
            if(!world.isRemote){
                PosUtil.setBlock(pos, world, this.isOn ? InitBlocks.blockColoredLamp : InitBlocks.blockColoredLampOn, PosUtil.getMetadata(pos, world), 2);
            }
            return true;
        }

        ItemStack stack = player.getCurrentEquippedItem();
        if(stack != null){
            //Changing Colors
            int[] oreIDs = OreDictionary.getOreIDs(stack);
            if(oreIDs.length > 0){
                for(int oreID : oreIDs){
                    String name = OreDictionary.getOreName(oreID);
                    TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                    if(color != null){
                        if(PosUtil.getMetadata(pos, world) != color.ordinal()){
                            if(!world.isRemote){
                                PosUtil.setMetadata(pos, world, color.ordinal(), 2);
                                if(!player.capabilities.isCreativeMode){
                                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack createStackedBlock(IBlockState state){
        return new ItemStack(InitBlocks.blockColoredLamp, 1, this.getMetaFromState(state));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos){
        return Item.getItemFromBlock(InitBlocks.blockColoredLamp);
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allLampTypes.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos){
        return this.isOn ? 15 : 0;
    }

    @Override
    protected void registerRendering(){
        ResourceLocation[] resLocs = new ResourceLocation[allLampTypes.length];
        for(int i = 0; i < allLampTypes.length; i++){
            String name = this.getBaseName()+allLampTypes[i].name;
            resLocs[i] = new ResourceLocation(ModUtil.MOD_ID_LOWER, name);
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ResourceLocation(ModUtil.MOD_ID_LOWER, name));
        }
        ActuallyAdditions.proxy.addRenderVariant(Item.getItemFromBlock(this), resLocs);
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
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
        public String getItemStackDisplayName(ItemStack stack){
            if(stack.getItemDamage() >= allLampTypes.length){
                return null;
            }
            return StringUtil.localize(this.getUnlocalizedName(stack)+".name")+(((BlockColoredLamp)this.block).isOn ? " ("+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".onSuffix.desc")+")" : "");
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return InitBlocks.blockColoredLamp.getUnlocalizedName()+allLampTypes[stack.getItemDamage()].name;
        }
    }
}