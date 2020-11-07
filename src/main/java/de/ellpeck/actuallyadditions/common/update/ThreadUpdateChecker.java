package de.ellpeck.actuallyadditions.common.update;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.common.util.Util;

public class ThreadUpdateChecker extends Thread {

    public ThreadUpdateChecker() {
        this.setName(ActuallyAdditions.NAME + " Update Checker");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        ActuallyAdditions.LOGGER.info("Starting Update Check...");
        try {
            URL newestURL = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/main/update/updateVersions.properties");
            Properties updateProperties = new Properties();
            updateProperties.load(new InputStreamReader(newestURL.openStream()));

            String currentMcVersion = Util.getMcVersion();
            if (ConfigBoolValues.UPDATE_CHECK_VERSION_SPECIFIC.isEnabled()) {
                String newestVersionProp = updateProperties.getProperty(currentMcVersion);

                UpdateChecker.updateVersionInt = Integer.parseInt(newestVersionProp);
                UpdateChecker.updateVersionString = currentMcVersion + "-r" + newestVersionProp;
            } else {
                int highest = 0;
                String highestString = "";

                for (String updateMC : updateProperties.stringPropertyNames()) {
                    String updateVersion = updateProperties.getProperty(updateMC);
                    int update = Integer.parseInt(updateVersion);
                    if (highest < update) {
                        highest = update;
                        highestString = updateMC + "-r" + updateVersion;
                    }
                }

                UpdateChecker.updateVersionInt = highest;
                UpdateChecker.updateVersionString = highestString;
            }

            String clientVersionString = Util.getMajorModVersion();
            int clientVersion = Integer.parseInt(clientVersionString.contains("_") ? clientVersionString.substring(0, clientVersionString.indexOf("_")) : clientVersionString);
            if (UpdateChecker.updateVersionInt > clientVersion) {
                UpdateChecker.needsUpdateNotify = true;
            }

            ActuallyAdditions.LOGGER.info("Update Check done!");
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Update Check failed!", e);
            UpdateChecker.checkFailed = true;
        }

        if (!UpdateChecker.checkFailed) {
            if (UpdateChecker.needsUpdateNotify) {
                ActuallyAdditions.LOGGER.info("There is an Update for " + ActuallyAdditions.NAME + " available!");
                ActuallyAdditions.LOGGER.info("Current Version: " + ActuallyAdditions.VERSION + ", newest Version: " + UpdateChecker.updateVersionString + "!");
                ActuallyAdditions.LOGGER.info("View the Changelog at " + UpdateChecker.CHANGELOG_LINK);
                ActuallyAdditions.LOGGER.info("Download at " + UpdateChecker.DOWNLOAD_LINK);
            } else {
                ActuallyAdditions.LOGGER.info(ActuallyAdditions.NAME + " is up to date!");
            }
        }

        UpdateChecker.threadFinished = true;
    }
}