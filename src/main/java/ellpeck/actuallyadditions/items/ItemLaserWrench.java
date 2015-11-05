/*
 * This file ("ItemLaserWrench.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.misc.LaserRelayConnectionHandler;
import ellpeck.actuallyadditions.tile.TileEntityLaserRelay;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemLaserWrench extends Item implements IActAddItemOrBlock{

    public ItemLaserWrench(){
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntityLaserRelay){
                if(ItemPhantomConnector.getStoredPosition(stack) == null){
                    ItemPhantomConnector.storeConnection(stack, x, y, z, world);
                    player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".laser.stored.desc")));
                }
                else{
                    WorldPos savedPos = ItemPhantomConnector.getStoredPosition(stack);
                    WorldPos otherPos = new WorldPos(world, x, y, z);
                    if(savedPos.getTileEntity() instanceof TileEntityLaserRelay && LaserRelayConnectionHandler.getInstance().addConnection(savedPos, otherPos)){
                        ItemPhantomConnector.clearStorage(stack);

                        savedPos.update();
                        otherPos.update();

                        player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".laser.connected.desc")));
                    }
                    else{
                        player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".laser.cantConnect.desc")));
                        ItemPhantomConnector.clearStorage(stack);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(ItemPhantomConnector.getStoredPosition(stack) == null){
            ItemPhantomConnector.clearStorage(stack);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        WorldPos coords = ItemPhantomConnector.getStoredPosition(stack);
        if(coords != null){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".boundTo.desc")+":");
            list.add("X: "+coords.getX());
            list.add("Y: "+coords.getY());
            list.add("Z: "+coords.getZ());
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".inWorld.desc")+" "+coords.getWorldID());
            list.add(EnumChatFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".clearStorage.desc"));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getName(){
        return "itemLaserWrench";
    }
}
