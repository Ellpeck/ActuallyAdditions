package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDrillUpgrade extends ItemBase {

    public final UpgradeType type;

    public ItemDrillUpgrade(UpgradeType type, String unlocName) {
        super(unlocName);
        this.type = type;
        this.setMaxStackSize(1);
    }

    public static int getSlotToPlaceFrom(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null) { return compound.getInteger("SlotToPlaceFrom") - 1; }
        return -1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && this.type == UpgradeType.PLACER) {
            this.setSlotToPlaceFrom(stack, player.inventory.currentItem);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    public void setSlotToPlaceFrom(ItemStack stack, int slot) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
        }

        compound.setInteger("SlotToPlaceFrom", slot + 1);

        stack.setTagCompound(compound);
    }

    public enum UpgradeType {
        SPEED,
        SPEED_II,
        SPEED_III,
        SILK_TOUCH,
        FORTUNE,
        FORTUNE_II,
        THREE_BY_THREE,
        FIVE_BY_FIVE,
        PLACER
    }
}
