package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.internal.IEntrySet;
import de.ellpeck.actuallyadditions.api.internal.IMethodHandler;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class MethodHandler implements IMethodHandler{

    @Override
    public boolean addEffectToStack(ItemStack stack, CoffeeIngredient ingredient){
        boolean worked = false;
        if(ingredient != null){
            PotionEffect[] effects = ingredient.getEffects();
            if(effects != null && effects.length > 0){
                for(PotionEffect effect : effects){
                    PotionEffect effectHas = this.getSameEffectFromStack(stack, effect);
                    if(effectHas != null){
                        if(effectHas.getAmplifier() < ingredient.maxAmplifier-1){
                            this.addEffectProperties(stack, effect, false, true);
                            worked = true;
                        }
                    }
                    else{
                        this.addEffectToStack(stack, effect);
                        worked = true;
                    }
                }
            }
        }
        return worked;
    }

    @Override
    public PotionEffect getSameEffectFromStack(ItemStack stack, PotionEffect effect){
        PotionEffect[] effectsStack = this.getEffectsFromStack(stack);
        if(effectsStack != null && effectsStack.length > 0){
            for(PotionEffect effectStack : effectsStack){
                if(effect.getPotion() == effectStack.getPotion()){
                    return effectStack;
                }
            }
        }
        return null;
    }

    @Override
    public void addEffectProperties(ItemStack stack, PotionEffect effect, boolean addDur, boolean addAmp){
        PotionEffect[] effects = this.getEffectsFromStack(stack);
        stack.setTagCompound(new NBTTagCompound());
        for(int i = 0; i < effects.length; i++){
            if(effects[i].getPotion() == effect.getPotion()){
                effects[i] = new PotionEffect(effects[i].getPotion(), effects[i].getDuration()+(addDur ? effect.getDuration() : 0), effects[i].getAmplifier()+(addAmp ? (effect.getAmplifier() > 0 ? effect.getAmplifier() : 1) : 0));
            }
            this.addEffectToStack(stack, effects[i]);
        }
    }

    @Override
    public void addEffectToStack(ItemStack stack, PotionEffect effect){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            tag = new NBTTagCompound();
        }

        int prevCounter = tag.getInteger("Counter");
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("ID", Potion.getIdFromPotion(effect.getPotion()));
        compound.setInteger("Duration", effect.getDuration());
        compound.setInteger("Amplifier", effect.getAmplifier());

        int counter = prevCounter+1;
        tag.setTag(counter+"", compound);
        tag.setInteger("Counter", counter);

        stack.setTagCompound(tag);
    }

    @Override
    public PotionEffect[] getEffectsFromStack(ItemStack stack){
        ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            int counter = tag.getInteger("Counter");
            while(counter > 0){
                NBTTagCompound compound = (NBTTagCompound)tag.getTag(counter+"");
                PotionEffect effect = new PotionEffect(Potion.getPotionById(compound.getInteger("ID")), compound.getInteger("Duration"), compound.getByte("Amplifier"));
                effects.add(effect);
                counter--;
            }
        }
        return effects.size() > 0 ? effects.toArray(new PotionEffect[effects.size()]) : null;
    }
}
