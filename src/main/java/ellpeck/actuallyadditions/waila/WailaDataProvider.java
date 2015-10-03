/*
 * This file ("WailaDataProvider.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.waila;

import ellpeck.actuallyadditions.util.ModUtil;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unused")
public class WailaDataProvider implements IWailaDataProvider{

    public static void register(IWailaRegistrar registrar){
        ModUtil.LOGGER.info("Initializing Waila Plugin...");
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config){
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        return currentTip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        return currentTip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        return currentTip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z){
        return tag;
    }
}
