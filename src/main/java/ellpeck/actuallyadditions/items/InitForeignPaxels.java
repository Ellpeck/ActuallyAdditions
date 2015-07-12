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

    private static Item[] foreignPaxels = new Item[9];
    private static final String[] names = new String[]{"Copper", "Tin", "Silver", "Lead", "Nickel", "Electrum", "Bronze", "Platinum", "Invar"};

    public static void init(){
        if(ConfigBoolValues.TF_PAXELS.isEnabled() && Loader.isModLoaded("ThermalFoundation")){
            ModUtil.LOGGER.info("Initializing Thermal Foundation Material Paxels...");

            for(int i = 0; i < foreignPaxels.length; i++){
                Item axe = ItemUtil.getItemFromName("ThermalFoundation:tool.axe"+names[i]);
                Item pickaxe = ItemUtil.getItemFromName("ThermalFoundation:tool.pickaxe"+names[i]);
                Item hoe = ItemUtil.getItemFromName("ThermalFoundation:tool.hoe"+names[i]);
                Item sword = ItemUtil.getItemFromName("ThermalFoundation:tool.sword"+names[i]);
                Item shovel = ItemUtil.getItemFromName("ThermalFoundation:tool.shovel"+names[i]);

                if(axe != null && pickaxe != null && hoe != null && sword != null && shovel != null && axe instanceof ItemTool){
                    Item.ToolMaterial material = ((ItemTool)axe).func_150913_i();
                    foreignPaxels[i] = new ItemAllToolAA(material, "ingot"+names[i], "paxel"+names[i], EnumRarity.rare);
                    ItemUtil.register(foreignPaxels[i]);

                    if(ConfigCrafting.PAXELS.isEnabled()){
                        GameRegistry.addRecipe(new ShapelessOreRecipe(foreignPaxels[i], axe, pickaxe, hoe, sword, shovel));
                    }
                }
            }
        }
        else ModUtil.LOGGER.info("Thermal Foundation not loaded, can't initialize special Paxels.");
    }

    public static void addToCreativeTab(List list){
        for(Item item : foreignPaxels){
            if(item != null){
                item.getSubItems(item, CreativeTab.instance, list);
            }
        }
    }
}
