package de.ellpeck.actuallyadditions.common.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityPlayerInterface;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
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

public class ItemPlayerProbe extends ItemBase {

    public ItemPlayerProbe(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            if (stack.hasTagCompound()) {
                NBTTagCompound compound = stack.getTagCompound();
                if (compound.hasKey("UUIDMost")) {
                    UUID id = compound.getUniqueId("UUID");
                    EntityPlayer player = world.getPlayerEntityByUUID(id);
                    if (player != null) {
                        if (player.isSneaking()) {
                            ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                            entity.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".playerProbe.disconnect.1"));
                            player.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".playerProbe.notice"));
                            //TheAchievements.GET_UNPROBED.get(player);
                        }
                    } else {
                        ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                        entity.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".playerProbe.disconnect.2"));
                    }
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPlayerInterface) {
            if (stack.hasTagCompound()) {
                NBTTagCompound compound = stack.getTagCompound();
                if (compound.hasKey("UUIDMost")) {
                    if (!world.isRemote) {
                        TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
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
    public boolean itemInteractionForEntity(ItemStack aStack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if (!player.world.isRemote) {
            ItemStack stack = player.getHeldItemMainhand();
            if (StackUtil.isValid(stack) && stack.getItem() == this) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer playerHit = (EntityPlayer) entity;

                    if (!playerHit.isSneaking()) {
                        if (!stack.hasTagCompound()) {
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
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasTagCompound()) {
            String name = stack.getTagCompound().getString("Name");
            if (name != null) {
                tooltip.add(StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".playerProbe.probing") + ": " + name);
            }
        }
    }
}
