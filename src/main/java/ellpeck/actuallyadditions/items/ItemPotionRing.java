/*
 * This file ("ItemPotionRing.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.base.ItemBase;
import ellpeck.actuallyadditions.items.metalists.ThePotionRings;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemPotionRing extends ItemBase{

    public static final ThePotionRings[] allRings = ThePotionRings.values();

    private boolean isAdvanced;

    public ItemPotionRing(boolean isAdvanced, String name){
        super(name);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.isAdvanced = isAdvanced;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allRings.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allRings[stack.getItemDamage()].name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass){
        return stack.getItemDamage() >= allRings.length ? 0 : allRings[stack.getItemDamage()].color;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5){
        super.onUpdate(stack, world, player, par4, par5);

        if(!world.isRemote && stack.getItemDamage() < allRings.length){
            if(player instanceof EntityPlayer){
                EntityPlayer thePlayer = (EntityPlayer)player;
                ItemStack equippedStack = ((EntityPlayer)player).getCurrentEquippedItem();

                ThePotionRings effect = ThePotionRings.values()[stack.getItemDamage()];
                if(!effect.needsWaitBeforeActivating || !thePlayer.isPotionActive(effect.effectID)){
                    if(!((ItemPotionRing)stack.getItem()).isAdvanced){
                        if(equippedStack != null && stack == equippedStack){
                            thePlayer.addPotionEffect(new PotionEffect(effect.effectID, effect.activeTime, effect.normalAmplifier, true));
                        }
                    }
                    else{
                        thePlayer.addPotionEffect(new PotionEffect(effect.effectID, effect.activeTime, effect.advancedAmplifier, true));
                    }
                }
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        String standardName = StringUtil.localize(this.getUnlocalizedName()+".name");
        if(stack.getItemDamage() < allRings.length){
            String effect = StringUtil.localize(allRings[stack.getItemDamage()].name);
            return standardName+" "+effect;
        }
        return standardName;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allRings.length ? EnumRarity.common : allRings[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allRings.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }
}
