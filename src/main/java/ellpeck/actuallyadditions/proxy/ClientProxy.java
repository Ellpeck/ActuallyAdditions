package ellpeck.actuallyadditions.proxy;


import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.render.*;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.event.RenderPlayerEventAA;
import ellpeck.actuallyadditions.tile.TileEntityCompost;
import ellpeck.actuallyadditions.tile.TileEntityFishingNet;
import ellpeck.actuallyadditions.tile.TileEntityFurnaceSolar;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    @Override
    public void preInit(){
        Util.logInfo("PreInitializing ClientProxy...");
    }

    @Override
    public void init(){
        Util.logInfo("Initializing ClientProxy...");

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompost.class, new RenderTileEntity(new ModelCompost()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockCompost), new RenderItems(new ModelCompost()));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFishingNet.class, new RenderTileEntity(new ModelFishingNet()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockFishingNet), new RenderItems(new ModelFishingNet()));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceSolar.class, new RenderTileEntity(new ModelFurnaceSolar()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockFurnaceSolar), new RenderItems(new ModelFurnaceSolar()));

        VillagerRegistry.instance().registerVillagerSkin(ConfigValues.jamVillagerID, new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/entity/villager/jamVillager.png"));

        Util.registerEvent(new RenderPlayerEventAA());
    }

    @Override
    public void postInit(){
        Util.logInfo("PostInitializing ClientProxy...");
    }
}
