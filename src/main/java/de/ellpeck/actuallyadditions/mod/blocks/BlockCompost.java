/*
 * This file ("BlockCompost.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.items.ItemFertilizer;
import de.ellpeck.actuallyadditions.mod.items.ItemMisc;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCompost;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockCompost extends BlockContainerBase implements IHudDisplay{

    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0, 0.0625, 1-0.0625, 11*0.0625, 1-0.0625);

    public BlockCompost(String name){
        super(Material.WOOD, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);

        //this.setBlockBoundsForItemRender();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return AABB;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn){
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stackPlayer, EnumFacing f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntityCompost tile = (TileEntityCompost)world.getTileEntity(pos);
            //Add items to be composted
            if(stackPlayer != null && stackPlayer.getItem() instanceof ItemMisc && stackPlayer.getItemDamage() == TheMiscItems.MASHED_FOOD.ordinal() && (tile.slots[0] == null || (!(tile.slots[0].getItem() instanceof ItemFertilizer) && tile.slots[0].stackSize < TileEntityCompost.AMOUNT))){
                if(tile.slots[0] == null){
                    tile.slots[0] = new ItemStack(stackPlayer.getItem(), 1, TheMiscItems.MASHED_FOOD.ordinal());
                }
                else{
                    tile.slots[0].stackSize++;
                }
                if(!player.capabilities.isCreativeMode){
                    player.inventory.getCurrentItem().stackSize--;
                }
                tile.markDirty();
            }

            //Add Fertilizer to player's inventory
            else if(tile.slots[0] != null && (stackPlayer == null || (stackPlayer.getItem() instanceof ItemFertilizer && stackPlayer.stackSize <= stackPlayer.getMaxStackSize()-tile.slots[0].stackSize)) && tile.slots[0].getItem() instanceof ItemFertilizer){
                if(stackPlayer == null){
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, tile.slots[0].copy());
                }
                else{
                    stackPlayer.stackSize += tile.slots[0].stackSize;
                }
                tile.slots[0] = null;
                tile.markDirty();
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCompost();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, Profiler profiler, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.getBlockPos());
        if(tile instanceof TileEntityCompost){
            ItemStack slot = ((TileEntityCompost)tile).getStackInSlot(0);
            String strg;
            if(slot == null){
                strg = "Empty";
            }
            else{
                strg = slot.getItem().getItemStackDisplayName(slot);

                AssetUtil.renderStackToGui(slot, resolution.getScaledWidth()/2+15, resolution.getScaledHeight()/2-29, 1F);
            }
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg, resolution.getScaledWidth()/2+35, resolution.getScaledHeight()/2-25, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}
