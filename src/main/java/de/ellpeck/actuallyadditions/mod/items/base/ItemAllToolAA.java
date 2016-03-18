/*
 * This file ("ItemAllToolAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ItemAllToolAA extends ItemTool{

    private int color;

    private String name;
    private EnumRarity rarity;
    private ItemStack repairItem;
    private String repairOredict;

    public ItemAllToolAA(ToolMaterial toolMat, String repairItem, String unlocalizedName, EnumRarity rarity, int color){
        this(toolMat, (ItemStack)null, unlocalizedName, rarity, color);
        this.repairOredict = repairItem;
    }

    public ItemAllToolAA(ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, EnumRarity rarity, int color){
        super(4.0F, -2F, toolMat, new HashSet<Block>());

        this.repairItem = repairItem;
        this.name = unlocalizedName;
        this.rarity = rarity;
        this.color = color;

        this.setMaxDamage(this.getMaxDamage()*4);

        this.register();
    }

    private void register(){
        ItemUtil.registerItem(this, this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName(){
        return this.name;
    }

    public boolean shouldAddCreative(){
        return true;
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), new ResourceLocation(ModUtil.MOD_ID_LOWER, "itemPaxel"));
        ActuallyAdditions.proxy.addRenderVariant(this, new ResourceLocation(ModUtil.MOD_ID_LOWER, "itemPaxel"));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        return Items.iron_hoe.onItemUse(stack, playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
    }

    //TODO Fix ItemStack color
    /*@Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass){
        return pass > 0 ? this.color : super.(stack, pass);
    }*/

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack){

        return this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getMaterial(state).isToolNotRequired() || (state.getBlock() == Blocks.snow_layer || state.getBlock()== Blocks.snow || (state.getBlock()== Blocks.obsidian ? this.toolMaterial.getHarvestLevel() >= 3 : (state.getBlock()!= Blocks.diamond_block && state.getBlock()!= Blocks.diamond_ore ? (state.getBlock()!= Blocks.emerald_ore && state.getBlock()!= Blocks.emerald_block? (state.getBlock()!= Blocks.gold_block&& state.getBlock()!= Blocks.gold_ore ? (state.getBlock()!= Blocks.iron_block&& state.getBlock()!= Blocks.iron_ore ? (state.getBlock()!= Blocks.lapis_block&& state.getBlock()!= Blocks.lapis_ore ? (state.getBlock()!= Blocks.redstone_ore && state.getBlock()!= Blocks.lit_redstone_ore ? (state.getBlock().getMaterial(state) == Material.rock || (state.getBlock().getMaterial(state) == Material.iron || state.getBlock().getMaterial(state) == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2)));
    }

    private boolean hasExtraWhitelist(Block block){
        String name = block.getRegistryName();
        if(name != null){
            for(String list : ConfigValues.paxelExtraMiningWhitelist){
                if(list.equals(name)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        if(this.repairItem != null){
            return ItemUtil.areItemsEqual(this.repairItem, stack, false);
        }
        else if(this.repairOredict != null){
            int[] idsStack = OreDictionary.getOreIDs(stack);
            for(int id : idsStack){
                if(OreDictionary.getOreName(id).equals(this.repairOredict)){
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
        return this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state).isEmpty() || this.getToolClasses(stack).contains(state.getBlock().getHarvestTool(state)) ? this.efficiencyOnProperMaterial : 1.0F;
    }
}