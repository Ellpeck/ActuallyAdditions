package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityLavaFactoryController;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.KeyUtil;
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

public class BlockLavaFactoryController extends BlockContainerBase implements INameableItem{

    private IIcon topIcon;

    public BlockLavaFactoryController(){
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
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityLavaFactoryController();
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return side == 1 ? this.topIcon : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Top");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController)world.getTileEntity(x, y, z);
            if(factory != null){
                int state = factory.isMultiblock();
                if(state == TileEntityLavaFactoryController.NOT_MULTI){
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".factory.notPart.desc")));
                }
                if(state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA){
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".factory.works.desc")));
                }
                player.addChatComponentMessage(new ChatComponentText(factory.storage.getEnergyStored() + "/" + factory.storage.getMaxEnergyStored() + " RF"));
            }
            return true;
        }
        return true;
    }

    @Override
    public String getName(){
        return "blockLavaFactoryController";
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
            return EnumRarity.uncommon;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            BlockUtil.addInformation(theBlock, list, 3, "");
            if(KeyUtil.isShiftPressed()){
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".uses.desc") + " " + TileEntityLavaFactoryController.energyNeededToProduceLava + " RF/B");
            }
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
