package ellpeck.someprettyrandomstuff.proxy;

import ellpeck.someprettyrandomstuff.util.Util;

@SuppressWarnings("unused")
public class ServerProxy implements IProxy{

    public void preInit(){
        Util.logInfo("PreInitializing ServerProxy...");
    }

    public void init(){
        Util.logInfo("Initializing ServerProxy...");
    }

    public void postInit(){
        Util.logInfo("PostInitializing ServerProxy...");
    }
}
