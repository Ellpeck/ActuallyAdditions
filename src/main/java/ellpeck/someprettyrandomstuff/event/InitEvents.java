package ellpeck.someprettyrandomstuff.event;

import ellpeck.someprettyrandomstuff.util.Util;

public class InitEvents{

    public static void init(){
        Util.logInfo("Initializing Events...");

        Util.registerEvent(new SmeltEvent());
        Util.registerEvent(new CraftEvent());
        Util.registerEvent(new KilledEvent());
    }

}
