package ellpeck.someprettyrandomstuff.proxy;


import ellpeck.someprettyrandomstuff.blocks.renderer.RenderRegistry;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    public void preInit(){

    }

    public void init(){
        RenderRegistry.init();
    }

    public void postInit(){

    }
}
