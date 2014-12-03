package ellpeck.thingycraft.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import ellpeck.thingycraft.blocks.InitBlocks;
import ellpeck.thingycraft.blocks.models.ModelCrucible;
import ellpeck.thingycraft.blocks.models.RendererCrucible;
import ellpeck.thingycraft.blocks.models.RendererHoldingTileEntity;
import ellpeck.thingycraft.tile.TileEntityCrucible;
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
