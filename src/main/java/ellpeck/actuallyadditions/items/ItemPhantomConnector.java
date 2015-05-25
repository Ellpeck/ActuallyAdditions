package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityPhantomPlacer;
import ellpeck.actuallyadditions.tile.TileEntityPhantomface;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
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
                    if(this.checkHasConnection(stack, player)){
                        ChunkCoordinates coords = this.getStoredPosition(stack);
                        TileEntity toStore = this.getStoredWorld(stack).getTileEntity(coords.posX, coords.posY, coords.posZ);
                        if(toStore != null){
                            ((TileEntityPhantomface)tile).boundTile = toStore;
                            this.clearStorage(stack);
                            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connected.desc")));
                            return true;
                        }
                        else player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.notInventory.desc")));
                    }
                    return false;
                }
                //Passing to Placer
                else if(tile instanceof TileEntityPhantomPlacer){
                    if(this.checkHasConnection(stack, player)){
                        ((TileEntityPhantomPlacer)tile).boundPosition = this.getStoredPosition(stack);
                        ((TileEntityPhantomPlacer)tile).boundWorld = this.getStoredWorld(stack);
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

    public boolean checkHasConnection(ItemStack stack, EntityPlayer player){
        if(this.getStoredPosition(stack) != null && this.getStoredWorld(stack) != null){
            return true;
        }
        else{
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.noBound.desc")));
            return false;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(this.getStoredPosition(stack) == null || this.getStoredWorld(stack) == null) this.clearStorage(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(KeyUtil.isAltPressed()) this.clearStorage(stack);
        return stack;
    }

    public ChunkCoordinates getStoredPosition(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            int x = tag.getInteger("XCoordOfTileStored");
            int y = tag.getInteger("YCoordOfTileStored");
            int z = tag.getInteger("ZCoordOfTileStored");

            if(x == 0 && y == 0 && z == 0) return null;
            return new ChunkCoordinates(x, y, z);
        }
        return null;
    }

    public World getStoredWorld(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            return DimensionManager.getWorld(tag.getInteger("WorldOfTileStored"));
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
        ItemUtil.addInformation(this, list, 2, "");
        ChunkCoordinates coords = this.getStoredPosition(stack);
        World world = this.getStoredWorld(stack);
        if(coords != null && world != null){
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.boundTo.desc") + ":");
            list.add("X: " + coords.posX);
            list.add("Y: " + coords.posY);
            list.add("Z: " + coords.posZ);
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.inWorld.desc") + " " + world.provider.dimensionId);
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
    public String getOredictName(){
        return this.getName();
    }

    @Override
    public boolean getShareTag(){
        return true;
    }
}
