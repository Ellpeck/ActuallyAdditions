/*
 * This file ("ThreadUpdateChecker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.update;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class ThreadUpdateChecker extends Thread{

    public ThreadUpdateChecker(){
        this.setName(ModUtil.NAME+" Update Checker");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run(){
        ModUtil.LOGGER.info("Starting Update Check...");
        try{
            URL newestURL = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/update/updateVersions.properties");
            Properties updateProperties = new Properties();
            updateProperties.load(new InputStreamReader(newestURL.openStream()));

            String currentMcVersion = Util.getMcVersion();
            if(ConfigBoolValues.UPDATE_CHECK_VERSION_SPECIFIC.isEnabled()){
                String newestVersionProp = updateProperties.getProperty(currentMcVersion);

                UpdateChecker.updateVersionInt = Integer.parseInt(newestVersionProp);
                UpdateChecker.updateVersionString = currentMcVersion+"-r"+newestVersionProp;
            }
            else{
                int highest = 0;
                String highestString = "";

                for(String updateMC : updateProperties.stringPropertyNames()){
                    String updateVersion = updateProperties.getProperty(updateMC);
                    int update = Integer.parseInt(updateVersion);
                    if(highest < update){
                        highest = update;
                        highestString = updateMC+"-r"+updateVersion;
                    }
                }

                UpdateChecker.updateVersionInt = highest;
                UpdateChecker.updateVersionString = highestString;
            }

            String clientVersionString = Util.getMajorModVersion();
            int clientVersion = Integer.parseInt(clientVersionString.contains("_") ? clientVersionString.substring(0, clientVersionString.indexOf("_")) : clientVersionString);
            if(UpdateChecker.updateVersionInt > clientVersion){
                UpdateChecker.needsUpdateNotify = true;
            }

            ModUtil.LOGGER.info("Update Check done!");
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Update Check failed!", e);
            UpdateChecker.checkFailed = true;
        }

        if(!UpdateChecker.checkFailed){
            if(UpdateChecker.needsUpdateNotify){
                ModUtil.LOGGER.info("There is an Update for "+ModUtil.NAME+" available!");
                ModUtil.LOGGER.info("Current Version: "+ModUtil.VERSION+", newest Version: "+UpdateChecker.updateVersionString+"!");
                ModUtil.LOGGER.info("View the Changelog at "+UpdateChecker.CHANGELOG_LINK);
                ModUtil.LOGGER.info("Download at "+UpdateChecker.DOWNLOAD_LINK);
            }
            else{
                ModUtil.LOGGER.info(ModUtil.NAME+" is up to date!");
            }
        }
    }
}