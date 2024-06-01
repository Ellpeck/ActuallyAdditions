/*
 * This file ("ItemDrill.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDrill;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DrillItem extends ItemEnergy {
    public static final int HARVEST_LEVEL = 4;
    private static final int ENERGY_USE = 100;
    private static final List<ToolAction> ACTIONS = List.of(ToolActions.SHOVEL_DIG, ToolActions.PICKAXE_DIG);

    public DrillItem() {
        super(ActuallyItems.defaultProps().defaultDurability(0).stacksTo(1), 250000, 1000);
    }

    @Override
    public boolean canPerformAction(@Nonnull ItemStack stack, @Nonnull ToolAction toolAction) {
        return ACTIONS.contains(toolAction);
    }
    @Override
    public boolean isCorrectToolForDrops(@Nonnull BlockState pBlock) {
        Tier tier = Tiers.NETHERITE; //Use Nettherite as the tier as it has the same harvest level as the drill
        if (TierSortingRegistry.isTierSorted(tier)) {
            return TierSortingRegistry.isCorrectTierForDrops(tier, pBlock) && pBlock.is(ActuallyTags.Blocks.MINEABLE_WITH_DRILL);
        }
        if (HARVEST_LEVEL < 3 && pBlock.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (HARVEST_LEVEL < 2 && pBlock.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else {
            return HARVEST_LEVEL < 1 && pBlock.is(BlockTags.NEEDS_STONE_TOOL) ? false : pBlock.is(ActuallyTags.Blocks.MINEABLE_WITH_DRILL);
        }
    }

    /**
     * Gets all of the Slots from NBT
     *
     * @param stack The Drill
     */
    public static void loadSlotsFromNBT(IItemHandlerModifiable slots, ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag();
        TileEntityInventoryBase.loadSlots(slots, compound);
    }

    /**
     * Writes all of the Slots to NBT
     *
     * @param slots The Slots
     * @param stack The Drill
     */
    public static void writeSlotsToNBT(IItemHandler slots, ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag();

        TileEntityInventoryBase.saveSlots(slots, compound);
        stack.setTag(compound);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();

        ItemStack stack = player.getItemInHand(hand);
        ItemStack upgrade = this.getHasUpgradeAsStack(stack, ItemDrillUpgrade.UpgradeType.PLACER);
        if (!upgrade.isEmpty()) {
            int slot = ItemDrillUpgrade.getSlotToPlaceFrom(upgrade);
            if (slot >= 0 && slot < 9) { // TODO: validate... old = PlayerInventory.getHotbarSize(); new = 9
                ItemStack equip = player.getInventory().getItem(slot);
                if (!equip.isEmpty() && equip != stack) {
                    ItemStack toPlaceStack = equip;

	                //Places the Block into the World
	                BlockHitResult result = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
	                return toPlaceStack.useOn(new UseOnContext(level, player, hand, toPlaceStack, result));
                }
            }
        }

        return super.useOn(context);
    }

    /**
     * Checks if a certain Upgrade is installed and returns it as an ItemStack
     *
     * @param stack   The Drill
     * @param upgrade The Upgrade to be checked
     * @return The Upgrade, if it's installed
     */
    public ItemStack getHasUpgradeAsStack(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade) {
        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerDrill.SLOT_AMOUNT);
        loadSlotsFromNBT(inv, stack);
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (!slotStack.isEmpty() && slotStack.getItem() instanceof ItemDrillUpgrade drillUpgrade) {
                if (drillUpgrade.type == upgrade) {
                    return slotStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!world.isClientSide && player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            player.openMenu(new SimpleMenuProvider((id, inv, p) -> new ContainerDrill(id, inv), Component.translatable("container.actuallyadditions.drill")));
//            player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.DRILL.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity entityHit, @Nonnull InteractionHand hand) {
        int use = this.getEnergyUsePerBlock(stack);
        if (!(entityHit instanceof Player) || !((Player) entityHit).isCreative()) {
            if (this.getEnergyStored(stack) >= use) {
                this.extractEnergyInternal(stack, use, false);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, @Nonnull ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();

        if (slot == EquipmentSlot.MAINHAND) {
            map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("Drill Modifier", this.getEnergyStored(stack) >= ENERGY_USE
                    ? 8.0F
                    : 0.1F, AttributeModifier.Operation.ADDITION));
            map.put(Attributes.ATTACK_SPEED, new AttributeModifier("Tool Modifier", 1.5F, AttributeModifier.Operation.ADDITION));
        }

        return map;
    }


    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack)
                ? (this.hasExtraWhitelist(state.getBlock()) || state.is(ActuallyTags.Blocks.MINEABLE_WITH_DRILL))
                ? this.getEfficiencyFromUpgrade(stack)
                : 1.0F
                : 0.1F;
    }

    @Override
    public boolean onBlockStartBreak(@Nonnull ItemStack stack, @Nonnull BlockPos pos, @Nonnull Player player) {
        boolean toReturn = false;
        int use = this.getEnergyUsePerBlock(stack);
        if (this.getEnergyStored(stack) >= use) {
            //Enchants the Drill depending on the Upgrades it has
            if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)) {
                ItemUtil.addEnchantment(stack, Enchantments.SILK_TOUCH, 1);
            } else {
                if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)) {
                    ItemUtil.addEnchantment(stack, Enchantments.BLOCK_FORTUNE, this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II)
                            ? 3
                            : 1);
                }
            }
            //Block hit
            HitResult ray = player.pick(Util.getReachDistance(player), 1f, false);
            if (ray instanceof BlockHitResult trace) {
                //Breaks the Blocks
                if (!player.isShiftKeyDown() && this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
                    if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                        toReturn = this.breakBlocks(stack, 2, player.level(), pos, trace.getDirection(), player);
                    } else {
                        toReturn = this.breakBlocks(stack, 1, player.level(), pos, trace.getDirection(), player);
                    }
                } else {
                    toReturn = this.breakBlocks(stack, 0, player.level(), pos, trace.getDirection(), player);
                }

                //Removes Enchantments added above
                ItemUtil.removeEnchantment(stack, Enchantments.SILK_TOUCH);
                ItemUtil.removeEnchantment(stack, Enchantments.BLOCK_FORTUNE);
            }
        }
        return toReturn;
    }

