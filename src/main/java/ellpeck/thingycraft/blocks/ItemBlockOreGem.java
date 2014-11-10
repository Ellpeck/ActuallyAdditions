package ellpeck.thingycraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.thingycraft.Util;
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
        return this.getUnlocalizedName() + Util.gemTypes[stack.getItemDamage()];
    }

    public int getMetadata(int i) {
        return i;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()){
            for(int i = 0; i < Util.gemTypes.length; i++){
                if(this.getDamage(stack) == i) list.add(StatCollector.translateToLocal("tooltip.gem" + Util.gemTypes[i] + ".desc"));
            }
            list.add(EnumChatFormatting.BOLD + StatCollector.translateToLocal("tooltip.gemIsOre.desc"));
        }
        else{
            list.add(Util.shiftForInfo());
        }
    }
}
