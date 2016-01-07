/*
 * This file ("ItemEnergy.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import cofh.api.energy.ItemEnergyContainer;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class ItemEnergy extends ItemEnergyContainer{

    private String name;

    public ItemEnergy(int maxPower, int transfer, String name){
        super(maxPower, transfer);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.name = name;

        this.register();
    }

    private void register(){
        this.setUnlocalizedName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerItem(this, this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }
    }

    protected String getBaseName(){
        return this.name;
    }

    public boolean shouldAddCreative(){
        return true;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player){
        this.setEnergy(stack, 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        list.add(this.getEnergyStored(stack)+"/"+this.getMaxEnergyStored(stack)+" RF");
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

    @Override
    public boolean showDurabilityBar(ItemStack itemStack){
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        double energyDif = getMaxEnergyStored(stack)-getEnergyStored(stack);
        double maxAmount = getMaxEnergyStored(stack);
        return energyDif/maxAmount;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass){
        return false;
    }

    public void setEnergy(ItemStack stack, int energy){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            compound = new NBTTagCompound();
        }
        compound.setInteger("Energy", energy);
        stack.setTagCompound(compound);
    }
}
