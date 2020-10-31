// todo: remove
//package de.ellpeck.actuallyadditions.common.blocks;
//
//import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
//import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
//import de.ellpeck.actuallyadditions.common.tile.TileEntityCompost;
//import de.ellpeck.actuallyadditions.common.util.AssetUtil;
//import de.ellpeck.actuallyadditions.common.util.ItemUtil;
//import de.ellpeck.actuallyadditions.common.util.StackUtil;
//import de.ellpeck.actuallyadditions.common.util.StringUtil;
//import net.minecraft.block.Block;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.BlockStateContainer;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.ScaledResolution;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.state.IProperty;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockRenderLayer;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.common.ToolType;
//import net.minecraftforge.common.property.ExtendedBlockState;
//import net.minecraftforge.common.property.IExtendedBlockState;
//import net.minecraftforge.common.property.IUnlistedProperty;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import org.apache.commons.lang3.tuple.Pair;
//
//import java.util.List;
//
//public class BlockCompost extends BlockContainerBase implements IHudDisplay {
//
//    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
//    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
//    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
//    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
//    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
//    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0, 0.0625, 1 - 0.0625, 11 * 0.0625, 1 - 0.0625);
//
//    public BlockCompost() {
//        super(Block.Properties.create(Material.WOOD)
//                .hardnessAndResistance(0.5f, 5.0f)
//                .harvestTool(ToolType.AXE)
//                .sound(SoundType.WOOD));
//    }
//
//    @Override
//    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
//        return AABB;
//    }
//
//    @Override
//    @Deprecated
//    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean someBool) {
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
//    }
//
//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing f6, float f7, float f8, float f9) {
//        ItemStack stackPlayer = player.getHeldItem(hand);
//        if (!world.isRemote) {
//            TileEntity tile = world.getTileEntity(pos);
//            if (tile instanceof TileEntityCompost) {
//                TileEntityCompost compost = (TileEntityCompost) tile;
//                ItemStack slot = compost.inv.getStackInSlot(0);
//                CompostRecipe recipeIn = TileEntityCompost.getRecipeForInput(slot);
//                if (!StackUtil.isValid(slot) || recipeIn != null) {
//                    if (StackUtil.isValid(stackPlayer)) {
//                        CompostRecipe recipeHand = TileEntityCompost.getRecipeForInput(stackPlayer);
//                        if (recipeHand != null && (recipeIn == null || recipeIn == recipeHand)) {
//                            int maxAdd = stackPlayer.getCount();
//
//                            if (!StackUtil.isValid(slot)) {
//                                ItemStack stackToAdd = stackPlayer.copy();
//                                stackToAdd.setCount(maxAdd);
//                                compost.inv.setStackInSlot(0, stackToAdd);
//                                player.inventory.decrStackSize(player.inventory.currentItem, maxAdd);
//                                return true;
//                            } else {
//                                ItemStack stackIn = slot.copy();
//                                if (stackIn.getCount() < slot.getMaxStackSize()) {
//                                    int sizeAdded = Math.min(maxAdd, slot.getMaxStackSize() - stackIn.getCount());
//                                    stackIn.grow(sizeAdded);
//                                    compost.inv.setStackInSlot(0, stackIn);
//                                    player.inventory.decrStackSize(player.inventory.currentItem, sizeAdded);
//                                    return true;
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    if (!StackUtil.isValid(stackPlayer)) {
//                        player.setHeldItem(hand, slot.copy());
//                        compost.inv.setStackInSlot(0, StackUtil.getEmpty());
//                        return true;
//                    } else if (ItemUtil.canBeStacked(stackPlayer, slot)) {
//                        int addedStackSize = Math.min(slot.getCount(), stackPlayer.getMaxStackSize() - stackPlayer.getCount());
//                        ItemStack stackToAdd = stackPlayer.copy();
//                        stackToAdd.grow(addedStackSize);
//                        player.setHeldItem(hand, stackToAdd);
//                        compost.inv.getStackInSlot(0).shrink(addedStackSize);
//                        return true;
//
//                    }
//                }
//                tile.markDirty();
//                world.notifyBlockUpdate(pos, this.getDefaultState(), this.getDefaultState(), 3);
//            }
//        } else {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(World world, int meta) {
//        return new TileEntityCompost();
//    }
//
//    @Override
//    public EnumRarity getRarity(ItemStack stack) {
//        return EnumRarity.UNCOMMON;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution) {
//        TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
//        if (tile instanceof TileEntityCompost) {
//            ItemStack slot = ((TileEntityCompost) tile).inv.getStackInSlot(0);
//            String strg;
//            if (!StackUtil.isValid(slot)) {
//                strg = "Empty";
//            } else {
//                strg = slot.getDisplayName();
//
//                AssetUtil.renderStackToGui(slot, resolution.getScaledWidth() / 2 + 15, resolution.getScaledHeight() / 2 - 29, 1F);
//            }
//            minecraft.fontRenderer.drawStringWithShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg, resolution.getScaledWidth() / 2 + 35, resolution.getScaledHeight() / 2 - 25, StringUtil.DECIMAL_COLOR_WHITE);
//        }
//    }
//
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] { COMPOST_PROP });
//    }
//
//    @Override
//    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
//        TileEntity te = world.getTileEntity(pos);
//        if (te instanceof TileEntityCompost && state instanceof IExtendedBlockState) {
//            TileEntityCompost compost = (TileEntityCompost) te;
//            return ((IExtendedBlockState) state).withProperty(COMPOST_PROP, Pair.of(compost.getCurrentDisplay(), compost.getHeight()));
//        }
//        return state;
//    }
//
//    public BlockRenderLayer getBlockLayer() {
//        return BlockRenderLayer.CUTOUT;
//    }
//
//    public static CompostProperty COMPOST_PROP = new CompostProperty();
//
//    @SuppressWarnings("rawtypes")
//    private static class CompostProperty implements IProperty<Pair> {
//
//        @Override
//        public String getName() {
//            return "compost";
//        }
//
//        @Override
//        public boolean isValid(Pair value) {
//            return true;
//        }
//
//        @Override
//        public Class<Pair> getType() {
//            return Pair.class;
//        }
//
//        @Override
//        public String valueToString(Pair value) {
//            return "";
//        }
//
//    }
//}