/*    @Override //TODO old one
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack) && (this.hasExtraWhitelist(block) || state.canBeReplaced() || block == Blocks.SNOW_BLOCK || block == Blocks.SNOW || (block == Blocks.OBSIDIAN
                ? HARVEST_LEVEL >= 3
                : block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE
                ? block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK
                ? block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE
                ? block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE
                ? block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE
                ? block != Blocks.REDSTONE_ORE
                ? state.is(Tags.Blocks.STONE) || state.is(Tags.Blocks.STORAGE_BLOCKS)
                : HARVEST_LEVEL >= 2
                : HARVEST_LEVEL >= 1
                : HARVEST_LEVEL >= 1
                : HARVEST_LEVEL >= 2
                : HARVEST_LEVEL >= 2
                : HARVEST_LEVEL >= 2));
    }*/

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack) && super.isCorrectToolForDrops(stack, state);
    }

//    @Override
//    public int getHarvestLevel(ItemStack stack, ToolType p_getHarvestLevel_2_, @Nullable Player p_getHarvestLevel_3_, @Nullable BlockState p_getHarvestLevel_4_) {
//        return HARVEST_LEVEL;
//    }

    /**
     * Gets the Energy that is used per Block broken
     *
     * @param stack The Drill
     * @return The Energy use per Block
     */
    public int getEnergyUsePerBlock(ItemStack stack) {
        int use = ENERGY_USE;

        //Speed
        if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED)) {
            use += 50;
            if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_II)) {
                use += 75;
                if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_III)) {
                    use += 175;
                }
            }
        }

        //Silk Touch
        if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)) {
            use += 100;
        }

        //Fortune
        if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)) {
            use += 40;
            if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II)) {
                use += 80;
            }
        }

        //Size
        if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
            use += 10;
            if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                use += 30;
            }
        }

        return use;
    }

    /**
     * Checks if a certain Upgrade is applied
     *
     * @param stack   The Drill
     * @param upgrade The Upgrade to be checked
     * @return Is the Upgrade applied?
     */
    public boolean getHasUpgrade(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade) {
        return !this.getHasUpgradeAsStack(stack, upgrade).isEmpty();
    }

