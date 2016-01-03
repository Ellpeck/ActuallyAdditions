/*
 * This file ("ThreadUpdateChecker.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.update;

import de.ellpeck.actuallyadditions.util.ModUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ThreadUpdateChecker extends Thread{

    public ThreadUpdateChecker(){
        this.setName(ModUtil.MOD_ID+" Update Checker");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run(){
        ModUtil.LOGGER.info("Starting Update Check...");
        try{
            URL newestURL = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/update/updateVersion.txt");
            BufferedReader newestReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            UpdateChecker.updateVersion = newestReader.readLine();
            newestReader.close();

            int updateVersion = Integer.parseInt(UpdateChecker.updateVersion.replace("-", "").replace(".", "").replace("r", ""));
            int clientVersion = Integer.parseInt(ModUtil.VERSION.replace("-", "").replace(".", "").replace("r", ""));
            if(updateVersion > clientVersion){
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
                ModUtil.LOGGER.info("Current Version: "+ModUtil.VERSION+", newest Version: "+UpdateChecker.updateVersion+"!");
                ModUtil.LOGGER.info("View the Changelog at "+UpdateChecker.CHANGELOG_LINK);
                ModUtil.LOGGER.info("Download at "+UpdateChecker.DOWNLOAD_LINK);
            }
            else{
                ModUtil.LOGGER.info(ModUtil.NAME+" is up to date!");
            }
        }
    }
}