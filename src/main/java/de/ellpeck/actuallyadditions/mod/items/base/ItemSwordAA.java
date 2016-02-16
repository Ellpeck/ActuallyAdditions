/*
 * This file ("ItemSwordAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSwordAA extends ItemSword{

    private String name;
    private EnumRarity rarity;
    private ItemStack repairItem;

    public ItemSwordAA(ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(toolMat);

        this.repairItem = repairItem;
        this.name = unlocalizedName;
        this.rarity = rarity;

        this.register();
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

        this.registerRendering();
    }

    protected String getBaseName(){
        return this.name;
    }

    public boolean shouldAddCreative(){
        return true;
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), new ResourceLocation(ModUtil.MOD_ID_LOWER, this.getBaseName()));
    }

    protected Class<? extends ItemBlockBase> getItemBlock(){
        return ItemBlockBase.class;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        return ItemUtil.areItemsEqual(this.repairItem, stack, false);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }
}
