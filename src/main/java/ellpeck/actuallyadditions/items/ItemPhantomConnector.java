package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityPhantomPlacer;
import ellpeck.actuallyadditions.tile.TileEntityPhantomface;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.List;

public class ItemPhantomConnector extends Item implements INameableItem{

    public ItemPhantomConnector(){
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
        if(!world.isRemote){
            //Passing Data to Phantoms
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile != null){
                //Passing to Face
                if(tile instanceof TileEntityPhantomface){
                    if(this.checkHasConnection(stack, player, tile)){
                        ((TileEntityPhantomface)tile).boundPosition = this.getStoredPosition(stack);
                        this.clearStorage(stack);
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connected.desc")));
                        return true;
                    }
                    return false;
                }
                //Passing to Placer
                else if(tile instanceof TileEntityPhantomPlacer){
                    if(this.checkHasConnection(stack, player, tile)){
                        ((TileEntityPhantomPlacer)tile).boundPosition = this.getStoredPosition(stack);
                        tile.markDirty();
                        this.clearStorage(stack);
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connected.desc")));
                        return true;
                    }
                    return false;
                }
            }
            //Storing Connections
            this.storeConnection(stack, x, y, z, world);
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.stored.desc")));
        }
        return true;
    }

    public boolean checkHasConnection(ItemStack stack, EntityPlayer player, TileEntity tile){
        if(this.getStoredPosition(stack) != null){
            return true;
        }
        else{
            if(tile instanceof TileEntityPhantomPlacer){
                ((TileEntityPhantomPlacer)tile).boundPosition = null;
            }
            if(tile instanceof TileEntityPhantomface){
                ((TileEntityPhantomface)tile).boundPosition = null;
            }
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.unbound.desc")));
            return false;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(this.getStoredPosition(stack) == null) this.clearStorage(stack);
    }

    public WorldPos getStoredPosition(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            int x = tag.getInteger("XCoordOfTileStored");
            int y = tag.getInteger("YCoordOfTileStored");
            int z = tag.getInteger("ZCoordOfTileStored");
            World world = DimensionManager.getWorld(tag.getInteger("WorldOfTileStored"));
            if(x != 0 && y != 0 && z != 0 && world != null){
                return new WorldPos(world, x, y, z);
            }
        }
        return null;
    }

    public void storeConnection(ItemStack stack, int x, int y, int z, World world){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null) tag = new NBTTagCompound();

        tag.setInteger("XCoordOfTileStored", x);
        tag.setInteger("YCoordOfTileStored", y);
        tag.setInteger("ZCoordOfTileStored", z);
        tag.setInteger("WorldOfTileStored", world.provider.dimensionId);

        stack.setTagCompound(tag);
    }

    public void clearStorage(ItemStack stack){
        stack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        ItemUtil.addInformation(this, list, 1, "");
        WorldPos coords = this.getStoredPosition(stack);
        if(coords != null){
            World world = coords.getWorld();
            if(world != null){
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.boundTo.desc")+":");
                list.add("X: "+coords.getX());
                list.add("Y: "+coords.getY());
                list.add("Z: "+coords.getZ());
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.inWorld.desc")+" "+world.provider.dimensionId);
            }
        }
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

    @Override
    public String getName(){
        return "itemPhantomConnector";
    }

    @Override
    public boolean getShareTag(){
        return true;
    }
}
