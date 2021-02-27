package de.ellpeck.actuallyadditions.mod.material;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

/**
 * Complete copy paste from {@link net.minecraft.item.ArmorMaterial}
 * <p>
 * todo validate all values refect correctly
 */
public enum ArmorMaterials implements IArmorMaterial {
    //    EMERALD("emerald_armor_material", 30,  new int[] { 5, 8, 9, 4 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2, 0f, () -> Ingredient.fromItems(Items.EMERALD)),
    //    OBSIDIAN("obsidian_armor_material", 28, new int[] { 1, 3, 4, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 0f, () -> Ingredient.fromItems(Items.OBSIDIAN)),

    QUARTZ("quartz_armor_material", 15, new int[]{3, 5, 6, 3}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 0f, () -> Ingredient.fromItems(InitItems.BLACK_QUARTZ.get())),
    RESTONIA("restonia_armor_material", 18, new int[]{3, 6, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.fromItems(InitItems.RESTONIA_CRYSTAL.get())),
    PALIS("palis_armor_material", 10, new int[]{3, 6, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.fromItems(InitItems.PALIS_CRYSTAL.get())),
    DIAMATINE("diamatine_armor_material", 36, new int[]{4, 7, 8, 4}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3, 0f, () -> Ingredient.fromItems(InitItems.DIAMATINE_CRYSTAL.get())),
    VOID("void_armor_material", 23, new int[]{1, 3, 4, 1}, 13, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.fromItems(InitItems.VOID_CRYSTAL.get())),
    EMERADIC("emeradic_armor_material", 32, new int[]{6, 9, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3, 0f, () -> Ingredient.fromItems(InitItems.EMERADIC_CRYSTAL.get())),
    ENORI("enori_armor_material", 24, new int[]{3, 6, 6, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.fromItems(InitItems.ENORI_CRYSTAL.get())),
    GOGGLES("goggles_armor_material", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.EMPTY);
    
    // HMMM Tasty stolen code from MC, how lovely.
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    ArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = String.format("%s:%s", ActuallyAdditions.MODID, name);
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
