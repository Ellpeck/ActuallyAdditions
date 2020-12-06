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
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

import static de.ellpeck.actuallyadditions.common.items.misc.DrillAugmentItem.AugmentType;

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

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos posIn, PlayerEntity player) {
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

        if ((hasAugment(augments, AugmentType.MINING_AUGMENT_I) || hasAugment(augments, AugmentType.MINING_AUGMENT_II))) {
            return this.breakBlocksWithAoe(player, player.world, stack, drill, fluxPerBlock, hasAugment(augments, AugmentType.MINING_AUGMENT_I) ? 1 : 2);
        } else {
            return this.destroyBlock(posIn, player, player.world, stack, drill, fluxPerBlock);
        }
    }

    private boolean breakBlocksWithAoe(PlayerEntity player, World world, ItemStack drill, ItemStack drillEnchanted, int fluxPerBlock, int radius) {
        double distance = Objects.requireNonNull(player.getAttribute(ForgeMod.REACH_DISTANCE.get())).getValue();
        RayTraceResult trace = player.pick(player.isCreative() ? distance : distance - 0.5D, 1F, false);
        if (trace.getType() != RayTraceResult.Type.BLOCK) {
            return false;
        }

        BlockRayTraceResult pick = (BlockRayTraceResult) trace;

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
            if (!destroyBlock(e, player, world, drill, drillEnchanted, fluxPerBlock)) {
                failed.add(e);
            }
        });

        return failed.contains(pick.getPos());
    }

    private boolean destroyBlock(BlockPos pos, PlayerEntity player, World world, ItemStack drill, ItemStack drillEnchanted, int fluxPerBlock) {
        BlockState state = world.getBlockState(pos);
        int flux = this.getCrystalFlux(drill).map(IEnergyStorage::getEnergyStored).orElse(0);

        if (!ForgeHooks.canHarvestBlock(state, player, world, pos) || flux < fluxPerBlock) {
            return false;
        }

        if (!world.isRemote) {
            int event = ForgeHooks.onBlockBreakEvent(world, ((ServerPlayerEntity) player).interactionManager.getGameType(), (ServerPlayerEntity) player, pos);
            if (event == -1) {
                return false;
            }

            TileEntity tileEntity = world.getTileEntity(pos);
            state.getBlock().onPlayerDestroy(world, pos, state);
            state.getBlock().harvestBlock(world, player, pos, state, tileEntity, drillEnchanted);
            state.getBlock().dropXpOnBlockBreak((ServerWorld) world, pos, event);
        }

        // Old Ell Code :cry:, I mean, it's perfect!
        // Client code
        world.playEvent(2001, pos, Block.getStateId(state));
        if (state.getBlock().removedByPlayer(state, world, pos, player, true, world.getFluidState(pos))) {
            state.getBlock().onPlayerDestroy(world, pos, state);
        }

        // callback to the tool
        drill.onBlockDestroyed(world, state, pos, player);
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
