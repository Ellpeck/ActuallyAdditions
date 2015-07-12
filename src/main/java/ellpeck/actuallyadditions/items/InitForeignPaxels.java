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

import java.util.List;

public class InitForeignPaxels{

    public static Item[] tfPaxels = new Item[9];
    private static final String[] tfNames = new String[]{"Copper", "Tin", "Silver", "Lead", "Nickel", "Electrum", "Bronze", "Platinum", "Invar"};
    private static Item[] mtPaxels = new Item[6];
    private static final String[] mtRepairNames = new String[]{"ingotRefinedObsidian", "gemLapis", "ingotOsmium", "ingotBronze", "ingotRefinedGlowstone", "ingotSteel"};
    public static final String[] mtNames = new String[]{"Obsidian", "LapisLazuli", "Osmium", "Bronze", "Glowstone", "Steel"};

    public static void init(){
        //MekanismTools
        if(ConfigBoolValues.MT_PAXELS.isEnabled()){
            if(Loader.isModLoaded("MekanismTools")){
                ModUtil.LOGGER.info("Initializing MekanismTools Material Paxels...");

                for(int i = 0; i < mtPaxels.length; i++){
                    if(!(!ConfigBoolValues.DUPLICATE_PAXELS.isEnabled() && (i == 0 || (i == 3 && ConfigBoolValues.TF_PAXELS.isEnabled() && Loader.isModLoaded("ThermalFoundation"))))){
                        Item axe = ItemUtil.getItemFromName("MekanismTools:"+mtNames[i]+"Axe");
                        Item pickaxe = ItemUtil.getItemFromName("MekanismTools:"+mtNames[i]+"Pickaxe");
                        Item hoe = ItemUtil.getItemFromName("MekanismTools:"+mtNames[i]+"Hoe");
                        Item sword = ItemUtil.getItemFromName("MekanismTools:"+mtNames[i]+"Sword");
                        Item shovel = ItemUtil.getItemFromName("MekanismTools:"+mtNames[i]+"Shovel");

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
            else ModUtil.LOGGER.info("MekanismTools not loaded, can't initialize special Paxels.");
        }

        //Thermal Foundation
        if(ConfigBoolValues.TF_PAXELS.isEnabled()){
            if(Loader.isModLoaded("ThermalFoundation")){
                ModUtil.LOGGER.info("Initializing Thermal Foundation Material Paxels...");

                for(int i = 0; i < tfPaxels.length; i++){
                    Item axe = ItemUtil.getItemFromName("ThermalFoundation:tool.axe"+tfNames[i]);
                    Item pickaxe = ItemUtil.getItemFromName("ThermalFoundation:tool.pickaxe"+tfNames[i]);
                    Item hoe = ItemUtil.getItemFromName("ThermalFoundation:tool.hoe"+tfNames[i]);
                    Item sword = ItemUtil.getItemFromName("ThermalFoundation:tool.sword"+tfNames[i]);
                    Item shovel = ItemUtil.getItemFromName("ThermalFoundation:tool.shovel"+tfNames[i]);

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
            else ModUtil.LOGGER.info("Thermal Foundation not loaded, can't initialize special Paxels.");
        }
    }

    public static void addToCreativeTab(List list){
        for(Item item : tfPaxels){
            if(item != null){
                item.getSubItems(item, CreativeTab.instance, list);
            }
        }
        for(Item item : mtPaxels){
            if(item != null){
                item.getSubItems(item, CreativeTab.instance, list);
            }
        }
    }
}
