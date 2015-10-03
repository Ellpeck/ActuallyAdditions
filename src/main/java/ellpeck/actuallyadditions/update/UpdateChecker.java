/*
 * This file ("UpdateChecker.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.update;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;

public class UpdateChecker{

    public static final String DOWNLOAD_LINK = "http://minecraft.curseforge.com/mc-mods/228404-actually-additions/files";
    public static boolean doneChecking = false;
    public static boolean checkFailed = false;
    public static String updateVersionS;
    public static int updateVersion;
    public static int clientVersion;
    public static String changelog;

    public void init(){
        if(ConfigBoolValues.DO_UPDATE_CHECK.isEnabled()){
            ModUtil.LOGGER.info("Initializing Update Checker...");
            Util.registerEvent(this);
            new ThreadUpdateChecker();
        }
    }
}
