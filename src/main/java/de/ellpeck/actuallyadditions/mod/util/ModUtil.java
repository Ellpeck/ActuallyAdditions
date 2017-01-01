/*
 * This file ("ModUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ModUtil{

    public static final String VERSION = "@VERSION@"; //build.gradle

    public static final String MOD_ID = ActuallyAdditionsAPI.MOD_ID;
    public static final String NAME = "Actually Additions";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    private static final String PROXY_BASE = "de.ellpeck.actuallyadditions.mod.proxy.";
    public static final String PROXY_CLIENT = PROXY_BASE+"ClientProxy";
    public static final String PROXY_SERVER = PROXY_BASE+"ServerProxy";
}
