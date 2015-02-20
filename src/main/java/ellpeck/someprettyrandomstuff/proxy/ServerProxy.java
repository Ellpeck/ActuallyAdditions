package ellpeck.someprettyrandomstuff.proxy;

import ellpeck.someprettyrandomstuff.util.Util;

@SuppressWarnings("unused")
public class ServerProxy implements IProxy{

    @Override
    public void preInit(){
        Util.logInfo("PreInitializing ServerProxy...");
    }

    @Override
    public void init(){
        Util.logInfo("Initializing ServerProxy...");
    }

    @Override
    public void postInit(){
        Util.logInfo("PostInitializing ServerProxy...");
    }
}
