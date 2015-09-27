/*
 * This file ("PersistantServerPlayerData.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util.playerdata;

import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PersistantServerData implements IExtendedEntityProperties{

    public boolean bookGottenAlready;

    @Override
    public void saveNBTData(NBTTagCompound aComp){
        NBTTagCompound compound = new NBTTagCompound();

        compound.setBoolean("BookGotten", bookGottenAlready);

        aComp.setTag(ModUtil.MOD_ID, compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound aComp){
        NBTBase base = aComp.getTag(ModUtil.MOD_ID);
        if(base != null && base instanceof NBTTagCompound){
            NBTTagCompound compound = (NBTTagCompound)base;

            this.bookGottenAlready = compound.getBoolean("BookGotten");
        }
    }

    @Override
    public void init(Entity entity, World world){

    }

    public static PersistantServerData get(EntityPlayer player){
        IExtendedEntityProperties properties = player.getExtendedProperties(ModUtil.MOD_ID);
        if(properties != null && properties instanceof PersistantServerData){
            return (PersistantServerData)properties;
        }
        return null;
    }
}
