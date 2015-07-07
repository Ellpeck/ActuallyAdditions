package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityPhantomPlacer;
import ellpeck.actuallyadditions.tile.TileEntityPhantomface;
import ellpeck.actuallyadditions.util.*;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class BlockPhantomface extends BlockContainerBase implements INameableItem{

    public static final int FACE = 0;
    public static final int PLACER = 1;
    public static final int BREAKER = 2;
    public static final int LIQUIFACE = 3;
    public static final int ENERGYFACE = 4;

    public int type;
    public int range;

    public BlockPhantomface(int type){
        super(Material.rock);
        this.type = type;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);

        if(type == FACE || type == LIQUIFACE || type == ENERGYFACE) this.range = ConfigIntValues.PHANTOMFACE_RANGE.getValue();
        else if(type == BREAKER || type == PLACER) this.range = ConfigIntValues.PHANTOM_PLACER_RANGE.getValue();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(this.type == PLACER || this.type == BREAKER) this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile != null){
                if(tile instanceof TileEntityPhantomface){
                    player.addChatComponentMessage(new ChatComponentText(""));
                    TileEntityPhantomface phantom = (TileEntityPhantomface)tile;
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomRange.desc") + ": " + phantom.range));
                    if(phantom.hasBoundTile()){
                        int distance = (int)Vec3.createVectorHelper(x, y, z).distanceTo(Vec3.createVectorHelper(phantom.boundPosition.getX(), phantom.boundPosition.getY(), phantom.boundPosition.getZ()));
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.blockInfo.desc", phantom.boundPosition.getBlock().getLocalizedName(), phantom.boundPosition.getX(), phantom.boundPosition.getY(), phantom.boundPosition.getZ(), distance)));

                        if(phantom.isBoundTileInRage()) player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedRange.desc")));
                        else player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedNoRange.desc")));
                    }
                    else player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.notConnected.desc")));
                    player.addChatComponentMessage(new ChatComponentText(""));
                }

                else if(tile instanceof TileEntityPhantomPlacer){
                    if(player.isSneaking()){
                        player.addChatComponentMessage(new ChatComponentText(""));
                        TileEntityPhantomPlacer phantom = (TileEntityPhantomPlacer)tile;
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomRange.desc") + ": " + phantom.range));
                        if(phantom.hasBoundPosition()){
                            int distance = (int)Vec3.createVectorHelper(x, y, z).distanceTo(Vec3.createVectorHelper(phantom.boundPosition.getX(), phantom.boundPosition.getY(), phantom.boundPosition.getZ()));
                            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.blockInfo.desc", phantom.boundPosition.getBlock().getLocalizedName(), phantom.boundPosition.getX(), phantom.boundPosition.getY(), phantom.boundPosition.getZ(), distance)));

                            if(phantom.isBoundPositionInRange()) player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedRange.desc")));
                            else player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedNoRange.desc")));
                        }
                        else player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.notConnected.desc")));
                        player.addChatComponentMessage(new ChatComponentText(""));
                    }
                    else player.openGui(ActuallyAdditions.instance, GuiHandler.PHANTOM_PLACER_ID, world, x, y, z);
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        switch(this.type){
            case PLACER:
                return new TileEntityPhantomPlacer();
            case BREAKER:
                return new TileEntityPhantomPlacer.TileEntityPhantomBreaker();
            case LIQUIFACE:
                return new TileEntityPhantomface.TileEntityPhantomLiquiface();
            case ENERGYFACE:
                return new TileEntityPhantomface.TileEntityPhantomEnergyface();
            default:
                return new TileEntityPhantomface.TileEntityPhantomItemface();
        }
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
        switch(this.type){
            case PLACER:
                return "blockPhantomPlacer";
            case BREAKER:
                return "blockPhantomBreaker";
            case LIQUIFACE:
                return "blockPhantomLiquiface";
            case ENERGYFACE:
                return "blockPhantomEnergyface";
            default:
                return "blockPhantomface";
        }
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
            if(KeyUtil.isShiftPressed()){
                if(((BlockPhantomface)this.theBlock).type == LIQUIFACE){
                    list.add(StringUtil.ORANGE+StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomLiquiface.desc.3"));
                    list.add(StringUtil.ORANGE+StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomLiquiface.desc.4"));
                }
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomRange.desc") + ": " + ((BlockPhantomface)theBlock).range);
            }
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}