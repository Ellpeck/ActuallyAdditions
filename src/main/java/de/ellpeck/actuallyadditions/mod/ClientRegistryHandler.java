package de.ellpeck.actuallyadditions.mod;

import de.ellpeck.actuallyadditions.mod.blocks.render.IHasModel;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.util.FluidStateMapper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ClientRegistryHandler{

    public static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS_FOR_REGISTERING = new HashMap<ItemStack, ModelResourceLocation>();

    /**
     * (Excerpted from Tinkers' Construct with permission, thanks guys!)
     */
    private static void registerCustomFluidBlockRenderer(Fluid fluid){
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomMeshDefinition(item, mapper);
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event){
        for(Block block : RegistryHandler.BLOCKS_TO_REGISTER){
            if(block instanceof IHasModel){
                ((IHasModel)block).registerRendering();
            }
        }

        for(Map.Entry<ItemStack, ModelResourceLocation> entry : MODEL_LOCATIONS_FOR_REGISTERING.entrySet()){
            ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
        }

        registerCustomFluidBlockRenderer(InitFluids.fluidCanolaOil);
        registerCustomFluidBlockRenderer(InitFluids.fluidRefinedCanolaOil);
        registerCustomFluidBlockRenderer(InitFluids.fluidCrystalOil);
        registerCustomFluidBlockRenderer(InitFluids.fluidEmpoweredOil);
    }
}
