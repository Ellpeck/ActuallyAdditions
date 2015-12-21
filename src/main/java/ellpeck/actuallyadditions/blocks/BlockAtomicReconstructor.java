/*
 * This file ("BlockAtomicReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import ellpeck.actuallyadditions.items.lens.ItemLens;
import ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAtomicReconstructor extends BlockContainerBase implements IHudDisplay{

    @SideOnly(Side.CLIENT)
    private IIcon frontIcon;
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;

    public BlockAtomicReconstructor(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(10F);
        this.setResistance(80F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);

        super.onBlockPlacedBy(world, x, y, z, player, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side != meta && (side == 0 || side == 1)){
            return this.topIcon;
        }
        if(side == meta){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(side == 0 || side == 1){
            return this.topIcon;
        }
        if(side == 3){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(this.tryToggleRedstone(world, x, y, z, player)){
            return true;
        }
        if(!world.isRemote){
            TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor)world.getTileEntity(x, y, z);
            if(reconstructor != null){
                ItemStack heldItem = player.getCurrentEquippedItem();
                if(heldItem != null){
                    if(heldItem.getItem() instanceof ItemLens && reconstructor.getStackInSlot(0) == null){
                        ItemStack toPut = heldItem.copy();
                        toPut.stackSize = 1;
                        reconstructor.setInventorySlotContents(0, toPut);
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    }
                }
                else{
                    if(reconstructor.getStackInSlot(0) != null){
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, reconstructor.getStackInSlot(0).copy());
                        reconstructor.setInventorySlotContents(0, null);
                    }
                }
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Front");
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Top");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityAtomicReconstructor();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, MovingObjectPosition posHit, Profiler profiler, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.blockX, posHit.blockY, posHit.blockZ);
        if(tile instanceof TileEntityAtomicReconstructor){
            ItemStack slot = ((TileEntityAtomicReconstructor)tile).getStackInSlot(0);
            String strg;
            if(slot == null){
                strg = StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".noLens");
            }
            else{
                strg = slot.getItem().getItemStackDisplayName(slot);
            }
            minecraft.fontRenderer.drawStringWithShadow(EnumChatFormatting.GOLD+""+EnumChatFormatting.ITALIC+strg, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+25, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}