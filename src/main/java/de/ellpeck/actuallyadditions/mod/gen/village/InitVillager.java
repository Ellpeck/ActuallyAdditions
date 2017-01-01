/*
 * This file ("InitVillager.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.village;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.gen.village.component.VillageComponentCustomCropField;
import de.ellpeck.actuallyadditions.mod.gen.village.component.VillageComponentEngineerHouse;
import de.ellpeck.actuallyadditions.mod.gen.village.component.VillageComponentJamHouse;
import de.ellpeck.actuallyadditions.mod.gen.village.component.handler.VillageCustomCropFieldHandler;
import de.ellpeck.actuallyadditions.mod.gen.village.component.handler.VillageEngineerHouseHandler;
import de.ellpeck.actuallyadditions.mod.gen.village.component.handler.VillageJamHouseHandler;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public final class InitVillager{

    public static VillagerProfession jamProfession;
    public static VillagerProfession engineerProfession;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Village Addons...");

        if(ConfigBoolValues.JAM_VILLAGER_EXISTS.isEnabled()){
            initJamVillagePart();
        }
        if(ConfigBoolValues.CROP_FIELD_EXISTS.isEnabled()){
            initCustomCropFieldPart();
        }
        if(ConfigBoolValues.ENGINEER_VILLAGER_EXISTS.isEnabled()){
            initEngineerVillagePart();
        }
    }

    private static void initEngineerVillagePart(){
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageEngineerHouseHandler());
        MapGenStructureIO.registerStructureComponent(VillageComponentEngineerHouse.class, ModUtil.MOD_ID+":engineerHouseStructure");

        engineerProfession = new VillagerProfession(ModUtil.MOD_ID+":engineer", ModUtil.MOD_ID+":textures/entity/villager/engineer_villager.png", ModUtil.MOD_ID+":textures/entity/villager/engineer_villager_zombie.png");
        VillagerRegistry.instance().register(engineerProfession);

        VillagerCareer crystallizer = new VillagerCareer(engineerProfession, ModUtil.MOD_ID+".crystallizer");
        crystallizer.addTrade(1,
                new BasicTradeList(new PriceInfo(1, 2), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), new PriceInfo(2, 8)),
                new BasicTradeList(new PriceInfo(1, 3), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), new PriceInfo(2, 6)),
                new BasicTradeList(new PriceInfo(1, 3), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), new PriceInfo(1, 4)),
                new BasicTradeList(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), new PriceInfo(10, 16), new PriceInfo(1, 1)));
        crystallizer.addTrade(2,
                new BasicTradeList(new PriceInfo(2, 3), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), new PriceInfo(2, 4)),
                new BasicTradeList(new PriceInfo(1, 3), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), new PriceInfo(2, 6)),
                new BasicTradeList(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), new PriceInfo(8, 12), new PriceInfo(1, 1)),
                new BasicTradeList(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), new PriceInfo(8, 16), new PriceInfo(1, 2)));
        crystallizer.addTrade(3,
                new BasicTradeList(new PriceInfo(2, 4), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), new PriceInfo(2, 3)),
                new BasicTradeList(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), new PriceInfo(6, 10), new PriceInfo(1, 1)),
                new BasicTradeList(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), new PriceInfo(4, 6), new PriceInfo(1, 1)),
                new BasicTradeList(new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), new PriceInfo(6, 12), new PriceInfo(1, 2)));

        VillagerCareer engineer = new VillagerCareer(engineerProfession, ModUtil.MOD_ID+".engineer");
        engineer.addTrade(1,
                new BasicTradeList(new PriceInfo(1, 2), new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()), new PriceInfo(2, 3)),
                new BasicTradeList(new PriceInfo(1, 2), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), new PriceInfo(6, 8)),
                new BasicTradeList(new PriceInfo(1, 3), new ItemStack(InitItems.itemLaserWrench), new PriceInfo(1, 1)));
        engineer.addTrade(2,
                new BasicTradeList(new ItemStack(InitItems.itemCoffeeBean), new PriceInfo(20, 30), new PriceInfo(1, 2)),
                new BasicTradeList(new PriceInfo(3, 5), new ItemStack(InitItems.itemPhantomConnector), new PriceInfo(1, 1)),
                new BasicTradeList(new PriceInfo(10, 20), new ItemStack(InitBlocks.blockLaserRelay), new PriceInfo(1, 2)));
        engineer.addTrade(3,
                new BasicTradeList(new ItemStack(InitBlocks.blockTinyTorch), new PriceInfo(30, 40), new PriceInfo(1, 2)),
                new BasicTradeList(new PriceInfo(1, 2), new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()), new PriceInfo(1, 2)));
        engineer.addTrade(4,
                new BasicTradeList(new PriceInfo(3, 5), new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), new PriceInfo(1, 2)),
                new BasicTradeList(new ItemStack(InitBlocks.blockEmpowerer), new PriceInfo(1, 1), new PriceInfo(15, 20)),
                new BasicTradeList(new PriceInfo(30, 40), new ItemStack(InitBlocks.blockLaserRelayExtreme), new PriceInfo(1, 1)));
    }

    private static void initJamVillagePart(){
        jamProfession = new VillagerProfession(ModUtil.MOD_ID+":jamGuy", ModUtil.MOD_ID+":textures/entity/villager/jam_villager.png", ModUtil.MOD_ID+":textures/entity/villager/jam_villager_zombie.png");
        VillagerRegistry.instance().register(jamProfession);

        VillagerCareer career = new VillagerCareer(jamProfession, ModUtil.MOD_ID+".jammer");
        career.addTrade(1,
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.CU_BA_RA.ordinal()), new PriceInfo(1, 3)),
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.GRA_KI_BA.ordinal()), new PriceInfo(1, 3)));
        career.addTrade(2,
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.PL_AP_LE.ordinal()), new PriceInfo(1, 3)),
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.CH_AP_CI.ordinal()), new PriceInfo(1, 3)),
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.HO_ME_KI.ordinal()), new PriceInfo(1, 3)));
        career.addTrade(3,
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.HO_ME_CO.ordinal()), new PriceInfo(1, 3)),
                new BasicTradeList(new PriceInfo(1, 4), new ItemStack(InitItems.itemJams, 1, TheJams.PI_CO.ordinal()), new PriceInfo(1, 3)));

        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJamHouseHandler());
        MapGenStructureIO.registerStructureComponent(VillageComponentJamHouse.class, ModUtil.MOD_ID+":jamHouseStructure");
    }

    private static void initCustomCropFieldPart(){
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageCustomCropFieldHandler());
        MapGenStructureIO.registerStructureComponent(VillageComponentCustomCropField.class, ModUtil.MOD_ID+":customCropFieldStructure");
    }

}
