package de.ellpeck.actuallyadditions.common.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Supplier;

public class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MOD_ID);

    // tools
    // all tool types have the same values for attack and speed. Their tool tier modifies the values down or up
    public static final RegistryObject<Item> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.EMERALD, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(ActuallyToolsTiers.EMERALD, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(ActuallyToolsTiers.EMERALD, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(ActuallyToolsTiers.EMERALD, 1, ActuallyToolsTiers.EMERALD.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(ActuallyToolsTiers.EMERALD, 1, ActuallyToolsTiers.EMERALD.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> OBSIDIAN_PICKAXE = ITEMS.register("obsidian_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.OBSIDIAN, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> OBSIDIAN_AXE = ITEMS.register("obsidian_axe", () -> new AxeItem(ActuallyToolsTiers.OBSIDIAN, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> OBSIDIAN_SHOVEL = ITEMS.register("obsidian_shovel", () -> new ShovelItem(ActuallyToolsTiers.OBSIDIAN, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> OBSIDIAN_SWORD = ITEMS.register("obsidian_sword", () -> new SwordItem(ActuallyToolsTiers.OBSIDIAN, 1, ActuallyToolsTiers.OBSIDIAN.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> OBSIDIAN_HOE = ITEMS.register("obsidian_hoe", () -> new HoeItem(ActuallyToolsTiers.OBSIDIAN, 1, ActuallyToolsTiers.OBSIDIAN.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> QUARTZ_PICKAXE = ITEMS.register("quartz_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.QUARTZ, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> QUARTZ_AXE = ITEMS.register("quartz_axe", () -> new AxeItem(ActuallyToolsTiers.QUARTZ, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> QUARTZ_SHOVEL = ITEMS.register("quartz_shovel", () -> new ShovelItem(ActuallyToolsTiers.QUARTZ, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> QUARTZ_SWORD = ITEMS.register("quartz_sword", () -> new SwordItem(ActuallyToolsTiers.QUARTZ, 1, ActuallyToolsTiers.QUARTZ.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> QUARTZ_HOE = ITEMS.register("quartz_hoe", () -> new HoeItem(ActuallyToolsTiers.QUARTZ, 1, ActuallyToolsTiers.QUARTZ.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> ENORI_PICKAXE = ITEMS.register("enori_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.ENORI, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> ENORI_AXE = ITEMS.register("enori_axe", () -> new AxeItem(ActuallyToolsTiers.ENORI, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> ENORI_SHOVEL = ITEMS.register("enori_shovel", () -> new ShovelItem(ActuallyToolsTiers.ENORI, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> ENORI_SWORD = ITEMS.register("enori_sword", () -> new SwordItem(ActuallyToolsTiers.ENORI, 1, ActuallyToolsTiers.ENORI.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> ENORI_HOE = ITEMS.register("enori_hoe", () -> new HoeItem(ActuallyToolsTiers.ENORI, 1, ActuallyToolsTiers.ENORI.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> EMERADIC_PICKAXE = ITEMS.register("emeradic_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.EMERADIC, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> EMERADIC_AXE = ITEMS.register("emeradic_axe", () -> new AxeItem(ActuallyToolsTiers.EMERADIC, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> EMERADIC_SHOVEL = ITEMS.register("emeradic_shovel", () -> new ShovelItem(ActuallyToolsTiers.EMERADIC, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> EMERADIC_SWORD = ITEMS.register("emeradic_sword", () -> new SwordItem(ActuallyToolsTiers.EMERADIC, 1, ActuallyToolsTiers.EMERADIC.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> EMERADIC_HOE = ITEMS.register("emeradic_hoe", () -> new HoeItem(ActuallyToolsTiers.EMERADIC, 1, ActuallyToolsTiers.EMERADIC.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> VOID_PICKAXE = ITEMS.register("void_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.VOID, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> VOID_AXE = ITEMS.register("void_axe", () -> new AxeItem(ActuallyToolsTiers.VOID, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> VOID_SHOVEL = ITEMS.register("void_shovel", () -> new ShovelItem(ActuallyToolsTiers.VOID, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> VOID_SWORD = ITEMS.register("void_sword", () -> new SwordItem(ActuallyToolsTiers.VOID, 1, ActuallyToolsTiers.VOID.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> VOID_HOE = ITEMS.register("void_hoe", () -> new HoeItem(ActuallyToolsTiers.VOID, 1, ActuallyToolsTiers.VOID.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> DIAMATINE_PICKAXE = ITEMS.register("diamatine_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.DIAMATINE, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> DIAMATINE_AXE = ITEMS.register("diamatine_axe", () -> new AxeItem(ActuallyToolsTiers.DIAMATINE, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> DIAMATINE_SHOVEL = ITEMS.register("diamatine_shovel", () -> new ShovelItem(ActuallyToolsTiers.DIAMATINE, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> DIAMATINE_SWORD = ITEMS.register("diamatine_sword", () -> new SwordItem(ActuallyToolsTiers.DIAMATINE, 1, ActuallyToolsTiers.DIAMATINE.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> DIAMATINE_HOE = ITEMS.register("diamatine_hoe", () -> new HoeItem(ActuallyToolsTiers.DIAMATINE, 1, ActuallyToolsTiers.DIAMATINE.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> PALIS_PICKAXE = ITEMS.register("palis_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.PALIS, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> PALIS_AXE = ITEMS.register("palis_axe", () -> new AxeItem(ActuallyToolsTiers.PALIS, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> PALIS_SHOVEL = ITEMS.register("palis_shovel", () -> new ShovelItem(ActuallyToolsTiers.PALIS, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> PALIS_SWORD = ITEMS.register("palis_sword", () -> new SwordItem(ActuallyToolsTiers.PALIS, 1, ActuallyToolsTiers.PALIS.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> PALIS_HOE = ITEMS.register("palis_hoe", () -> new HoeItem(ActuallyToolsTiers.PALIS, 1, ActuallyToolsTiers.PALIS.getAttackDamage() + 1.f, defaultProps()));

    public static final RegistryObject<Item> RESTONIA_PICKAXE = ITEMS.register("restonia_pickaxe", () -> new PickaxeItem(ActuallyToolsTiers.RESTONIA, 1, -2.8f, defaultProps()));
    public static final RegistryObject<Item> RESTONIA_AXE = ITEMS.register("restonia_axe", () -> new AxeItem(ActuallyToolsTiers.RESTONIA, 6, -3.0f, defaultProps()));
    public static final RegistryObject<Item> RESTONIA_SHOVEL = ITEMS.register("restonia_shovel", () -> new ShovelItem(ActuallyToolsTiers.RESTONIA, 1, -3.0f, defaultProps()));
    public static final RegistryObject<Item> RESTONIA_SWORD = ITEMS.register("restonia_sword", () -> new SwordItem(ActuallyToolsTiers.RESTONIA, 1, ActuallyToolsTiers.RESTONIA.getAttackDamage() + 1.f, defaultProps()));
    public static final RegistryObject<Item> RESTONIA_HOE = ITEMS.register("restonia_hoe", () -> new HoeItem(ActuallyToolsTiers.RESTONIA, 1, ActuallyToolsTiers.RESTONIA.getAttackDamage() + 1.f, defaultProps()));

    public static final Set<RegistryObject<Item>> TOOL_ITEMS = ImmutableSet.of(
            EMERALD_PICKAXE, EMERALD_AXE, EMERALD_SHOVEL, EMERALD_SWORD, EMERALD_HOE, OBSIDIAN_PICKAXE, OBSIDIAN_AXE,
            OBSIDIAN_SHOVEL, OBSIDIAN_SWORD, OBSIDIAN_HOE, ENORI_PICKAXE, ENORI_AXE, ENORI_SHOVEL, ENORI_SWORD, ENORI_HOE,
            EMERADIC_PICKAXE, EMERADIC_AXE, EMERADIC_SHOVEL, EMERADIC_SWORD, EMERADIC_HOE, VOID_PICKAXE, VOID_AXE, VOID_SHOVEL,
            VOID_SWORD, VOID_HOE, DIAMATINE_PICKAXE, DIAMATINE_AXE, DIAMATINE_SHOVEL, DIAMATINE_SWORD, DIAMATINE_HOE,
            PALIS_PICKAXE, PALIS_AXE, PALIS_SHOVEL, PALIS_SWORD, PALIS_HOE, RESTONIA_PICKAXE, RESTONIA_AXE, RESTONIA_SHOVEL,
            RESTONIA_SWORD, RESTONIA_HOE, QUARTZ_PICKAXE, QUARTZ_AXE, QUARTZ_SHOVEL, QUARTZ_SWORD, QUARTZ_HOE
    );

    // Resources
    public static final RegistryObject<Item> BLACK_QUARTS = ITEMS.register("black_quartz", basicItem());
    public static final RegistryObject<Item> RESTONIA_CRYSTAL = ITEMS.register("restonia_crystal", basicItem());
    public static final RegistryObject<Item> PALIS_CRYSTAL = ITEMS.register("palis_crystal", basicItem());
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL = ITEMS.register("diamatine_crystal", basicItem());
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", basicItem());
    public static final RegistryObject<Item> EMERADIC_CRYSTAL = ITEMS.register("emeradic_crystal", basicItem());
    public static final RegistryObject<Item> ENORI_CRYSTAL = ITEMS.register("enori_crystal", basicItem());

    public static final Set<RegistryObject<Item>> SIMPLE_ITEMS;

    static {
        final ImmutableSet.Builder<RegistryObject<Item>> simpleBuilder = ImmutableSet.builder();
        simpleBuilder.addAll(TOOL_ITEMS);
        simpleBuilder.addAll(ImmutableSet.of(
                BLACK_QUARTS, RESTONIA_CRYSTAL, PALIS_CRYSTAL, DIAMATINE_CRYSTAL,
                VOID_CRYSTAL, EMERADIC_CRYSTAL, ENORI_CRYSTAL
        ));

        SIMPLE_ITEMS = simpleBuilder.build();
    }

    private static Supplier<Item> basicItem() {
        return () -> new Item(defaultProps());
    }

    private static Item.Properties defaultProps() {
        return new Item.Properties().group(ActuallyAdditions.ACTUALLY_GROUP);
    }
}
