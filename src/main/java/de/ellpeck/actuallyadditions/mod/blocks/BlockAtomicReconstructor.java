package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockItemBase;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.List;

public class BlockAtomicReconstructor extends BlockContainerBase implements IHudDisplay {

    public static final int NAME_FLAVOR_AMOUNTS_1 = 12;
    public static final int NAME_FLAVOR_AMOUNTS_2 = 14;

    public BlockAtomicReconstructor() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(10f, 80f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        ItemStack heldItem = player.getHeldItem(hand);
        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.SUCCESS;
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
                    else if (ConfigIntValues.ELEVEN.getValue() == 11 && item == Items.MUSIC_DISC_11) {
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
        return ActionResultType.SUCCESS;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new TileEntityAtomicReconstructor();
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(Minecraft minecraft, PlayerEntity player, ItemStack stack, BlockRayTraceResult posHit, MainWindow window) {
        TileEntity tile = minecraft.world.getTileEntity(posHit.getPos());
        if (tile instanceof TileEntityAtomicReconstructor) {
            ItemStack slot = ((TileEntityAtomicReconstructor) tile).inv.getStackInSlot(0);
            ITextComponent displayString;
            if (!StackUtil.isValid(slot)) {
                displayString = new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".noLens");
            } else {
                displayString = slot.getItem().getDisplayName(slot);

                AssetUtil.renderStackToGui(slot, window.getScaledWidth() / 2 + 15, window.getScaledHeight() / 2 - 19, 1F);
            }
            minecraft.fontRenderer.drawStringWithShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + displayString.getFormattedText(), window.getScaledWidth() / 2 + 35, window.getScaledHeight() / 2 - 15, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    @Override
    protected BlockItemBase getItemBlock() {
        return new BlockItem();
    }
    
    @Override
    public Rarity getRarity(){
        return Rarity.EPIC;
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.FACING);
    }
    
    @Override
    public BlockState rotate(BlockState state, Rotation rot){
        return state.with(BlockStateProperties.FACING, rot.rotate(state.get(BlockStateProperties.FACING)));
    }
    
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn){
        return this.rotate(state, mirrorIn.toRotation(state.get(BlockStateProperties.FACING)));
    }
    
    @Override
    public boolean hasComparatorInputOverride(BlockState state){
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
    
    public class BlockItem extends BlockItemBase {
        private long lastSysTime;
        private int toPick1;
    
        private int toPick2;
    
        public BlockItem() {
            super(BlockAtomicReconstructor.this, new Properties());
        }
    
        @Override
        public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag){
            long sysTime = System.currentTimeMillis();
    
            if (this.lastSysTime + 3000 < sysTime) {
                this.lastSysTime = sysTime;
                if (world != null) {
                    this.toPick1 = world.rand.nextInt(NAME_FLAVOR_AMOUNTS_1) + 1;
                    this.toPick2 = world.rand.nextInt(NAME_FLAVOR_AMOUNTS_2) + 1;
                }
            }
    
            String base = String.format("tile.%s.%s.info", ActuallyAdditions.MODID, BlockAtomicReconstructor.this.getRegistryName().getPath());
            tooltip.add(new TranslationTextComponent(String.format("%s1.%s", base, this.toPick1)).appendSibling(new TranslationTextComponent(String.format("%s2.%s", base, this.toPick2))));
        }

    }
}