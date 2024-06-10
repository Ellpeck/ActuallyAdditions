/*
 * This file ("ThreadSpecialFetcher.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.special;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

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
            //URL url = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/main/specialPeopleStuff.properties");
            URL url = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/1.20.4/specialPeopleStuff.properties");
            Properties specialProperties = new Properties();
            specialProperties.load(new InputStreamReader(url.openStream()));
            SpecialRenderInit.parse(specialProperties);

            ActuallyAdditions.LOGGER.info("Fetching Special People Stuff done!");
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Fetching Special People Stuff failed! (You can ignore this error technically.)", e);
        }
    }
}
