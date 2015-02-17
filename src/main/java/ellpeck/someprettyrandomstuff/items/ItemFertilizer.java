package ellpeck.someprettyrandomstuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemFertilizer extends Item{

    public ItemFertilizer(){
        this.setUnlocalizedName("itemFertilizer");
        this.setCreativeTab(CreativeTab.instance);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
        if(ItemDye.applyBonemeal(stack, world, x, y, z, player)){
            if(!world.isRemote) world.playAuxSFX(2005, x, y, z, 0);
            return true;
        }
        return super.onItemUse(stack, player, world, x, y, z, par7, par8, par9, par10);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
    }

    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + Util.getSubbedUnlocalized(this));
    }
}
