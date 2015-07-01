package ellpeck.actuallyadditions.waila;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unused")
public class WailaDataProvider implements IWailaDataProvider{

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config){
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){

        if(ConfigBoolValues.DO_WAILA_INFO.isEnabled()){
            if(BlockUtil.wailaRegisterList.contains(accessor.getBlock())){
                Item.getItemFromBlock(accessor.getBlock()).addInformation(stack, accessor.getPlayer(), currentTip, true);
            }
        }

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

    public static void register(IWailaRegistrar registrar){
        ModUtil.LOGGER.info("Initializing Waila Plugin...");

        WailaDataProvider provider = new WailaDataProvider();
        for(Block theBlock : BlockUtil.wailaRegisterList){
            registrar.registerHeadProvider(provider, theBlock.getClass());
        }
    }
}
