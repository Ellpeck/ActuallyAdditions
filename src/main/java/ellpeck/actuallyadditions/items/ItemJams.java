/*
 * This file ("ItemJams.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.base.ItemFoodBase;
import ellpeck.actuallyadditions.items.metalists.TheJams;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemJams extends ItemFoodBase{

    public static final TheJams[] allJams = TheJams.values();
    @SideOnly(Side.CLIENT)
    public IIcon overlayIcon;

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
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass){
        return pass > 0 ? (stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].color) : super.getColorFromItemStack(stack, pass);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? EnumRarity.common : allJams[stack.getItemDamage()].rarity;
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

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allJams.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.overlayIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Overlay");
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
        ItemStack stackToReturn = super.onEaten(stack, world, player);

        if(!world.isRemote && stack.getItemDamage() < allJams.length){
            PotionEffect firstEffectToGet = new PotionEffect(allJams[stack.getItemDamage()].firstEffectToGet, 200);
            player.addPotionEffect(firstEffectToGet);

            PotionEffect secondEffectToGet = new PotionEffect(allJams[stack.getItemDamage()].secondEffectToGet, 600);
            player.addPotionEffect(secondEffectToGet);

            ItemStack returnItem = new ItemStack(Items.glass_bottle);
            if(!player.inventory.addItemStackToInventory(returnItem.copy())){
                EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem.copy());
                entityItem.delayBeforeCanPickup = 0;
                player.worldObj.spawnEntityInWorld(entityItem);
            }
        }
        return stackToReturn;
    }

    @Override
    public int func_150905_g(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].healAmount;
    }

    @Override
    public float func_150906_h(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].saturation;
    }
}