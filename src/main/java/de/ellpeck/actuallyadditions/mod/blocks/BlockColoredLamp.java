/*
 * This file ("BlockColoredLamp.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class BlockColoredLamp extends BlockBase{

    public static final TheColoredLampColors[] ALL_LAMP_TYPES = TheColoredLampColors.values();
    private static final PropertyEnum<TheColoredLampColors> TYPE = PropertyEnum.create("type", TheColoredLampColors.class);
    public final boolean isOn;

    public BlockColoredLamp(boolean isOn, String name){
        super(Material.REDSTONE_LIGHT, name);
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        ItemStack stack = player.getHeldItem(hand);
        //Turning On
        if(player.isSneaking()){
            if(!world.isRemote){
                world.setBlockState(pos, (this.isOn ? InitBlocks.blockColoredLamp : InitBlocks.blockColoredLampOn).getStateFromMeta(this.getMetaFromState(state)), 2);
            }
            return true;
        }

        if(StackUtil.isValid(stack)){
            //Changing Colors
            int[] oreIDs = OreDictionary.getOreIDs(stack);
            if(oreIDs.length > 0){
                for(int oreID : oreIDs){
                    String name = OreDictionary.getOreName(oreID);
                    TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                    if(color != null){
                        if(this.getMetaFromState(state) != color.ordinal()){
                            if(!world.isRemote){
                                world.setBlockState(pos, this.getStateFromMeta(color.ordinal()), 2);
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
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList list){
        for(int j = 0; j < ALL_LAMP_TYPES.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
        return this.isOn ? 15 : 0;
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < ALL_LAMP_TYPES.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName()+"="+ALL_LAMP_TYPES[i].regName);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(TYPE, TheColoredLampColors.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, TYPE);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }


        @Override
        public String getItemStackDisplayName(ItemStack stack){
            if(stack.getItemDamage() >= ALL_LAMP_TYPES.length){
                return StringUtil.BUGGED_ITEM_NAME;
            }
            return StringUtil.localize(this.getUnlocalizedName(stack)+".name")+(((BlockColoredLamp)this.block).isOn ? " ("+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".onSuffix.desc")+")" : "");
        }


        @Override
        public String getUnlocalizedName(ItemStack stack){
            return InitBlocks.blockColoredLamp.getUnlocalizedName()+"_"+ALL_LAMP_TYPES[stack.getItemDamage()].regName;
        }
    }
}