//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
//        if (this.isInCreativeTab(tabs)) {
//            for (int i = 0; i < 16; i++) {
//                this.addDrillStack(list, i);
//            }
//        }
//    }

//    private void addDrillStack(List<ItemStack> list, int meta) {
//        ItemStack stackFull = new ItemStack(this, 1, meta);
//        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
//        list.add(stackFull);
//
//        ItemStack stackEmpty = new ItemStack(this, 1, meta);
//        this.setEnergy(stackEmpty, 0);
//        list.add(stackEmpty);
//    }

    /**
     * Gets the Mining Speed of the Drill
     *
     * @param stack The Drill
     * @return The Mining Speed depending on the Speed Upgrades
     */
    public float getEfficiencyFromUpgrade(ItemStack stack) {
        float efficiency = 8.0F;
        if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED)) {
            if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_II)) {
                if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_III)) {
                    efficiency += 37.0F;
                } else {
                    efficiency += 25.0F;
                }
            } else {
                efficiency += 8.0F;
            }
        }
        if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
            efficiency *= 0.5F;
            if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                efficiency *= 0.35F;
            }
        }
        return efficiency;
    }

    /**
     * Breaks Blocks in a certain Radius
     * Has to be called on both Server and Client
     *
     * @param stack  The Drill
     * @param radius The Radius to break Blocks in (0 means only 1 Block will be broken!)
     * @param world  The World
     * @param player The Player who breaks the Blocks
     */
    public boolean breakBlocks(ItemStack stack, int radius, Level world, BlockPos aPos, Direction side, Player player) {
        int xRange = radius;
        int yRange = radius;
        int zRange = 0;

        //Corrects Blocks to hit depending on Side of original Block hit
        if (side.getAxis() == Direction.Axis.Y) {
            zRange = radius;
            yRange = 0;
        }
        if (side.getAxis() == Direction.Axis.X) {
            xRange = 0;
            zRange = radius;
        }

        //Not defined later because main Block is getting broken below
        BlockState state = world.getBlockState(aPos);
        float mainHardness = state.getDestroySpeed(world, aPos);

        //Break Middle Block first
        int use = this.getEnergyUsePerBlock(stack);
        if (this.getEnergyStored(stack) >= use) {
            if (!this.tryHarvestBlock(world, aPos, false, stack, player, use)) {
                return false;
            }
        } else {
            return false;
        }

        if (radius == 2 && side.getAxis() != Direction.Axis.Y) {
            aPos = aPos.above();
            BlockState theState = world.getBlockState(aPos);
            if (theState.getDestroySpeed(world, aPos) <= mainHardness + 5.0F) {
                this.tryHarvestBlock(world, aPos, true, stack, player, use);
            }
        }

        //Break Blocks around
        if (radius > 0 && mainHardness >= 0.2F) {
            for (int xPos = aPos.getX() - xRange; xPos <= aPos.getX() + xRange; xPos++) {
                for (int yPos = aPos.getY() - yRange; yPos <= aPos.getY() + yRange; yPos++) {
                    for (int zPos = aPos.getZ() - zRange; zPos <= aPos.getZ() + zRange; zPos++) {
                        if (!(aPos.getX() == xPos && aPos.getY() == yPos && aPos.getZ() == zPos)) {
                            if (this.getEnergyStored(stack) >= use) {
                                //Only break Blocks around that are (about) as hard or softer
                                BlockPos thePos = new BlockPos(xPos, yPos, zPos);
                                BlockState theState = world.getBlockState(thePos);
                                if (theState.getDestroySpeed(world, thePos) <= mainHardness + 5.0F) {
                                    this.tryHarvestBlock(world, thePos, true, stack, player, use);
                                }
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Generate a list of block positions that can be broken taking radius, poker and side into account
     * @param stack The Drill
     * @param radius The Radius to break Blocks in (0 means only 1 Block will be broken!)
     * @param world The World
     * @param aPos The position of the block being broken
     * @param side The side of the block being broken
     * @param player The Player who breaks the Blocks
     * @return A list of block positions that can be broken
     */
    public List<BlockPos> gatherBreakingPositions(ItemStack stack, int radius, Level world, BlockPos aPos, Direction side, Player player) {
        int energyStored = this.getEnergyStored(stack);
        List<BlockPos> positions = new ArrayList<>();

        int xRange = radius;
        int yRange = radius;
        int zRange = 0;

        //Corrects Blocks to hit depending on Side of original Block hit
        if (side.getAxis() == Direction.Axis.Y) {
            zRange = radius;
            yRange = 0;
        }
        if (side.getAxis() == Direction.Axis.X) {
            xRange = 0;
            zRange = radius;
        }

        //Not defined later because main Block is getting broken below
        BlockState state = world.getBlockState(aPos);
        float mainHardness = state.getDestroySpeed(world, aPos);

        //Break Middle Block first
        int use = this.getEnergyUsePerBlock(stack);
        if (energyStored < use) {
            return positions;
        }

        if (radius == 2 && side.getAxis() != Direction.Axis.Y) {
            aPos = aPos.above();
            BlockState theState = world.getBlockState(aPos);
            if (theState.getDestroySpeed(world, aPos) <= mainHardness + 5.0F) {
                positions.add(aPos.immutable());
            }
        }

        //Break Blocks around
        if (radius > 0 && mainHardness >= 0.2F) {
            for (int xPos = aPos.getX() - xRange; xPos <= aPos.getX() + xRange; xPos++) {
                for (int yPos = aPos.getY() - yRange; yPos <= aPos.getY() + yRange; yPos++) {
                    for (int zPos = aPos.getZ() - zRange; zPos <= aPos.getZ() + zRange; zPos++) {
                        if (!(aPos.getX() == xPos && aPos.getY() == yPos && aPos.getZ() == zPos)) {
                            if (energyStored >= use) {
                                //Only break Blocks around that are (about) as hard or softer
                                BlockPos thePos = new BlockPos(xPos, yPos, zPos);
                                BlockState theState = world.getBlockState(thePos);
                                if (theState.getDestroySpeed(world, thePos) <= mainHardness + 5.0F) {
                                    energyStored -= use;
                                    positions.add(thePos.immutable());
                                }
                            } else {
                                return positions;
                            }
                        }
                    }
                }
            }
        }
        return positions;
    }

    /**
     * Tries to harvest a certain Block
     * Breaks the Block, drops Particles etc.
     * Has to be called on both Server and Client
     *
     * @param world   The World
     * @param isExtra If the Block is the Block that was looked at when breaking or an additional Block
     * @param stack   The Drill
     * @param player  The Player breaking the Blocks
     * @param use     The Energy that should be extracted per Block
     */
    private boolean tryHarvestBlock(Level world, BlockPos pos, boolean isExtra, ItemStack stack, Player player, int use) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        float hardness = state.getDestroySpeed(world, pos);

        boolean canHarvest = (CommonHooks.isCorrectToolForDrops(state, player) || this.isCorrectToolForDrops(stack, state)) && (!isExtra || this.getDestroySpeed(stack, world.getBlockState(pos)) > 1.0F);
        if (hardness >= 0.0F && (!isExtra || canHarvest && !state.hasBlockEntity())) {
            if (!player.isCreative()) {
                this.extractEnergyInternal(stack, use, false);
            }
            //Break the Block
            return WorldUtil.breakExtraBlock(stack, world, player, pos);
        }
        return false;
    }

    private boolean hasExtraWhitelist(Block block) {
        if (block != null) {
            ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
            if (location != null) {
                String name = location.toString();
                if (name != null) {
                    for (String s : CommonConfig.ItemSettings.DRILL_EXTRA_MINING_WHITELIST.get()) {
                        if (s != null && s.equals(name)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldCauseBlockBreakReset(@Nonnull ItemStack oldStack, @Nonnull ItemStack newStack) {
        return !ItemStack.isSameItem(newStack, oldStack);
    }
}
