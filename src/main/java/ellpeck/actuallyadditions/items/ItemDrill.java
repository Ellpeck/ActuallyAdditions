package ellpeck.actuallyadditions.items;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.config.values.ConfigFloatValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.items.tools.ItemAllToolAA;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ItemDrill extends ItemEnergy implements INameableItem{

    private static final Set allSet = Sets.newHashSet();
    static{
        allSet.addAll(ItemAllToolAA.pickSet);
        allSet.addAll(ItemAllToolAA.shovelSet);
    }

    public ItemDrill(){
        super(500000, 5000, 3);
    }

    public static float defaultEfficiency = ConfigFloatValues.DRILL_DAMAGE.getValue();
    public static int energyUsePerBlockOrHit = ConfigIntValues.DRILL_ENERGY_USE.getValue();

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ){
        ItemStack upgrade = this.getHasUpgradeAsStack(stack, ItemDrillUpgrade.UpgradeType.PLACER);
        if(upgrade != null){
            int slot = ItemDrillUpgrade.getSlotToPlaceFrom(upgrade);
            if(slot >= 0 && slot < InventoryPlayer.getHotbarSize()){
                ItemStack anEquip =player.inventory.getStackInSlot(slot);
                if(anEquip != null && anEquip != stack){
                    ItemStack equip = anEquip.copy();
                    if(!world.isRemote){
                        try{
                            if(equip.tryPlaceItemIntoWorld(player, world, x, y, z, hitSide, hitX, hitY, hitZ)){
                                if(!player.capabilities.isCreativeMode) player.inventory.setInventorySlotContents(slot, equip.stackSize <= 0 ? null : equip.copy());
                                player.inventoryContainer.detectAndSendChanges();
                                return true;
                            }
                        }
                        catch(Exception e){
                            player.addChatComponentMessage(new ChatComponentText("Ouch! That really hurt! You must have done something wrong, don't do that again please!"));
                            ModUtil.LOGGER.log(Level.ERROR, "Player "+player.getDisplayName()+" who should place a Block using a Drill at "+player.posX+", "+player.posY+", "+player.posZ+" in World "+world.provider.dimensionId+" threw an Exception! Don't let that happen again!");
                        }
                    }
                    else return true;
                }
            }
        }
        return false;
    }

    public float getEfficiencyFromUpgrade(ItemStack stack){
        float efficiency = defaultEfficiency;
        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED)){
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_II)){
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_III)) efficiency += 37.0F;
                else efficiency += 28.0F;
            }
            else efficiency += 15.0F;
        }
        return efficiency;
    }

    public int getEnergyUsePerBlock(ItemStack stack){
        int use = energyUsePerBlockOrHit;

        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED)){
            use += ConfigIntValues.DRILL_SPEED_EXTRA_USE.getValue();
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_II)){
                use += ConfigIntValues.DRILL_SPEED_II_EXTRA_USE.getValue();
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SPEED_III)) use += ConfigIntValues.DRILL_SPEED_III_EXTRA_USE.getValue();
            }
        }

        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)) use += ConfigIntValues.DRILL_SILK_EXTRA_USE.getValue();

        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)){
            use += ConfigIntValues.DRILL_FORTUNE_EXTRA_USE.getValue();
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II)) use += ConfigIntValues.DRILL_FORTUNE_II_EXTRA_USE.getValue();
        }

        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)){
            use += ConfigIntValues.DRILL_THREE_BY_THREE_EXTRA_USE.getValue();
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) use += ConfigIntValues.DRILL_FIVE_BY_FIVE_EXTRA_USE.getValue();
        }

        return use;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    public boolean getHasUpgrade(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade){
        return this.getHasUpgradeAsStack(stack, upgrade) != null;
    }

    public ItemStack getHasUpgradeAsStack(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) return null;

        ItemStack[] slots = this.getSlotsFromNBT(stack);
        if(slots != null && slots.length > 0){
            for(ItemStack slotStack : slots){
                if(slotStack != null && slotStack.getItem() instanceof ItemDrillUpgrade){
                    if(((ItemDrillUpgrade)slotStack.getItem()).type == upgrade) return slotStack;
                }
            }
        }
        return null;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    public void writeSlotsToNBT(ItemStack[] slots, ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();

        if(slots != null && slots.length > 0){
            compound.setInteger("SlotAmount", slots.length);
            NBTTagList tagList = new NBTTagList();
            for(int currentIndex = 0; currentIndex < slots.length; currentIndex++){
                if(slots[currentIndex] != null){
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte)currentIndex);
                    slots[currentIndex].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            compound.setTag("Items", tagList);
        }
        stack.setTagCompound(compound);
    }

    public ItemStack[] getSlotsFromNBT(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) return null;

        int slotAmount = compound.getInteger("SlotAmount");
        ItemStack[] slots = new ItemStack[slotAmount];

        if(slots.length > 0){
            NBTTagList tagList = compound.getTagList("Items", 10);
            for(int i = 0; i < tagList.tagCount(); i++){
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                byte slotIndex = tagCompound.getByte("Slot");
                if(slotIndex >= 0 && slotIndex < slots.length){
                    slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                }
            }
        }
        return slots;
    }

    public boolean breakBlocks(ItemStack stack, int radius, World world, int x, int y, int z, EntityPlayer player){
        int xRange = radius;
        int yRange = radius;
        int zRange = 0;

        MovingObjectPosition pos = WorldUtil.getNearestBlockWithDefaultReachDistance(world, player);
        if(pos == null) return false;

        int side = pos.sideHit;
        if(side == 0 || side == 1){
            zRange = radius;
            yRange = 0;
        }
        if(side == 4 || side == 5){
            xRange = 0;
            zRange = radius;
        }

        //Break Middle Block first
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            if(!this.tryHarvestBlock(world, x, y, z, false, stack, player, use)) return false;
        }
        else return true;

        //Break Blocks around
        if(radius > 0){
            for(int xPos = x-xRange; xPos <= x+xRange; xPos++){
                for(int yPos = y-yRange; yPos <= y+yRange; yPos++){
                    for(int zPos = z-zRange; zPos <= z+zRange; zPos++){
                        if(!(x == xPos && y == yPos && z == zPos)){
                            if(this.getEnergyStored(stack) >= use){
                                if(!this.tryHarvestBlock(world, xPos, yPos, zPos, true, stack, player, use)) return false;
                            }
                            else return true;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean tryHarvestBlock(World world, int xPos, int yPos, int zPos, boolean isExtra, ItemStack stack, EntityPlayer player, int use){
        Block block = world.getBlock(xPos, yPos, zPos);
        float hardness = block.getBlockHardness(world, xPos, yPos, zPos);
        int meta = world.getBlockMetadata(xPos, yPos, zPos);
        if(hardness >= 0.0F && (!isExtra || (this.canHarvestBlock(block, stack) && !block.hasTileEntity(meta)))){
            if(!world.isRemote){
                this.extractEnergy(stack, use, false);

                block.onBlockHarvested(world, xPos, yPos, zPos, meta, player);
                if(block.removedByPlayer(world, player, xPos, yPos, zPos, true)){
                    block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                    block.harvestBlock(world, player, xPos, yPos, zPos, meta);

                    if(!EnchantmentHelper.getSilkTouchModifier(player)){
                        block.dropXpOnBlockBreak(world, xPos, yPos, zPos, block.getExpDrop(world, meta, EnchantmentHelper.getFortuneModifier(player)));
                    }

                    if(player instanceof EntityPlayerMP){
                        ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(xPos, yPos, zPos, world));
                    }
                    //Should only happen when a severe weird bug occurs
                    //In that case, the Block just gets broken normally
                    else return false;
                }
            }
            else{
                world.playAuxSFX(2001, xPos, yPos, zPos, Block.getIdFromBlock(block)+(meta << 12));

                if(block.removedByPlayer(world, player, xPos, yPos, zPos, true)){
                    block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                }

                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, xPos, yPos, zPos, Minecraft.getMinecraft().objectMouseOver.sideHit));
            }
        }
        return true;
    }

    @Override
    public String getName(){
        return "itemDrill";
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player){
        boolean toReturn = true;
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.SILK_TOUCH)) stack.addEnchantment(Enchantment.silkTouch, 1);
            else{
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE)) stack.addEnchantment(Enchantment.fortune, this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II) ? 3 : 1);
            }

            if(!player.isSneaking() && this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)){
                if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)){
                    toReturn = this.breakBlocks(stack, 2, player.worldObj, x, y, z, player);
                }
                else toReturn = this.breakBlocks(stack, 1, player.worldObj, x, y, z, player);
            }
            else toReturn = this.breakBlocks(stack, 0, player.worldObj, x, y, z, player);

            NBTTagList ench = stack.getEnchantmentTagList();
            if(ench != null){
                for(int i = 0; i < ench.tagCount(); i++){
                    short id = ench.getCompoundTagAt(i).getShort("id");
                    if(id == Enchantment.silkTouch.effectId || id == Enchantment.fortune.effectId){
                        ench.removeTag(i);
                    }
                }
            }
        }
        return toReturn;
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block){
        if(this.getEnergyStored(stack) < this.getEnergyUsePerBlock(stack)) return 0.0F;
        if(block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil || block.getMaterial() == Material.rock || allSet.contains(block)) return this.getEfficiencyFromUpgrade(stack);
        else return super.func_150893_a(stack, block);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity1, EntityLivingBase entity2){
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            this.extractEnergy(stack, use, false);
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack){
        return this.func_150893_a(stack, block) > super.func_150893_a(stack, block);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote && player.isSneaking() && stack == player.getCurrentEquippedItem()){
            player.openGui(ActuallyAdditions.instance, GuiHandler.DRILL_ID, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return stack;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass){
        return ToolMaterial.EMERALD.getHarvestLevel();
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack){
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("pickaxe");
        hashSet.add("shovel");
        return hashSet;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta){
        return this.func_150893_a(stack, block);
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack){
        Multimap map = super.getAttributeModifiers(stack);
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.getEnergyStored(stack) >= energyUsePerBlockOrHit ? 8.0F : 0.0F, 0));
        return map;
    }
}
