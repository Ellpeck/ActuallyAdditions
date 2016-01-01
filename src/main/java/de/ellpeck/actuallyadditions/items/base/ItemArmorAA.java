/*
 * This file ("ItemArmorAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.base;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.creative.CreativeTab;
import de.ellpeck.actuallyadditions.util.ItemUtil;
import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemArmorAA extends ItemArmor{

    private ItemStack repairItem;
    private String name;
    private String[] textures;
    private EnumRarity rarity;

    public ItemArmorAA(String name, ArmorMaterial material, int type, ItemStack repairItem, String textureBase, EnumRarity rarity){
        super(material, 0, type);
        this.repairItem = repairItem;
        this.name = name;
        String texture = ModUtil.MOD_ID_LOWER+":textures/armor/"+textureBase;
        textures = new String[]{texture+"1.png", texture+"2.png"};
        this.rarity = rarity;

        this.register();
    }

    public ItemArmorAA(String name, ArmorMaterial material, int type, ItemStack repairItem, String textureBase){
        this(name, material, type, repairItem, textureBase, EnumRarity.rare);
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
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
        return this.textures[slot == 2 ? 1 : 0];
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        return ItemUtil.areItemsEqual(this.repairItem, stack, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }
}
