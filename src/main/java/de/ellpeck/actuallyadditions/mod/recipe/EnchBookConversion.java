package de.ellpeck.actuallyadditions.mod.recipe;

import com.google.common.collect.ImmutableMap;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

// TODO: [port][test] check that this works
public class EnchBookConversion extends LensConversionRecipe {

    public EnchBookConversion() {
        super(Ingredient.of(Items.ENCHANTED_BOOK), ItemStack.EMPTY, 155000, ActuallyAdditionsAPI.lensDefaultConversion);
    }

    @Override
    public void transformHook(ItemStack stack, BlockState state, BlockPos pos, IAtomicReconstructor tile) {
        for (Map.Entry<Enchantment, Integer> e : EnchantmentHelper.getEnchantments(stack).entrySet()) {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            Map<Enchantment, Integer> ench = ImmutableMap.of(e.getKey(), e.getValue());
            EnchantmentHelper.setEnchantments(ench, book);
            Block.popResource(tile.getWorldObject(), pos, book);
        }
    }

}
