package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockItemBase;
import de.ellpeck.actuallyadditions.common.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.common.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.common.tile.TileEntityInputterAdvanced;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import de.ellpeck.actuallyadditions.common.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class BlockInputter extends BlockContainerBase {

    public static final int NAME_FLAVOR_AMOUNTS = 15;

    public final boolean isAdvanced;

    public BlockInputter(boolean isAdvanced) {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE)
                .tickRandomly());

        this.isAdvanced = isAdvanced;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return this.isAdvanced ? new TileEntityInputterAdvanced() : new TileEntityInputter();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            TileEntityInputter inputter = (TileEntityInputter) world.getTileEntity(pos);
            if (inputter != null) {
                player.openGui(ActuallyAdditions.INSTANCE, this.isAdvanced ? GuiHandler.GuiTypes.INPUTTER_ADVANCED.ordinal() : GuiHandler.GuiTypes.INPUTTER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    protected BlockItemBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    public static class TheItemBlock extends BlockItemBase {

        private final Random rand = new Random();
        private long lastSysTime;
        private int toPick;

        public TheItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return this.getTranslationKey();
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack) {
            if (Util.isClient()) {
                long sysTime = System.currentTimeMillis();

                if (this.lastSysTime + 5000 < sysTime) {
                    this.lastSysTime = sysTime;
                    this.toPick = this.rand.nextInt(NAME_FLAVOR_AMOUNTS) + 1;
                }

                return StringUtil.localize(this.getTranslationKey() + ".name") + " (" + StringUtil.localize("tile." + ActuallyAdditions.MODID + ".block_inputter.add." + this.toPick + ".name") + ")";
            } else return super.getItemStackDisplayName(stack);
        }
    }
}
