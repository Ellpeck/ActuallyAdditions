package ellpeck.actuallyadditions.items;

import ellpeck.actuallyadditions.items.tools.*;
import ellpeck.actuallyadditions.material.InitItemMaterials;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

        itemPickaxeEmerald = new ItemPickaxeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemPickaxeEmerald", EnumRarity.rare);
        itemAxeEmerald = new ItemAxeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemAxeEmerald", EnumRarity.rare);
        itemShovelEmerald = new ItemShovelAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemShovelEmerald", EnumRarity.rare);
        itemSwordEmerald = new ItemSwordAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemSwordEmerald", EnumRarity.rare);
        itemHoeEmerald = new ItemHoeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemHoeEmerald", EnumRarity.rare);
        Util.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemHoeObsidian", EnumRarity.uncommon);
        Util.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

    }
}
