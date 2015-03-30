package ellpeck.actuallyadditions.proxy;


import cpw.mods.fml.client.registry.ClientRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.render.*;
import ellpeck.actuallyadditions.tile.TileEntityCompost;
import ellpeck.actuallyadditions.tile.TileEntityFishingNet;
import ellpeck.actuallyadditions.tile.TileEntityFurnaceSolar;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    @Override
    public void preInit(){

    }

    @Override
    public void init(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompost.class, new RenderTileEntity(new ModelCompost()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockCompost), new RenderItems(new ModelCompost()));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFishingNet.class, new RenderTileEntity(new ModelFishingNet()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockFishingNet), new RenderItems(new ModelFishingNet()));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceSolar.class, new RenderTileEntity(new ModelFurnaceSolar()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockFurnaceSolar), new RenderItems(new ModelFurnaceSolar()));
    }

    @Override
    public void postInit(){

    }
}
