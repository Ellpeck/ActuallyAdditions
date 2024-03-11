/*
 * This file ("BasicTradeList.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.village;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class BasicTradeList implements VillagerTrades.ItemListing {

    private final ItemStack input;
    private final PriceRange inputAmount;
    private final ItemStack stack;
    private final PriceRange outputAmount;
	private int maxUses = 3;
	private int villagerXp = 15;
	private float priceMultiplier = 0.05F;

    public BasicTradeList(ItemStack input, PriceRange inputAmount, ItemStack stack, PriceRange outputAmount) {
        this.input = input;
        this.inputAmount = inputAmount;
        this.stack = stack;
        this.outputAmount = outputAmount;
    }

    public BasicTradeList(PriceRange emeraldInput, ItemStack stack, PriceRange outputAmount) {
        this(new ItemStack(Items.EMERALD), emeraldInput, stack, outputAmount);
    }

    public BasicTradeList(ItemStack input, PriceRange inputAmount, PriceRange emeraldOutput) {
        this(input, inputAmount, new ItemStack(Items.EMERALD), emeraldOutput);
    }

	public BasicTradeList withMaxUses(int maxUses) {
		this.maxUses = maxUses;
		return this;
	}

	public BasicTradeList withVillagerXp(int villagerXp) {
		this.villagerXp = villagerXp;
		return this;
	}

	public BasicTradeList withPriceMultiplier(float priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
		return this;
	}

	@Nullable
	@Override
	public MerchantOffer getOffer(Entity trader, RandomSource random) {
		ItemStack in = this.input.copy();
		in.setCount(this.inputAmount.getPrice(random));
		ItemStack out = this.stack.copy();
		out.setCount(this.outputAmount.getPrice(random));
		return new MerchantOffer(in, out, this.maxUses, this.villagerXp, this.priceMultiplier);
	}

	public static class PriceRange extends Tuple<Integer, Integer> {
		public PriceRange(int min, int max) {
			super(Integer.valueOf(min), Integer.valueOf(max));

			if (max < min) {
				ActuallyAdditions.LOGGER.warn("PriceRange({}, {}) invalid, {} smaller than {}", Integer.valueOf(min), Integer.valueOf(max), Integer.valueOf(max), Integer.valueOf(min));
			}
		}

		public int getPrice(RandomSource rand) {
			return this.getA() >= this.getB() ? this.getA() : this.getA() + rand.nextInt(this.getB() - this.getA() + 1);
		}
	}
}
