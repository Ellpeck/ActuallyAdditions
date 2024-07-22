package de.ellpeck.actuallyadditions.mod.material;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Complete copy paste from {@link net.minecraft.world.item.ArmorMaterial}
 * <p>
 * //TODO validate all values refect correctly
 */
public class ArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, ActuallyAdditions.MODID);
    //    EMERALD("emerald_armor_material", 30,  createProtectionMap( 5, 8, 9, 4 ), 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2, 0f, () -> Ingredient.fromItems(Items.EMERALD)),
    //    OBSIDIAN("obsidian_armor_material", 28, createProtectionMap( 1, 3, 4, 3 ), 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 0f, () -> Ingredient.fromItems(Items.OBSIDIAN)),

//    QUARTZ("quartz_armor_material", 15, createProtectionMap(3, 5, 6, 3), 8, SoundEvents.ARMOR_EQUIP_GENERIC, 1, 0f, () -> Ingredient.of(ActuallyItems.BLACK_QUARTZ.get())),
//    RESTONIA("restonia_armor_material", 18, createProtectionMap(3, 6, 7, 3), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.RESTONIA_CRYSTAL.get())),
//    PALIS("palis_armor_material", 10, createProtectionMap(3, 6, 7, 3), 10, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.PALIS_CRYSTAL.get())),
//    DIAMATINE("diamatine_armor_material", 36, createProtectionMap(4, 7, 8, 4), 12, SoundEvents.ARMOR_EQUIP_GENERIC, 3, 0f, () -> Ingredient.of(ActuallyItems.DIAMATINE_CRYSTAL.get())),
//    VOID("void_armor_material", 23, createProtectionMap(1, 3, 4, 1), 13, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.VOID_CRYSTAL.get())),
//    EMERADIC("emeradic_armor_material", 32, createProtectionMap(6, 9, 9, 4), 18, SoundEvents.ARMOR_EQUIP_GENERIC, 3, 0f, () -> Ingredient.of(ActuallyItems.EMERADIC_CRYSTAL.get())),
//    ENORI("enori_armor_material", 24, createProtectionMap(3, 6, 6, 3), 11, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.of(ActuallyItems.ENORI_CRYSTAL.get())),
//    GOGGLES("goggles_armor_material", 0, createProtectionMap(0, 0, 0, 0), 0, SoundEvents.ARMOR_EQUIP_GENERIC, 0, 0f, () -> Ingredient.EMPTY);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOGGLES = MATERIALS.register(
            "goggles_armor_material",
            () -> new ArmorMaterial(
                    Map.of(
                            ArmorItem.Type.BOOTS, 0,
                            ArmorItem.Type.LEGGINGS, 0,
                            ArmorItem.Type.CHESTPLATE, 0,
                            ArmorItem.Type.HELMET, 0,
                            ArmorItem.Type.BODY, 0
                    ),
                    0,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.EMPTY,
                    List.of(
                            new ArmorMaterial.Layer(ActuallyAdditions.modLoc("goggles"), "", true),
                            new ArmorMaterial.Layer(ActuallyAdditions.modLoc("goggles"), "_overlay", false)
                    ),
                    0.0F,
                    0.0F
            ));

    public static void init(IEventBus bus) {
        MATERIALS.register(bus);
    }
}