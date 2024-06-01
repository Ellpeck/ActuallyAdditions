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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CoffeeIngredientRecipe;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCoffee extends ItemBase { //TODO: Do we want to change the saturation? Currently it's 5.0F which becomes 40 in game. That's a lot of saturation.
    private static final FoodProperties FOOD = new FoodProperties.Builder().nutrition(8).saturationMod(5.0F).alwaysEat().build();

    public ItemCoffee() {
        super(ActuallyItems.defaultProps().food(FOOD).durability(3));
    }

    @Nullable
    public static RecipeHolder<CoffeeIngredientRecipe> getIngredientRecipeFromStack(ItemStack stack) {
        for (RecipeHolder<CoffeeIngredientRecipe> recipeHolder : ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS) {
            if (recipeHolder.value().getIngredient().test(stack)) {
                return recipeHolder;
            }
        }
        return null;
    }

    public static void applyPotionEffectsFromStack(ItemStack stack, LivingEntity player) {
        MobEffectInstance[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
        if (effects != null && effects.length > 0) {
            for (MobEffectInstance effect : effects) {
                player.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration() * 20, effect.getAmplifier()));
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack theStack = stack.copy();
        super.finishUsingItem(stack, level, livingEntity);
        applyPotionEffectsFromStack(stack, livingEntity);
        theStack.setDamageValue(theStack.getDamageValue() + 1);
        if (theStack.getMaxDamage() - theStack.getDamageValue() < 0) {
            return new ItemStack(ActuallyItems.EMPTY_CUP.get());
        } else {
            return theStack;
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level playerIn, List<Component> tooltip, TooltipFlag advanced) {
        MobEffectInstance[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
        if (effects != null) {
            for (MobEffectInstance effect : effects) {
                tooltip.add(Component.translatable(effect.getDescriptionId())
                        .append(" " + (effect.getAmplifier() + 1) + ", " + StringUtil.formatTickDuration(effect.getDuration(), 1))
                        .withStyle(ChatFormatting.GRAY));
            }
        } else {
           tooltip.add(Component.translatable("tooltip.actuallyadditions.coffeeCup.noEffect").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public static class MilkIngredient extends CoffeeIngredient {

        public MilkIngredient(Ingredient ingredient) {
            super(ingredient, 0);
        }

        @Override
        public boolean effect(ItemStack stack) {
            //PotionEffect[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
            //ArrayList<PotionEffect> effectsNew = new ArrayList<>();
            if (effects != null && effects.length > 0) {
/*                for (PotionEffect effect : effects) {
                    if (effect.getAmplifier() > 0) {
                        effectsNew.add(new PotionEffect(effect.getPotion(), effect.getDuration() + 120, effect.getAmplifier() - 1));
                    }
                }
                stack.setTagCompound(new CompoundNBT());
                if (effectsNew.size() > 0) {
                    this.effects = effectsNew.toArray(new PotionEffect[effectsNew.size()]);
                    ActuallyAdditionsAPI.methodHandler.addEffectToStack(stack, this);
                }*/
            }
            this.effects = null;
            return true;
        }

        @Override
        public String getExtraText() {
            return I18n.get("jei.actuallyadditions.coffee.extra.milk");
        }
    }
}
