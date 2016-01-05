/*
 * This file ("UpdateChecker.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.update;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;

public class UpdateChecker{

    public static final String DOWNLOAD_LINK = "http://minecraft.curseforge.com/mc-mods/228404-actually-additions/files";
    public static final String CHANGELOG_LINK = "http://ellpeck.de/actaddchangelog/";
    public static boolean checkFailed;
    public static boolean needsUpdateNotify;
    public static String updateVersion;

    public static void init(){
        if(ConfigBoolValues.DO_UPDATE_CHECK.isEnabled() && !Util.isDevVersion()){
            ModUtil.LOGGER.info("Initializing Update Checker...");
            new ThreadUpdateChecker();
        }
    }
}
