/*
 * This file ("ItemFoods.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemFoods extends ItemFood implements IActAddItemOrBlock{

    public static final TheFoods[] allFoods = TheFoods.values();
    @SideOnly(Side.CLIENT)
    public IIcon[] textures;

    public ItemFoods(){
        super(0, 0.0F, false);
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
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return par1 >= textures.length ? null : textures[par1];
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
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+allFoods[i].name);
        }
    }

    @Override
    public String getName(){
        return "itemFood";
    }
}