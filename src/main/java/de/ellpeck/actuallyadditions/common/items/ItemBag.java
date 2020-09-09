package de.ellpeck.actuallyadditions.common.items;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.common.inventory.GuiHandler.GuiTypes;
import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemBag extends ItemBase {

    public final boolean isVoid;

    public ItemBag(String name, boolean isVoid) {
        super(name);
        this.isVoid = isVoid;
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!this.isVoid) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                if (!worldIn.isRemote) {
                    IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                    if (handler != null) {
                        boolean changed = false;

                        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerBag.getSlotAmount(this.isVoid));
                        ItemDrill.loadSlotsFromNBT(inv, stack);

                        for (int j = 0; j < inv.getSlots(); j++) {
                            ItemStack invStack = inv.getStackInSlot(j);
                            if (StackUtil.isValid(invStack)) {
                                for (int i = 0; i < handler.getSlots(); i++) {
                                    ItemStack remain = handler.insertItem(i, invStack, false);
                                    if (!ItemStack.areItemStacksEqual(remain, invStack)) {
                                        inv.setStackInSlot(j, remain.copy());
                                        changed = true;
                                        if (!StackUtil.isValid(remain)) {
                                            break;
                                        }
                                        invStack = remain;
                                    }
                                }
                            }
                        }

                        if (changed) {
                            ItemDrill.writeSlotsToNBT(inv, stack);
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
            player.openGui(ActuallyAdditions.INSTANCE, (this.isVoid ? GuiTypes.VOID_BAG : GuiTypes.BAG).ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.isVoid ? EnumRarity.RARE : EnumRarity.UNCOMMON;
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        return null;
    }
}
