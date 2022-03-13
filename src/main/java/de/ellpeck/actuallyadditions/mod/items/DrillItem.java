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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDrill;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class DrillItem extends ItemEnergy {

    public static final int HARVEST_LEVEL = 4;
    private static final int ENERGY_USE = 100;

    public DrillItem() {
        super(ActuallyItems.defaultProps().defaultDurability(0).stacksTo(1).addToolType(ToolType.SHOVEL, HARVEST_LEVEL).addToolType(ToolType.PICKAXE, HARVEST_LEVEL), 250000, 1000);
    }

    /**
     * Gets all of the Slots from NBT
     *
     * @param stack The Drill
     */
    public static void loadSlotsFromNBT(IItemHandlerModifiable slots, ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();
        TileEntityInventoryBase.loadSlots(slots, compound);
    }

    /**
     * Writes all of the Slots to NBT
     *
     * @param slots The Slots
     * @param stack The Drill
     */
    public static void writeSlotsToNBT(IItemHandler slots, ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();

        TileEntityInventoryBase.saveSlots(slots, compound);
        stack.setTag(compound);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();

        ItemStack stack = player.getItemInHand(hand);
        ItemStack upgrade = this.getHasUpgradeAsStack(stack, ItemDrillUpgrade.UpgradeType.PLACER);
        if (StackUtil.isValid(upgrade)) {
            int slot = ItemDrillUpgrade.getSlotToPlaceFrom(upgrade);
            if (slot >= 0 && slot < 9) { // TODO: validate... old = PlayerInventory.getHotbarSize(); new = 9
                ItemStack equip = player.inventory.getItem(slot);
                if (StackUtil.isValid(equip) && equip != stack) {
                    ItemStack toPlaceStack = equip.copy();

                    WorldUtil.setHandItemWithoutAnnoyingSound(player, hand, toPlaceStack);

                    //tryPlaceItemIntoWorld could throw an Exception
                    try {
                        //Places the Block into the World
                        if (toPlaceStack.useOn(context) != ActionResultType.FAIL) {
                            if (!player.isCreative()) {
                                WorldUtil.setHandItemWithoutAnnoyingSound(player, hand, toPlaceStack.copy());
                            }
                        }
                    }
                    //Notify the Player and log the Exception
                    catch (Exception e) {
                        ActuallyAdditions.LOGGER.error("Player " + player.getName() + " who should place a Block using a Drill at " + player.getX() + ", " + player.getY() + ", " + player.getZ() + " in World " + context.getLevel().dimension() + " threw an Exception! Don't let that happen again!");
                    }

                    player.inventory.setItem(slot, player.getItemInHand(hand));
                    WorldUtil.setHandItemWithoutAnnoyingSound(player, hand, stack);

                    return ActionResultType.SUCCESS;
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
        CompoundNBT compound = stack.getOrCreateTag();

        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerDrill.SLOT_AMOUNT);
        loadSlotsFromNBT(inv, stack);
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (StackUtil.isValid(slotStack) && slotStack.getItem() instanceof ItemDrillUpgrade) {
                if (((ItemDrillUpgrade) slotStack.getItem()).type == upgrade) {
                    return slotStack;
                }
            }
        }
        return StackUtil.getEmpty();
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide && player.isShiftKeyDown() && hand == Hand.MAIN_HAND) {
            player.openMenu(new SimpleNamedContainerProvider((id, inv, p) -> new ContainerDrill(id, inv), new StringTextComponent("")));
//            player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.DRILL.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entityHit, Hand hand) {
        int use = this.getEnergyUsePerBlock(stack);
        if (!(entityHit instanceof PlayerEntity) || !((PlayerEntity) entityHit).isCreative()) {
            if (this.getEnergyStored(stack) >= use) {
                this.extractEnergyInternal(stack, use, false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();

        if (slot == EquipmentSlotType.MAINHAND) {
            map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("Drill Modifier", this.getEnergyStored(stack) >= ENERGY_USE
                    ? 8.0F
                    : 0.1F, AttributeModifier.Operation.ADDITION));
            map.put(Attributes.ATTACK_SPEED, new AttributeModifier("Tool Modifier", -2.5F, AttributeModifier.Operation.ADDITION));
        }

        return map;
    }


    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack)
                ? (this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state) == ToolType.PICKAXE || state.getBlock().getHarvestTool(state) == ToolType.SHOVEL)
                ? this.getEfficiencyFromUpgrade(stack)
                : 1.0F
                : 0.1F;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
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
            RayTraceResult ray = WorldUtil.getNearestBlockWithDefaultReachDistance(player.level, player);
            if (ray != null && ray.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult trace = (BlockRayTraceResult) ray;
                //Breaks the Blocks
                if (!player.isShiftKeyDown() && this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
                    if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                        toReturn = this.breakBlocks(stack, 2, player.level, pos, trace.getDirection(), player);
                    } else {
                        toReturn = this.breakBlocks(stack, 1, player.level, pos, trace.getDirection(), player);
                    }
                } else {
                    toReturn = this.breakBlocks(stack, 0, player.level, pos, trace.getDirection(), player);
                }

                //Removes Enchantments added above
                ItemUtil.removeEnchantment(stack, Enchantments.SILK_TOUCH);
                ItemUtil.removeEnchantment(stack, Enchantments.BLOCK_FORTUNE);
            }
        }
        return toReturn;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack) && (this.hasExtraWhitelist(block) || state.getMaterial().isReplaceable() || block == Blocks.SNOW_BLOCK || block == Blocks.SNOW || (block == Blocks.OBSIDIAN
                ? HARVEST_LEVEL >= 3
                : block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE
                ? block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK
                ? block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE
                ? block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE
                ? block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE
                ? block != Blocks.REDSTONE_ORE
                ? state.getMaterial() == Material.STONE || state.getMaterial() == Material.METAL || state.getMaterial() == Material.HEAVY_METAL
                : HARVEST_LEVEL >= 2
                : HARVEST_LEVEL >= 1
                : HARVEST_LEVEL >= 1
                : HARVEST_LEVEL >= 2
                : HARVEST_LEVEL >= 2
                : HARVEST_LEVEL >= 2));
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType p_getHarvestLevel_2_, @Nullable PlayerEntity p_getHarvestLevel_3_, @Nullable BlockState p_getHarvestLevel_4_) {
        return HARVEST_LEVEL;
    }

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
        return StackUtil.isValid(this.getHasUpgradeAsStack(stack, upgrade));
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
    public boolean breakBlocks(ItemStack stack, int radius, World world, BlockPos aPos, Direction side, PlayerEntity player) {
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
    private boolean tryHarvestBlock(World world, BlockPos pos, boolean isExtra, ItemStack stack, PlayerEntity player, int use) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        float hardness = state.getDestroySpeed(world, pos);
        boolean canHarvest = (ForgeHooks.canHarvestBlock(state, player, world, pos) || this.canHarvestBlock(stack, state)) && (!isExtra || this.getDestroySpeed(stack, world.getBlockState(pos)) > 1.0F);
        if (hardness >= 0.0F && (!isExtra || canHarvest && !block.hasTileEntity(world.getBlockState(pos)))) {
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
            ResourceLocation location = block.getRegistryName();
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
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return !newStack.sameItem(oldStack);
    }
}
