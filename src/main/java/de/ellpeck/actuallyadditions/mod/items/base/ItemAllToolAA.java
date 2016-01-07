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

import com.google.common.collect.Sets;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ItemAllToolAA extends ItemTool{

    @SideOnly(Side.CLIENT)
    private IIcon overlayIcon;
    private int color;

    private String name;
    private EnumRarity rarity;
    private ItemStack repairItem;
    private String repairOredict;

    public ItemAllToolAA(ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, EnumRarity rarity, int color){
        super(4.0F, toolMat, Sets.newHashSet());

        this.repairItem = repairItem;
        this.name = unlocalizedName;
        this.rarity = rarity;
        this.color = color;

        this.setMaxDamage(this.getMaxDamage()*4);

        this.register();
    }

    public ItemAllToolAA(ToolMaterial toolMat, String repairItem, String unlocalizedName, EnumRarity rarity, int color){
        this(toolMat, (ItemStack)null, unlocalizedName, rarity, color);
        this.repairOredict = repairItem;
    }

    private void register(){
        this.setUnlocalizedName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerItem(this, this.getBaseName());

        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }
    }

    protected String getBaseName(){
        return this.name;
    }

    public boolean shouldAddCreative(){
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
        if(!player.canPlayerEdit(x, y, z, side, stack)){
            return false;
        }
        else{
            UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
            if(MinecraftForge.EVENT_BUS.post(event)){
                return false;
            }
            if(event.getResult() == Event.Result.ALLOW){
                stack.damageItem(1, player);
                return true;
            }
            Block block = world.getBlock(x, y, z);
            if(side != 0 && world.getBlock(x, y+1, z).isAir(world, x, y+1, z) && (block == Blocks.grass || block == Blocks.dirt)){
                Block block1 = Blocks.farmland;
                world.playSoundEffect((double)((float)x+0.5F), (double)((float)y+0.5F), (double)((float)z+0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume()+1.0F)/2.0F, block1.stepSound.getPitch()*0.8F);
                if(world.isRemote){
                    return true;
                }
                else{
                    world.setBlock(x, y, z, block1);
                    stack.damageItem(1, player);
                    return true;
                }
            }
            else{
                return false;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass){
        return pass > 0 ? this.color : super.getColorFromItemStack(stack, pass);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    public boolean requiresMultipleRenderPasses(){
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass){
        return pass > 0 ? this.overlayIcon : super.getIconFromDamageForRenderPass(damage, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":itemPaxel");
        this.overlayIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":itemPaxelOverlay");
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack){
        return this.hasExtraWhitelist(block) || block.getMaterial().isToolNotRequired() || (block == Blocks.snow_layer || block == Blocks.snow || (block == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() >= 3 : (block != Blocks.diamond_block && block != Blocks.diamond_ore ? (block != Blocks.emerald_ore && block != Blocks.emerald_block ? (block != Blocks.gold_block && block != Blocks.gold_ore ? (block != Blocks.iron_block && block != Blocks.iron_ore ? (block != Blocks.lapis_block && block != Blocks.lapis_ore ? (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore ? (block.getMaterial() == Material.rock || (block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2)));
    }

    private boolean hasExtraWhitelist(Block block){
        String name = Block.blockRegistry.getNameForObject(block);
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
    public float getDigSpeed(ItemStack stack, Block block, int meta){
        return this.hasExtraWhitelist(block) || block.getHarvestTool(meta) == null || block.getHarvestTool(meta).isEmpty() || this.getToolClasses(stack).contains(block.getHarvestTool(meta)) ? this.efficiencyOnProperMaterial : 1.0F;
    }
}