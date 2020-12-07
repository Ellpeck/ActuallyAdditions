package de.ellpeck.actuallyadditions.common.materials;

import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

/**
 * Complete copy paste from {@link net.minecraft.item.ItemTier}
 *
 * todo: review to ensure all values act as intended
 */
public enum ToolMaterials implements IItemTier {
    QUARTZ(2, 280, 6.5f, 2.0f, 10, () -> Ingredient.fromItems(ActuallyItems.BLACK_QUARTS.get())),
    RESTONIA(2, 300, 7.0f, 2.25f, 12, () -> Ingredient.fromItems(ActuallyItems.RESTONIA_CRYSTAL.get())),
    PALIS(2, 300, 7.0f, 2.25f, 12, () -> Ingredient.fromItems(ActuallyItems.PALIS_CRYSTAL.get())),
    DIAMATINE(3, 1600, 9.0f, 4.0f, 14, () -> Ingredient.fromItems(ActuallyItems.DIAMATINE_CRYSTAL.get())),
    VOID(2, 280, 6.0f, 2.0f, 8, () -> Ingredient.fromItems(ActuallyItems.VOID_CRYSTAL.get())),
    EMERADIC(4, 2200, 9.5f, 5.55f, 18, () -> Ingredient.fromItems(ActuallyItems.EMERADIC_CRYSTAL.get())),
    ENORI(2, 280, 6.25f, 6.25f, 15, () -> Ingredient.fromItems(ActuallyItems.ENORI_CRYSTAL.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    ToolMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
