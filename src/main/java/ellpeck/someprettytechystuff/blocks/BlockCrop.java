package ellpeck.someprettytechystuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockCrop extends BlockCrops{

    private IIcon[] textures;

    private Item seedItem;
    private ItemStack cropItemStack;
    private int growthStages;

    public BlockCrop(String blockName, int growthStages){
        super();
        this.growthStages = growthStages;
        this.textures = new IIcon[growthStages];
        this.setBlockName(blockName);
    }

    public void setSeedItem(Item seedItem){
        this.seedItem = seedItem;
    }

    public void setCropItem(ItemStack cropItemStack){
        this.cropItemStack = cropItemStack;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(meta < 7){
            if(meta == 6) meta = 5;
            return this.textures[meta >> 1];
        }
        return textures[growthStages-1];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        for(int i = 0; i < this.textures.length-1; i++){
            this.textures[i] = iconReg.registerIcon(Util.MOD_ID + ":" + "blockCropStage" + i);
        }
        this.textures[this.textures.length-1] = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + "Stage" + (this.textures.length-1));
    }

    public Item func_149866_i(){
        return seedItem;
    }

    public Item func_149865_P(){
        return cropItemStack.getItem();
    }

    public int damageDropped(int par1){
        return cropItemStack.getItemDamage();
    }
}
