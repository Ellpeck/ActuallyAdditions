/*
 * This file ("InitEntities.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Locale;

public class InitEntities{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Entities...");

        EntityRegistry.registerModEntity(EntityFireworkBoxMinecart.class, ModUtil.MOD_ID+":minecartFireworkBox", 0, ActuallyAdditions.instance, 256, 1, true);
    }
}
