package ellpeck.actuallyadditions.items;

import cofh.api.energy.ItemEnergyContainer;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.items.tools.ItemAllToolAA;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ItemDrill extends ItemEnergyContainer implements INameableItem{

    private static final Set allSet = Sets.newHashSet();
    static{
        allSet.addAll(ItemAllToolAA.pickSet);
        allSet.addAll(ItemAllToolAA.shovelSet);
    }

    public ItemDrill(){
        super(500000);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }

    public static float defaultEfficiency = 8.0F;
    public static int energyUsePerBlockOrHit = 100;

    public float efficiency = defaultEfficiency;

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        double energyDif = getMaxEnergyStored(stack)-getEnergyStored(stack);
        double maxAmount = getMaxEnergyStored(stack);
        return energyDif/maxAmount;
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack){
        return true;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player){
        this.setEnergy(stack, 0);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        this.addEnchantFromUpgrade(Enchantment.silkTouch, ItemDrillUpgrade.UpgradeType.SILK_TOUCH, stack, 1);
        this.addEnchantFromUpgrade(Enchantment.fortune, ItemDrillUpgrade.UpgradeType.FORTUNE, stack, this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FORTUNE_II) ? 3 : 1);
    }

    public void addEnchantFromUpgrade(Enchantment enchantment, ItemDrillUpgrade.UpgradeType upgrade, ItemStack stack, int level){
        boolean hasEnchant = this.hasEnchantment(stack, enchantment) >= 0;
        if(this.getHasUpgrade(stack, upgrade)){
            if(!hasEnchant){
                stack.addEnchantment(enchantment, level);
            }
        }
        else if(hasEnchant) this.removeEnchantment(stack, enchantment);
    }

    public int getEnergyUsePerBlock(ItemStack stack){
        int use = energyUsePerBlockOrHit;
        ItemDrillUpgrade.UpgradeType[] types = ItemDrillUpgrade.UpgradeType.values();
        for(ItemDrillUpgrade.UpgradeType type : types){
            if(this.getHasUpgrade(stack, type)){
                use += type.extraEnergy;
            }
        }
        return use;
    }

    public void removeEnchantment(ItemStack stack, Enchantment ench){
        NBTTagList list = stack.getEnchantmentTagList();
        if(list != null){
            int hasEnchantment = this.hasEnchantment(stack, ench);
            if(hasEnchantment >= 0){
                list.removeTag(hasEnchantment);
            }
        }
    }

    public int hasEnchantment(ItemStack stack, Enchantment ench){
        NBTTagList list = stack.getEnchantmentTagList();
        if(list != null){
            for(int i = 0; i < list.tagCount(); i++){
                NBTTagCompound compound = list.getCompoundTagAt(i);
                short id = compound.getShort("id");
                if(id == ench.effectId){
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass){
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    public boolean getHasUpgrade(ItemStack stack, ItemDrillUpgrade.UpgradeType upgrade){
        if(upgrade == ItemDrillUpgrade.UpgradeType.THREE_BY_THREE) return true;
        if(upgrade == ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE) return true;

        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) return false;

        ItemStack[] slots = this.getSlotsFromNBT(stack);
        if(slots != null && slots.length > 0){
            for(ItemStack slotStack : slots){
                if(slotStack != null && slotStack.getItem() instanceof ItemDrillUpgrade){
                    if(((ItemDrillUpgrade)slotStack.getItem()).type == upgrade) return true;
                }
            }
        }
        return false;
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

    public void setEnergy(ItemStack stack, int energy){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();
        compound.setInteger("Energy", energy);
        stack.setTagCompound(compound);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list){
        ItemStack stackFull = new ItemStack(this);
        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
        list.add(stackFull);

        ItemStack stackEmpty = new ItemStack(this);
        this.setEnergy(stackEmpty, 0);
        list.add(stackEmpty);
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

    public void breakBlocks(ItemStack stack, int radius, World world, int x, int y, int z, Entity entity){
        int xRange = radius;
        int yRange = radius;
        int zRange = 0;

        MovingObjectPosition pos = WorldUtil.raytraceBlocksFromEntity(world, entity, 4.5D);
        if(pos != null){
            int side = pos.sideHit;
            if(side == 0 || side == 1){
                zRange = radius;
                yRange = 0;
            }
            if(side == 4 || side == 5){
                xRange = 0;
                zRange = radius;
            }

            for(int xPos = x-xRange; xPos <= x+xRange; xPos++){
                for(int yPos = y-yRange; yPos <= y+yRange; yPos++){
                    for(int zPos = z-zRange; zPos <= z+zRange; zPos++){
                        int use = this.getEnergyUsePerBlock(stack);
                        if(this.getEnergyStored(stack) >= use){
                            Block block = world.getBlock(xPos, yPos, zPos);
                            float hardness = block.getBlockHardness(world, xPos, yPos, zPos);
                            if(!(xPos == x && yPos == y && zPos == z) && hardness > -1.0F && this.canHarvestBlock(block, stack)){
                                this.extractEnergy(stack, use, false);

                                ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                                int meta = world.getBlockMetadata(xPos, yPos, zPos);

                                if(block.canSilkHarvest(world, (EntityPlayer)entity, xPos, yPos, zPos, meta) && EnchantmentHelper.getSilkTouchModifier((EntityPlayer)entity)){
                                    drops.add(new ItemStack(block, 1, meta));
                                }
                                else{
                                    drops.addAll(block.getDrops(world, xPos, yPos, zPos, meta, EnchantmentHelper.getFortuneModifier((EntityPlayer)entity)));
                                    block.dropXpOnBlockBreak(world, x, y, z, block.getExpDrop(world, meta, EnchantmentHelper.getFortuneModifier((EntityPlayer)entity)));
                                }

                                world.playAuxSFX(2001, xPos, yPos, zPos, Block.getIdFromBlock(block)+(meta << 12));
                                world.setBlockToAir(xPos, yPos, zPos);
                                for(ItemStack theDrop : drops){
                                    world.spawnEntityInWorld(new EntityItem(world, xPos+0.5, yPos+0.5, zPos+0.5, theDrop));
                                }
                            }
                        }
                        else return;
                    }
                }
            }
        }
    }

    @Override
    public String getName(){
        return "itemDrill";
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living){
        int use = this.getEnergyUsePerBlock(stack);
        if(this.getEnergyStored(stack) >= use){
            this.extractEnergy(stack, use, false);
            if(!world.isRemote){
                if(!living.isSneaking()){
                    if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)){
                        if(this.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)){
                            this.breakBlocks(stack, 2, world, x, y, z, living);
                        }
                        else this.breakBlocks(stack, 1, world, x, y, z, living);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block){
        if(this.getEnergyStored(stack) < this.getEnergyUsePerBlock(stack)) return 0.0F;
        if(block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil || block.getMaterial() == Material.rock || allSet.contains(block)) return efficiency;
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
        if(!world.isRemote && player.isSneaking()) player.openGui(ActuallyAdditions.instance, GuiHandler.DRILL_ID, world, (int)player.posX, (int)player.posY, (int)player.posZ);
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(KeyUtil.isShiftPressed()){
            list.add(this.getEnergyStored(stack) + "/" + this.getMaxEnergyStored(stack) + " RF");
        }
        else list.add(ItemUtil.shiftForInfo());
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack){
        Multimap map = super.getAttributeModifiers(stack);
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.getEnergyStored(stack) >= energyUsePerBlockOrHit ? 8.0F : 0.0F, 0));
        return map;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }
}
