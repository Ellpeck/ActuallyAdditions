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
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemLaserWrench extends Item implements IActAddItemOrBlock{

    public ItemLaserWrench(){
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(player.isSneaking() && !world.isRemote){
            ItemPhantomConnector.clearStorage(stack);
            player.addChatComponentMessage(new ChatComponentText("Storage cleared!"));
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntityLaserRelay && !player.isSneaking()){
            if(!world.isRemote){
                if(ItemPhantomConnector.getStoredPosition(stack) == null){
                    ItemPhantomConnector.storeConnection(stack, x, y, z, world);
                    player.addChatComponentMessage(new ChatComponentText("Stored!"));
                }
                else{
                    WorldPos savedPos = ItemPhantomConnector.getStoredPosition(stack);
                    if(savedPos.getTileEntity() instanceof TileEntityLaserRelay){
                        WorldPos otherPos = new WorldPos(world, x, y, z);
                        int distance = (int)Vec3.createVectorHelper(otherPos.getX(), otherPos.getY(), otherPos.getZ()).distanceTo(Vec3.createVectorHelper(savedPos.getX(), savedPos.getY(), savedPos.getZ()));
                        if(distance <= 15){
                            if(!savedPos.isEqual(otherPos) && savedPos.getWorld() == otherPos.getWorld()){
                                if(LaserRelayConnectionHandler.getInstance().addConnection(savedPos, otherPos)){
                                    player.addChatComponentMessage(new ChatComponentText("Connected!"));
                                    ItemPhantomConnector.clearStorage(stack);

                                    savedPos.update();
                                    otherPos.update();
                                }
                                else{
                                    player.addChatComponentMessage(new ChatComponentText("Couldn't connect!"));
                                }
                            }
                            else{
                                player.addChatComponentMessage(new ChatComponentText("Can't connect a Laser Relay to itself!"));
                            }
                        }
                        else{
                            player.addChatComponentMessage(new ChatComponentText("Too far away! Distance is "+distance+ "blocks!"));
                        }
                    }
                    else{
                        player.addChatComponentMessage(new ChatComponentText("The Laser Relay you were trying to connect to doesn't exist!"));
                        ItemPhantomConnector.clearStorage(stack);
                    }
                }
            }
            return true;
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
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getName(){
        return "itemLaserWrench";
    }
}
