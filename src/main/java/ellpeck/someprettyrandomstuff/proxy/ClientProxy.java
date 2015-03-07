package ellpeck.someprettyrandomstuff.proxy;


import cpw.mods.fml.client.registry.ClientRegistry;
import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.blocks.render.ModelCompost;
import ellpeck.someprettyrandomstuff.blocks.render.RenderItems;
import ellpeck.someprettyrandomstuff.blocks.render.RenderTileEntity;
import ellpeck.someprettyrandomstuff.tile.TileEntityCompost;
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
    }

    @Override
    public void postInit(){

    }
}
