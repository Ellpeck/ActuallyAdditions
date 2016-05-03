/*
 * This file ("ItemJams.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemJams extends ItemFoodBase implements IColorProvidingItem{

    public static final TheJams[] allJams = TheJams.values();

    public ItemJams(String name){
        super(0, 0.0F, false, name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setAlwaysEdible();
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allJams[stack.getItemDamage()].name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? EnumRarity.COMMON : allJams[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allJams.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player){
        ItemStack stackToReturn = super.onItemUseFinish(stack, world, player);

        if(player instanceof EntityPlayer && !world.isRemote && stack.getItemDamage() < allJams.length){
            PotionEffect firstEffectToGet = new PotionEffect(Potion.getPotionById(allJams[stack.getItemDamage()].firstEffectToGet), 200);
            player.addPotionEffect(firstEffectToGet);

            PotionEffect secondEffectToGet = new PotionEffect(Potion.getPotionById(allJams[stack.getItemDamage()].secondEffectToGet), 600);
            player.addPotionEffect(secondEffectToGet);

            ItemStack returnItem = new ItemStack(Items.GLASS_BOTTLE);
            if(!((EntityPlayer)player).inventory.addItemStackToInventory(returnItem.copy())){
                EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem.copy());
                entityItem.setPickupDelay(0);
                player.worldObj.spawnEntityInWorld(entityItem);
            }
        }
        return stackToReturn;
    }

    @Override
    public int getHealAmount(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].healAmount;
    }

    @Override
    public float getSaturationModifier(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].saturation;
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < allJams.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ModelResourceLocation(this.getRegistryName(), "inventory"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IItemColor getColor(){
        return new IItemColor(){
            @Override
            public int getColorFromItemstack(ItemStack stack, int pass){
                return pass > 0 ? (stack.getItemDamage() >= allJams.length ? 0xFFFFFF : allJams[stack.getItemDamage()].color) : 0xFFFFFF;
            }
        };
    }
}