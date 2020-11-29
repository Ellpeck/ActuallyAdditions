package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.items.CrystalFluxItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class TeleportStaffItem extends CrystalFluxItem {
    private static final int BASE_COST_PER_USE = 200;

    public TeleportStaffItem() {
        super(baseProps(), () -> 250000);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            return super.onItemRightClick(world, player, hand);
        }

        RayTraceResult traceResult = player.pick(100, 1.0f, false);
        if (traceResult.getType() != RayTraceResult.Type.BLOCK) {
            return super.onItemRightClick(world, player, hand);
        }

        BlockRayTraceResult blockTrace = ((BlockRayTraceResult) traceResult);
        BlockPos toPos = blockTrace.getPos().offset(blockTrace.getFace(), 1);

        Vector3f centerOfHit = new Vector3f(toPos.getX(), toPos.getY(), toPos.getZ());
        centerOfHit.add(.5f, (blockTrace.getFace().getAxis() == Direction.Axis.Y ? .5f : 0), .5f);

        int energyCost = BASE_COST_PER_USE + (int) (BASE_COST_PER_USE * (player.getDistanceSq(toPos.getX(), toPos.getY(), toPos.getZ()) / 100));
        boolean canUse = stack.getCapability(CapabilityEnergy.ENERGY).map(e -> e.getEnergyStored() >= energyCost).orElse(false);

        if (!canUse) {
            return super.onItemRightClick(world, player, hand);
        }

        if (!player.isCreative()) {
            player.getCooldownTracker().setCooldown(this, 50);
            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energy ->
                    energy.extractEnergy(energyCost, false));
        }

        player.dismount();
        world.playSound(null, toPos.getX(), toPos.getY(), toPos.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        ((ServerPlayerEntity) player).connection.setPlayerLocation(centerOfHit.getX(), centerOfHit.getY(), centerOfHit.getZ(), player.rotationYaw, player.rotationPitch);

        player.swingArm(hand);
        return ActionResult.resultSuccess(stack);
    }
}
