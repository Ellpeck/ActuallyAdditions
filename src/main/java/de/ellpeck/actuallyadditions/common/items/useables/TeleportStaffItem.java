package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.items.ActuallyItem;
import de.ellpeck.actuallyadditions.common.items.IUseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class TeleportStaffItem extends ActuallyItem implements IUseItem {
    public TeleportStaffItem() {
        super(baseProps());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            return super.onItemRightClick(world, player, hand);
        }


        RayTraceResult traceResult = player.pick(100, 1.0f, false);
        if (traceResult.getType() != RayTraceResult.Type.BLOCK || !this.canUse(stack)) {
            return super.onItemRightClick(world, player, hand);
        }

        BlockPos pos = ((BlockRayTraceResult) traceResult).getPos();
        BlockPos toPos = pos.offset(((BlockRayTraceResult) traceResult).getFace(), 1);

        Vector3f centerOfHit = new Vector3f(toPos.getX(), toPos.getY(), toPos.getZ());
        centerOfHit.add(.5f, (((BlockRayTraceResult) traceResult).getFace().getAxis() == Direction.Axis.Y ? .5f : 0), .5f);

        // power cost for thing
        //                int use = baseUse + (int) (baseUse * pos.hitVec.distanceTo(new Vec3d(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ)));

        if (!player.isCreative()) {
            player.getCooldownTracker().setCooldown(this, 50);
        }

        player.dismount();
        player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        ((ServerPlayerEntity) player).connection.setPlayerLocation(centerOfHit.getX(), centerOfHit.getY(), centerOfHit.getZ(), player.rotationYaw, player.rotationPitch);
//
//        if (pos != null && (pos.typeOfHit == RayTraceResult.Type.BLOCK || player.rotationPitch >= -5)) {
//            int side = pos.sideHit.ordinal();
//            if (side != -1) {
//                double x = pos.hitVec.x - (side == 4 ? 0.5 : 0) + (side == 5 ? 0.5 : 0);
//                double y = pos.hitVec.y - (side == 0 ? 2.0 : 0) + (side == 1 ? 0.5 : 0);
//                double z = pos.hitVec.z - (side == 2 ? 0.5 : 0) + (side == 3 ? 0.5 : 0);
//                int baseUse = 200;
//                int use = baseUse + (int) (baseUse * pos.hitVec.distanceTo(new Vec3d(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ)));
//                if (this.getEnergyStored(stack) >= use) {
//                    ((EntityPlayerMP) player).connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
//                    player.dismountRidingEntity();
//                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
//                    if (!player.capabilities.isCreativeMode) {
//                        this.extractEnergyInternal(stack, use, false);
//                        player.getCooldownTracker().setCooldown(this, 50);
//                    }
//                    return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
//                }
//            }
//        }

        player.swingArm(hand);
        return ActionResult.resultSuccess(stack);
    }

    @Override
    public boolean canUse(ItemStack stack) {
        return true;
    }
}
