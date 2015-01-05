package ellpeck.someprettytechystuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.blocks.BlockCrop;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSeed extends ItemSeeds{

    public ItemSeed(Block blockCrop, ItemStack cropItemStack, String unlocalizedName){
        super(blockCrop, Blocks.farmland);
        ((BlockCrop)blockCrop).setSeedItem(this);
        ((BlockCrop)blockCrop).setCropItem(cropItemStack);
        this.setCreativeTab(CreativeTab.instance);
        this.setUnlocalizedName(unlocalizedName);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }

}