package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.blocks.metalists.TheMiscBlocks;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Random;

public class BlockMisc extends Block{

    public static final TheMiscBlocks[] allMiscBlocks = TheMiscBlocks.values();
    public IIcon[] textures = new IIcon[allMiscBlocks.length];

    public BlockMisc(){
        super(Material.rock);
        this.setBlockName("blockMisc");
        this.setCreativeTab(CreativeTab.instance);
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for (int j = 0; j < allMiscBlocks.length; ++j){
            list.add(new ItemStack(item, 1, j));
        }
    }

    public Item getItemDropped(int meta, Random rand, int par3){
        return meta == TheMiscBlocks.ORE_QUARTZ.ordinal() ? InitItems.itemMisc : super.getItemDropped(meta, rand, par3);
    }

    public int damageDropped(int meta){
        return meta == TheMiscBlocks.ORE_QUARTZ.ordinal() ? TheMiscItems.QUARTZ.ordinal() : super.damageDropped(meta);
    }

    public IIcon getIcon(int side, int metadata){
        return textures[metadata];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + Util.getSubbedUnlocalized(this) + allMiscBlocks[i].getName());
        }
    }

    public static class ItemBlockMisc extends ItemBlock{

        public ItemBlockMisc(Block block){
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName() + allMiscBlocks[stack.getItemDamage()].getName();
        }

        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName(stack).substring(5) + ".desc"));
            else list.add(Util.shiftForInfo());
        }

        public int getMetadata(int damage){
            return damage;
        }
    }
}
