package de.ellpeck.actuallyadditions.common.misc.special;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;

public class ThreadSpecialFetcher extends Thread {

    public ThreadSpecialFetcher() {
        this.setName(ActuallyAdditions.NAME + " Special Fetcher");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        ActuallyAdditions.LOGGER.info("Fetching Special People Stuff...");
        try {
            URL url = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/specialPeopleStuff.properties");
            Properties specialProperties = new Properties();
            specialProperties.load(new InputStreamReader(url.openStream()));
            SpecialRenderInit.parse(specialProperties);

            ActuallyAdditions.LOGGER.info("Fetching Special People Stuff done!");
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Fetching Special People Stuff failed! (You can ignore this error technically.)", e);
        }
    }
}
