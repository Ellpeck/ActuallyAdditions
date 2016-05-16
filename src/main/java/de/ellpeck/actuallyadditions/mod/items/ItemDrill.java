/*
 * This file ("ItemDrill.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import cofh.api.energy.IEnergyContainerItem;
import com.google.common.collect.Multimap;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDrill;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ItemDrill extends ItemEnergy{

    private static final int ENERGY_USE = 100;
    private static final int HARVEST_LEVEL = 4;

    public ItemDrill(String name){
        super(500000, 5000, name);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);

        //For Iguana Tweaks author
        //
        //You know what? It's bad, when you know
        //There is already getHarvestLevel(), yo
        //But then Iguana comes and fucks with you
        //So that you need to use setHarvestLevel() too.
        this.setHarvestLevel("shovel", HARVEST_LEVEL);
        this.setHarvestLevel("pickaxe", HARVEST_LEVEL);
    }

    @Override
    //Places Blocks if the Placing Upgrade is installed
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        ItemStack upgrade = this.getHasUpgradeAsStack(stack, ItemDrillUpgrade.UpgradeType.PLACER);
        if(upgrade != null){
            int slot = ItemDrillUpgrade.getSlotToPlaceFrom(upgrade);
            if(slot >= 0 && slot < InventoryPlayer.getHotbarSize()){
                ItemStack anEquip = player.inventory.getStackInSlot(slot);
                if(anEquip != null && anEquip != stack){
                    ItemStack equip = anEquip.copy();
                    if(!world.isRemote){
                        //tryPlaceItemIntoWorld could throw an Exception
                        try{
                            //Places the Block into the World
                            if(equip.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ) != EnumActionResult.FAIL){
                                if(!player.capabilities.isCreativeMode){
                                    player.inventory.setInventorySlotContents(slot, equip.stackSize <= 0 ? null : equip.copy());
                                }
                                //Synchronizes the Client
                                player.inventoryContainer.detectAndSendChanges();
                                return EnumActionResult.SUCCESS;
                            }
                        }
                        //Notify the Player and log the Exception
                        catch(Exception e){
                            player.addChatComponentMessage(new TextComponentString("Ouch! That really hurt! You must have done something wrong, don't do that again please!"));
                            ModUtil.LOGGER.error("Player "+player.getName()+" who should place a Block using a Drill at "+player.posX+", "+player.posY+", "+player.posZ+" in World "+world.provider.getDimension()+" threw an Exception! Don't let that happen again!");
                        }
                    }
                    else{
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    /**
     * Checks if a certain Upgrade is installed and returns it as an ItemStack
     *
     * @param stack   The Drill
     * @param upgrade The Upgrade to be checked
     * @return The Upgrade, if it's installed
     */
    public ItemStack getHasUpgradeAsStack(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            return null;
        }

        ItemStack[] slots = this.getSlotsFromNBT(stack);
        if(slots != null && slots.length > 0){
            for(ItemStack slotStack : slots){
                if(slotStack != null && slotStack.getItem() instanceof ItemDrillUpgrade){
                    if(((ItemDrillUpgrade)slotStack.getItem()).type == upgrade){
                        return slotStack;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets all of the Slots from NBT
     *
     * @param stack The Drill
     * @return All of the Slots
     */
    public ItemStack[] getSlotsFromNBT(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            return null;
        }

        ItemStack[] slots = new ItemStack[ContainerDrill.SLOT_AMOUNT];
        TileEntityInventoryBase.loadSlots(slots, compound);

        return slots;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote && player.isSneaking()){
            player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.DRILL.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity1, EntityLivingBase entity2){
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            this.extractEnergy(stack, use, false);
        }
        return true;
    }

    //Checks for Energy Containers in the Upgrade Slots and charges the Drill from them
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        ItemStack[] slots = this.getSlotsFromNBT(stack);
        if(slots != null && slots.length > 0){
            for(ItemStack slotStack : slots){
                if(slotStack != null && slotStack.getItem() instanceof IEnergyContainerItem){
                    if(this.getEnergyStored(stack) < this.getMaxEnergyStored(stack)){
                        int energy = ((IEnergyContainerItem)slotStack.getItem()).getEnergyStored(slotStack);
                        if(energy > 0){
                            //Charge the Drill and discharge the "Upgrade"
                            int toReceive = ((IEnergyContainerItem)stack.getItem()).receiveEnergy(stack, energy, true);
                            int actualReceive = ((IEnergyContainerItem)slotStack.getItem()).extractEnergy(slotStack, toReceive, false);
                            ((IEnergyContainerItem)stack.getItem()).receiveEnergy(stack, actualReceive, false);

                        }
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);

        if(slot == EntityEquipmentSlot.MAINHAND){
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Drill Modifier", this.getEnergyStored(stack) >= ENERGY_USE ? 8.0F : 0.1F, 0));
            map.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool Modifier", -2.5F, 0));
        }

        return map;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack) ? (this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state).isEmpty() || this.getToolClasses(stack).contains(state.getBlock().getHarvestTool(state)) ? this.getEfficiencyFromUpgrade(stack) : 1.0F) : 0.1F;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player){
        boolean toReturn = false;
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            //Enchants the Drill depending on the Upgrades it has
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)){
                ItemUtil.addEnchantment(stack, Enchantments.SILK_TOUCH, 1);
            }
            else{
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)){
                    ItemUtil.addEnchantment(stack, Enchantments.FORTUNE, this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II) ? 3 : 1);
                }
            }

            //Breaks the Blocks
            if(!player.isSneaking() && this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)){
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)){
                    toReturn = this.breakBlocks(stack, 2, player.worldObj, pos, player);
                }
                else{
                    toReturn = this.breakBlocks(stack, 1, player.worldObj, pos, player);
                }
            }
            else{
                toReturn = this.breakBlocks(stack, 0, player.worldObj, pos, player);
            }

            //Removes Enchantments added above
            ItemUtil.removeEnchantment(stack, Enchantments.SILK_TOUCH);
            ItemUtil.removeEnchantment(stack, Enchantments.FORTUNE);
        }
        return toReturn;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack){
        int harvestLevel = this.getHarvestLevel(stack, "");
        Block block = state.getBlock();
        return this.getEnergyStored(stack) >= this.getEnergyUsePerBlock(stack) && (this.hasExtraWhitelist(block) || block.getMaterial(state).isToolNotRequired() || (block == Blocks.SNOW_LAYER || block == Blocks.SNOW || (block == Blocks.OBSIDIAN ? harvestLevel >= 3 : (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE ? (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK ? (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE ? (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE ? (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE ? (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE ? (block.getMaterial(state) == Material.ROCK || (block.getMaterial(state) == Material.IRON || block.getMaterial(state) == Material.ANVIL)) : harvestLevel >= 2) : harvestLevel >= 1) : harvestLevel >= 1) : harvestLevel >= 2) : harvestLevel >= 2) : harvestLevel >= 2))));
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack){
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("pickaxe");
        hashSet.add("shovel");
        return hashSet;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass){
        return HARVEST_LEVEL;
    }

    /**
     * Gets the Energy that is used per Block broken
     *
     * @param stack The Drill
     * @return The Energy use per Block
     */
    public int getEnergyUsePerBlock(ItemStack stack){
        int use = ENERGY_USE;

        //Speed
        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED)){
            use += 50;
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_II)){
                use += 75;
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_III)){
                    use += 175;
                }
            }
        }

        //Silk Touch
        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)){
            use += 100;
        }

        //Fortune
        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)){
            use += 40;
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II)){
                use += 80;
            }
        }

        //Size
        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)){
            use += 10;
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)){
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
    public boolean getHasUpgrade(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade){
        return this.getHasUpgradeAsStack(stack, upgrade) != null;
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < 16; i++){
            String name = this.getRegistryName()+TheColoredLampColors.values()[i].name;
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ModelResourceLocation(name, "inventory"));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list){
        for(int i = 0; i < 16; i++){
            this.addDrillStack(list, i);
        }
    }

    private void addDrillStack(List list, int meta){
        ItemStack stackFull = new ItemStack(this, 1, meta);
        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
        list.add(stackFull);

        ItemStack stackEmpty = new ItemStack(this, 1, meta);
        this.setEnergy(stackEmpty, 0);
        list.add(stackEmpty);
    }

    /**
     * Gets the Mining Speed of the Drill
     *
     * @param stack The Drill
     * @return The Mining Speed depending on the Speed Upgrades
     */
    public float getEfficiencyFromUpgrade(ItemStack stack){
        float efficiency = 8.0F;
        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED)){
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_II)){
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_III)){
                    efficiency += 37.0F;
                }
                else{
                    efficiency += 25.0F;
                }
            }
            else{
                efficiency += 8.0F;
            }
        }
        return efficiency;
    }

    /**
     * Writes all of the Slots to NBT
     *
     * @param slots The Slots
     * @param stack The Drill
     */
    public void writeSlotsToNBT(ItemStack[] slots, ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            compound = new NBTTagCompound();
        }
        TileEntityInventoryBase.saveSlots(slots, compound);
        stack.setTagCompound(compound);
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
    public boolean breakBlocks(ItemStack stack, int radius, World world, BlockPos aPos, EntityPlayer player){
        int xRange = radius;
        int yRange = radius;
        int zRange = 0;

        //Block hit
        RayTraceResult pos = WorldUtil.getNearestBlockWithDefaultReachDistance(world, player);
        if(pos == null){
            return false;
        }

        //Corrects Blocks to hit depending on Side of original Block hit
        int side = pos.sideHit.ordinal();
        if(side == 0 || side == 1){
            zRange = radius;
            yRange = 0;
        }
        if(side == 4 || side == 5){
            xRange = 0;
            zRange = radius;
        }

        //Not defined later because main Block is getting broken below
        float mainHardness = PosUtil.getBlock(aPos, world).getBlockHardness(world.getBlockState(aPos), world, aPos);

        //Break Middle Block first
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            if(!this.tryHarvestBlock(world, aPos, false, stack, player, use)){
                return false;
            }
        }
        else{
            return false;
        }

        //Break Blocks around
        if(radius > 0 && mainHardness >= 0.2F){
            for(int xPos = aPos.getX()-xRange; xPos <= aPos.getX()+xRange; xPos++){
                for(int yPos = aPos.getY()-yRange; yPos <= aPos.getY()+yRange; yPos++){
                    for(int zPos = aPos.getZ()-zRange; zPos <= aPos.getZ()+zRange; zPos++){
                        if(!(aPos.getX() == xPos && aPos.getY() == yPos && aPos.getZ() == zPos)){
                            if(this.getEnergyStored(stack) >= use){
                                //Only break Blocks around that are (about) as hard or softer
                                BlockPos thePos = new BlockPos(xPos, yPos, zPos);
                                if(PosUtil.getBlock(thePos, world).getBlockHardness(world.getBlockState(thePos), world, thePos) <= mainHardness+5.0F){
                                    this.tryHarvestBlock(world, thePos, true, stack, player, use);
                                }
                            }
                            else{
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
    private boolean tryHarvestBlock(World world, BlockPos pos, boolean isExtra, ItemStack stack, EntityPlayer player, int use){
        Block block = PosUtil.getBlock(pos, world);
        IBlockState state = world.getBlockState(pos);
        float hardness = block.getBlockHardness(state, world, pos);
        boolean canHarvest = (ForgeHooks.canHarvestBlock(block, player, world, pos) || this.canHarvestBlock(state, stack)) && (!isExtra || this.getStrVsBlock(stack, world.getBlockState(pos)) > 1.0F);
        if(hardness >= 0.0F && (!isExtra || (canHarvest && !block.hasTileEntity(world.getBlockState(pos))))){
            this.extractEnergy(stack, use, false);
            //Break the Block
            return WorldUtil.playerHarvestBlock(world, pos, player);
        }
        return false;
    }

    private boolean hasExtraWhitelist(Block block){
        String name = block.getRegistryName().toString();
        if(name != null){
            for(String list : ConfigValues.drillExtraMiningWhitelist){
                if(list.equals(name)){
                    return true;
                }
            }
        }
        return false;
    }
}
