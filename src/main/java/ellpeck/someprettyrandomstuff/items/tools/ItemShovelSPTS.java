package ellpeck.someprettyrandomstuff.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemShovelSPTS extends ItemSpade{

    public ItemShovelSPTS(ToolMaterial toolMat, String unlocalizedName){
        super(toolMat);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(CreativeTab.instance);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        Util.addStandardInformation(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
