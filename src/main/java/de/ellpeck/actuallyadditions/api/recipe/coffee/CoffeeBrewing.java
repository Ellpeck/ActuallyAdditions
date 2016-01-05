/*
 * This file ("CoffeeBrewing.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe.coffee;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

/**
 * This is a util class for adding Ingredients to ItemStacks
 * Use when making a custom Coffee Ingredient
 */
public class CoffeeBrewing{

    public static boolean addEffectToStack(ItemStack stack, CoffeeIngredient ingredient){
        boolean worked = false;
        if(ingredient != null){
            PotionEffect[] effects = ingredient.getEffects();
            if(effects != null && effects.length > 0){
                for(PotionEffect effect : effects){
                    PotionEffect effectHas = getSameEffectFromStack(stack, effect);
                    if(effectHas != null){
                        if(effectHas.getAmplifier() < ingredient.maxAmplifier-1){
                            addEffectProperties(stack, effect, false, true);
                            worked = true;
                        }
                    }
                    else{
                        addEffectToStack(stack, effect);
                        worked = true;
                    }
                }
            }
        }
        return worked;
    }

    public static PotionEffect getSameEffectFromStack(ItemStack stack, PotionEffect effect){
        PotionEffect[] effectsStack = getEffectsFromStack(stack);
        if(effectsStack != null && effectsStack.length > 0){
            for(PotionEffect effectStack : effectsStack){
                if(effect.getPotionID() == effectStack.getPotionID()){
                    return effectStack;
                }
            }
        }
        return null;
    }

    public static void addEffectProperties(ItemStack stack, PotionEffect effect, boolean addDur, boolean addAmp){
        PotionEffect[] effects = getEffectsFromStack(stack);
        stack.setTagCompound(new NBTTagCompound());
        for(int i = 0; i < effects.length; i++){
            if(effects[i].getPotionID() == effect.getPotionID()){
                effects[i] = new PotionEffect(effects[i].getPotionID(), effects[i].getDuration()+(addDur ? effect.getDuration() : 0), effects[i].getAmplifier()+(addAmp ? (effect.getAmplifier() > 0 ? effect.getAmplifier() : 1) : 0));
            }
            addEffectToStack(stack, effects[i]);
        }
    }

    public static void addEffectToStack(ItemStack stack, PotionEffect effect){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            tag = new NBTTagCompound();
        }

        int prevCounter = tag.getInteger("Counter");
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("ID", effect.getPotionID());
        compound.setInteger("Duration", effect.getDuration());
        compound.setInteger("Amplifier", effect.getAmplifier());

        int counter = prevCounter+1;
        tag.setTag(counter+"", compound);
        tag.setInteger("Counter", counter);

        stack.setTagCompound(tag);
    }

    public static PotionEffect[] getEffectsFromStack(ItemStack stack){
        ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            int counter = tag.getInteger("Counter");
            while(counter > 0){
                NBTTagCompound compound = (NBTTagCompound)tag.getTag(counter+"");
                PotionEffect effect = new PotionEffect(compound.getInteger("ID"), compound.getInteger("Duration"), compound.getByte("Amplifier"));
                if(effect.getPotionID() > 0){
                    effects.add(effect);
                }
                counter--;
            }
        }
        return effects.size() > 0 ? effects.toArray(new PotionEffect[effects.size()]) : null;
    }

}
