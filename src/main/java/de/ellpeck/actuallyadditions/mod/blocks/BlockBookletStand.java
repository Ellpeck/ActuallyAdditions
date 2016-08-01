/*
 * This file ("BlockBookletStand.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBookletStand extends BlockContainerBase implements IHudDisplay{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);

    private static final AxisAlignedBB AABB_1 = new AxisAlignedBB(0, 3*0.0625, 0, 1, 14*0.0625, 0.0625);
    private static final AxisAlignedBB AABB_2 = new AxisAlignedBB(0, 3*0.0625, 0, 0.0625, 14*0.0625, 1);
    private static final AxisAlignedBB AABB_3 = new AxisAlignedBB(1-0.0625, 3*0.0625, 0, 1, 14*0.0625, 1);
    private static final AxisAlignedBB AABB_4 = new AxisAlignedBB(1, 3*0.0625, 1-0.0625, 0, 14*0.0625, 1);

    public BlockBookletStand(String name){
        super(Material.WOOD, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setResistance(4.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        int meta = this.getMetaFromState(state);
        switch(meta){
            case 0:
                return AABB_4;
            case 1:
                return AABB_1;
            case 2:
                return AABB_3;
            case 3:
                return AABB_2;
        }
        return super.getBoundingBox(state, source, pos);
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.BOOK_STAND.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            world.setBlockState(pos, this.getStateFromMeta(0), 2);
        }
        if(rotation == 1){
            world.setBlockState(pos, this.getStateFromMeta(3), 2);
        }
        if(rotation == 2){
            world.setBlockState(pos, this.getStateFromMeta(1), 2);
        }
        if(rotation == 3){
            world.setBlockState(pos, this.getStateFromMeta(2), 2);
        }

        TileEntityBookletStand tile = (TileEntityBookletStand)world.getTileEntity(pos);
        if(tile != null){
            if(tile.assignedPlayer == null){
                tile.assignedPlayer = player.getName();
                tile.markDirty();
                tile.sendUpdate();
            }
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityBookletStand();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.getBlockPos());
        if(tile instanceof TileEntityBookletStand){
            EntrySet set = ((TileEntityBookletStand)tile).assignedEntry;

            String strg1;
            String strg2;
            if(set.getCurrentEntry() == null){
                strg1 = "No entry saved! Save one if";
                strg2 = "you are the player who placed it!";
            }
            else if(set.getCurrentChapter() == null){
                strg1 = set.getCurrentEntry().getLocalizedName();
                strg2 = "Page "+set.getPageInIndex();
            }
            else{
                strg1 = set.getCurrentChapter().getLocalizedName();
                strg2 = "Page "+set.getCurrentPage().getID();

                AssetUtil.renderStackToGui(set.getCurrentChapter().getDisplayItemStack() != null ? set.getCurrentChapter().getDisplayItemStack() : new ItemStack(InitItems.itemBooklet), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+10, 1F);
            }
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg1, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+8, StringUtil.DECIMAL_COLOR_WHITE);
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg2, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+18, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }
}
