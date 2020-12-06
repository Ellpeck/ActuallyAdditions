package de.ellpeck.actuallyadditions.common.items.misc;

import de.ellpeck.actuallyadditions.common.items.ActuallyItem;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class YummyItem extends ActuallyItem {
    private final Foods food;

    public YummyItem(Foods food) {
        super(baseProps().food(food.food));

        this.food = food;
    }

    public enum Foods {
        CHEESE(new Food.Builder().hunger(1).saturation(0.05F), 3),
        PUMPKIN_STEW(new Food.Builder().hunger(6).saturation(0.3F), 30, true, new ItemStack(Items.BOWL)),
        CARROT_JUICE(new Food.Builder().hunger(4).saturation(0.2F), 20, true, new ItemStack(Items.GLASS_BOTTLE)),
        FISH_N_CHIPS(new Food.Builder().hunger(14).saturation(0.65F), 40, new ItemStack(ActuallyItems.PAPER_CONE.get())),
        FRENCH_FRIES(new Food.Builder().hunger(10).saturation(0.6F), 32, new ItemStack(ActuallyItems.PAPER_CONE.get())),
        FRENCH_FRY(new Food.Builder().hunger(2).saturation(0.025F), 3),
        SPAGHETTI(new Food.Builder().hunger(7).saturation(0.4F), 38, new ItemStack(Items.BOWL)),
        NOODLE(new Food.Builder().hunger(1).saturation(0.01F), 3),
        CHOCOLATE_CAKE(new Food.Builder().hunger(16).saturation(0.8F), 45),
        CHOCOLATE(new Food.Builder().hunger(3).saturation(0.3F), 15),
        TOAST(new Food.Builder().hunger(3).saturation(0.08F), 25),
        SUBMARINE_SANDWICH(new Food.Builder().hunger(9).saturation(0.4F), 40),
        BIG_COOKIE(new Food.Builder().hunger(4).saturation(0.25F), 20),
        HAMBURGER(new Food.Builder().hunger(13).saturation(0.65F), 40),
        PIZZA(new Food.Builder().hunger(16).saturation(0.8F), 45),
        BAGUETTE(new Food.Builder().hunger(6).saturation(0.5F), 25),
        RICE(new Food.Builder().hunger(2).saturation(0.05F), 10),
        RICE_BREAD(new Food.Builder().hunger(6).saturation(0.5F), 25),
        DOUGHNUT(new Food.Builder().hunger(2).saturation(0.1F), 10),
        CHOCOLATE_TOAST(new Food.Builder().hunger(5).saturation(0.2F), 40),
        BACON(new Food.Builder().hunger(4).saturation(0.1F), 30);

        Food food;
        int useDuration;
        boolean isDrunken;
        ItemStack returnItem;

        Foods(Food.Builder food, int useDuration, boolean isDrunken, ItemStack returnItem) {
            this.food = food.build();
            this.useDuration = useDuration;
            this.isDrunken = isDrunken;
            this.returnItem = returnItem;
        }

        Foods(Food.Builder food, int useDuration) {
            this(food, useDuration, false, ItemStack.EMPTY);
        }

        Foods(Food.Builder food, int useDuration, ItemStack returnItem) {
            this(food, useDuration, false, returnItem);
        }
    }
}
