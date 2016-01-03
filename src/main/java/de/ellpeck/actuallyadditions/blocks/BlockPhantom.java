/*
 * This file ("BlockPhantom.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.tile.*;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockPhantom extends BlockContainerBase implements IHudDisplay{

    public Type type;
    public int range;

    @SideOnly(Side.CLIENT)
    private IIcon iconSeasonal;

    public BlockPhantom(Type type, String name){
        super(Material.rock, name);
        this.type = type;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);

        if(type == Type.FACE || type == Type.LIQUIFACE || type == Type.ENERGYFACE){
            this.range = TileEntityPhantomface.RANGE;
        }
        else if(type == Type.BREAKER || type == Type.PLACER){
            this.range = TileEntityPhantomPlacer.RANGE;
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(this.type == Type.PLACER || this.type == Type.BREAKER){
            this.dropInventory(world, x, y, z);
        }
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        switch(this.type){
            case PLACER:
                return new TileEntityPhantomPlacer();
            case BREAKER:
                return new TileEntityPhantomPlacer.TileEntityPhantomBreaker();
            case LIQUIFACE:
                return new TileEntityPhantomLiquiface();
            case ENERGYFACE:
                return new TileEntityPhantomEnergyface();
            default:
                return new TileEntityPhantomItemface();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return (this.type == Type.FACE && ClientProxy.pumpkinBlurPumpkinBlur && side > 1) ? this.iconSeasonal : this.blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ){
        if(this.tryToggleRedstone(world, x, y, z, player)){
            return true;
        }
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof IPhantomTile && ((IPhantomTile)tile).getGuiID() != -1){
                player.openGui(ActuallyAdditions.instance, ((IPhantomTile)tile).getGuiID(), world, x, y, z);
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());

        this.iconSeasonal = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":blockPhantomfacePumpkin");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, MovingObjectPosition posHit, Profiler profiler, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.blockX, posHit.blockY, posHit.blockZ);
        if(tile != null){
            if(tile instanceof IPhantomTile){
                IPhantomTile phantom = (IPhantomTile)tile;
                minecraft.fontRenderer.drawStringWithShadow(EnumChatFormatting.GOLD+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".blockPhantomRange.desc")+": "+phantom.getRange(), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2-40, StringUtil.DECIMAL_COLOR_WHITE);
                if(phantom.hasBoundPosition()){
                    int distance = (int)Vec3.createVectorHelper(posHit.blockX, posHit.blockY, posHit.blockZ).distanceTo(Vec3.createVectorHelper(phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ()));
                    Item item = phantom.getBoundPosition().getItemBlock(minecraft.theWorld);
                    String name = item == null ? "Absolutely Nothing" : item.getItemStackDisplayName(new ItemStack(phantom.getBoundPosition().getBlock(minecraft.theWorld), 1, phantom.getBoundPosition().getMetadata(minecraft.theWorld)));
                    StringUtil.drawSplitString(minecraft.fontRenderer, StringUtil.localizeFormatted("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.blockInfo.desc", name, phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ(), distance), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2-30, 200, StringUtil.DECIMAL_COLOR_WHITE, true);

                    if(phantom.isBoundThingInRange()){
                        StringUtil.drawSplitString(minecraft.fontRenderer, EnumChatFormatting.DARK_GREEN+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedRange.desc"), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+25, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                    else{
                        StringUtil.drawSplitString(minecraft.fontRenderer, EnumChatFormatting.DARK_RED+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.connectedNoRange.desc"), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+25, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                }
                else{
                    minecraft.fontRenderer.drawStringWithShadow(EnumChatFormatting.RED+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".phantom.notConnected.desc"), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+25, StringUtil.DECIMAL_COLOR_WHITE);
                }
            }
        }
    }

    public enum Type{
        FACE,
        PLACER,
        BREAKER,
        LIQUIFACE,
        ENERGYFACE
    }
}