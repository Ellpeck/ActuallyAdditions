/*
 * This file ("InitVillager.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class InitVillager{

    public static final String JAM_HOUSE_CHEST_NAME = ModUtil.MOD_ID+".jamHouseChest";

    public static void init(){
        ModUtil.LOGGER.info("Initializing Village Addons...");

        if(ConfigBoolValues.JAM_VILLAGER_EXISTS.isEnabled()){
            initJamVillagePart();
        }
        if(ConfigBoolValues.CROP_FIELD_EXISTS.isEnabled()){
            initCustomCropFieldPart();
        }
    }

    private static void initJamVillagePart(){
        //TODO Fix villager
        /*int jamID = ConfigIntValues.JAM_VILLAGER_ID.getValue();
        VillagerRegistry.instance().registerVillagerId(jamID);
        VillagerRegistry.instance().registerVillageTradeHandler(jamID, new JamVillagerTradeHandler());

        ChestGenHooks jamHouseChest = ChestGenHooks.getInfo(JAM_HOUSE_CHEST_NAME);
        jamHouseChest.setMin(5);
        jamHouseChest.setMax(10);
        for(int i = 0; i < TheJams.values().length; i++){
            ChestGenHooks.addItem(JAM_HOUSE_CHEST_NAME, new WeightedRandomChestContent(new ItemStack(InitItems.itemJams, 1, i), 1, 1, 10));
        }
        ChestGenHooks.addItem(JAM_HOUSE_CHEST_NAME, new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 30));
        ChestGenHooks.addItem(JAM_HOUSE_CHEST_NAME, new WeightedRandomChestContent(new ItemStack(Items.potionitem), 1, 1, 20));

        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJamHouseHandler());
        MapGenStructureIO.func_143031_a(VillageComponentJamHouse.class, ModUtil.MOD_ID+":jamHouseStructure");*/
    }

    private static void initCustomCropFieldPart(){
        //TODO Fix village
        //VillagerRegistry.instance().registerVillageCreationHandler(new VillageCustomCropFieldHandler());
        MapGenStructureIO.registerStructureComponent(VillageComponentCustomCropField.class, ModUtil.MOD_ID+":customCropFieldStructure");
    }

}
