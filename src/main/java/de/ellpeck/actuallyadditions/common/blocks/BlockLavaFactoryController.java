package de.ellpeck.actuallyadditions.blocks;

import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.tile.TileEntityLavaFactoryController;
import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLavaFactoryController extends BlockContainerBase implements IHudDisplay {

    public BlockLavaFactoryController() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(4.5f, 20.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityLavaFactoryController();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution) {
        TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController) minecraft.world.getTileEntity(posHit.getBlockPos());
        if (factory != null) {
            int state = factory.isMultiblock();
            if (state == TileEntityLavaFactoryController.NOT_MULTI) {
                StringUtil.drawSplitString(minecraft.fontRenderer, StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".factory.notPart.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 5, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
            } else if (state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA) {
                StringUtil.drawSplitString(minecraft.fontRenderer, StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".factory.works.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 5, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
            }
        }
    }
}
