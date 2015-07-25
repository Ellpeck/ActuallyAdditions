package ellpeck.actuallyadditions.items;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.creative.CreativeTab;
import ellpeck.actuallyadditions.items.tools.ItemAllToolAA;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class InitForeignPaxels{

    private static final String THERMAL_FOUNDATION = "ThermalFoundation";
    private static final String MEKANISM_TOOLS = "MekanismTools";

    public static Item[] tfPaxels = new Item[9];
    private static final String[] tfNames = new String[]{"Copper", "Tin", "Silver", "Lead", "Nickel", "Electrum", "Bronze", "Platinum", "Invar"};

    private static Item[] mtPaxels = new Item[6];
    public static final String[] mtNames = new String[]{"Obsidian", "LapisLazuli", "Osmium", "Bronze", "Glowstone", "Steel"};
    private static final String[] mtRepairNames = new String[]{"ingotRefinedObsidian", "gemLapis", "ingotOsmium", "ingotBronze", "ingotRefinedGlowstone", "ingotSteel"};

    public static void init(){
        //MekanismTools
        if(ConfigBoolValues.MT_PAXELS.isEnabled()){
            if(Loader.isModLoaded(MEKANISM_TOOLS)){
                ModUtil.LOGGER.info("Initializing "+MEKANISM_TOOLS+" AIOTs...");

                for(int i = 0; i < mtPaxels.length; i++){
                    if(!(!ConfigBoolValues.DUPLICATE_PAXELS.isEnabled() && (i == 0 || (i == 3 && ConfigBoolValues.TF_PAXELS.isEnabled() && Loader.isModLoaded(THERMAL_FOUNDATION))))){
                        Item axe = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+mtNames[i]+"Axe");
                        Item pickaxe = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+mtNames[i]+"Pickaxe");
                        Item hoe = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+mtNames[i]+"Hoe");
                        Item sword = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+mtNames[i]+"Sword");
                        Item shovel = ItemUtil.getItemFromName(MEKANISM_TOOLS+":"+mtNames[i]+"Shovel");

                        if(axe != null && pickaxe != null && hoe != null && sword != null && shovel != null && axe instanceof ItemTool){
                            Item.ToolMaterial material = ((ItemTool)axe).func_150913_i();
                            mtPaxels[i] = new ItemAllToolAA(material, mtRepairNames[i], "paxelMT"+mtNames[i], EnumRarity.rare);
                            ItemUtil.register(mtPaxels[i]);

                            if(ConfigCrafting.PAXELS.isEnabled()){
                                GameRegistry.addRecipe(new ShapelessOreRecipe(mtPaxels[i], axe, pickaxe, hoe, sword, shovel));
                            }
                        }
                    }
                }
            }
            else ModUtil.LOGGER.info(MEKANISM_TOOLS+" not loaded, can't initialize Special AIOTs.");
        }

        //Thermal Foundation
        if(ConfigBoolValues.TF_PAXELS.isEnabled()){
            if(Loader.isModLoaded(THERMAL_FOUNDATION)){
                ModUtil.LOGGER.info("Initializing "+THERMAL_FOUNDATION+" AIOTs...");

                for(int i = 0; i < tfPaxels.length; i++){
                    Item axe = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.axe"+tfNames[i]);
                    Item pickaxe = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.pickaxe"+tfNames[i]);
                    Item hoe = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.hoe"+tfNames[i]);
                    Item sword = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.sword"+tfNames[i]);
                    Item shovel = ItemUtil.getItemFromName(THERMAL_FOUNDATION+":tool.shovel"+tfNames[i]);

                    if(axe != null && pickaxe != null && hoe != null && sword != null && shovel != null && axe instanceof ItemTool){
                        Item.ToolMaterial material = ((ItemTool)axe).func_150913_i();
                        tfPaxels[i] = new ItemAllToolAA(material, "ingot"+tfNames[i], "paxelTF"+tfNames[i], EnumRarity.rare);
                        ItemUtil.register(tfPaxels[i]);

                        if(ConfigCrafting.PAXELS.isEnabled()){
                            GameRegistry.addRecipe(new ShapelessOreRecipe(tfPaxels[i], axe, pickaxe, hoe, sword, shovel));
                        }
                    }
                }
            }
            else ModUtil.LOGGER.info(THERMAL_FOUNDATION+" not loaded, can't initialize Special AIOTs.");
        }
    }

    public static void addToCreativeTab(){
        for(Item item : tfPaxels){
            if(item != null){
                CreativeTab.instance.add(item);
            }
        }
        for(Item item : mtPaxels){
            if(item != null){
                CreativeTab.instance.add(item);
            }
        }
    }
}
