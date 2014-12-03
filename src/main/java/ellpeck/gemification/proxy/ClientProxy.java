package ellpeck.gemification.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.blocks.models.ModelCrucible;
import ellpeck.gemification.blocks.models.RendererCrucible;
import ellpeck.gemification.blocks.models.RendererHoldingTileEntity;
import ellpeck.gemification.tile.TileEntityCrucible;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    public void preInit() {

    }

    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucible.class, new RendererCrucible());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockCrucible), new RendererHoldingTileEntity(new ModelCrucible(), RendererCrucible.resLoc));
    }

    public void postInit() {
    }
}
