package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class DefaultItemBlock extends ItemBlock{

    Block theBlock;

    public DefaultItemBlock(Block block){
        super(block);
        this.theBlock = block;
        this.setHasSubtypes(false);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName();
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + "." + ((IName)theBlock).getName() + ".desc"));
        else list.add(Util.shiftForInfo());
    }
}
