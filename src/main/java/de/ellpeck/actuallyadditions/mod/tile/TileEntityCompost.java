// FIXME: [port] No longer required
///*
// * This file ("TileEntityCompost.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.tile;
//
//import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
//import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
//import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
//import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
//import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import net.minecraft.block.BlockState;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//
//public class TileEntityCompost extends TileEntityInventoryBase {
//
//    public static final int COMPOST_TIME_TICKS = 3000;
//
//    protected int conversionTime;
//    protected CompostRecipe recipe;
//
//    public TileEntityCompost() {
//        super(ActuallyTiles.COMPOST_TILE.get(), 1);
//    }
//
//    public static CompostRecipe getRecipeForInput(ItemStack input) {
//        if (StackUtil.isValid(input)) {
//            for (CompostRecipe recipe : ActuallyAdditionsAPI.COMPOST_RECIPES) {
//                if (recipe.matches(input)) {
//                    return recipe;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
//        super.writeSyncableNBT(compound, type);
//        if (type != NBTType.SAVE_BLOCK) {
//            compound.putInt("ConversionTime", this.conversionTime);
//        }
//    }
//
//    @Override
//    public boolean shouldSyncSlots() {
//        return true;
//    }
//
//    @Override
//    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
//        super.readSyncableNBT(compound, type);
//        if (type != NBTType.SAVE_BLOCK) {
//            this.conversionTime = compound.getInt("ConversionTime");
//        }
//        if (type == NBTType.SYNC) {
//            this.world.markBlockRangeForRenderUpdate(this.pos, this.pos.up());
//        }
//    }
//
//    @Override
//    public void updateEntity() {
//        super.updateEntity();
//        if (!this.world.isRemote) {
//            boolean theFlag = this.conversionTime > 0;
//            ItemStack input = this.inv.getStackInSlot(0);
//            if (StackUtil.isValid(input)) {
//                if (this.recipe == null || !this.recipe.matches(input)) {
//                    this.recipe = getRecipeForInput(input);
//                }
//                if (this.recipe != null) {
//                    this.conversionTime++;
//                    if (this.conversionTime >= COMPOST_TIME_TICKS) {
//                        ItemStack stack = this.recipe.getOutput().copy();
//                        stack.setCount(input.getCount());
//                        this.inv.setStackInSlot(0, stack);
//                        this.conversionTime = 0;
//                        this.markDirty();
//                    }
//                } else {
//                    this.conversionTime = 0;
//                }
//            }
//
//            if (theFlag != this.conversionTime > 0) {
//                this.markDirty();
//            }
//        }
//    }
//
//    @Override
//    public IAcceptor getAcceptor() {
//        return (slot, stack, automation) -> getRecipeForInput(stack) != null;
//    }
//
//    @Override
//    public IRemover getRemover() {
//        return (slot, automation) -> getRecipeForInput(this.inv.getStackInSlot(slot)) == null;
//    }
//
//    public BlockState getCurrentDisplay() {
//        ItemStack input = this.inv.getStackInSlot(0);
//        CompostRecipe displayRecipe = this.recipe;
//        if (displayRecipe == null || !displayRecipe.matches(input)) {
//            displayRecipe = getRecipeForInput(input);
//        }
//
//        if (displayRecipe == null) {
//            for (CompostRecipe r : ActuallyAdditionsAPI.COMPOST_RECIPES) {
//                if (ItemUtil.areItemsEqual(input, r.getOutput(), true)) {
//                    return r.getOutputDisplay();
//                } else if (r.getInput().apply(input)) {
//                    return r.getInputDisplay();
//                }
//            }
//        }
//
//        if (displayRecipe != null) {
//            return displayRecipe.getInputDisplay();
//        }
//        return Blocks.AIR.getDefaultState();
//    }
//
//    public float getHeight() {
//        ItemStack input = this.inv.getStackInSlot(0);
//        if (input.isEmpty()) {
//            return 0;
//        }
//        return (float) input.getCount() / input.getMaxStackSize();
//    }
//}
