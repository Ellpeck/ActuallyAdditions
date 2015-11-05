/*
 * This file ("ThreadSpecialFetcher.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc.special;

import ellpeck.actuallyadditions.util.ModUtil;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class ThreadSpecialFetcher extends Thread{

    public ThreadSpecialFetcher(){
        this.setName(ModUtil.MOD_ID+" Special Fetcher");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run(){
        ModUtil.LOGGER.info("Fetching Special People Stuff...");
        try{
            URL url = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/specialPeopleStuff.properties");
            Properties specialProperties = new Properties();
            specialProperties.load(new InputStreamReader(url.openStream()));
            SpecialRenderInit.parse(specialProperties);

            ModUtil.LOGGER.info("Fetching Special People Stuff done!");
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Fetching Special People Stuff failed! (You can ignore this error technically.)", e);
        }
    }
}
