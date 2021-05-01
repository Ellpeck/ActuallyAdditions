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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            RayTraceResult rayTraceResult = player.pick(100,0,false);
            if (rayTraceResult != null && (rayTraceResult.getType() == RayTraceResult.Type.BLOCK || player.rotationPitch >= -5)) {
                BlockRayTraceResult res = (BlockRayTraceResult)rayTraceResult;
                BlockPos pos = res.getPos().offset(res.getFace());
                int baseUse = 200;
                int use = baseUse + (int) (baseUse * rayTraceResult.getHitVec().distanceTo(new Vector3d(player.getPosX(), player.getPosY() + (player.getEyeHeight() - player.getEyeHeight()), player.getPosZ())));
                if (this.getEnergyStored(stack) >= use) {
                    ((ServerPlayerEntity) player).connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getY(), player.rotationYaw, player.rotationPitch);
                    player.dismount();
                    world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        this.extractEnergyInternal(stack, use, false);
                        player.getCooldownTracker().setCooldown(this, 50);
                    }
                    return ActionResult.resultSuccess(stack);
                }
            }
        }
        player.swingArm(hand);
        return ActionResult.resultFail(stack);
    }
}
