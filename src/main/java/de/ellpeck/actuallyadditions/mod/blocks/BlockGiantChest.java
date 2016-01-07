/*
 * This file ("BlockGiantChest.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class BlockGiantChest extends BlockContainerBase{

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;

    public BlockGiantChest(String name){
        super(Material.wood, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(0.5F);
        this.setResistance(15.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityGiantChest();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return side == 1 ? this.topIcon : (side == 0 ? this.bottomIcon : this.blockIcon);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityGiantChest chest = (TileEntityGiantChest)world.getTileEntity(x, y, z);
            if(chest != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.GIANT_CHEST.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Top");
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Bottom");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        if(stack.getTagCompound() != null){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntityGiantChest){
                NBTTagList list = stack.getTagCompound().getTagList("Items", 10);
                ItemStack[] slots = ((TileEntityGiantChest)tile).slots;

                for(int i = 0; i < list.tagCount(); i++){
                    slots[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
                }
            }
        }

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
        ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);

        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntityGiantChest){
            ItemStack[] slots = ((TileEntityGiantChest)tile).slots;
            int place = ItemUtil.getPlaceAt(slots, new ItemStack(InitItems.itemCrateKeeper), false);
            if(place >= 0){
                NBTTagList list = new NBTTagList();
                for(int i = 0; i < slots.length; i++){
                    //Destroy the keeper
                    if(i != place){
                        if(slots[i] != null){
                            list.appendTag(slots[i].writeToNBT(new NBTTagCompound()));
                        }
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
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntityGiantChest){
            if(!ItemUtil.contains(((TileEntityGiantChest)tile).slots, new ItemStack(InitItems.itemCrateKeeper), false)){
                this.dropInventory(world, x, y, z);
            }
        }

        super.breakBlock(world, x, y, z, block, par6);
    }
}
