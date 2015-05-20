package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityPhantomface;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
            TileEntity tile = world.getTileEntity(x, y, z);

            if(tile != null && tile instanceof TileEntityPhantomface && this.getStoredConnection(stack) != null){
                TileEntity stored = this.getStoredConnection(stack);
                if(stored != null && stored.getWorldObj().getTileEntity(stored.xCoord, stored.yCoord, stored.zCoord) == stored){
                    ((TileEntityPhantomface)tile).boundTile = stored.getWorldObj().getTileEntity(stored.xCoord, stored.yCoord, stored.zCoord);
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.connected.desc")));
                    this.clearStorage(stack);
                    return true;
                }
            }

            if(tile != null && !(tile instanceof TileEntityPhantomface) && tile instanceof IInventory){
                this.storeConnection(stack, tile);
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.stored.desc")));
                return true;
            }
            else{
                if(tile instanceof TileEntityPhantomface) player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.noBound.desc")));
                else player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.notInventory.desc")));
            }
        }
        return super.onItemUse(stack, player, world, x, y, z, par7, par8, par9, par10);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(this.getStoredConnection(stack) == null) this.clearStorage(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(KeyUtil.isControlPressed()) this.clearStorage(stack);
        return stack;
    }

    public TileEntity getStoredConnection(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            int x = tag.getInteger("XCoordOfTileStored");
            int y = tag.getInteger("YCoordOfTileStored");
            int z = tag.getInteger("ZCoordOfTileStored");
            World world = DimensionManager.getWorld(tag.getInteger("WorldOfTileStored"));

            return world.getTileEntity(x, y, z);
        }
        return null;
    }

    public void storeConnection(ItemStack stack, TileEntity tile){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null) tag = new NBTTagCompound();

        tag.setInteger("XCoordOfTileStored", tile.xCoord);
        tag.setInteger("YCoordOfTileStored", tile.yCoord);
        tag.setInteger("ZCoordOfTileStored", tile.zCoord);
        tag.setInteger("WorldOfTileStored", tile.getWorldObj().provider.dimensionId);

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
        TileEntity tile = this.getStoredConnection(stack);
        if(tile != null){
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.boundTo.desc") + ":");
            list.add("X: " + tile.xCoord);
            list.add("Y: " + tile.yCoord);
            list.add("Z: " + tile.zCoord);
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".phantom.inWorld.desc") + " " + tile.getWorldObj().provider.dimensionId);
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
