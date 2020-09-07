package de.ellpeck.actuallyadditions.mod.entity;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class InitEntities {

    public static void init() {
        ActuallyAdditions.LOGGER.info("Initializing Entities...");

        EntityRegistry.registerModEntity(new ResourceLocation(ActuallyAdditions.MODID, "worm"), EntityWorm.class, ActuallyAdditions.MODID + ".worm", 0, ActuallyAdditions.INSTANCE, 64, 1, false);
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        RenderingRegistry.registerEntityRenderingHandler(EntityWorm.class, RenderWorm::new);
    }

}
