package de.ellpeck.actuallyadditions.common;

//Class to wrap around the trainwreck that is the new registry system
// @todo: remove this completely
@Deprecated
public class RegistryHandler {
//
//    public static final List<Block> BLOCKS_TO_REGISTER = new ArrayList<>();
//    public static final List<Item> ITEMS_TO_REGISTER = new ArrayList<>();
//    public static final List<SoundEvent> SOUNDS_TO_REGISTER = new ArrayList<>();
//    public static final List<IRecipe> RECIPES_TO_REGISTER = new ArrayList<>();
//
//    @SubscribeEvent
//    public void onBlockRegistry(Register<Block> event) {
//        InitBlocks.init();
//
//        for (Block block : BLOCKS_TO_REGISTER) {
//            event.getRegistry().register(block);
//        }
//    }
//
//    @SubscribeEvent
//    public void onItemRegistry(Register<Item> event) {
//        InitItems.init();
//
//        for (Item item : ITEMS_TO_REGISTER) {
//            event.getRegistry().register(item);
//        }
//        ITEMS_TO_REGISTER.clear();
//
//        //Hack to make this register before recipes :>
//        InitOreDict.init();
//    }
//
//    @SubscribeEvent
//    public void onVillagerRegistry(Register<VillagerProfession> event) {
//        InitVillager.init();
//
//        if (ConfigBoolValues.JAM_VILLAGER_EXISTS.isEnabled()) event.getRegistry().register(InitVillager.jamProfession);
//        if (ConfigBoolValues.ENGINEER_VILLAGER_EXISTS.isEnabled()) event.getRegistry().register(InitVillager.engineerProfession);
//    }
//
//    @SubscribeEvent
//    public void onCraftingRegistry(Register<IRecipe> event) {
//        InitCrafting.init();
//
//        for (IRecipe recipe : RECIPES_TO_REGISTER) {
//            event.getRegistry().register(recipe);
//        }
//        RECIPES_TO_REGISTER.clear();
//    }
//
//    @SubscribeEvent
//    public void onSoundRegistry(Register<SoundEvent> event) {
//        SoundHandler.init();
//
//        for (SoundEvent sound : SOUNDS_TO_REGISTER) {
//            event.getRegistry().register(sound);
//        }
//        SOUNDS_TO_REGISTER.clear();
//    }
}
