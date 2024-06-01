// TODO: [port][note] no longer used
///*
// * This file ("BlockGiantChest.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
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
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.ListNBT;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.Hand;
//import net.minecraft.util.NonNullList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.items.IItemHandlerModifiable;
//
//import java.util.List;
//
//public class BlockGiantChest extends BlockContainerBase {
//
//    public final int type;
//
//    public BlockGiantChest(String name, int type) {
//        super(Material.WOOD, name);
//        this.type = type;
//
//        this.setHarvestLevel("axe", 0);
//        this.setHardness(0.5F);
//        this.setResistance(15.0F);
//        this.setSoundType(SoundType.WOOD);
//
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(IBlockReader worldIn) {
//        switch (this.type) {
//            case 1:
//                return new TileEntityGiantChestMedium();
//            case 2:
//                return new TileEntityGiantChestLarge();
//            default:
//                return new TileEntityGiantChest();
//        }
//    }
//
//    @Override
//    public boolean isFullCube(BlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube(BlockState state) {
//        return false;
//    }
//
//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
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
//    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase entity, ItemStack stack) {
//        if (stack.getTagCompound() != null) {
//            TileEntity tile = world.getTileEntity(pos);
//            if (tile instanceof TileEntityGiantChest) {
//                ListNBT list = stack.getTagCompound().getList("Items", 10);
//                IItemHandlerModifiable inv = ((TileEntityGiantChest) tile).inv;
//
//                for (int i = 0; i < list.size(); i++) {
//                    CompoundNBT compound = list.getCompound(i);
//                    if (compound != null && compound.hasKey("id")) {
//                        inv.setStackInSlot(i, new ItemStack(list.getCompound(i)));
//                    }
//                }
//            }
//        }
//
//        super.onBlockPlacedBy(world, pos, state, entity, stack);
//    }
//
//    @Override
//    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
//        super.getDrops(drops, world, pos, state, fortune);
//        TileEntity tile = world.getTileEntity(pos);
//        if (tile instanceof TileEntityGiantChest) {
//            ItemStackHandlerAA slots = ((TileEntityGiantChest) tile).inv;
//            int place = ItemUtil.getPlaceAt(slots.getItems(), new ItemStack(InitItems.itemCrateKeeper), false);
//            if (place >= 0) {
//                ListNBT list = new ListNBT();
//                for (int i = 0; i < slots.getSlots(); i++) {
//                    //Destroy the keeper
//                    if (i != place) {
//                        CompoundNBT compound = new CompoundNBT();
//                        if (StackUtil.isValid(slots.getStackInSlot(i))) {
//                            slots.getStackInSlot(i).writeToNBT(compound);
//                        }
//                        list.appendTag(compound);
//                    }
//                }
//
//                if (list.size() > 0) {
//                    ItemStack stackInQuestion = drops.get(0);
//                    if (StackUtil.isValid(stackInQuestion)) {
//                        if (stackInQuestion.getTagCompound() == null) {
//                            stackInQuestion.setTagCompound(new CompoundNBT());
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
//            int type = this.block instanceof BlockGiantChest
//                ? ((BlockGiantChest) this.block).type
//                : -1;
//            if (type == 2) {
//                tooltip.add(TextFormatting.ITALIC + StringUtil.localize("container.actuallyadditions.giantChestLarge.desc"));
//            } else if (type == 0) {
//                tooltip.add(TextFormatting.ITALIC + StringUtil.localize("container.actuallyadditions.giantChest.desc"));
//            }
//        }
//
//        @Override
//        public CompoundNBT getNBTShareTag(ItemStack stack) {
//            return null;
//        }
//    }
//}
