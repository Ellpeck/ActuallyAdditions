/*
 * This file ("ItemJams.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ActuallyItem;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemJams extends ActuallyItem {

    public static final TheJams[] ALL_JAMS = TheJams.values();

    public ItemJams() {
        super(baseProps().food(new Food.Builder().alwaysEat().build()));
    }


    //@Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity player) {
        ItemStack stackToReturn = super.finishUsingItem(stack, world, player);
/*
        if (player instanceof PlayerEntity && !world.isClientSide && stack.getItemDamage() < ALL_JAMS.length) {
            Effect firstEffectToGet = new Effect(Potion.getPotionById(ALL_JAMS[stack.getItemDamage()].firstEffectToGet), 200);
            player.addEffect(firstEffectToGet);

            Effect secondEffectToGet = new Effect(Potion.getPotionById(ALL_JAMS[stack.getItemDamage()].secondEffectToGet), 600);
            player.addEffect(secondEffectToGet);

            ItemStack returnItem = new ItemStack(Items.GLASS_BOTTLE);
            if (!((PlayerEntity) player).inventory.add(returnItem.copy())) {
                ItemEntity entityItem = new ItemEntity(player.world, player.posX, player.posY, player.posZ, returnItem.copy());
                entityItem.setPickUpDelay(0);
                player.world.addEntity(entityItem);
            }
        }

 */
        return stackToReturn;
    }

    /*
    @Override
    public int getHealAmount(ItemStack stack) {
        return stack.getItemDamage() >= ALL_JAMS.length
            ? 0
            : ALL_JAMS[stack.getItemDamage()].healAmount;
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        return stack.getItemDamage() >= ALL_JAMS.length
            ? 0
            : ALL_JAMS[stack.getItemDamage()].saturation;
    }

     */
}
