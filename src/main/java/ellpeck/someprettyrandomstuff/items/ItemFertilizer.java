package ellpeck.someprettyrandomstuff.items;

import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemFertilizer extends Item{

    public ItemFertilizer(){
        this.setUnlocalizedName("itemFertilizer");
        this.setCreativeTab(CreativeTab.instance);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(ItemDye.applyBonemeal(stack, world, blockPos, player)){
            if(!world.isRemote) world.playAuxSFX(2005, blockPos, 0);
            return true;
        }
        return super.onItemUse(stack, player, world, blockPos, side, hitX, hitY, hitZ);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
    }
}
