/*
 * This file ("ItemToolAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

public class ItemToolAA extends ItemTool{

    private final String name;
    private final EnumRarity rarity;
    private final ItemStack repairItem;
    private String repairOredict;

    public ItemToolAA(float attack, float speed, ToolMaterial toolMat, String repairItem, String unlocalizedName, EnumRarity rarity, Set<Block> effectiveStuff){
        this(attack, speed, toolMat, (ItemStack)null, unlocalizedName, rarity, effectiveStuff);
        this.repairOredict = repairItem;
    }

    public ItemToolAA(float attack, float speed, ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, EnumRarity rarity, Set<Block> effectiveStuff){
        super(attack, speed, toolMat, effectiveStuff);

        this.repairItem = repairItem;
        this.name = unlocalizedName;
        this.rarity = rarity;

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
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        if(StackUtil.isValid(this.repairItem)){
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
}
