/*
 * This file ("InitVillager.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public final class InitVillager{

    public static VillagerProfession jamProfession;

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
        jamProfession = new VillagerProfession(ModUtil.MOD_ID+":jamGuy", ModUtil.MOD_ID+":textures/entity/villager/jam_villager.png", ModUtil.MOD_ID+":textures/entity/villager/jam_villager_zombie.png");
        VillagerRegistry.instance().register(jamProfession);

        VillagerCareer career = new VillagerCareer(jamProfession, ModUtil.MOD_ID+".jammer");
        for(int i = 0; i < 3; i++){
            career.addTrade(i+1, new JamVillagerTradeList());
        }

        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJamHouseHandler());
        MapGenStructureIO.registerStructureComponent(VillageComponentJamHouse.class, ModUtil.MOD_ID+":jamHouseStructure");
    }

    private static void initCustomCropFieldPart(){
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageCustomCropFieldHandler());
        MapGenStructureIO.registerStructureComponent(VillageComponentCustomCropField.class, ModUtil.MOD_ID+":customCropFieldStructure");
    }

}
