/*
 * This file ("BannerHelper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public final class BannerHelper {

    public static ResourceKey<BannerPattern> DRILL = create("drill");
    public static ResourceKey<BannerPattern> LEAF_BLO = create("leaf_blo");
    public static ResourceKey<BannerPattern> PHAN_CON = create("phan_con");
    public static ResourceKey<BannerPattern> BOOK = create("book");

    private static ResourceKey<BannerPattern> create(String pName) {
        return ResourceKey.create(Registries.BANNER_PATTERN, ActuallyAdditions.modLoc(pName));
    }

    public static void bootstrap(BootstrapContext<BannerPattern> pContext) {
        register(pContext, DRILL);
        register(pContext, LEAF_BLO);
        register(pContext, PHAN_CON);
        register(pContext, BOOK);
    }

    public static void register(BootstrapContext<BannerPattern> pContext, ResourceKey<BannerPattern> pResourceKey) {
        pContext.register(pResourceKey, new BannerPattern(pResourceKey.location(), "block.minecraft.banner." + pResourceKey.location().toShortLanguageKey()));
    }
}
