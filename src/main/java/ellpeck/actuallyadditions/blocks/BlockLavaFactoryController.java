/*
 * This file ("BlockLavaFactoryController.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import ellpeck.actuallyadditions.tile.TileEntityLavaFactoryController;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockLavaFactoryController extends BlockContainerBase implements IHudDisplay{

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;

    public BlockLavaFactoryController(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(20.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityLavaFactoryController();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return side == 1 ? this.topIcon : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Top");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, MovingObjectPosition posHit, Profiler profiler, ScaledResolution resolution){
        TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController)minecraft.theWorld.getTileEntity(posHit.blockX, posHit.blockY, posHit.blockZ);
        if(factory != null){
            int state = factory.isMultiblock();
            if(state == TileEntityLavaFactoryController.NOT_MULTI){
                StringUtil.drawSplitStringWithShadow(minecraft.fontRenderer, StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".factory.notPart.desc"), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+5, 200, StringUtil.DECIMAL_COLOR_WHITE);
            }
            else if(state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA){
                StringUtil.drawSplitStringWithShadow(minecraft.fontRenderer, StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".factory.works.desc"), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+5, 200, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
    }
}
