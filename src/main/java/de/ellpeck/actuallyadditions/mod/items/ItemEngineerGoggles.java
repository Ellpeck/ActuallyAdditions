/*
 * This file ("ItemInfraredGoggles.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.misc.IGoggles;
import de.ellpeck.actuallyadditions.mod.items.base.ItemArmorAA;
import de.ellpeck.actuallyadditions.mod.material.ArmorMaterials;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.Set;

public class ItemEngineerGoggles extends ItemArmorAA implements IGoggles {

    private final boolean displayMobs;

    public ItemEngineerGoggles(boolean displayMobs) {
        super(ArmorMaterials.GOGGLES, Type.HELMET, ActuallyItems.defaultProps().setNoRepair().durability(0));
        this.displayMobs = displayMobs;
    }

    public static boolean isWearing(Player player) {
        ItemStack face = player.getInventory().armor.get(3);
        return !face.isEmpty() && face.getItem() instanceof IGoggles;
    }

    @Override
    public boolean displaySpectralMobs() {
        return this.displayMobs;
    }
}
