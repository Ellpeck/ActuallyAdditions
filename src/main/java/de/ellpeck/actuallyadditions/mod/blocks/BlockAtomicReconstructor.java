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

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public class BlockAtomicReconstructor extends FullyDirectionalBlock.Container implements IHudDisplay {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public static final int NAME_FLAVOR_AMOUNTS_1 = 12;
    public static final int NAME_FLAVOR_AMOUNTS_2 = 14;

    public BlockAtomicReconstructor() {
        super(ActuallyBlocks.defaultPickProps(10.0F, 80F));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (this.tryToggleRedstone(world, pos, player)) {
            return InteractionResult.SUCCESS;
        }
        if (!world.isClientSide) {
            TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor) world.getBlockEntity(pos);
            if (reconstructor != null) {
                if (!heldItem.isEmpty()) {
                    Item item = heldItem.getItem();
                    if (item instanceof ILensItem && reconstructor.inv.getStackInSlot(0).isEmpty()) {
                        ItemStack toPut = heldItem.copy();
                        toPut.setCount(1);
                        reconstructor.inv.setStackInSlot(0, toPut);
                        if (!player.isCreative()) {
                            heldItem.shrink(1);
                        }
                        return InteractionResult.CONSUME;
                    }
                } else {
                    ItemStack slot = reconstructor.inv.getStackInSlot(0);
                    if (!slot.isEmpty() && hand == InteractionHand.MAIN_HAND) {
                        player.setItemInHand(hand, slot.copy());
                        reconstructor.inv.setStackInSlot(0, ItemStack.EMPTY);
                        return InteractionResult.CONSUME;
                    }
                }
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.CONSUME;
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return VoxelShapes.AtomicReconstructorShapes.SHAPE_U;
            case DOWN:
                return VoxelShapes.AtomicReconstructorShapes.SHAPE_D;
            case EAST:
                return VoxelShapes.AtomicReconstructorShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.AtomicReconstructorShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.AtomicReconstructorShapes.SHAPE_W;
            default:
                return VoxelShapes.AtomicReconstructorShapes.SHAPE_N;
        }
    }*/

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityAtomicReconstructor(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityAtomicReconstructor::clientTick : TileEntityAtomicReconstructor::serverTick;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult) || minecraft.level == null) {
            return;
        }

        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityAtomicReconstructor) {
            ItemStack slot = ((TileEntityAtomicReconstructor) tile).inv.getStackInSlot(0);
            Component lens_name;
            if (slot.isEmpty()) {
                lens_name = Component.translatable("info.actuallyadditions.nolens");
            } else {
                lens_name = slot.getItem().getName(slot);

                AssetUtil.renderStackToGui(slot, resolution.getGuiScaledWidth() / 2 + 15, resolution.getGuiScaledHeight() / 2 - 19, 1F);
            }
            guiGraphics.drawString(minecraft.font, lens_name.plainCopy().withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC).getString(), (int) (resolution.getGuiScaledWidth() / 2.0f + 35), (int) (resolution.getGuiScaledHeight() / 2.0f - 15), 0xFFFFFF);
        }
    }

    public static class TheItemBlock extends AABlockItem {

        private long lastSysTime;
        private int toPick1;
        private int toPick2;
        private final Block block;

        public TheItemBlock(Block blockIn) {
            super(blockIn, ActuallyBlocks.defaultBlockItemProperties);
            block = blockIn;
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public void appendHoverText(@Nonnull ItemStack pStack, @Nullable Level pLevel, @Nonnull List<Component> pTooltip, @Nonnull TooltipFlag pFlag) {
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

            long sysTime = System.currentTimeMillis();

            if (this.lastSysTime + 3000 < sysTime) {
                this.lastSysTime = sysTime;
                if (Minecraft.getInstance().level != null) {
                    RandomSource random = Minecraft.getInstance().level.random;
                    this.toPick1 = random.nextInt(NAME_FLAVOR_AMOUNTS_1) + 1;
                    this.toPick2 = random.nextInt(NAME_FLAVOR_AMOUNTS_2) + 1;
                }
            }

            String base = block.getDescriptionId() + ".info.";
            pTooltip.add(Component.translatable(base + "1." + this.toPick1).append(" ").append(Component.translatable(base + "2." + this.toPick2)).withStyle(s -> s.withColor(ChatFormatting.GRAY)));

            if (pStack.hasTag() && pStack.getTag().contains("BlockEntityTag")) {
                CompoundTag BET = pStack.getTag().getCompound("BlockEntityTag");
                int energy = 0;
                if (BET.contains("Energy")) {
                    energy = BET.getInt("Energy");
                }
                NumberFormat format = NumberFormat.getInstance();
                pTooltip.add(Component.translatable("misc.actuallyadditions.power_single", format.format(energy)).withStyle(ChatFormatting.GRAY));

                if (BET.contains("IsPulseMode")) {
                    pTooltip.add(Component.translatable("info.actuallyadditions.redstoneMode").append(": ")
                            .append(Component.translatable(BET.getBoolean("IsPulseMode")?"info.actuallyadditions.redstoneMode.pulse":"info.actuallyadditions.redstoneMode.deactivation").withStyle($ -> $.withColor(ChatFormatting.RED))));
                }
            }
        }

        @Override
        protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState) {
            boolean ret = super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);



            return ret;
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity t = world.getBlockEntity(pos);
        int i = 0;
        if (t instanceof TileEntityAtomicReconstructor) {
            i = ((TileEntityAtomicReconstructor) t).getEnergy();
        }
        return Mth.clamp(i / 20000, 0, 15);
    }
}
