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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class BannerHelper {
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(BuiltInRegistries.BANNER_PATTERN, ActuallyAdditions.MODID);

    public static DeferredHolder<BannerPattern, BannerPattern> DRILL = BANNER_PATTERNS.register("drill", () -> new BannerPattern(ActuallyAdditions.MODID + ":drill"));
    public static DeferredHolder<BannerPattern, BannerPattern> LEAF_BLO = BANNER_PATTERNS.register("leaf_blo", () -> new BannerPattern(ActuallyAdditions.MODID + ":leaf_blo"));
    public static DeferredHolder<BannerPattern, BannerPattern> PHAN_CON = BANNER_PATTERNS.register("phan_con", () -> new BannerPattern(ActuallyAdditions.MODID + ":phan_con"));
    public static DeferredHolder<BannerPattern, BannerPattern> BOOK = BANNER_PATTERNS.register("book", () -> new BannerPattern(ActuallyAdditions.MODID + ":book"));

    public static void init(IEventBus eventBus) {
        BANNER_PATTERNS.register(eventBus);
    }
}
