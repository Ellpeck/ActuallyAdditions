package ellpeck.someprettytechystuff.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemSwordG extends ItemSword{

    public ItemSwordG(ToolMaterial toolMat, String unlocalizedName){
        super(toolMat);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(CreativeTab.instance);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName().substring(5) + ".desc"));
        else list.add(Util.shiftForInfo());
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
