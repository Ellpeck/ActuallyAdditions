/*
 * This file ("ItemAllToolAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.tools;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
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
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ItemAllToolAA extends ItemTool implements IActAddItemOrBlock{

    private String name;
    private EnumRarity rarity;
    private String repairItem;

    public ItemAllToolAA(ToolMaterial toolMat, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(4.0F, toolMat, Sets.newHashSet());

        this.repairItem = repairItem;
        this.name = unlocalizedName;
        this.rarity = rarity;

        this.setMaxDamage(this.getMaxDamage()*4);
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
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack){
        return this.hasExtraWhitelist(block) || block.getMaterial().isToolNotRequired() || (block == Blocks.snow_layer || block == Blocks.snow || (block == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (block != Blocks.diamond_block && block != Blocks.diamond_ore ? (block != Blocks.emerald_ore && block != Blocks.emerald_block ? (block != Blocks.gold_block && block != Blocks.gold_ore ? (block != Blocks.iron_block && block != Blocks.iron_ore ? (block != Blocks.lapis_block && block != Blocks.lapis_ore ? (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore ? (block.getMaterial() == Material.rock || (block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2)));
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
    public String getName(){
        return name;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        int[] idsStack = OreDictionary.getOreIDs(stack);
        for(int id : idsStack){
            if(OreDictionary.getOreName(id).equals(repairItem)){
                return true;
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