package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;

public class TreasureChestHandler{

    public static ArrayList<Return> returns = new ArrayList<Return>();

    public static void init(){
        addReturn(new ItemStack(Items.diamond), 5, 1, 2);
        addReturn(new ItemStack(Items.iron_ingot), 30, 1, 5);
        addReturn(new ItemStack(Items.gold_nugget), 60, 1, 8);
        addReturn(new ItemStack(Items.gold_ingot), 35, 1, 3);
        addReturn(new ItemStack(Items.ender_pearl), 10, 1, 2);
        addReturn(new ItemStack(Items.emerald), 3, 1, 1);
        addReturn(new ItemStack(Items.experience_bottle), 5, 3, 6);
    }

    public static void addReturn(ItemStack stack, int chance, int minAmount, int maxAmount){
        returns.add(new Return(stack, chance, minAmount, maxAmount));
    }

    public static class Return extends WeightedRandom.Item{

        public ItemStack returnItem;
        public ItemStack input;
        public int minAmount;
        public int maxAmount;

        public Return(ItemStack returnItem, int chance, int minAmount, int maxAmount){
            super(chance);
            this.returnItem = returnItem;
            this.input = new ItemStack(InitBlocks.blockTreasureChest);
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }

    }

}
