package de.ellpeck.actuallyadditions.common.items;

import com.google.common.collect.Multimap;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.common.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.common.inventory.ContainerDrill;
import de.ellpeck.actuallyadditions.common.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.common.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.common.util.ItemUtil;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

public class ItemDrill extends ItemEnergy {

    public static final int HARVEST_LEVEL = 4;
    private static final int ENERGY_USE = 100;

    public ItemDrill(Properties properties, String name) {
        super(properties.maxDamage(0).addToolType(ToolType.SHOVEL, HARVEST_LEVEL).addToolType(ToolType.PICKAXE, HARVEST_LEVEL), 250000, 1000, name);
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
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
        ItemStack upgrade = this.getHasUpgradeAsStack(stack, ItemDrillUpgrade.UpgradeType.PLACER);
        if (StackUtil.isValid(upgrade)) {
            int slot = ItemDrillUpgrade.getSlotToPlaceFrom(upgrade);
            if (slot >= 0 && slot < PlayerInventory.getHotbarSize()) {
                ItemStack equip = context.getPlayer().inventory.getStackInSlot(slot);
                if (StackUtil.isValid(equip) && equip != stack) {
                    ItemStack toPlaceStack = equip.copy();

                    WorldUtil.setHandItemWithoutAnnoyingSound(context.getPlayer(), context.getHand(), toPlaceStack);

                    //tryPlaceItemIntoWorld could throw an Exception
                    try {
                        //Places the Block into the World
                        if (toPlaceStack.onItemUse(context) != ActionResultType.FAIL) {
                            if (!context.getPlayer().isCreative()) {
                                WorldUtil.setHandItemWithoutAnnoyingSound(context.getPlayer(), context.getHand(), toPlaceStack.copy());
                            }
                        }
                    }
                    //Notify the Player and log the Exception
                    catch (Exception e) {
                        ActuallyAdditions.LOGGER.error("Player " + context.getPlayer().getName() + " who should place a Block using a Drill at " + context.getPlayer().getPosX() + ", " + context.getPlayer().getPosY() + ", " + context.getPlayer().getPosY() + " in World " + context.getWorld().getDimension() + " threw an Exception! Don't let that happen again!");
                    }

                    context.getPlayer().inventory.setInventorySlotContents(slot, context.getPlayer().getHeldItem(context.getHand()));
                    WorldUtil.setHandItemWithoutAnnoyingSound(context.getPlayer(), context.getHand(), stack);

                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.FAIL;
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
        if (compound == null) { return StackUtil.getEmpty(); }

        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerDrill.SLOT_AMOUNT);
        loadSlotsFromNBT(inv, stack);
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (StackUtil.isValid(slotStack) && slotStack.getItem() instanceof ItemDrillUpgrade) {
                if (((ItemDrillUpgrade) slotStack.getItem()).type == upgrade) { return slotStack; }
            }
        }
        return StackUtil.getEmpty();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        // todo: reimplement
        if (!world.isRemote && player.isSneaking() && hand == Hand.MAIN_HAND) {
//            player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.DRILL.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int use = this.getEnergyUsePerBlock(stack);
        if (!(attacker instanceof PlayerEntity) || !((PlayerEntity) attacker).isCreative()) {
            if (this.getEnergyStored(stack) >= use) {
                this.extractEnergyInternal(stack, use, false);
            }
        }
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);

        // todo: operation might be wrong here
        if (slot == EquipmentSlotType.MAINHAND) {
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Drill Modifier", this.getEnergyStored(stack) >= ENERGY_USE ? 8.0F : 0.1F, AttributeModifier.Operation.ADDITION));
            map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool Modifier", -2.5F, AttributeModifier.Operation.ADDITION));
        }

        return map;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack)
                ? (this.hasExtraWhitelist(state.getBlock())
                        || state.getBlock().getHarvestTool(state) == null
                        || this.getToolTypes(stack).contains(state.getBlock().getHarvestTool(state)))
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
                    ItemUtil.addEnchantment(stack, Enchantments.FORTUNE, this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II) ? 3 : 1);
                }
            }

            //Block hit
            RayTraceResult ray = WorldUtil.getNearestBlockWithDefaultReachDistance(player.world, player);
            if (ray != null) {

                //Breaks the Blocks
                if (!player.isSneaking() && this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
                    if (this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                        toReturn = this.breakBlocks(stack, 2, player.world, pos, ((BlockRayTraceResult)ray).getFace(), player);
                    } else {
                        toReturn = this.breakBlocks(stack, 1, player.world, pos, ((BlockRayTraceResult)ray).getFace(), player);
                    }
                } else {
                    toReturn = this.breakBlocks(stack, 0, player.world, pos, ((BlockRayTraceResult)ray).getFace(), player);
                }

                //Removes Enchantments added above
                ItemUtil.removeEnchantment(stack, Enchantments.SILK_TOUCH);
                ItemUtil.removeEnchantment(stack, Enchantments.FORTUNE);
            }
        }
        return toReturn;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack) && (this.hasExtraWhitelist(block) || state.getMaterial().isToolNotRequired() || block == Blocks.SNOW_BLOCK || block == Blocks.SNOW || (block == Blocks.OBSIDIAN ? HARVEST_LEVEL >= 3 : block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE ? block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK ? block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE ? block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE ? block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE ? block != Blocks.REDSTONE_ORE ? state.getMaterial() == Material.ROCK || state.getMaterial() == Material.IRON || state.getMaterial() == Material.ANVIL : HARVEST_LEVEL >= 2 : HARVEST_LEVEL >= 1 : HARVEST_LEVEL >= 1 : HARVEST_LEVEL >= 2 : HARVEST_LEVEL >= 2 : HARVEST_LEVEL >= 2));
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

    @Override
    protected void registerRendering() {
        for (int i = 0; i < 16; i++) {
            String name = this.getRegistryName() + "_" + TheColoredLampColors.values()[i].regName;
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1), new ResourceLocation(name), "inventory");
        }
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
//        if (this.isInCreativeTab(tabs)) {
//            for (int i = 0; i < 16; i++) {
//                this.addDrillStack(list, i);
//            }
//        }
//    }

    // todo: fix this
    private void addDrillStack(List<ItemStack> list, int meta) {
        ItemStack stackFull = new ItemStack(this, 1);
        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
        list.add(stackFull);

        ItemStack stackEmpty = new ItemStack(this, 1);
        this.setEnergy(stackEmpty, 0);
        list.add(stackEmpty);
    }

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
        float mainHardness = state.getBlockHardness(world, aPos);

        //Break Middle Block first
        int use = this.getEnergyUsePerBlock(stack);
        if (this.getEnergyStored(stack) >= use) {
            if (!this.tryHarvestBlock(world, aPos, false, stack, player, use)) { return false; }
        } else {
            return false;
        }

        if (radius == 2 && side.getAxis() != Direction.Axis.Y) {
            aPos = aPos.up();
            BlockState theState = world.getBlockState(aPos);
            if (theState.getBlockHardness(world, aPos) <= mainHardness + 5.0F) {
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
                                if (theState.getBlockHardness(world, thePos) <= mainHardness + 5.0F) {
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
        float hardness = state.getBlockHardness(world, pos);
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
                    for (String s : ConfigStringListValues.DRILL_EXTRA_MINING_WHITELIST.getValue()) {
                        if (s != null && s.equals(name)) { return true; }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return !newStack.isItemEqual(oldStack);
    }
}
