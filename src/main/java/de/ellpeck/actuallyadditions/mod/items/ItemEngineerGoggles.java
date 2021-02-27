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
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Set;

public class ItemEngineerGoggles extends ItemArmorAA implements IGoggles {

    private final Set<Entity> cachedGlowingEntities = new ConcurrentSet<>();

    private final boolean displayMobs;

    public ItemEngineerGoggles(boolean displayMobs) {
        super(ArmorMaterials.GOGGLES, EquipmentSlotType.HEAD, InitItems.defaultProps().setNoRepair().maxDamage(0));
        this.displayMobs = displayMobs;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isWearing(PlayerEntity player) {
        ItemStack face = player.inventory.armorInventory.get(3);
        return StackUtil.isValid(face) && face.getItem() instanceof IGoggles;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        PlayerEntity player = ClientProxy.getCurrentPlayer();
        if (player != null && isWearing(player)) {
            ItemStack face = player.inventory.armorInventory.get(3);
            if (((IGoggles) face.getItem()).displaySpectralMobs()) {
                double range = 8;
                AxisAlignedBB aabb = new AxisAlignedBB(player.getPosX() - range, player.getPosY() - range, player.getPosZ() - range, player.getPosX() + range, player.getPosY() + range, player.getPosZ() + range);
                List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, aabb);
                if (entities != null && !entities.isEmpty()) {
                    this.cachedGlowingEntities.addAll(entities);
                }

                if (!this.cachedGlowingEntities.isEmpty()) {
                    for (Entity entity : this.cachedGlowingEntities) {
                        if (!entity.isAlive() || entity.getDistanceSq(player.getPosX(), player.getPosY(), player.getPosZ()) > range * range) {
                            entity.setGlowing(false);

                            this.cachedGlowingEntities.remove(entity);
                        } else {
                            entity.setGlowing(true);
                        }
                    }
                }

                return;
            }
        }

        if (!this.cachedGlowingEntities.isEmpty()) {
            for (Entity entity : this.cachedGlowingEntities) {
                if (entity.isAlive()) {
                    entity.setGlowing(false);
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
