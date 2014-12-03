package ellpeck.gemification.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockOreGem extends ItemBlock {

    public ItemBlockOreGem(Block block){
        super(block);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + Util.gemList.get(stack.getItemDamage()).name.substring(5);
    }

    public int getMetadata(int i) {
        return i;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()){
            for(int i = 0; i < Util.gemList.size(); i++){
                if(this.getDamage(stack) == i) list.add(StatCollector.translateToLocal("tooltip.gem" + Util.gemList.get(i).name.substring(5) + ".desc"));
            }
            list.add(EnumChatFormatting.BOLD + StatCollector.translateToLocal("tooltip.gemIsOre.desc"));
        }
        else{
            list.add(Util.shiftForInfo());
        }
    }
}
