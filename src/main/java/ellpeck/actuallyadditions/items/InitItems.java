package ellpeck.actuallyadditions.items;

import ellpeck.actuallyadditions.items.tools.*;
import ellpeck.actuallyadditions.material.InitItemMaterials;
import ellpeck.actuallyadditions.util.ItemUtil;
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
    public static Item itemJams;
    public static Item itemKnife;
    public static Item itemCrafterOnAStick;
    public static Item itemDust;
    public static Item itemSpecialDrop;
    public static Item itemLeafBlower;
    public static Item itemLeafBlowerAdvanced;

    public static Item itemPotionRing;
    public static Item itemPotionRingAdvanced;

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
        ItemUtil.register(itemFertilizer);

        itemMisc = new ItemMisc();
        ItemUtil.register(itemMisc, ItemMisc.allMiscItems);

        itemFoods = new ItemFoods();
        ItemUtil.register(itemFoods, ItemFoods.allFoods);

        itemJams = new ItemJams();
        ItemUtil.register(itemJams, ItemJams.allJams);

        itemKnife = new ItemKnife();
        ItemUtil.register(itemKnife);

        itemCrafterOnAStick = new ItemCrafterOnAStick();
        ItemUtil.register(itemCrafterOnAStick);

        itemDust = new ItemDust();
        ItemUtil.register(itemDust, ItemDust.allDusts);

        itemSpecialDrop = new ItemSpecialDrop();
        ItemUtil.register(itemSpecialDrop, ItemSpecialDrop.allDrops);

        itemLeafBlower = new ItemLeafBlower(false);
        ItemUtil.register(itemLeafBlower);

        itemLeafBlowerAdvanced = new ItemLeafBlower(true);
        ItemUtil.register(itemLeafBlowerAdvanced);

        itemPotionRing = new ItemPotionRing(false);
        ItemUtil.register(itemPotionRing);

        itemPotionRingAdvanced = new ItemPotionRing(true);
        ItemUtil.register(itemPotionRingAdvanced);

        itemPickaxeEmerald = new ItemPickaxeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemPickaxeEmerald", EnumRarity.rare);
        itemAxeEmerald = new ItemAxeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemAxeEmerald", EnumRarity.rare);
        itemShovelEmerald = new ItemShovelAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemShovelEmerald", EnumRarity.rare);
        itemSwordEmerald = new ItemSwordAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemSwordEmerald", EnumRarity.rare);
        itemHoeEmerald = new ItemHoeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemHoeEmerald", EnumRarity.rare);
        ItemUtil.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemHoeObsidian", EnumRarity.uncommon);
        ItemUtil.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

    }
}
