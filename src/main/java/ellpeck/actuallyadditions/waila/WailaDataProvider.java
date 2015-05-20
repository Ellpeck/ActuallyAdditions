package ellpeck.actuallyadditions.waila;

import ellpeck.actuallyadditions.blocks.BlockCompost;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.tile.TileEntityCompost;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class WailaDataProvider implements IWailaDataProvider{

    private final String WAILA_PRE_LANG = "gui." + ModUtil.MOD_ID_LOWER + ".waila.";

    public static final ArrayList<Block> registerList = new ArrayList<Block>();

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config){
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){

        if(ConfigBoolValues.DO_WAILA_INFO.isEnabled()){
            if(accessor.getBlock() instanceof INameableItem){
                Item.getItemFromBlock(accessor.getBlock()).addInformation(stack, accessor.getPlayer(), currentTip, true);
            }
        }

        return currentTip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){

        if(accessor.getTileEntity() instanceof TileEntityCompost){
            this.compostBody(accessor, currentTip);
        }

        return currentTip;
    }

    public void compostBody(IWailaDataAccessor accessor, List<String> currentTip){
        int meta = accessor.getMetadata();
        TileEntityCompost tile = (TileEntityCompost)accessor.getTileEntity();

        if(meta <= tile.amountNeededToConvert){
            String tip1 = StatCollector.translateToLocal(WAILA_PRE_LANG + "compostAmount.name") + ": " + meta + "/" + tile.amountNeededToConvert;
            currentTip.add(tip1);

            if(meta == tile.amountNeededToConvert){
                currentTip.add(StatCollector.translateToLocal(WAILA_PRE_LANG + "compostConverting.name"));
            }
        }

        if(meta == tile.amountNeededToConvert+1){
            currentTip.add(StatCollector.translateToLocal(WAILA_PRE_LANG + "compostDone.name"));
        }
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        return currentTip;
    }

    @Override
    public NBTTagCompound getNBTData(TileEntity te, NBTTagCompound tag, World world, int x, int y, int z){
        return tag;
    }

    public static void register(IWailaRegistrar registrar){
        Util.logInfo("Initializing Waila Plugin...");

        WailaDataProvider provider = new WailaDataProvider();

        registrar.registerBodyProvider(provider, BlockCompost.class);

        for(Block theBlock : registerList){
            registrar.registerHeadProvider(provider, theBlock.getClass());
        }
    }
}