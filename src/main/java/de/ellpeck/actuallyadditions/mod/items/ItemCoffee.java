/*
 * This file ("ItemCoffee.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

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
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new MilkIngredient(new ItemStack(Items.MILK_BUCKET)));
        //Pam's Soy Milk (For Jemx because he's lactose intolerant. YER HAPPY NAO!?)
        if(Loader.isModLoaded("harvestcraft")){
            Item item = ItemUtil.getItemFromName("harvestcraft:soymilkItem");
            if(item != null){
                ActuallyAdditionsAPI.addCoffeeMachineIngredient(new MilkIngredient(new ItemStack(item)));
            }
        }

        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.SUGAR), new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 30, 0)}, 4));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.MAGMA_CREAM), new PotionEffect[]{new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 0)}, 2));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.FISH, 1, 3), new PotionEffect[]{new PotionEffect(MobEffects.WATER_BREATHING, 10, 0)}, 2));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.GOLDEN_CARROT), new PotionEffect[]{new PotionEffect(MobEffects.NIGHT_VISION, 30, 0)}, 2));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.GHAST_TEAR), new PotionEffect[]{new PotionEffect(MobEffects.REGENERATION, 5, 0)}, 3));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.BLAZE_POWDER), new PotionEffect[]{new PotionEffect(MobEffects.STRENGTH, 15, 0)}, 4));
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(new ItemStack(Items.FERMENTED_SPIDER_EYE), new PotionEffect[]{new PotionEffect(MobEffects.INVISIBILITY, 25, 0)}, 2));
    }

    public static CoffeeIngredient getIngredientFromStack(ItemStack stack){
        for(CoffeeIngredient ingredient : ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS){
            if(ingredient.ingredient.copy().isItemEqual(stack)){
                return ingredient;
            }
        }
        return null;
    }

    public static void applyPotionEffectsFromStack(ItemStack stack, EntityLivingBase player){
        PotionEffect[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
        if(effects != null && effects.length > 0){
            for(PotionEffect effect : effects){
                player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()*20, effect.getAmplifier()));
            }
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player){
        ItemStack theStack = stack.copy();
        super.onItemUseFinish(stack, world, player);
        applyPotionEffectsFromStack(stack, player);
        theStack.setItemDamage(theStack.getItemDamage()+1);
        if(theStack.getMaxDamage()-theStack.getItemDamage() < 0){
            return new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal());
        }
        else{
            return theStack;
        }
    }


    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.DRINK;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        PotionEffect[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
        if(effects != null){
            for(PotionEffect effect : effects){
                list.add(StringUtil.localize(effect.getEffectName())+" "+(effect.getAmplifier()+1)+", "+StringUtils.ticksToElapsedTime(effect.getDuration()*20));
            }
        }
        else{
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+".coffeeCup.noEffect"));
        }
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    public static class MilkIngredient extends CoffeeIngredient{

        public MilkIngredient(ItemStack ingredient){
            super(ingredient, null, 0);
        }

        @Override
        public boolean effect(ItemStack stack){
            PotionEffect[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
            ArrayList<PotionEffect> effectsNew = new ArrayList<PotionEffect>();
            if(effects != null && effects.length > 0){
                for(PotionEffect effect : effects){
                    if(effect.getAmplifier() > 0){
                        effectsNew.add(new PotionEffect(effect.getPotion(), effect.getDuration()+120, effect.getAmplifier()-1));
                    }
                }
                stack.setTagCompound(new NBTTagCompound());
                if(effectsNew.size() > 0){
                    this.effects = effectsNew.toArray(new PotionEffect[effectsNew.size()]);
                    ActuallyAdditionsAPI.methodHandler.addEffectToStack(stack, this);
                }
            }
            this.effects = null;
            return true;
        }

        @Override
        public String getExtraText(){
            return StringUtil.localize("container.nei."+ModUtil.MOD_ID+".coffee.extra.milk");
        }
    }
}