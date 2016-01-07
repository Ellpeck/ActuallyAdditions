/*
 * This file ("ItemCoffee.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.coffee.CoffeeBrewing;
import de.ellpeck.actuallyadditions.api.recipe.coffee.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemCoffee extends ItemFoodBase{

    public ItemCoffee(String name){
        super(8, 5.0F, false, name);
        this.setMaxDamage(3);
        this.setAlwaysEdible();
        this.setMaxStackSize(1);
        this.setNoRepair();
    }

    public static void initIngredients(){
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new MilkIngredient(new ItemStack(Items.milk_bucket)));
        //Pam's Soy Milk (For Jemx because he's lactose intolerant. YER HAPPY NAO!?)
        if(Loader.isModLoaded("harvestcraft")){
            Item item = ItemUtil.getItemFromName("harvestcraft:soymilkItem");
            if(item != null){
                ActuallyAdditionsAPI.addCoffeeMachineIngredient(new MilkIngredient(new ItemStack(item)));
            }
        }

        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.sugar), new PotionEffect[]{new PotionEffect(Potion.moveSpeed.getId(), 30, 0)}, 4));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.magma_cream), new PotionEffect[]{new PotionEffect(Potion.fireResistance.getId(), 20, 0)}, 2));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.fish, 1, 3), new PotionEffect[]{new PotionEffect(Potion.waterBreathing.getId(), 10, 0)}, 2));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.golden_carrot), new PotionEffect[]{new PotionEffect(Potion.nightVision.getId(), 30, 0)}, 2));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.ghast_tear), new PotionEffect[]{new PotionEffect(Potion.regeneration.getId(), 5, 0)}, 3));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.blaze_powder), new PotionEffect[]{new PotionEffect(Potion.damageBoost.getId(), 15, 0)}, 4));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.fermented_spider_eye), new PotionEffect[]{new PotionEffect(Potion.invisibility.getId(), 25, 0)}, 2));
    }

    public static CoffeeIngredient getIngredientFromStack(ItemStack stack){
        for(CoffeeIngredient ingredient : ActuallyAdditionsAPI.coffeeMachineIngredients){
            if(ingredient.ingredient.copy().isItemEqual(stack)){
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
        ItemStack theStack = stack.copy();
        super.onEaten(stack, world, player);
        applyPotionEffectsFromStack(stack, player);
        theStack.setItemDamage(theStack.getItemDamage()+1);
        if(theStack.getMaxDamage()-theStack.getItemDamage() < 0){
            return new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal());
        }
        else{
            return theStack;
        }
    }

    public static void applyPotionEffectsFromStack(ItemStack stack, EntityPlayer player){
        PotionEffect[] effects = CoffeeBrewing.getEffectsFromStack(stack);
        if(effects != null && effects.length > 0){
            for(PotionEffect effect : effects){
                player.addPotionEffect(new PotionEffect(effect.getPotionID(), effect.getDuration()*20, effect.getAmplifier()));
            }
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.drink;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return this.itemIcon;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        PotionEffect[] effects = CoffeeBrewing.getEffectsFromStack(stack);
        if(effects != null){
            for(PotionEffect effect : effects){
                list.add(StringUtil.localize(effect.getEffectName())+" "+(effect.getAmplifier()+1)+", "+StringUtils.ticksToElapsedTime(effect.getDuration()*20));
            }
        }
        else{
            list.add("No Effects");
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    public static class MilkIngredient extends CoffeeIngredient{

        public MilkIngredient(ItemStack ingredient){
            super(ingredient, null, 0);
        }

        @Override
        public boolean effect(ItemStack stack){
            PotionEffect[] effects = CoffeeBrewing.getEffectsFromStack(stack);
            ArrayList<PotionEffect> effectsNew = new ArrayList<PotionEffect>();
            if(effects != null && effects.length > 0){
                for(PotionEffect effect : effects){
                    if(effect.getAmplifier() > 0){
                        effectsNew.add(new PotionEffect(effect.getPotionID(), effect.getDuration()+120, effect.getAmplifier()-1));
                    }
                }
                stack.setTagCompound(new NBTTagCompound());
                if(effectsNew.size() > 0){
                    this.effects = effectsNew.toArray(new PotionEffect[effectsNew.size()]);
                    CoffeeBrewing.addEffectToStack(stack, this);
                }
            }
            this.effects = null;
            return true;
        }

        @Override
        public String getExtraText(){
            return StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".coffee.extra.milk");
        }
    }
}