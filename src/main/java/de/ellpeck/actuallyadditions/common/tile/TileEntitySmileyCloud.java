package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.network.gui.IStringReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySmileyCloud extends TileEntityBase implements IStringReactor {

    public String name;
    private String nameBefore;

    public TileEntitySmileyCloud() {
        super("smileyCloud");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (this.name != null && type != NBTType.SAVE_BLOCK) {
            compound.setString("Name", this.name);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.name = compound.getString("Name");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            boolean nameChanged = this.name != null ? !this.name.equals(this.nameBefore) : this.nameBefore != null;
            if (nameChanged && this.sendUpdateWithInterval()) {
                this.nameBefore = this.name;
                this.markDirty();
            }
        }
    }

    @Override
    public void onTextReceived(String text, int textID, EntityPlayer player) {
        this.name = text;
    }
}
