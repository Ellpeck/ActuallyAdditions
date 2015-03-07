package ellpeck.actuallyadditions.items;

import ellpeck.actuallyadditions.items.tools.*;
import ellpeck.actuallyadditions.material.InitItemMaterials;
import ellpeck.actuallyadditions.util.Util;
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

        itemPickaxeEmerald = new ItemPickaxeAA(InitItemMaterials.toolMaterialEmerald, "itemPickaxeEmerald", EnumRarity.rare);
        itemAxeEmerald = new ItemAxeAA(InitItemMaterials.toolMaterialEmerald, "itemAxeEmerald", EnumRarity.rare);
        itemShovelEmerald = new ItemShovelAA(InitItemMaterials.toolMaterialEmerald, "itemShovelEmerald", EnumRarity.rare);
        itemSwordEmerald = new ItemSwordAA(InitItemMaterials.toolMaterialEmerald, "itemSwordEmerald", EnumRarity.rare);
        itemHoeEmerald = new ItemHoeAA(InitItemMaterials.toolMaterialEmerald, "itemHoeEmerald", EnumRarity.rare);
        Util.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeAA(InitItemMaterials.toolMaterialObsidian, "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitItemMaterials.toolMaterialObsidian, "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitItemMaterials.toolMaterialObsidian, "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitItemMaterials.toolMaterialObsidian, "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitItemMaterials.toolMaterialObsidian, "itemHoeObsidian", EnumRarity.uncommon);
        Util.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

    }
}
