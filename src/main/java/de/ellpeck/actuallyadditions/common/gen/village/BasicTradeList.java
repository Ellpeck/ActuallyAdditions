package de.ellpeck.actuallyadditions.common.gen.village;

import java.util.Random;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class BasicTradeList implements ITradeList {

    private final ItemStack input;
    private final PriceInfo inputAmount;
    private final ItemStack output;
    private final PriceInfo outputAmount;

    public BasicTradeList(ItemStack input, PriceInfo inputAmount, ItemStack output, PriceInfo outputAmount) {
        this.input = input;
        this.inputAmount = inputAmount;
        this.output = output;
        this.outputAmount = outputAmount;
    }

    public BasicTradeList(PriceInfo emeraldInput, ItemStack output, PriceInfo outputAmount) {
        this(new ItemStack(Items.EMERALD), emeraldInput, output, outputAmount);
    }

    public BasicTradeList(ItemStack input, PriceInfo inputAmount, PriceInfo emeraldOutput) {
        this(input, inputAmount, new ItemStack(Items.EMERALD), emeraldOutput);
    }

    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        ItemStack in = this.input.copy();
        in.setCount(this.inputAmount.getPrice(random));
        ItemStack out = this.output.copy();
        out.setCount(this.outputAmount.getPrice(random));
        recipeList.add(new MerchantRecipe(in, out));
    }
}
