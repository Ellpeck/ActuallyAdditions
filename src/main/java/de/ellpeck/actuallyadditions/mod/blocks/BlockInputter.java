/*
 * This file ("BlockInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputterAdvanced;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockInputter extends BlockContainerBase {

    public static final int NAME_FLAVOR_AMOUNTS = 15;

    public final boolean isAdvanced;

    public BlockInputter(boolean isAdvanced) {
        super(ActuallyBlocks.defaultPickProps(0).tickRandomly());
        this.isAdvanced = isAdvanced;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return this.isAdvanced
            ? new TileEntityInputterAdvanced()
            : new TileEntityInputter();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.isAdvanced) {
            return this.openGui(world, player, pos, TileEntityInputterAdvanced.class);
        }

        return this.openGui(world, player, pos, TileEntityInputter.class);
    }

    // TODO: [port] ADD BACK

    //    public static class TheItemBlock extends ItemBlockBase {
    //
    //        private final Random rand = new Random();
    //        private long lastSysTime;
    //        private int toPick;
    //
    //        public TheItemBlock(Block block) {
    //            super(block);
    //            this.setHasSubtypes(false);
    //            this.setMaxDamage(0);
    //        }
    //
    //        @Override
    //        public String getTranslationKey(ItemStack stack) {
    //            return this.getTranslationKey();
    //        }
    //
    //        @Override
    //        public int getMetadata(int damage) {
    //            return damage;
    //        }
    //
    //        @Override
    //        public String getItemStackDisplayName(ItemStack stack) {
    //            if (Util.isClient()) {
    //                long sysTime = System.currentTimeMillis();
    //
    //                if (this.lastSysTime + 5000 < sysTime) {
    //                    this.lastSysTime = sysTime;
    //                    this.toPick = this.rand.nextInt(NAME_FLAVOR_AMOUNTS) + 1;
    //                }
    //
    //                return StringUtil.localize(this.getTranslationKey() + ".name") + " (" + StringUtil.localize("tile." + ActuallyAdditions.MODID + ".block_inputter.add." + this.toPick + ".name") + ")";
    //            } else {
    //                return super.getItemStackDisplayName(stack);
    //            }
    //        }
    //    }
}
