package de.ellpeck.actuallyadditions.mod.material;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * Complete copy paste from {@link net.minecraft.world.item.ArmorMaterial}
 * <p>
 * todo validate all values refect correctly
 */
public enum ArmorMaterials implements ArmorMaterial {
    //    EMERALD("emerald_armor_material", 30,  createProtectionMap( 5, 8, 9, 4 ), 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2, 0f, () -> Ingredient.fromItems(Items.EMERALD)),
    //    OBSIDIAN("obsidian_armor_material", 28, createProtectionMap( 1, 3, 4, 3 ), 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 0f, () -> Ingredient.fromItems(Items.OBSIDIAN)),

    QUARTZ("quartz_armor_material", 15, createProtectionMap(3, 5, 6, 3), 8, SoundEvents.ARMOR_EQUIP_GENERIC, 1, 0f, () -> Ingredient.of(ActuallyItems.BLACK_QUARTZ.get())),
    RESTONIA("restonia_armor_material", 18, createProtectionMap(3, 6, 7, 3), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.RESTONIA_CRYSTAL.get())),
    PALIS("palis_armor_material", 10, createProtectionMap(3, 6, 7, 3), 10, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.PALIS_CRYSTAL.get())),
    DIAMATINE("diamatine_armor_material", 36, createProtectionMap(4, 7, 8, 4), 12, SoundEvents.ARMOR_EQUIP_GENERIC, 3, 0f, () -> Ingredient.of(ActuallyItems.DIAMATINE_CRYSTAL.get())),
    VOID("void_armor_material", 23, createProtectionMap(1, 3, 4, 1), 13, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.VOID_CRYSTAL.get())),
    EMERADIC("emeradic_armor_material", 32, createProtectionMap(6, 9, 9, 4), 18, SoundEvents.ARMOR_EQUIP_GENERIC, 3, 0f, () -> Ingredient.of(ActuallyItems.EMERADIC_CRYSTAL.get())),
    ENORI("enori_armor_material", 24, createProtectionMap(3, 6, 6, 3), 11, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.ENORI_CRYSTAL.get())),
    GOGGLES("goggles_armor_material", 0, createProtectionMap(0, 0, 0, 0), 0, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.EMPTY);

    private static EnumMap<ArmorItem.Type, Integer> createProtectionMap(int boots, int leggings, int chest, int helmet) {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.CHESTPLATE, chest);
            map.put(ArmorItem.Type.HELMET, helmet);
        });
    }

    // HMMM Tasty stolen code from MC, how lovely.
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionFunctionForType, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = String.format("%s:%s", ActuallyAdditions.MODID, name);
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protectionFunctionForType;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
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
