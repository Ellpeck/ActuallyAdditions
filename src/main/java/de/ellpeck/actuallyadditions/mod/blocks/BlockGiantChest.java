// todo: delete. I'm not allowed to port this one haha
//package de.ellpeck.actuallyadditions.mod.blocks;
//
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
//import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
//import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
//import de.ellpeck.actuallyadditions.mod.items.InitItems;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChestLarge;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChestMedium;
//import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
//import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.block.Block;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.NonNullList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.common.ToolType;
//import net.minecraftforge.items.IItemHandlerModifiable;
//
//import java.util.List;
//
//public class BlockGiantChest extends BlockContainerBase {
//
//    public final int type;
//
//    public BlockGiantChest(int type) {
//        super(Block.Properties.create(Material.WOOD)
//                .hardnessAndResistance(0.5f, 15.0f)
//                .harvestTool(ToolType.AXE)
//                .sound(SoundType.STONE));
//
//        this.type = type;
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(World world, int par2) {
//        switch (this.type) {
//        case 1:
//            return new TileEntityGiantChestMedium();
//        case 2:
//            return new TileEntityGiantChestLarge();
//        default:
//            return new TileEntityGiantChest();
//        }
//    }
//
//    @Override
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
//        if (!world.isRemote) {
//            TileEntityGiantChest chest = (TileEntityGiantChest) world.getTileEntity(pos);
//            if (chest != null) {
//                chest.fillWithLoot(player);
//                player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.GIANT_CHEST.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
//            }
//            return true;
//        }
//        return true;
//    }
//
//    @Override
//    public EnumRarity getRarity(ItemStack stack) {
//        return EnumRarity.EPIC;
//    }
//
//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
//        if (stack.getTagCompound() != null) {
//            TileEntity tile = world.getTileEntity(pos);
//            if (tile instanceof TileEntityGiantChest) {
//                NBTTagList list = stack.getTagCompound().getTagList("Items", 10);
//                IItemHandlerModifiable inv = ((TileEntityGiantChest) tile).inv;
//
//                for (int i = 0; i < list.tagCount(); i++) {
//                    NBTTagCompound compound = list.getCompoundTagAt(i);
//                    if (compound != null && compound.hasKey("id")) {
//                        inv.setStackInSlot(i, new ItemStack(list.getCompoundTagAt(i)));
//                    }
//                }
//            }
//        }
//
//        super.onBlockPlacedBy(world, pos, state, entity, stack);
//    }
//
//    @Override
//    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
//        super.getDrops(drops, world, pos, state, fortune);
//        TileEntity tile = world.getTileEntity(pos);
//        if (tile instanceof TileEntityGiantChest) {
//            ItemStackHandlerAA slots = ((TileEntityGiantChest) tile).inv;
//            int place = ItemUtil.getPlaceAt(slots.getItems(), new ItemStack(InitItems.itemCrateKeeper), false);
//            if (place >= 0) {
//                NBTTagList list = new NBTTagList();
//                for (int i = 0; i < slots.getSlots(); i++) {
//                    //Destroy the keeper
//                    if (i != place) {
//                        NBTTagCompound compound = new NBTTagCompound();
//                        if (StackUtil.isValid(slots.getStackInSlot(i))) {
//                            slots.getStackInSlot(i).writeToNBT(compound);
//                        }
//                        list.appendTag(compound);
//                    }
//                }
//
//                if (list.tagCount() > 0) {
//                    ItemStack stackInQuestion = drops.get(0);
//                    if (StackUtil.isValid(stackInQuestion)) {
//                        if (stackInQuestion.getTagCompound() == null) {
//                            stackInQuestion.setTagCompound(new NBTTagCompound());
//                        }
//                        stackInQuestion.getTagCompound().setTag("Items", list);
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean shouldDropInventory(World world, BlockPos pos) {
//        TileEntity tile = world.getTileEntity(pos);
//        return !(tile instanceof TileEntityGiantChest) || !ItemUtil.contains(((TileEntityGiantChest) tile).inv.getItems(), new ItemStack(InitItems.itemCrateKeeper), false);
//    }
//
//    @Override
//    protected ItemBlockBase getItemBlock() {
//        return new TheItemBlock(this);
//    }
//
//    public static class TheItemBlock extends ItemBlockBase {
//
//        public TheItemBlock(Block block) {
//            super(block);
//        }
//
//        @Override
//        public void addInformation(ItemStack stack, World playerIn, List<String> tooltip, ITooltipFlag advanced) {
//            int type = this.block instanceof BlockGiantChest ? ((BlockGiantChest) this.block).type : -1;
//            if (type == 2) {
//                tooltip.add(TextFormatting.ITALIC + StringUtil.localize("container." + ActuallyAdditions.MODID + ".giantChestLarge.desc"));
//            } else if (type == 0) {
//                tooltip.add(TextFormatting.ITALIC + StringUtil.localize("container." + ActuallyAdditions.MODID + ".giantChest.desc"));
//            }
//        }
//
//        @Override
//        public NBTTagCompound getNBTShareTag(ItemStack stack) {
//            return null;
//        }
//    }
//}
