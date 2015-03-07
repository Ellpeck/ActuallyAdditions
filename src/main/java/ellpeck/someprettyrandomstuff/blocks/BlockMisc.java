package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.blocks.metalists.TheMiscBlocks;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Random;

public class BlockMisc extends Block implements IName{

    public static final TheMiscBlocks[] allMiscBlocks = TheMiscBlocks.values();
    public IIcon[] textures = new IIcon[allMiscBlocks.length];

    public BlockMisc(){
        super(Material.rock);
        this.setBlockName(Util.setUnlocalizedName(this));
        this.setCreativeTab(CreativeTab.instance);
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for (int j = 0; j < allMiscBlocks.length; ++j){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int par3){
        return meta == TheMiscBlocks.ORE_QUARTZ.ordinal() ? InitItems.itemMisc : super.getItemDropped(meta, rand, par3);
    }

    @Override
    public int damageDropped(int meta){
        return meta == TheMiscBlocks.ORE_QUARTZ.ordinal() ? TheMiscItems.QUARTZ.ordinal() : super.damageDropped(meta);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random){
        return meta == TheMiscBlocks.ORE_QUARTZ.ordinal() ? 1+random.nextInt(2)+fortune : 1;
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return textures[metadata];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName() + allMiscBlocks[i].getName());
        }
    }

    @Override
    public String getName(){
        return "blockMisc";
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return allMiscBlocks[stack.getItemDamage()].rarity;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName() + allMiscBlocks[stack.getItemDamage()].getName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + "." + ((IName)theBlock).getName() + allMiscBlocks[stack.getItemDamage()].getName() + ".desc"));
            else list.add(Util.shiftForInfo());
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
