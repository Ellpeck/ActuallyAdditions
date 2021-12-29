/*
 * This file ("BlockAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.Lang;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockAtomicReconstructor extends FullyDirectionalBlock.Container implements IHudDisplay {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public static final int NAME_FLAVOR_AMOUNTS_1 = 12;
    public static final int NAME_FLAVOR_AMOUNTS_2 = 14;

    public BlockAtomicReconstructor() {
        super(ActuallyBlocks.defaultPickProps(0, 10.0F, 80F));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.PASS;
        }
        if (!world.isClientSide) {
            TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor) world.getBlockEntity(pos);
            if (reconstructor != null) {
                if (StackUtil.isValid(heldItem)) {
                    Item item = heldItem.getItem();
                    if (item instanceof ILensItem && !StackUtil.isValid(reconstructor.inv.getStackInSlot(0))) {
                        ItemStack toPut = heldItem.copy();
                        toPut.setCount(1);
                        reconstructor.inv.setStackInSlot(0, toPut);
                        player.inventory.removeItem(player.inventory.selected, 1);
                    }
                    //Shush, don't tell anyone!
                    else if (CommonConfig.OTHER.ELEVEN.get() == 11 && item == Items.MUSIC_DISC_11) {
                        reconstructor.counter++;
                        reconstructor.setChanged();
                    }
                } else {
                    ItemStack slot = reconstructor.inv.getStackInSlot(0);
                    if (StackUtil.isValid(slot)) {
                        player.setItemInHand(hand, slot.copy());
                        reconstructor.inv.setStackInSlot(0, StackUtil.getEmpty());
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Nullable
    //@Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityAtomicReconstructor();
    }

    //    public BlockState getBaseConstructorState() {
    //        return this.stateContainer.getBaseState().with(FACING, Direction.NORTH);
    //    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (!(rayCast instanceof BlockRayTraceResult) || minecraft.level == null) {
            return;
        }

        TileEntity tile = minecraft.level.getBlockEntity(((BlockRayTraceResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityAtomicReconstructor) {
            ItemStack slot = ((TileEntityAtomicReconstructor) tile).inv.getStackInSlot(0);
            ITextComponent strg;
            if (!StackUtil.isValid(slot)) {
                strg = Lang.trans("info", "nolens");
            } else {
                strg = slot.getItem().getName(slot);

                AssetUtil.renderStackToGui(slot, resolution.getGuiScaledWidth() / 2 + 15, resolution.getGuiScaledHeight() / 2 - 19, 1F);
            }
            minecraft.font.drawShadow(matrices, strg.plainCopy().withStyle(TextFormatting.YELLOW).withStyle(TextFormatting.ITALIC).getString(), resolution.getGuiScaledWidth() / 2 + 35, resolution.getGuiScaledHeight() / 2f - 15, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }


    //    @Override
    //    public BlockState withRotation(BlockState state, Rotation rot) {
    //        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    //    }
    //
    //    @Override
    //    public BlockState withMirror(BlockState state, Mirror mirror) {
    //        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
    //    }

    //    public static class TheItemBlock extends ItemBlockBase {
    //
    //        private long lastSysTime;
    //        private int toPick1;
    //        private int toPick2;
    //
    //        public TheItemBlock(Block block) {
    //            super(block, new Item.Properties().group(ActuallyAdditions.GROUP).setNoRepair());
    //        }
    //
    //        @Override
    //        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    //            long sysTime = System.currentTimeMillis();
    //
    //            if (this.lastSysTime + 3000 < sysTime) {
    //                this.lastSysTime = sysTime;
    //                if (Minecraft.getInstance().world != null) {
    //                    this.toPick1 = Minecraft.getInstance().world.rand.nextInt(NAME_FLAVOR_AMOUNTS_1) + 1;
    //                    this.toPick2 = Minecraft.getInstance().world.rand.nextInt(NAME_FLAVOR_AMOUNTS_2) + 1;
    //                }
    //            }
    //
    //            String base = "tile." + ActuallyAdditions.MODID + "." + ((BlockAtomicReconstructor) this.block).getBaseName() + ".info.";
    //            tooltip.add(StringUtil.localize(base + "1." + this.toPick1) + " " + StringUtil.localize(base + "2." + this.toPick2));
    //        }
    //    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
        TileEntity t = world.getBlockEntity(pos);
        int i = 0;
        if (t instanceof TileEntityAtomicReconstructor) {
            i = ((TileEntityAtomicReconstructor) t).getEnergy();
        }
        return MathHelper.clamp(i / 20000, 0, 15);
    }
}
