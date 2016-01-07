/*
 * This file ("ItemChestToCrateUpgrade.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChestToCrateUpgrade extends ItemBase{

    public ItemChestToCrateUpgrade(String name){
        super(name);
    }

    @Override
    public boolean onItemUse(ItemStack heldStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
        if(player.isSneaking()){
            TileEntity tileHit = world.getTileEntity(x, y, z);
            if(world.getBlock(x, y, z) instanceof BlockChest && tileHit instanceof TileEntityChest){
                if(!world.isRemote){
                    TileEntityChest chest = (TileEntityChest)tileHit;

                    //Copy Slots
                    ItemStack[] stacks = new ItemStack[chest.getSizeInventory()];
                    for(int i = 0; i < stacks.length; i++){
                        ItemStack aStack = chest.getStackInSlot(i);
                        if(aStack != null){
                            stacks[i] = aStack.copy();
                            chest.setInventorySlotContents(i, null);
                        }
                    }

                    //Set New Block
                    world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z))+(world.getBlockMetadata(x, y, z) << 12));
                    world.setBlock(x, y, z, InitBlocks.blockGiantChest, 0, 2);

                    //Copy Items into new Chest
                    TileEntity newTileHit = world.getTileEntity(x, y, z);
                    if(newTileHit instanceof TileEntityGiantChest){
                        TileEntityGiantChest newChest = (TileEntityGiantChest)newTileHit;
                        for(int i = 0; i < stacks.length; i++){
                            if(stacks[i] != null){
                                if(newChest.getSizeInventory() > i){
                                    newChest.setInventorySlotContents(i, stacks[i].copy());
                                }
                            }
                        }
                    }

                    if(!player.capabilities.isCreativeMode){
                        heldStack.stackSize--;
                    }
                }
                return true;
            }
        }

        return super.onItemUse(heldStack, player, world, x, y, z, par7, par8, par9, par10);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

}
