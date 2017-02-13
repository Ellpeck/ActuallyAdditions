/*
 * This file ("ItemAllToolAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemToolAA;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

public class ItemAllToolAA extends ItemToolAA implements IColorProvidingItem{

    public final int color;

    public ItemAllToolAA(ToolMaterial toolMat, String repairItem, String unlocalizedName, EnumRarity rarity, int color){
        super(4.0F, -2F, toolMat, repairItem, unlocalizedName, rarity, new HashSet<Block>());
        this.color = color;

        this.setMaxDamage(this.getMaxDamage()*4);
        this.setHarvestLevel("pickaxe", toolMat.getHarvestLevel());
    }

    public ItemAllToolAA(ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, EnumRarity rarity, int color){
        super(4.0F, -2F, toolMat, repairItem, unlocalizedName, rarity, new HashSet<Block>());
        this.color = color;

        this.setMaxDamage(this.getMaxDamage()*4);
        this.setHarvestLevel("pickaxe", toolMat.getHarvestLevel());
    }

    @Override
    protected void registerRendering(){
        ResourceLocation resLoc = new ResourceLocation(ModUtil.MOD_ID, "item_paxel");
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), resLoc, "inventory");
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!playerIn.isSneaking()){
            return Items.IRON_HOE.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        }
        else{
            return Items.IRON_SHOVEL.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        }
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack){

        return this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getMaterial(state).isToolNotRequired() || (state.getBlock() == Blocks.SNOW_LAYER || state.getBlock() == Blocks.SNOW || (state.getBlock() == Blocks.OBSIDIAN ? this.toolMaterial.getHarvestLevel() >= 3 : (state.getBlock() != Blocks.DIAMOND_BLOCK && state.getBlock() != Blocks.DIAMOND_ORE ? (state.getBlock() != Blocks.EMERALD_ORE && state.getBlock() != Blocks.EMERALD_BLOCK ? (state.getBlock() != Blocks.GOLD_BLOCK && state.getBlock() != Blocks.GOLD_ORE ? (state.getBlock() != Blocks.IRON_BLOCK && state.getBlock() != Blocks.IRON_ORE ? (state.getBlock() != Blocks.LAPIS_BLOCK && state.getBlock() != Blocks.LAPIS_ORE ? (state.getBlock() != Blocks.REDSTONE_ORE && state.getBlock() != Blocks.LIT_REDSTONE_ORE ? (state.getBlock().getMaterial(state) == Material.ROCK || (state.getBlock().getMaterial(state) == Material.IRON || state.getBlock().getMaterial(state) == Material.ANVIL)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2)));
    }

    private boolean hasExtraWhitelist(Block block){
        String name = block.getRegistryName().toString();
        if(name != null){
            for(String list : ConfigStringListValues.PAXEL_EXTRA_MINING_WHITELIST.getValue()){
                if(list.equals(name)){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Set<String> getToolClasses(ItemStack stack){
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("pickaxe");
        hashSet.add("axe");
        hashSet.add("shovel");
        return hashSet;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        if(state.getBlock() == Blocks.WEB){
            return 15.0F;
        }
        else{
            return this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state).isEmpty() || this.getToolClasses(stack).contains(state.getBlock().getHarvestTool(state)) ? this.efficiencyOnProperMaterial : 1.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IItemColor getItemColor(){
        return new IItemColor(){
            @Override
            public int getColorFromItemstack(ItemStack stack, int pass){
                return pass > 0 ? ItemAllToolAA.this.color : 0xFFFFFF;
            }
        };
    }
}