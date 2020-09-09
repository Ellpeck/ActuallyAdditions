package de.ellpeck.actuallyadditions.common.crafting;

import de.ellpeck.actuallyadditions.common.items.ItemPotionRing;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.crafting.RecipeHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipePotionRingCharging extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public RecipePotionRingCharging(ResourceLocation location) {
        RecipeHelper.addRecipe(location.getPath(), this);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasRing = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (StackUtil.isValid(stack)) {
                if (stack.getItem() instanceof ItemPotionRing) {
                    if (!hasRing) {
                        hasRing = true;
                    } else {
                        return false;
                    }
                } else if (stack.getItem() != Items.BLAZE_POWDER) { return false; }
            }
        }

        return hasRing;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack inputRing = StackUtil.getEmpty();
        int totalBlaze = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (StackUtil.isValid(stack)) {
                if (stack.getItem() instanceof ItemPotionRing) {
                    inputRing = stack;
                } else if (stack.getItem() == Items.BLAZE_POWDER) {
                    totalBlaze += 20;
                }
            }
        }

        if (StackUtil.isValid(inputRing) && totalBlaze > 0) {
            ItemStack copy = inputRing.copy();

            int total = ItemPotionRing.getStoredBlaze(copy) + totalBlaze;
            if (total <= ItemPotionRing.MAX_BLAZE) {
                ItemPotionRing.setStoredBlaze(copy, total);
                return copy;
            }
        }

        return StackUtil.getEmpty();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height > 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return StackUtil.getEmpty();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}
