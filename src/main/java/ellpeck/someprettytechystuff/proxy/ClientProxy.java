package ellpeck.someprettytechystuff.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import ellpeck.someprettytechystuff.blocks.InitBlocks;
import ellpeck.someprettytechystuff.blocks.models.*;
import ellpeck.someprettytechystuff.tile.TileEntityCrucible;
import ellpeck.someprettytechystuff.tile.TileEntityCrucibleFire;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    public void preInit() {

    }

    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucible.class, new RendererCrucible());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockCrucible), new RendererHoldingTileEntity(new ModelCrucible(), RendererCrucible.resLoc));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucibleFire.class, new RendererCrucibleFire());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(InitBlocks.blockCrucibleFire), new RendererHoldingTileEntity(new ModelCrucibleFire(), RendererCrucibleFire.resLoc));
    }

    public void postInit() {

    }
}
