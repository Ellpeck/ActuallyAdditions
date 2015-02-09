package ellpeck.someprettyrandomstuff.items.tools;

import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHoeSPTS extends ItemHoe{

    public ItemHoeSPTS(ToolMaterial toolMat, String unlocalizedName){
        super(toolMat);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(CreativeTab.instance);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        Util.addStandardInformation(this);
    }
}
