package de.ellpeck.actuallyadditions.common.items.useables;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import de.ellpeck.actuallyadditions.common.config.Config;
import de.ellpeck.actuallyadditions.common.container.DrillContainer;
import de.ellpeck.actuallyadditions.common.items.CrystalFluxItem;
import de.ellpeck.actuallyadditions.common.items.misc.DrillAugmentItem;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import static de.ellpeck.actuallyadditions.common.items.misc.DrillAugmentItem.AugmentType;

/**
 * @implNote The augment system might be made more abstract as I think other things might use it.
 *
 * todo: Still need to support the placement augment. Tired out after all the AOE crap
 */
public class DrillItem extends CrystalFluxItem {
    public static final String NBT_AUGMENT_TAG = "augments";

    private static final int BASE_ENERGY_USE = 100;

    public DrillItem() {
        super(
                baseProps()
                    .maxDamage(0)
                    .setNoRepair()
                    .addToolType(ToolType.PICKAXE, 4)
                    .addToolType(ToolType.SHOVEL, 4),
                Config.ITEM_CONFIG.drillMaxEnergy::get,
                1000
        );
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();

        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Drill Modifier", getCrystalFlux(stack).map(IEnergyStorage::getEnergyStored).orElse(0) >= BASE_ENERGY_USE ? 8.0F : 0.1F, AttributeModifier.Operation.ADDITION));
            map.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool Modifier", -2.5F, AttributeModifier.Operation.ADDITION));
        }

