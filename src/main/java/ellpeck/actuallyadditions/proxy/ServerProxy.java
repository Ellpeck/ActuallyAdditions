package ellpeck.actuallyadditions.proxy;

import ellpeck.actuallyadditions.util.ModUtil;

@SuppressWarnings("unused")
public class ServerProxy implements IProxy{

    @Override
    public void preInit(){
        ModUtil.LOGGER.info("PreInitializing ServerProxy...");
    }

    @Override
    public void init(){
        ModUtil.LOGGER.info("Initializing ServerProxy...");
    }

    @Override
    public void postInit(){
        ModUtil.LOGGER.info("PostInitializing ServerProxy...");
    }
}
