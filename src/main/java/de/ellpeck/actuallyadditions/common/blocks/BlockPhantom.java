package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.*;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPhantom extends BlockContainerBase implements IHudDisplay {

    public final Type type;

    public BlockPhantom(Type type) {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(4.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));

        this.type = type;
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return this.type == Type.REDSTONEFACE;
    }

    @Override
    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (this.type == Type.REDSTONEFACE) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityPhantomRedstoneface) { return ((TileEntityPhantomRedstoneface) tile).providesWeak[side.ordinal()]; }
        }
        return 0;
    }

    @Override
    public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (this.type == Type.REDSTONEFACE) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityPhantomRedstoneface) { return ((TileEntityPhantomRedstoneface) tile).providesStrong[side.ordinal()]; }
        }
        return 0;
    }

    @Override
    public boolean shouldDropInventory(World world, BlockPos pos) {
        return this.type == Type.PLACER || this.type == Type.BREAKER;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        switch (this.type) {
        case PLACER:
            return new TileEntityPhantomPlacer();
        case BREAKER:
            return new TileEntityPhantomBreaker();
        case LIQUIFACE:
            return new TileEntityPhantomLiquiface();
        case ENERGYFACE:
            return new TileEntityPhantomEnergyface();
        case REDSTONEFACE:
            return new TileEntityPhantomRedstoneface();
        default:
            return new TileEntityPhantomItemface();
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (this.tryToggleRedstone(world, pos, player)) { return true; }
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof IPhantomTile && ((IPhantomTile) tile).getGuiID() != -1) {
                player.openGui(ActuallyAdditions.INSTANCE, ((IPhantomTile) tile).getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution) {
        TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
        if (tile != null) {
            if (tile instanceof IPhantomTile) {
                IPhantomTile phantom = (IPhantomTile) tile;
                minecraft.fontRenderer.drawStringWithShadow(TextFormatting.GOLD + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".blockPhantomRange.desc") + ": " + phantom.getRange(), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 - 40, StringUtil.DECIMAL_COLOR_WHITE);
                if (phantom.hasBoundPosition()) {
                    int distance = MathHelper.ceil(new Vec3d(posHit.getBlockPos()).distanceTo(new Vec3d(phantom.getBoundPosition())));
                    IBlockState state = minecraft.world.getBlockState(phantom.getBoundPosition());
                    Block block = state.getBlock();
                    Item item = Item.getItemFromBlock(block);
                    String name = item == null ? "Something Unrecognizable" : item.getItemStackDisplayName(new ItemStack(block, 1, block.getMetaFromState(state)));
                    StringUtil.drawSplitString(minecraft.fontRenderer, StringUtil.localizeFormatted("tooltip." + ActuallyAdditions.MODID + ".phantom.blockInfo.desc", name, phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ(), distance), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 - 30, 200, StringUtil.DECIMAL_COLOR_WHITE, true);

                    if (phantom.isBoundThingInRange()) {
                        StringUtil.drawSplitString(minecraft.fontRenderer, TextFormatting.DARK_GREEN + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".phantom.connectedRange.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 25, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
                    } else {
                        StringUtil.drawSplitString(minecraft.fontRenderer, TextFormatting.DARK_RED + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".phantom.connectedNoRange.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 25, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                } else {
                    minecraft.fontRenderer.drawStringWithShadow(TextFormatting.RED + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".phantom.notConnected.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 25, StringUtil.DECIMAL_COLOR_WHITE);
                }
            }
        }
    }

    public enum Type {
        FACE,
        PLACER,
        BREAKER,
        LIQUIFACE,
        ENERGYFACE,
        REDSTONEFACE
    }
}