        return map;
    }

    /**
     * Works out what enchants we should have and handles how many blocks it should break
     *
     * todo: add hardness check for other blocks so it doesn't instant break blocks a lot harder than the target
     */
    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos posIn, PlayerEntity player) {
        // Fail nice and early
        double distance = Objects.requireNonNull(player.getAttribute(ForgeMod.REACH_DISTANCE.get())).getValue();
        RayTraceResult trace = player.pick(player.isCreative() ? distance : distance - 0.5D, 1F, false);
        if (trace.getType() != RayTraceResult.Type.BLOCK) {
            return true;
        }

        BlockRayTraceResult pick = (BlockRayTraceResult) trace;

        int crystalFlux = getCrystalFlux(stack).map(IEnergyStorage::getEnergyStored).orElse(0);
        int fluxPerBlock = this.getFluxPerBlock(stack);

        if (crystalFlux < fluxPerBlock) {
            return false;
        }

        ImmutableSet<AugmentType> augments = getAugments(stack);
        ItemStack drill = stack.copy(); // copy to apply enchants

        // Apply enchants
        if (hasAugment(augments, AugmentType.SILK_TOUCH_AUGMENT)) {
            drill.addEnchantment(Enchantments.SILK_TOUCH, 1);
        }

        if (hasAugment(augments, AugmentType.FORTUNE_AUGMENT_I) || hasAugment(augments, AugmentType.FORTUNE_AUGMENT_II)) {
            drill.addEnchantment(Enchantments.FORTUNE, hasAugment(augments, AugmentType.FORTUNE_AUGMENT_I) ? 1 : 3);
        }

        if ((hasAugment(augments, AugmentType.MINING_AUGMENT_I) || hasAugment(augments, AugmentType.MINING_AUGMENT_II)) && !player.isSneaking()) {
            return this.breakBlocksWithAoe(pick, player, player.world, stack, drill, fluxPerBlock, hasAugment(augments, AugmentType.MINING_AUGMENT_I) ? 1 : 2);
        } else {
            return this.destroyBlock(posIn, pick.getFace(), player, player.world, stack, drill, fluxPerBlock, (cost) -> getCrystalFlux(drill).ifPresent(a -> a.extractEnergy(cost, false)));
        }
    }

    /**
     * Breaks an AOE from a single point. Requires a rayTrace to figure out it's facing direction. Still MC
     *
     * @param pick BlockRayTrace for facing
     * @param player The Player Duh
     * @param world The players world
     * @param drill The non-enchanted current item stack
     * @param drillEnchanted A cloned stack with enchantments
     * @param fluxPerBlock How much each block will cost to break
     * @param radius How many blocks to break (uses 1+(n2)) so 3x3 = 1, 5x5 = 2
     *
     * @return if the block was destroyed, this method will only return false if the center block fails to break.
     */
    private boolean breakBlocksWithAoe(BlockRayTraceResult pick, PlayerEntity player, World world, ItemStack drill, ItemStack drillEnchanted, int fluxPerBlock, int radius) {
        BlockPos pos = pick.getPos();
        Direction.Axis axis = pick.getFace().getAxis();

        Set<BlockPos> posSet = new HashSet<>();

        // Uses the facing axis to move around the X,Y,Z to allow for multiple faces in 2 for loops
        int a = axis != Direction.Axis.X ? pos.getX() : pos.getY(); // Z & Y plane both use X
        int b = axis != Direction.Axis.Z ? pos.getZ() : pos.getY(); // X & Y plane both use Z

        for (int i = (a - radius); i < (a + radius) + 1; i++) {
            for (int j = (b - radius); j < (b + radius) + 1; j++) {
                // Assign [a] and [b] to X, Y, or Z depending on the axis
                posSet.add(new BlockPos(
                        axis != Direction.Axis.X ? i : pos.getX(),
                        axis == Direction.Axis.X ? i : (axis == Direction.Axis.Z ? j : pos.getY()),
                        axis != Direction.Axis.Z ? j : pos.getZ()
                ));
            }
        }

        Set<BlockPos> failed = new HashSet<>();
        posSet.forEach(e -> {
            if (!destroyBlock(e, pick.getFace(), player, world, drill, drillEnchanted, fluxPerBlock, (cost) -> getCrystalFlux(drill).ifPresent(x -> x.extractEnergy(cost, false)))) {
                failed.add(e);
            }
        });

        return failed.contains(pick.getPos());
    }

    /**
     * Handles destroying blocks on both the CLIENT & SERVER sides. Would not recommend using this on
     * Server only / Client classes. Also, thanks to all the mods and Ell for helping me
     * piecing this together, hopefully it doesn't fuck up.
     *
     * @param player The Player Duh
     * @param face The blocks face
     * @param world The players world
     * @param drill The non-enchanted current item stack
     * @param drillEnchanted A cloned stack with enchantments
     * @param fluxPerBlock How much each block will cost to break
     * @param onBreak Call back to do something once the block breaks
     *
     * @return returns false if the block gets blocked by another mod
     */
    // Todo: if we ever need this again, move to world helper
    private boolean destroyBlock(BlockPos pos, Direction face, PlayerEntity player, World world, ItemStack drill, ItemStack drillEnchanted, int fluxPerBlock, Consumer<Integer> onBreak) {
        BlockState state = world.getBlockState(pos);
        int flux = this.getCrystalFlux(drill).map(IEnergyStorage::getEnergyStored).orElse(0);

        if (world.isAirBlock(pos)
                || flux < fluxPerBlock
                || state.getBlockHardness(world, pos) <= 0f
                || !ForgeHooks.canHarvestBlock(state, player, world, pos)) {
            return false;
        }

        Block block = state.getBlock();

        // Handle what we do when in creative
        if (player.isCreative()) {
            block.onBlockHarvested(world, pos, state, player);

            if (block.removedByPlayer(state, world, pos, player, false, world.getFluidState(pos))) {
                block.onPlayerDestroy(world, pos, state);
            }

            if (!world.isRemote && player instanceof ServerPlayerEntity) {
                // Honestly not super sure what this does but it's everywhere else so it's important for sure!
                ((ServerPlayerEntity) player).connection.sendPacket(new SChangeBlockPacket(world, pos));
            }

            onBreak.accept(fluxPerBlock);
            return true;
        }

        // Fire this for some stats and stuffs
        drill.onBlockDestroyed(world, state, pos, player);
        boolean removed = block.removedByPlayer(state, world, pos, player, true, world.getFluidState(pos));
        if (removed) {
            block.onPlayerDestroy(world, pos, state);
        }

        // Only server code
        if (!world.isRemote) {
            // event will return the XP to drop if it does not get cancelled
            int event = ForgeHooks.onBlockBreakEvent(world, ((ServerPlayerEntity) player).interactionManager.getGameType(), (ServerPlayerEntity) player, pos);
            if (event == -1) {
                // Rip
                return false;
            }

            if (removed) {
                block.onPlayerDestroy(world, pos, state);

                // Pass drill enchanted to ensure the fortune and silk are applied
                block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), drillEnchanted);
                block.dropXpOnBlockBreak((ServerWorld) world, pos, event);
            }

            ((ServerPlayerEntity) player).connection.sendPacket(new SChangeBlockPacket(world, pos));
            onBreak.accept(fluxPerBlock);
            return true;
        }

        // All this is client only code
        Objects.requireNonNull(Minecraft.getInstance().getConnection())
                .sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, pos, face));

        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        if (worldIn.isRemote()) {
            return ActionResult.resultPass(stack);
        }

        if (player.isSneaking() && handIn == Hand.MAIN_HAND) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider(
                    (windowId, playerInv, playerEntity) -> new DrillContainer(windowId, playerInv, stack),
                    Help.trans("gui.name.drill")
            ));
        }

        return ActionResult.resultSuccess(stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        int flux = getCrystalFlux(stack).map(IEnergyStorage::getEnergyStored).orElse(0);

        if (flux == 0 || flux <= this.getFluxPerBlock(stack))
            return 0.1f;

        return blockIsPartOfSpecialWhitelist(state) || state.getHarvestTool() == null || this.getToolTypes(stack).contains(state.getHarvestTool())
                ? this.getDestroySpeedFromUpgrades(stack)
                : 1.0f;
    }

    /**
     * Calculates the speed the drill can mine based on it's augments and it's reducer augments
     *
     * @return speed that the drill can mine
     */
    private float getDestroySpeedFromUpgrades(ItemStack stack) {
        float speed = 8.0f;

        ImmutableSet<AugmentType> augments = getAugments(stack);
        if (augments.size() == 0) {
            return speed;
        }

        float reduction = hasAugment(augments, AugmentType.MINING_AUGMENT_I) ? (hasAugment(augments, AugmentType.MINING_AUGMENT_II) ? 0.5f : 0.35f) : 1f;
        float modifier = hasAugment(augments, AugmentType.SPEED_AUGMENT_I)
                ? 8.0f
                : (hasAugment(augments, AugmentType.SPEED_AUGMENT_II)
                    ? 25.0f
                    : (hasAugment(augments, AugmentType.SPEED_AUGMENT_III) ? 37.0f : 0f));

        return (speed + modifier) * reduction;
    }

    /**
     * Calculates the cost to mine a block based on the items augments
     *
     * @return the cost per operation
     */
    private int getFluxPerBlock(ItemStack stack) {
        return BASE_ENERGY_USE + getAugments(stack).stream().mapToInt(AugmentType::getEnergyCost).sum();
    }

    /**
     * Checks whether or not a state can be mined even if the other operators fail
     */
    private boolean blockIsPartOfSpecialWhitelist(BlockState state) {
        ResourceLocation location = state.getBlock().getRegistryName();
        if (location == null) {
            return false;
        }

        return Config.ITEM_CONFIG.drillSpecialBlockWhitelist.get().contains(location.toString());
    }

    private boolean hasAugment(ItemStack stack, AugmentType type) {
        return getAugments(stack).contains(type);
    }

    /**
     * Use this in preference for {@link #hasAugment(ItemStack, AugmentType)} when doing multiple comparisons
     */
    private boolean hasAugment(ImmutableSet<AugmentType> augments, AugmentType type) {
        return augments.contains(type);
    }

    /**
     * Returns a list of AugmentTypes attached to the item stack.
     */
    private ImmutableSet<AugmentType> getAugments(ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler();
        handler.deserializeNBT(stack.getOrCreateChildTag(NBT_AUGMENT_TAG));

        ImmutableSet.Builder<AugmentType> augments = ImmutableSet.builder();
        for (int i = 0; i < handler.getSlots(); i ++) {
            ItemStack stackInSlot = handler.getStackInSlot(i);
            if (!stackInSlot.isEmpty() && stackInSlot.getItem() instanceof DrillAugmentItem) {
                augments.add(((DrillAugmentItem) stackInSlot.getItem()).getType());
            }
        }

        return augments.build();
    }
}
