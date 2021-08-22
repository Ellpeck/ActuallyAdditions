/*
 * This file ("BlockPhantom.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;


public class BlockPhantom extends BlockContainerBase implements IHudDisplay {

    public final Type type;

    public BlockPhantom(Type type) {
        super(ActuallyBlocks.defaultPickProps(0, 4.5F, 10.0F));
        this.type = type;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return this.type == Type.REDSTONEFACE;
    }

    @Override
    public int getSignal(BlockState blockState, IBlockReader world, BlockPos pos, Direction side) {
        if (this.type == Type.REDSTONEFACE) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityPhantomRedstoneface) {
                return ((TileEntityPhantomRedstoneface) tile).providesWeak[side.ordinal()];
            }
        }
        return 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, IBlockReader world, BlockPos pos, Direction side) {
        if (this.type == Type.REDSTONEFACE) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityPhantomRedstoneface) {
                return ((TileEntityPhantomRedstoneface) tile).providesStrong[side.ordinal()];
            }
        }
        return 0;
    }

    @Override
    public boolean shouldDropInventory(World world, BlockPos pos) {
        return this.type == Type.PLACER || this.type == Type.BREAKER;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
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

    // TODO: [port] validate this works
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.PASS;
        }

        if (!world.isClientSide) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof IPhantomTile && ((IPhantomTile) tile).getGuiID() != -1) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
            }
        }

        return ActionResultType.PASS;
    }

    // TODO: [port] fix all of this, it's a mess
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (!(rayCast instanceof BlockRayTraceResult)) {
            return;
        }
        BlockPos pos = ((BlockRayTraceResult) rayCast).getBlockPos();
        TileEntity tile = minecraft.level.getBlockEntity(pos);
        if (tile != null) {
            if (tile instanceof IPhantomTile) {
                IPhantomTile phantom = (IPhantomTile) tile;
                minecraft.font.drawShadow(matrices, TextFormatting.GOLD + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".blockPhantomRange.desc") + ": " + phantom.getRange(), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 - 40, StringUtil.DECIMAL_COLOR_WHITE);
                if (phantom.hasBoundPosition()) {
                    int distance = MathHelper.ceil(new Vector3d(pos.getX(), pos.getY(), pos.getZ()).distanceTo(new Vector3d(phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ())));
                    BlockState state = minecraft.level.getBlockState(phantom.getBoundPosition());
                    Block block = state.getBlock();
                    Item item = Item.byBlock(block);
                    String name = item.getName(new ItemStack(block)).getString();
                    StringUtil.drawSplitString(minecraft.font, StringUtil.localizeFormatted("tooltip." + ActuallyAdditions.MODID + ".phantom.blockInfo.desc", name, phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ(), distance), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 - 30, 200, StringUtil.DECIMAL_COLOR_WHITE, true);

                    if (phantom.isBoundThingInRange()) {
                        StringUtil.drawSplitString(minecraft.font, TextFormatting.DARK_GREEN + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".phantom.connectedRange.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
                    } else {
                        StringUtil.drawSplitString(minecraft.font, TextFormatting.DARK_RED + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".phantom.connectedNoRange.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                } else {
                    minecraft.font.drawShadow(matrices, TextFormatting.RED + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".phantom.notConnected.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, StringUtil.DECIMAL_COLOR_WHITE);
                }
            }
        }
    }

    public enum Type {
        ITEMFACE,
        PLACER,
        BREAKER,
        LIQUIFACE,
        ENERGYFACE,
        REDSTONEFACE
    }
}
