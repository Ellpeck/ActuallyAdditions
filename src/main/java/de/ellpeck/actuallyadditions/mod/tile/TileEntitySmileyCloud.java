// TODO: [port][note]: Not used
///*
// * This file ("TileEntitySmileyCloud.java") is part of the Actually Additions mod for Minecraft.
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
//import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.nbt.CompoundNBT;
//
//public class TileEntitySmileyCloud extends TileEntityBase implements IStringReactor {
//
//    public String name;
//    private String nameBefore;
//
//    public TileEntitySmileyCloud() {
//        super("smileyCloud");
//    }
//
//    @Override
//    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
//        super.writeSyncableNBT(compound, type);
//        if (this.name != null && type != NBTType.SAVE_BLOCK) {
//            compound.setString("Name", this.name);
//        }
//    }
//
//    @Override
//    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
//        super.readSyncableNBT(compound, type);
//        if (type != NBTType.SAVE_BLOCK) {
//            this.name = compound.getString("Name");
//        }
//    }
//
//    @Override
//    public void updateEntity() {
//        super.updateEntity();
//        if (!this.world.isRemote) {
//            boolean nameChanged = this.name != null
//                ? !this.name.equals(this.nameBefore)
//                : this.nameBefore != null;
//            if (nameChanged && this.sendUpdateWithInterval()) {
//                this.nameBefore = this.name;
//                this.markDirty();
//            }
//        }
//    }
//
//    @Override
//    public void onTextReceived(String text, int textID, PlayerEntity player) {
//        this.name = text;
//    }
//}
