package ellpeck.actuallyadditions.gen;

import cpw.mods.fml.common.registry.VillagerRegistry;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheJams;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

public class InitVillager{

    public static ChestGenHooks jamHouseChest;

    public static void init(){
        Util.logInfo("Initializing Village Addons...");

        if(ConfigValues.jamVillagerExists){

            int jamID = ConfigValues.jamVillagerID;
            VillagerRegistry.instance().registerVillagerId(jamID);
            VillagerRegistry.instance().registerVillageTradeHandler(jamID, new JamVillagerTradeHandler());

            jamHouseChest = new ChestGenHooks("JamHouse", new WeightedRandomChestContent[0], 5, 20);
            for(int i = 0; i < TheJams.values().length; i++){
                jamHouseChest.addItem(new WeightedRandomChestContent(new ItemStack(InitItems.itemJams, new Random().nextInt(5)+1, i), 1, 1, 15));
            }
            jamHouseChest.addItem(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle, new Random().nextInt(5)+1), 1, 1, 15));
            jamHouseChest.addItem(new WeightedRandomChestContent(new ItemStack(Items.potionitem, new Random().nextInt(5)+1), 1, 1, 15));

            VillagerRegistry.instance().registerVillageCreationHandler(new VillageJamHouseHandler());
            MapGenStructureIO.func_143031_a(VillageComponentJamHouse.class, ModUtil.MOD_ID_LOWER + ":jamHouseStructure");
        }
    }

}
