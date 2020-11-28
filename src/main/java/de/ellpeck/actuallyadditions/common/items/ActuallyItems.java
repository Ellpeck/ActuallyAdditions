package de.ellpeck.actuallyadditions.common.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.useables.AllInOneTool;
import de.ellpeck.actuallyadditions.common.items.useables.ManualItem;
import de.ellpeck.actuallyadditions.common.materials.ArmorMaterials;
import de.ellpeck.actuallyadditions.common.materials.ToolMaterials;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Supplier;

public final class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MOD_ID);

    // tools
    // If these ever need registry object referencing then I might be shit out of luck but it shouldn't be that complex to fix.
    public static final ToolSet QUARTZ_SET = new ToolSet("quartz", ToolMaterials.QUARTZ, ArmorMaterials.QUARTZ, ActuallyItems::defaultProps);
    public static final ToolSet ENORI_SET = new ToolSet("enori", ToolMaterials.ENORI, ArmorMaterials.ENORI, ActuallyItems::defaultProps);
    public static final ToolSet EMERADIC_SET = new ToolSet("emeradic", ToolMaterials.EMERADIC, ArmorMaterials.EMERADIC, ActuallyItems::defaultProps);
    public static final ToolSet VOID_SET = new ToolSet("void", ToolMaterials.VOID, ArmorMaterials.VOID, ActuallyItems::defaultProps);
    public static final ToolSet DIAMATINE_SET = new ToolSet("diamatine", ToolMaterials.DIAMATINE, ArmorMaterials.DIAMATINE, ActuallyItems::defaultProps);
    public static final ToolSet PALIS_SET = new ToolSet("palis", ToolMaterials.PALIS, ArmorMaterials.PALIS, ActuallyItems::defaultProps);
    public static final ToolSet RESTONIA_SET = new ToolSet("restonia", ToolMaterials.RESTONIA, ArmorMaterials.RESTONIA, ActuallyItems::defaultProps);

    // Paxels :D
    public static final RegistryObject<Item> WOODEN_PAXEL = ITEMS.register("wooden_paxel", () -> new AllInOneTool(ItemTier.WOOD));
    public static final RegistryObject<Item> STONE_PAXEL = ITEMS.register("stone_paxel", () -> new AllInOneTool(ItemTier.STONE));
    public static final RegistryObject<Item> IRON_PAXEL = ITEMS.register("iron_paxel", () -> new AllInOneTool(ItemTier.IRON));
    public static final RegistryObject<Item> GOLD_PAXEL = ITEMS.register("gold_paxel", () -> new AllInOneTool(ItemTier.GOLD));
    public static final RegistryObject<Item> DIAMOND_PAXEL = ITEMS.register("diamond_paxel", () -> new AllInOneTool(ItemTier.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_PAXEL = ITEMS.register("netherite_paxel", () -> new AllInOneTool(ItemTier.NETHERITE));
    public static final RegistryObject<Item> QUARTZ_PAXEL = ITEMS.register("quartz_paxel", () -> new AllInOneTool(ToolMaterials.QUARTZ));
    public static final RegistryObject<Item> ENORI_PAXEL = ITEMS.register("enori_paxel", () -> new AllInOneTool(ToolMaterials.ENORI));
    public static final RegistryObject<Item> EMERADIC_PAXEL = ITEMS.register("emeradic_paxel", () -> new AllInOneTool(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> VOID_PAXEL = ITEMS.register("void_paxel", () -> new AllInOneTool(ToolMaterials.VOID));
    public static final RegistryObject<Item> DIAMATINE_PAXEL = ITEMS.register("diamatine_paxel", () -> new AllInOneTool(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> PALIS_PAXEL = ITEMS.register("palis_paxel", () -> new AllInOneTool(ToolMaterials.PALIS));
    public static final RegistryObject<Item> RESTONIA_PAXEL = ITEMS.register("restonia_paxel", () -> new AllInOneTool(ToolMaterials.RESTONIA));

    public static final Set<ToolSet> ALL_TOOL_SETS = ImmutableSet.of(QUARTZ_SET, ENORI_SET, EMERADIC_SET, VOID_SET, DIAMATINE_SET, PALIS_SET, RESTONIA_SET);

    // Resources
    public static final RegistryObject<Item> BLACK_QUARTS = ITEMS.register("black_quartz", basicItem());
    public static final RegistryObject<Item> RESTONIA_CRYSTAL = ITEMS.register("restonia_crystal", basicItem());
    public static final RegistryObject<Item> PALIS_CRYSTAL = ITEMS.register("palis_crystal", basicItem());
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL = ITEMS.register("diamatine_crystal", basicItem());
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", basicItem());
    public static final RegistryObject<Item> EMERADIC_CRYSTAL = ITEMS.register("emeradic_crystal", basicItem());
    public static final RegistryObject<Item> ENORI_CRYSTAL = ITEMS.register("enori_crystal", basicItem());

    public static final RegistryObject<Item> BOOKLET = ITEMS.register("booklet", ManualItem::new);

    public static final Set<RegistryObject<Item>> SIMPLE_ITEMS = ImmutableSet.of(
            // Crystals
            BLACK_QUARTS, RESTONIA_CRYSTAL, PALIS_CRYSTAL, DIAMATINE_CRYSTAL,
            VOID_CRYSTAL, EMERADIC_CRYSTAL, ENORI_CRYSTAL,
            // All in one tools
            WOODEN_PAXEL, STONE_PAXEL, IRON_PAXEL, GOLD_PAXEL, DIAMOND_PAXEL, NETHERITE_PAXEL, QUARTZ_PAXEL, ENORI_PAXEL,
            EMERADIC_PAXEL, VOID_PAXEL, DIAMATINE_PAXEL, PALIS_PAXEL, RESTONIA_PAXEL
    );

    private static Supplier<Item> basicItem() {
        return () -> new Item(defaultProps());
    }

    private static Item.Properties defaultProps() {
        return new Item.Properties().group(ActuallyAdditions.ACTUALLY_GROUP);
    }

    static {
        ALL_TOOL_SETS.forEach(e -> e.register(ITEMS));
    }
}
