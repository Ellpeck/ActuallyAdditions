/*
 * This file ("ItemArmorAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class ItemArmorAA extends ItemArmor implements IActAddItemOrBlock{

    private String repairItem;
    private String name;
    private String[] textures;

    public ItemArmorAA(String name, ArmorMaterial material, int type, String repairItem, String textureBase){
        super(material, 0, type);
        this.repairItem = repairItem;
        this.name = name;
        String texture = ModUtil.MOD_ID_LOWER+":textures/armor/"+textureBase;
        textures = new String[]{texture+"1.png", texture+"2.png"};

        //Fixes vanilla's weird durability handling
        this.setMaxDamage(material.getDurability(type)/10);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public String getName(){
        return this.name;
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
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
        return this.textures[slot == 2 ? 1 : 0];
    }
}
