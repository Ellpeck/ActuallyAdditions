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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemTeleStaff extends ItemEnergy {

    public ItemTeleStaff() {
        super(250000, 1000);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            RayTraceResult rayTraceResult = player.pick(100,0,false);
            if (rayTraceResult != null && (rayTraceResult.getType() == RayTraceResult.Type.BLOCK || player.xRot >= -5)) {
                BlockRayTraceResult res = (BlockRayTraceResult)rayTraceResult;
                BlockPos pos = res.getBlockPos().relative(res.getDirection());
                int baseUse = 200;
                int use = baseUse + (int) (baseUse * rayTraceResult.getLocation().distanceTo(new Vector3d(player.getX(), player.getY() + (player.getEyeHeight() - player.getEyeHeight()), player.getZ())));
                if (this.getEnergyStored(stack) >= use) {
                    ((ServerPlayerEntity) player).connection.teleport(pos.getX(), pos.getY(), pos.getY(), player.yRot, player.xRot);
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
