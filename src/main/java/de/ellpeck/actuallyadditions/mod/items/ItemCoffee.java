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
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCoffee extends ItemFoodBase {

    public ItemCoffee() {
        super(8, 5.0F, false); //, name);
        //this.setMaxDamage(3);
        //this.setAlwaysEdible();
        //this.setMaxStackSize(1);
        //this.setNoRepair();
    }

    public static void initIngredients() {
        ActuallyAdditionsAPI.addCoffeeMachineIngredient(new MilkIngredient(Ingredient.of(Items.MILK_BUCKET)));
        //Pam's Soy Milk (For Jemx because he's lactose intolerant. YER HAPPY NAO!?)
        if (ModList.get().isLoaded("harvestcraft")) {
            Item item = ItemUtil.getItemFromName("harvestcraft:soymilkitem");
            if (item != null) {
                ActuallyAdditionsAPI.addCoffeeMachineIngredient(new MilkIngredient(Ingredient.of(item)));
            }
        }

        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(Items.SUGAR), 4, new PotionEffect(MobEffects.SPEED, 30, 0)));
        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(Items.MAGMA_CREAM), 2, new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 0)));
        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(new ItemStack(Items.FISH, 1, 3)), 2, new PotionEffect(MobEffects.WATER_BREATHING, 10, 0)));
        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(Items.GOLDEN_CARROT), 2, new PotionEffect(MobEffects.NIGHT_VISION, 30, 0)));
        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(Items.GHAST_TEAR), 3, new PotionEffect(MobEffects.REGENERATION, 5, 0)));
        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(Items.BLAZE_POWDER), 4, new PotionEffect(MobEffects.STRENGTH, 15, 0)));
        //ActuallyAdditionsAPI.addCoffeeMachineIngredient(new CoffeeIngredient(Ingredient.of(Items.FERMENTED_SPIDER_EYE), 2, new PotionEffect(MobEffects.INVISIBILITY, 25, 0)));
    }

    @Nullable
    public static CoffeeIngredient getIngredientFromStack(ItemStack stack) {
        for (CoffeeIngredient ingredient : ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS) {
            if (ingredient.getInput().test(stack)) {
                return ingredient;
            }
        }
        return null;
    }

    public static void applyPotionEffectsFromStack(ItemStack stack, LivingEntity player) {/*
        PotionEffect[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
        if (effects != null && effects.length > 0) {
            for (PotionEffect effect : effects) {
                player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() * 20, effect.getAmplifier()));
            }
        }
        */
    }

    //@Override
    public ItemStack onItemUseFinish(ItemStack stack, Level world, LivingEntity player) {
        ItemStack theStack = stack.copy();
        super.finishUsingItem(stack, world, player);
        applyPotionEffectsFromStack(stack, player);
        //theStack.setItemDamage(theStack.getItemDamage() + 1);
        //if (theStack.getMaxDamage() - theStack.getItemDamage() < 0) {
        //    return new ItemStack(ActuallyItems.COFFEE_CUP.get());
        //} else {
        //    return theStack;
        //}
        return ItemStack.EMPTY;
    }

    //@Override
    //public EnumAction getItemUseAction(ItemStack stack) {
    //    return EnumAction.DRINK;
    //}

//    @Nullable
//    @Override
//    public CompoundTag getShareTag(ItemStack stack) {
//        return super.getShareTag(stack);
//    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level playerIn, List<Component> tooltip, TooltipFlag advanced) {
        //PotionEffect[] effects = ActuallyAdditionsAPI.methodHandler.getEffectsFromStack(stack);
        //if (effects != null) {
        //    for (PotionEffect effect : effects) {
        //        tooltip.add(StringUtil.localize(effect.getEffectName()) + " " + (effect.getAmplifier() + 1) + ", " + StringUtils.formatTickDuration(effect.getDuration() * 20));
        //    }
        //} else {
        //   tooltip.add(StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".coffeeCup.noEffect"));
        //}
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
            return I18n.get("container.nei." + ActuallyAdditions.MODID + ".coffee.extra.milk");
        }
    }
}
