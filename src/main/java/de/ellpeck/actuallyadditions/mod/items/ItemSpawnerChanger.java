// TODO: [port] REMOVE THIS CLASS, NO longer needed
///*
// * This file ("ItemSpawnerChanger.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.items;
//
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
//import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.block.BlockState;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityList;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.tileentity.MobSpawnerBaseLogic;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityMobSpawner;
//import net.minecraft.util.Direction;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//
//import java.util.List;
//
//public class ItemSpawnerChanger extends ItemBase {
//
//    public ItemSpawnerChanger() {
//        super();
//        this.setMaxStackSize(1);
//    }
//
//    @Override
//    public EnumActionResult onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
//        if (!world.isRemote) {
//            ItemStack stack = player.getHeldItemMainhand();
//            if (player.canPlayerEdit(pos.offset(facing), facing, stack)) {
//                TileEntity tile = world.getTileEntity(pos);
//                if (tile instanceof TileEntityMobSpawner) {
//                    String entity = this.getStoredEntity(stack);
//                    if (entity != null) {
//                        MobSpawnerBaseLogic logic = ((TileEntityMobSpawner) tile).getSpawnerBaseLogic();
//
//                        //This is a hacky way to remove the spawn potentials that make the spawner reset from time to time
//                        //Don't judge, there isn't a method for it and it's better than Reflection hackiness
//                        CompoundNBT compound = new CompoundNBT();
//                        logic.writeToNBT(compound);
//                        compound.removeTag("SpawnPotentials");
//                        compound.removeTag("SpawnData");
//                        logic.readFromNBT(compound);
//
//                        logic.setEntityId(new ResourceLocation(entity));
//
//                        tile.markDirty();
//
//                        BlockState state = world.getBlockState(pos);
//                        world.notifyBlockUpdate(pos, state, state, 3);
//
//                        ItemPhantomConnector.clearStorage(stack, "Entity");
//
//                        if (!player.isCreative()) {
//                            player.setHeldItem(hand, StackUtil.shrink(stack, 1));
//                        }
//
//                        return EnumActionResult.SUCCESS;
//                    }
//                }
//            }
//        }
//        return EnumActionResult.FAIL;
//    }
//
//    @Override
//    public boolean itemInteractionForEntity(ItemStack aStack, PlayerEntity player, EntityLivingBase entity, Hand hand) {
//        if (!player.world.isRemote) {
//            ItemStack stack = player.getHeldItemMainhand();
//            if (this.getStoredEntity(stack) == null) {
//                if (this.storeClickedEntity(stack, entity)) {
//                    entity.setDead();
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//
//    private boolean storeClickedEntity(ItemStack stack, EntityLivingBase entity) {
//        if (!stack.hasTagCompound()) {
//            stack.setTagCompound(new CompoundNBT());
//        }
//
//        if (!(entity instanceof PlayerEntity) && entity.isNonBoss()) {
//            ResourceLocation entityLoc = EntityList.getKey(entity.getClass());
//            if (entityLoc != null) {
//                String entityName = entityLoc.toString();
//                if (entityName != null && !entityName.isEmpty()) {
//                    for (String name : ConfigStringListValues.SPAWNER_CHANGER_BLACKLIST.getValue()) {
//                        if (entityName.equals(name)) {
//                            return false;
//                        }
//                    }
//
//                    stack.getTagCompound().setString("Entity", entityName);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private String getStoredEntity(ItemStack stack) {
//        if (stack.hasTagCompound()) {
//            String entity = stack.getTagCompound().getString("Entity");
//            if (entity != null && !entity.isEmpty()) {
//                return entity;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void addInformation(ItemStack stack, World playerIn, List<String> list, ITooltipFlag advanced) {
//        String entity = this.getStoredEntity(stack);
//        if (entity != null) {
//            list.add("Entity: " + entity);
//            list.add(TextFormatting.ITALIC + StringUtil.localize("tooltip.actuallyadditions.clearStorage.desc"));
//        }
//    }
//}
