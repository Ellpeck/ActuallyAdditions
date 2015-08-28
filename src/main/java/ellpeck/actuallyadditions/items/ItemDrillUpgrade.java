package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDrillUpgrade extends Item implements INameableItem{

    public enum UpgradeType{
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

    public UpgradeType type;
    public String unlocalizedName;

    public ItemDrillUpgrade(UpgradeType type, String unlocName){
        this.type = type;
        this.unlocalizedName = unlocName;
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote && this.type == UpgradeType.PLACER){
            this.setSlotToPlaceFrom(stack, player.inventory.currentItem);
        }
        return stack;
    }

    public void setSlotToPlaceFrom(ItemStack stack, int slot){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();

        compound.setInteger("SlotToPlaceFrom", slot+1);

        stack.setTagCompound(compound);
    }

    public static int getSlotToPlaceFrom(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound != null){
            return compound.getInteger("SlotToPlaceFrom")-1;
        }
        return -1;
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
        return this.unlocalizedName;
    }
}
