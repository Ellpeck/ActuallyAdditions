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

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.List;

public class BlockAtomicReconstructor extends BlockContainerBase implements IHudDisplay {

    public static final int NAME_FLAVOR_AMOUNTS_1 = 12;
    public static final int NAME_FLAVOR_AMOUNTS_2 = 14;

    public BlockAtomicReconstructor() {
        super(Material.ROCK, this.name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(10F);
        this.setResistance(80F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction par6, float par7, float par8, float par9) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (this.tryToggleRedstone(world, pos, player)) {
            return true;
        }
        if (!world.isRemote) {
            TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor) world.getTileEntity(pos);
            if (reconstructor != null) {
                if (StackUtil.isValid(heldItem)) {
                    Item item = heldItem.getItem();
                    if (item instanceof ILensItem && !StackUtil.isValid(reconstructor.inv.getStackInSlot(0))) {
                        ItemStack toPut = heldItem.copy();
                        toPut.setCount(1);
                        reconstructor.inv.setStackInSlot(0, toPut);
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    }
                    //Shush, don't tell anyone!
                    else if (ConfigIntValues.ELEVEN.getValue() == 11 && item == Items.RECORD_11) {
                        reconstructor.counter++;
                        reconstructor.markDirty();
                    }
                } else {
                    ItemStack slot = reconstructor.inv.getStackInSlot(0);
                    if (StackUtil.isValid(slot)) {
                        player.setHeldItem(hand, slot.copy());
                        reconstructor.inv.setStackInSlot(0, StackUtil.getEmpty());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityAtomicReconstructor();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution) {
        TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
        if (tile instanceof TileEntityAtomicReconstructor) {
            ItemStack slot = ((TileEntityAtomicReconstructor) tile).inv.getStackInSlot(0);
            String strg;
            if (!StackUtil.isValid(slot)) {
                strg = StringUtil.localize("info." + ActuallyAdditions.MODID + ".noLens");
            } else {
                strg = slot.getItem().getItemStackDisplayName(slot);

                AssetUtil.renderStackToGui(slot, resolution.getScaledWidth() / 2 + 15, resolution.getScaledHeight() / 2 - 19, 1F);
            }
            minecraft.fontRenderer.drawStringWithShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg, resolution.getScaledWidth() / 2 + 35, resolution.getScaledHeight() / 2 - 15, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    @Override
    protected ItemBlockBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase player, ItemStack stack) {
        int rotation = Direction.getDirectionFromEntityLiving(pos, player).ordinal();
        world.setBlockState(pos, this.getStateFromMeta(rotation), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockDirectional.FACING, Direction.byIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirror) {
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
    }

    public static class TheItemBlock extends ItemBlockBase {

        private long lastSysTime;
        private int toPick1;
        private int toPick2;

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
        public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
            long sysTime = System.currentTimeMillis();

            if (this.lastSysTime + 3000 < sysTime) {
                this.lastSysTime = sysTime;
                if (world != null) {
                    this.toPick1 = world.rand.nextInt(NAME_FLAVOR_AMOUNTS_1) + 1;
                    this.toPick2 = world.rand.nextInt(NAME_FLAVOR_AMOUNTS_2) + 1;
                }
            }

            String base = "tile." + ActuallyAdditions.MODID + "." + ((BlockAtomicReconstructor) this.block).getBaseName() + ".info.";
            tooltip.add(StringUtil.localize(base + "1." + this.toPick1) + " " + StringUtil.localize(base + "2." + this.toPick2));
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
        TileEntity t = world.getTileEntity(pos);
        int i = 0;
        if (t instanceof TileEntityAtomicReconstructor) {
            i = ((TileEntityAtomicReconstructor) t).getEnergy();
        }
        return MathHelper.clamp(i / 20000, 0, 15);
    }
}
