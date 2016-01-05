/*
 * This file ("ItemFoods.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemFoods extends ItemFoodBase{

    public static final TheFoods[] allFoods = TheFoods.values();
    @SideOnly(Side.CLIENT)
    public IIcon[] textures;
    @SideOnly(Side.CLIENT)
    private IIcon iconEllspeck;

    private static final String ELLSPECK = "ellspeck";

    public ItemFoods(String name){
        super(0, 0.0F, false, name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        TheFoods.setReturnItems();
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
        ItemStack stackToReturn = super.onEaten(stack, world, player);
        ItemStack returnItem = stack.getItemDamage() >= allFoods.length ? null : allFoods[stack.getItemDamage()].returnItem;
        if(returnItem != null){
            if(!player.inventory.addItemStackToInventory(returnItem.copy())){
                if(!world.isRemote){
                    EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem.copy());
                    entityItem.delayBeforeCanPickup = 0;
                    player.worldObj.spawnEntityInWorld(entityItem);
                }
            }
        }
        return stackToReturn;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? 0 : allFoods[stack.getItemDamage()].useDuration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? EnumAction.eat : (allFoods[stack.getItemDamage()].getsDrunken ? EnumAction.drink : EnumAction.eat);
    }

    @Override
    public int func_150905_g(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? 0 : allFoods[stack.getItemDamage()].healAmount;
    }

    @Override
    public float func_150906_h(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? 0 : allFoods[stack.getItemDamage()].saturation;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return getIconIndex(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack){
        int damage = stack.getItemDamage();
        if(damage == TheFoods.BACON.ordinal() && StringUtil.equalsToLowerCase(stack.getDisplayName(), ELLSPECK)){
            return this.iconEllspeck;
        }
        else{
            return damage >= textures.length ? null : textures[damage];
        }
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allFoods[stack.getItemDamage()].name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? EnumRarity.common : allFoods[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allFoods.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.textures = new IIcon[allFoods.length];
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+allFoods[i].name);
        }
        this.iconEllspeck = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":itemEllspeck");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        if(stack.getItemDamage() == TheFoods.BACON.ordinal() && StringUtil.equalsToLowerCase(stack.getDisplayName(), ELLSPECK)){
            String strg = "Yes, this is an ugly texture of bacon with its legs behind its head. This is an homage to Ellpeck, the mod author, being able to put his legs behind his head. Wasn't my idea, so don't judge me.";
            list.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(strg, 200));
        }
    }
}