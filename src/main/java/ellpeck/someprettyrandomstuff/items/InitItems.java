package ellpeck.someprettyrandomstuff.items;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.items.tools.*;
import ellpeck.someprettyrandomstuff.material.InitItemMaterials;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.Item;

public class InitItems{

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemFoods;
    public static Item itemKnife;

    public static Item itemPickaxeEmerald;
    public static Item itemAxeEmerald;
    public static Item itemShovelEmerald;
    public static Item itemSwordEmerald;
    public static Item itemHoeEmerald;

    public static Item itemPickaxeObsidian;
    public static Item itemAxeObsidian;
    public static Item itemShovelObsidian;
    public static Item itemSwordObsidian;
    public static Item itemHoeObsidian;

    public static void init(){
        Util.logInfo("Initializing Items...");

        itemFertilizer = new ItemFertilizer();
        GameRegistry.registerItem(itemFertilizer, ((IName)itemFertilizer).getName());

        itemMisc = new ItemMisc();
        GameRegistry.registerItem(itemMisc, ((IName)itemMisc).getName());

        itemFoods = new ItemFoods();
        GameRegistry.registerItem(itemFoods, ((IName)itemFoods).getName());

        itemKnife = new ItemKnife();
        GameRegistry.registerItem(itemKnife, ((IName)itemKnife).getName());

        itemPickaxeEmerald = new ItemPickaxeSPRS(InitItemMaterials.toolMaterialEmerald, "itemPickaxeEmerald");
        itemAxeEmerald = new ItemAxeSPRS(InitItemMaterials.toolMaterialEmerald, "itemAxeEmerald");
        itemShovelEmerald = new ItemShovelSPRS(InitItemMaterials.toolMaterialEmerald, "itemShovelEmerald");
        itemSwordEmerald = new ItemSwordSPRS(InitItemMaterials.toolMaterialEmerald, "itemSwordEmerald");
        itemHoeEmerald = new ItemHoeSPRS(InitItemMaterials.toolMaterialEmerald, "itemHoeEmerald");
        Util.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeSPRS(InitItemMaterials.toolMaterialObsidian, "itemPickaxeObsidian");
        itemAxeObsidian = new ItemAxeSPRS(InitItemMaterials.toolMaterialObsidian, "itemAxeObsidian");
        itemShovelObsidian = new ItemShovelSPRS(InitItemMaterials.toolMaterialObsidian, "itemShovelObsidian");
        itemSwordObsidian = new ItemSwordSPRS(InitItemMaterials.toolMaterialObsidian, "itemSwordObsidian");
        itemHoeObsidian = new ItemHoeSPRS(InitItemMaterials.toolMaterialObsidian, "itemHoeObsidian");
        Util.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

    }
}
