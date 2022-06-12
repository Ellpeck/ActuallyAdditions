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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class ItemTeleportStaff extends ItemEnergy {

    public ItemTeleportStaff() {
        super(250000, 1000);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            RayTraceResult rayTraceResult = player.pick(100,1F,false);
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK || player.xRot >= -5) {
                Vector3d location = rayTraceResult.getLocation();
                Vector3d pos = Vector3d.atBottomCenterOf(new Vector3i(location.x, location.y, location.z));
                int baseUse = 200;
                int use = baseUse + (int) (baseUse * player.blockPosition().distSqr(new Vector3i(pos.x, pos.y, pos.z)));
                if (this.getEnergyStored(stack) >= use) {
                    ((ServerPlayerEntity) player).connection.teleport(pos.x, pos.y + 1F, pos.z, player.yRot, player.xRot);
                    player.removeVehicle();
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        this.extractEnergyInternal(stack, use, false);
                        player.getCooldowns().addCooldown(this, 50);
                    }
                    return ActionResult.success(stack);
                }
            }
        }
        player.swing(hand);
        return ActionResult.fail(stack);
    }
}
