package ellpeck.gemification.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockCrucibleFire extends ItemBlock {

    public ItemBlockCrucibleFire(Block block){
        super(block);
        setHasSubtypes(false);
    }

    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName().substring(5) + ".desc"));
        else list.add(Util.shiftForInfo());
    }
}
