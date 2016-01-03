/*
 * This file ("BlockMisc.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockMisc extends BlockBase{

    public static final TheMiscBlocks[] allMiscBlocks = TheMiscBlocks.values();
    @SideOnly(Side.CLIENT)
    public IIcon[] textures;

    @SideOnly(Side.CLIENT)
    private IIcon ironCasingSeasonalTop;
    @SideOnly(Side.CLIENT)
    private IIcon ironCasingSeasonal;

    public BlockMisc(String name){
        super(Material.rock, name);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        if(ClientProxy.jingleAllTheWay && side != 0){
            if(metadata == TheMiscBlocks.IRON_CASING.ordinal()){
                return side == 1 ? this.ironCasingSeasonalTop : this.ironCasingSeasonal;
            }
        }
        return metadata >= textures.length ? null : textures[metadata];
    }

    @Override
    public int damageDropped(int meta){
        return meta;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allMiscBlocks.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.textures = new IIcon[allMiscBlocks.length];
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+allMiscBlocks[i].name);
        }

        this.ironCasingSeasonalTop = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":blockMiscIronCasingSnowTop");
        this.ironCasingSeasonal = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":blockMiscIronCasingSnow");
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allMiscBlocks.length ? EnumRarity.common : allMiscBlocks[stack.getItemDamage()].rarity;
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return stack.getItemDamage() >= allMiscBlocks.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allMiscBlocks[stack.getItemDamage()].name;
        }
    }
}
