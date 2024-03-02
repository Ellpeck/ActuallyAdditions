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
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;

import java.util.List;
import java.util.Set;

public class ItemEngineerGoggles extends ItemArmorAA implements IGoggles {

    private final Set<Entity> cachedGlowingEntities = new ConcurrentSet<>();

    private final boolean displayMobs;

    public ItemEngineerGoggles(boolean displayMobs) {
        super(ArmorMaterials.GOGGLES, EquipmentSlot.HEAD, ActuallyItems.defaultProps().setNoRepair().durability(0));
        this.displayMobs = displayMobs;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(this));

    }

    public static boolean isWearing(Player player) {
        ItemStack face = player.getInventory().armor.get(3);
        return StackUtil.isValid(face) && face.getItem() instanceof IGoggles;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && isWearing(player)) {
            ItemStack face = player.getInventory().armor.get(3);
            if (((IGoggles) face.getItem()).displaySpectralMobs()) {
                double range = 8;
                AABB aabb = new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range);
                List<Entity> entities = player.level.getEntitiesOfClass(Entity.class, aabb);
                if (entities != null && !entities.isEmpty()) {
                    this.cachedGlowingEntities.addAll(entities);
                }

                if (!this.cachedGlowingEntities.isEmpty()) {
                    for (Entity entity : this.cachedGlowingEntities) {
                        if (!entity.isAlive() || entity.distanceToSqr(player.getX(), player.getY(), player.getZ()) > range * range) {
                            entity.setGlowingTag(false);

                            this.cachedGlowingEntities.remove(entity);
                        } else {
                            entity.setGlowingTag(true);
                        }
                    }
                }

                return;
            }
        }

        if (!this.cachedGlowingEntities.isEmpty()) {
            for (Entity entity : this.cachedGlowingEntities) {
                if (entity.isAlive()) {
                    entity.setGlowingTag(false);
                }
            }
            this.cachedGlowingEntities.clear();
        }
    }

    @Override
    public boolean displaySpectralMobs() {
        return this.displayMobs;
    }
}
