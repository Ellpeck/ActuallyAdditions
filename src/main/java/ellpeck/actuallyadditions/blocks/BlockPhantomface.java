package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityPhantomface;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class BlockPhantomface extends BlockContainerBase implements INameableItem{

    public BlockPhantomface(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ){
        if(!world.isRemote){
            TileEntityPhantomface tile = (TileEntityPhantomface)world.getTileEntity(x, y, z);
            if(tile != null){
                if(tile.hasBoundTile()){
                    if(tile.isBoundTileInRage()){
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedBlock.desc", tile.boundTile.xCoord, tile.boundTile.yCoord, tile.boundTile.zCoord)));
                        return true;
                    }
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedNoRange.desc", tile.boundTile.xCoord, tile.boundTile.yCoord, tile.boundTile.zCoord)));
                    return true;
                }
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.notConnected.desc")));
                return true;
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityPhantomface();
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public String getName(){
        return "blockPhantomface";
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.epic;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            BlockUtil.addInformation(theBlock, list, 2, "");
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
