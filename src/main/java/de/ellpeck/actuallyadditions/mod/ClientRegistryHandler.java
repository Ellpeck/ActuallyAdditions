package de.ellpeck.actuallyadditions.mod;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent.BakingCompleted;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: [port] eval all of this class
 */
public class ClientRegistryHandler {

    public static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS_FOR_REGISTERING = new HashMap<>();

    /**
     * (Excerpted from Tinkers' Construct with permission, thanks guys!)
     */
    private static void registerCustomFluidBlockRenderer(Fluid fluid) {
        //        Block block = fluid.getBlock();
        //        Item item = Item.getItemFromBlock(block);
        //        FluidStateMapper mapper = new FluidStateMapper(fluid);
        //        ModelBakery.registerItemVariants(item);
        //        ModelLoader.setCustomMeshDefinition(item, mapper);
        //        ModelLoader.setCustomStateMapper(block, mapper);
    }

    @SubscribeEvent
    public void onModelRegistry(RegisterGeometryLoaders event) {
        //        for (Block block : RegistryHandler.BLOCKS_TO_REGISTER) {
        //            if (block instanceof IHasModel) {
        //                ((IHasModel) block).registerRendering();
        //            }
        //        }
        //
        //        for (Map.Entry<ItemStack, ModelResourceLocation> entry : MODEL_LOCATIONS_FOR_REGISTERING.entrySet()) {
        //            ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
        //        }
        //
        //        registerCustomFluidBlockRenderer(InitFluids.fluidCanolaOil);
        //        registerCustomFluidBlockRenderer(InitFluids.fluidRefinedCanolaOil);
        //        registerCustomFluidBlockRenderer(InitFluids.fluidCrystalOil);
        //        registerCustomFluidBlockRenderer(InitFluids.fluidEmpoweredOil);
    }

    @SubscribeEvent
    public void onModelBake(BakingCompleted e) {
        //        ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(ActuallyAdditions.MODID, "block_compost"), "normal");
        //        CompostModel.compostBase = e.getModelRegistry().getObject(mrl);
        //        e.getModelRegistry().putObject(mrl, new ActualCompostModel());
    }
}
