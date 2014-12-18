package ellpeck.gemification.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.creative.CreativeTab;
import ellpeck.gemification.items.InitItems;
import ellpeck.gemification.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;
import java.util.Random;

public class OreGem extends Block{

    public final IIcon[] textures;

    public OreGem() {
        super(Material.rock);
        textures = new IIcon[Util.gemList.size()];
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(CreativeTab.instance);
        this.setBlockName("oreGem");
        this.setHarvestLevel("pickaxe", 2);
    }

    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item stack, CreativeTabs tab, List list) {
        for (int i = 0; i < Util.gemList.size(); i++) {
            list.add(new ItemStack(stack, 1, i));
        }
    }

    public Item getItemDropped(int par1, Random rand, int par3){
        return InitItems.itemGem;
    }

    public int damageDropped(int i){
        return i;
    }

    public int quantityDropped(Random rand){
        return 1 + rand.nextInt(5);
    }

    public IIcon getIcon(int side, int meta) {
        return textures[meta];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg) {
        for (int i = 0; i < Util.gemList.size(); i++) {
            textures[i] = iconReg.registerIcon(Util.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + Util.gemList.get(i).name.substring(5));
        }
    }
}
