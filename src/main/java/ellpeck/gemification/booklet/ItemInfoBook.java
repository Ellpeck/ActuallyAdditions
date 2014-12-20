package ellpeck.gemification.booklet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.Gemification;
import ellpeck.gemification.creative.CreativeTab;
import ellpeck.gemification.inventory.GuiHandler;
import ellpeck.gemification.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemInfoBook extends Item {

    public ItemInfoBook(){
        this.setCreativeTab(CreativeTab.instance);
        this.setUnlocalizedName("itemInfoBook");
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName().substring(5) + ".desc"));
        else list.add(Util.shiftForInfo());
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if (!world.isRemote){
            player.openGui(Gemification.instance, GuiHandler.guiInfoBook, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return stack;
    }
}
