package ellpeck.someprettytechystuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.items.InitItems;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

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

    public static class ItemBlockOreGem extends ItemBlock {

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
            else list.add(Util.shiftForInfo());
        }
    }
}
