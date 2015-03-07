package ellpeck.actuallyadditions.proxy;


import cpw.mods.fml.client.registry.ClientRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.render.ModelCompost;
import ellpeck.actuallyadditions.blocks.render.RenderItems;
import ellpeck.actuallyadditions.blocks.render.RenderTileEntity;
import ellpeck.actuallyadditions.tile.TileEntityCompost;
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
