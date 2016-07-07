/*
 * This file ("BlockGiantChest.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChestLarge;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChestMedium;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockGiantChest extends BlockContainerBase{

    public final int type;

    public BlockGiantChest(String name, int type){
        super(Material.WOOD, name);
        this.type = type;

        this.setHarvestLevel("axe", 0);
        this.setHardness(0.5F);
        this.setResistance(15.0F);
        this.setSoundType(SoundType.WOOD);

    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        switch(this.type){
            case 1:
                return new TileEntityGiantChestMedium();
            case 2:
                return new TileEntityGiantChestLarge();
            default:
                return new TileEntityGiantChest();
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityGiantChest chest = (TileEntityGiantChest)world.getTileEntity(pos);
            if(chest != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.GIANT_CHEST.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        if(stack.getTagCompound() != null){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityGiantChest){
                NBTTagList list = stack.getTagCompound().getTagList("Items", 10);
                ItemStack[] slots = ((TileEntityGiantChest)tile).slots;

                for(int i = 0; i < list.tagCount(); i++){
                    NBTTagCompound compound = list.getCompoundTagAt(i);
                    if(compound != null && compound.hasKey("id")){
                        slots[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
                    }
                }
            }
        }

        super.onBlockPlacedBy(world, pos, state, entity, stack);
    }


    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        ArrayList<ItemStack> drops = super.getDrops(world, pos, state, fortune);

        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityGiantChest){
            ItemStack[] slots = ((TileEntityGiantChest)tile).slots;
            int place = ItemUtil.getPlaceAt(slots, new ItemStack(InitItems.itemCrateKeeper), false);
            if(place >= 0){
                NBTTagList list = new NBTTagList();
                for(int i = 0; i < slots.length; i++){
                    //Destroy the keeper
                    if(i != place){
                        NBTTagCompound compound = new NBTTagCompound();
                        if(slots[i] != null){
                            slots[i].writeToNBT(compound);
                        }
                        list.appendTag(compound);
                    }
                }

                if(list.tagCount() > 0){
                    ItemStack stackInQuestion = drops.get(0);
                    if(stackInQuestion != null){
                        if(stackInQuestion.getTagCompound() == null){
                            stackInQuestion.setTagCompound(new NBTTagCompound());
                        }
                        stackInQuestion.getTagCompound().setTag("Items", list);
                    }
                }
            }
        }

        return drops;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityGiantChest){
            if(!ItemUtil.contains(((TileEntityGiantChest)tile).slots, new ItemStack(InitItems.itemCrateKeeper), false)){
                this.dropInventory(world, pos);
            }
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
            int type = this.block instanceof BlockGiantChest ? ((BlockGiantChest)this.block).type : -1;
            if(type == 2){
                tooltip.add(TextFormatting.ITALIC+"Supersolid");
            }
            else if(type == 0){
                tooltip.add(TextFormatting.ITALIC+"'Small'");
            }
        }
    }
}
