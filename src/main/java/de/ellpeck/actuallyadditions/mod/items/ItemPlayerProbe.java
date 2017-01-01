/*
 * This file ("ItemPlayerProbe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

public class ItemPlayerProbe extends ItemBase{

    public ItemPlayerProbe(String name){
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
        if(!world.isRemote){
            if(stack.hasTagCompound()){
                NBTTagCompound compound = stack.getTagCompound();
                if(compound.hasKey("UUIDMost")){
                    UUID id = compound.getUniqueId("UUID");
                    EntityPlayer player = world.getPlayerEntityByUUID(id);
                    if(player != null){
                        if(player.isSneaking()){
                            ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                            entity.sendMessage(new TextComponentTranslation("tooltip."+ModUtil.MOD_ID+".playerProbe.disconnect.1"));
                            player.sendMessage(new TextComponentTranslation("tooltip."+ModUtil.MOD_ID+".playerProbe.notice"));
                            TheAchievements.GET_UNPROBED.get(player);
                        }
                    }
                    else{
                        ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                        entity.sendMessage(new TextComponentTranslation("tooltip."+ModUtil.MOD_ID+".playerProbe.disconnect.2"));
                    }
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityPlayerInterface){
            if(stack.hasTagCompound()){
                NBTTagCompound compound = stack.getTagCompound();
                if(compound.hasKey("UUIDMost")){
                    if(!world.isRemote){
                        TileEntityPlayerInterface face = (TileEntityPlayerInterface)tile;
                        face.connectedPlayer = compound.getUniqueId("UUID");
                        face.playerName = compound.getString("Name");
                        face.markDirty();
                        face.sendUpdate();

                        ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack aStack, EntityPlayer player, EntityLivingBase entity, EnumHand hand){
        if(!player.world.isRemote){
            ItemStack stack = player.getHeldItemMainhand();
            if(StackUtil.isValid(stack) && stack.getItem() == this){
                if(entity instanceof EntityPlayer){
                    EntityPlayer playerHit = (EntityPlayer)entity;

                    if(!playerHit.isSneaking()){
                        if(!stack.hasTagCompound()){
                            stack.setTagCompound(new NBTTagCompound());
                        }

                        NBTTagCompound compound = stack.getTagCompound();
                        compound.setString("Name", playerHit.getName());
                        compound.setUniqueId("UUID", playerHit.getUniqueID());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(stack.hasTagCompound()){
            String name = stack.getTagCompound().getString("Name");
            if(name != null){
                list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+".playerProbe.probing")+": "+name);
            }
        }
    }
}
