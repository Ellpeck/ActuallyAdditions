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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Random;

public class BlockColoredLamp extends BlockBase{

    public static TheColoredLampColors[] allLampTypes = TheColoredLampColors.values();
    public boolean isOn;
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;

    public BlockColoredLamp(boolean isOn, String name){
        super(Material.redstoneLight, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(3.0F);
        this.isOn = isOn;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return meta >= allLampTypes.length ? null : textures[meta];
    }

    @Override
    public Item getItemDropped(int par1, Random rand, int par3){
        return Item.getItemFromBlock(InitBlocks.blockColoredLamp);
    }

    @Override
    public int damageDropped(int meta){
        return meta;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        //Turning On
        if(player.isSneaking()){
            if(!world.isRemote){
                world.setBlock(x, y, z, this.isOn ? InitBlocks.blockColoredLamp : InitBlocks.blockColoredLampOn, world.getBlockMetadata(x, y, z), 2);
            }
            return true;
        }

        //Changing Colors
        int[] oreIDs = OreDictionary.getOreIDs(player.getCurrentEquippedItem());
        if(oreIDs.length > 0){
            for(int oreID : oreIDs){
                String name = OreDictionary.getOreName(oreID);
                TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                if(color != null){
                    if(world.getBlockMetadata(x, y, z) != color.ordinal()){
                        if(!world.isRemote){
                            world.setBlockMetadataWithNotify(x, y, z, color.ordinal(), 2);
                            if(!player.capabilities.isCreativeMode){
                                player.inventory.decrStackSize(player.inventory.currentItem, 1);
                            }
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack createStackedBlock(int meta){
        return new ItemStack(InitBlocks.blockColoredLamp, 1, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z){
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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.textures = new IIcon[allLampTypes.length];
        for(int i = 0; i < allLampTypes.length; i++){
            this.textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+allLampTypes[i].name);
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return this.isOn ? 15 : 0;
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
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
            return StringUtil.localize(this.getUnlocalizedName(stack)+".name")+(((BlockColoredLamp)this.field_150939_a).isOn ? " ("+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".onSuffix.desc")+")" : "");
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return InitBlocks.blockColoredLamp.getUnlocalizedName()+allLampTypes[stack.getItemDamage()].name;
        }
    }
}