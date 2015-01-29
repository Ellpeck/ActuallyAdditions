package ellpeck.someprettytechystuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class DefaultItemBlock extends ItemBlock{

    public DefaultItemBlock(Block block){
        super(block);
        this.setHasSubtypes(false);
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName();
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
    }
}
