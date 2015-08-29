/*
 * This file ("BlockPhantom.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.tile.IPhantomTile;
import ellpeck.actuallyadditions.tile.TileEntityPhantomPlacer;
import ellpeck.actuallyadditions.tile.TileEntityPhantomface;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockPhantom extends BlockContainerBase implements INameableItem{

    public enum Type{
        FACE,
        PLACER,
        BREAKER,
        LIQUIFACE,
        ENERGYFACE
    }

    public Type type;
    public int range;

    public BlockPhantom(Type type){
        super(Material.rock);
        this.type = type;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);

        if(type == Type.FACE || type == Type.LIQUIFACE || type == Type.ENERGYFACE){
            this.range = ConfigIntValues.PHANTOMFACE_RANGE.getValue();
        }
        else if(type == Type.BREAKER || type == Type.PLACER){
            this.range = ConfigIntValues.PHANTOM_PLACER_RANGE.getValue();
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(this.type == Type.PLACER || this.type == Type.BREAKER) this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile != null){
                if(tile instanceof IPhantomTile){
                    IPhantomTile phantom = (IPhantomTile)tile;
                    if(player.isSneaking() || phantom.getGuiID() == -1){
                        player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomRange.desc") + ": " + phantom.getRange()));
                        if(phantom.hasBoundPosition()){
                            int distance = (int)Vec3.createVectorHelper(x, y, z).distanceTo(Vec3.createVectorHelper(phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ()));
                            Item item = phantom.getBoundPosition().getItemBlock();
                            String name = item == null ? "Absolutely Nothing" : item.getItemStackDisplayName(new ItemStack(phantom.getBoundPosition().getBlock(), 1, phantom.getBoundPosition().getMetadata()));
                            player.addChatComponentMessage(new ChatComponentText(StringUtil.localizeFormatted("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.blockInfo.desc", name, phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ(), distance)));

                            if(phantom.isBoundThingInRange()) player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedRange.desc")));
                            else player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedNoRange.desc")));
                        }
                        else player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.notConnected.desc")));
                    }
                    else player.openGui(ActuallyAdditions.instance, phantom.getGuiID(), world, x, y, z);
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
        public int getMetadata(int damage){
            return damage;
        }
    }
}