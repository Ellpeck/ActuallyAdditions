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
