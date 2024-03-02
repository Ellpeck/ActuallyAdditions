/*
 * This file ("ItemTeleStaff.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ItemTeleportStaff extends ItemEnergy {

    public ItemTeleportStaff() {
        super(250000, 1000);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            HitResult rayTraceResult = player.pick(100,1F,false);
            if (rayTraceResult.getType() == HitResult.Type.BLOCK || player.getXRot() >= -5) {
                Vec3 location = rayTraceResult.getLocation();
                Vec3 pos = Vec3.atBottomCenterOf(new Vec3i(location.x, location.y, location.z));
                int baseUse = 200;
                int use = baseUse + (int) (baseUse * player.blockPosition().distSqr(new Vec3i(pos.x, pos.y, pos.z)));
                if (this.getEnergyStored(stack) >= use) {
                    ((ServerPlayer) player).connection.teleport(pos.x, pos.y + 1F, pos.z, player.getYRot(), player.getXRot());
                    player.removeVehicle();
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        this.extractEnergyInternal(stack, use, false);
                        player.getCooldowns().addCooldown(this, 50);
                    }
                    return InteractionResultHolder.success(stack);
                }
            }
        }
        player.swing(hand);
        return InteractionResultHolder.fail(stack);
    }
}
