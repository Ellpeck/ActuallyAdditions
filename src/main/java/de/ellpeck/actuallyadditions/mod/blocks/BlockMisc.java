/*
 * This file ("BlockMisc.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMisc extends BlockBase{

    public static final TheMiscBlocks[] allMiscBlocks = TheMiscBlocks.values();
    private static final PropertyInteger META = PropertyInteger.create("meta", 0, allMiscBlocks.length-1);

    public BlockMisc(String name){
        super(Material.ROCK, name);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public int damageDropped(IBlockState state){
        return this.getMetaFromState(state);
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allMiscBlocks.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    @Override
    protected void registerRendering(){
        ResourceLocation[] resLocs = new ResourceLocation[allMiscBlocks.length];
        for(int i = 0; i < allMiscBlocks.length; i++){
            String name = this.getBaseName()+allMiscBlocks[i].name;
            resLocs[i] = new ResourceLocation(ModUtil.MOD_ID, name);
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ResourceLocation(ModUtil.MOD_ID, name));
        }
        ActuallyAdditions.proxy.addRenderVariant(Item.getItemFromBlock(this), resLocs);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allMiscBlocks.length ? EnumRarity.COMMON : allMiscBlocks[stack.getItemDamage()].rarity;
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
            return stack.getItemDamage() >= allMiscBlocks.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allMiscBlocks[stack.getItemDamage()].name;
        }
    }
}
