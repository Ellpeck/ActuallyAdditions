package ellpeck.actuallyadditions.items;

import cofh.api.energy.ItemEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemEnergy extends ItemEnergyContainer{

    private int infoLines;

    public ItemEnergy(int maxPower, int transfer, int infoLines){
        super(maxPower, transfer);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.infoLines = infoLines;
    }

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
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass){
        return false;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list){
        ItemStack stackFull = new ItemStack(this);
        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
        list.add(stackFull);

        ItemStack stackEmpty = new ItemStack(this);
        this.setEnergy(stackEmpty, 0);
        list.add(stackEmpty);
    }

    public void setEnergy(ItemStack stack, int energy){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();
        compound.setInteger("Energy", energy);
        stack.setTagCompound(compound);
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        ItemUtil.addInformation(this, list, this.infoLines, "");
        if(KeyUtil.isShiftPressed()){
            list.add(this.getEnergyStored(stack) + "/" + this.getMaxEnergyStored(stack) + " RF");
        }
    }
}
