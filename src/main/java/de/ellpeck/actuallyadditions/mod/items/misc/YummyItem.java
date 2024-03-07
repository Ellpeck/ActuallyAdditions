package de.ellpeck.actuallyadditions.mod.items.misc;


import de.ellpeck.actuallyadditions.mod.items.base.ActuallyItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class YummyItem extends ActuallyItem {
    private final Foods food;

    public YummyItem(Foods food) {
        super(baseProps().food(food.food));

        this.food = food;
    }

    public enum Foods {
        CHEESE(new FoodProperties.Builder().nutrition(1).saturationMod(0.05F), 3),
        PUMPKIN_STEW(new FoodProperties.Builder().nutrition(6).saturationMod(0.3F), 30, true, new ItemStack(Items.BOWL)),
        CARROT_JUICE(new FoodProperties.Builder().nutrition(4).saturationMod(0.2F), 20, true, new ItemStack(Items.GLASS_BOTTLE)),
        FRENCH_FRY(new FoodProperties.Builder().nutrition(2).saturationMod(0.025F), 3),
        SPAGHETTI(new FoodProperties.Builder().nutrition(7).saturationMod(0.4F), 38, new ItemStack(Items.BOWL)),
        NOODLE(new FoodProperties.Builder().nutrition(1).saturationMod(0.01F), 3),
        CHOCOLATE_CAKE(new FoodProperties.Builder().nutrition(16).saturationMod(0.8F), 45),
        CHOCOLATE(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F), 15),
        TOAST(new FoodProperties.Builder().nutrition(3).saturationMod(0.08F), 25),
        SUBMARINE_SANDWICH(new FoodProperties.Builder().nutrition(9).saturationMod(0.4F), 40),
        BIG_COOKIE(new FoodProperties.Builder().nutrition(4).saturationMod(0.25F), 20),
        HAMBURGER(new FoodProperties.Builder().nutrition(13).saturationMod(0.65F), 40),
        PIZZA(new FoodProperties.Builder().nutrition(16).saturationMod(0.8F), 45),
        BAGUETTE(new FoodProperties.Builder().nutrition(6).saturationMod(0.5F), 25),
        RICE(new FoodProperties.Builder().nutrition(2).saturationMod(0.05F), 10),
        RICE_BREAD(new FoodProperties.Builder().nutrition(6).saturationMod(0.5F), 25),
        DOUGHNUT(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F), 10),
        CHOCOLATE_TOAST(new FoodProperties.Builder().nutrition(5).saturationMod(0.2F), 40),
        BACON(new FoodProperties.Builder().nutrition(4).saturationMod(0.1F), 30);

        FoodProperties food;
        int useDuration;
        boolean isDrunken;
        ItemStack returnItem;

        Foods(FoodProperties.Builder food, int useDuration, boolean isDrunken, ItemStack returnItem) {
            this.food = food.build();
            this.useDuration = useDuration;
            this.isDrunken = isDrunken;
            this.returnItem = returnItem;
        }

        Foods(FoodProperties.Builder food, int useDuration) {
            this(food, useDuration, false, ItemStack.EMPTY);
        }

        Foods(FoodProperties.Builder food, int useDuration, ItemStack returnItem) {
            this(food, useDuration, false, returnItem);
        }
    }
}
