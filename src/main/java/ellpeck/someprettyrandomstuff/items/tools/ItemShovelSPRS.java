package ellpeck.someprettyrandomstuff.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemShovelSPRS extends ItemSpade{

    public ItemShovelSPRS(ToolMaterial toolMat, String unlocalizedName){
        super(toolMat);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(CreativeTab.instance);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
        if(Util.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip.durability.desc") + ": " + (this.getMaxDamage()-this.getDamage(stack)) + "/" + this.getMaxDamage());
        }
    }

    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + Util.getSubbedUnlocalized(this));
    }
}
