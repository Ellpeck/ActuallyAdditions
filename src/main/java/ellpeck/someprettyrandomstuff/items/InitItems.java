package ellpeck.someprettyrandomstuff.items;

import ellpeck.someprettyrandomstuff.items.tools.*;
import ellpeck.someprettyrandomstuff.material.InitItemMaterials;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;

public class InitItems{

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemFoods;
    public static Item itemKnife;
    public static Item itemCrafterOnAStick;
    public static Item itemDust;
    public static Item itemSpecialDrop;

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
        Util.register(itemFertilizer);

        itemMisc = new ItemMisc();
        Util.register(itemMisc);

        itemFoods = new ItemFoods();
        Util.register(itemFoods);

        itemKnife = new ItemKnife();
        Util.register(itemKnife);

        itemCrafterOnAStick = new ItemCrafterOnAStick();
        Util.register(itemCrafterOnAStick);

        itemDust = new ItemDust();
        Util.register(itemDust);

        itemSpecialDrop = new ItemSpecialDrop();
        Util.register(itemSpecialDrop);

        itemPickaxeEmerald = new ItemPickaxeSPRS(InitItemMaterials.toolMaterialEmerald, "itemPickaxeEmerald", EnumRarity.rare);
        itemAxeEmerald = new ItemAxeSPRS(InitItemMaterials.toolMaterialEmerald, "itemAxeEmerald", EnumRarity.rare);
        itemShovelEmerald = new ItemShovelSPRS(InitItemMaterials.toolMaterialEmerald, "itemShovelEmerald", EnumRarity.rare);
        itemSwordEmerald = new ItemSwordSPRS(InitItemMaterials.toolMaterialEmerald, "itemSwordEmerald", EnumRarity.rare);
        itemHoeEmerald = new ItemHoeSPRS(InitItemMaterials.toolMaterialEmerald, "itemHoeEmerald", EnumRarity.rare);
        Util.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeSPRS(InitItemMaterials.toolMaterialObsidian, "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeSPRS(InitItemMaterials.toolMaterialObsidian, "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelSPRS(InitItemMaterials.toolMaterialObsidian, "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordSPRS(InitItemMaterials.toolMaterialObsidian, "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeSPRS(InitItemMaterials.toolMaterialObsidian, "itemHoeObsidian", EnumRarity.uncommon);
        Util.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

    }
}
