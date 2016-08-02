/*
 * This file ("BlockAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockAtomicReconstructor extends BlockContainerBase implements IHudDisplay{

    public static final int NAME_FLAVOR_AMOUNTS_1 = 12;
    public static final int NAME_FLAVOR_AMOUNTS_2 = 14;
    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 5);

    public BlockAtomicReconstructor(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(10F);
        this.setResistance(80F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing par6, float par7, float par8, float par9){
        if(this.tryToggleRedstone(world, pos, player)){
            return true;
        }
        if(!world.isRemote){
            TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor)world.getTileEntity(pos);
            if(reconstructor != null){
                if(heldItem != null){
                    Item item = heldItem.getItem();
                    if(item instanceof ILensItem && reconstructor.getStackInSlot(0) == null){
                        ItemStack toPut = heldItem.copy();
                        toPut.stackSize = 1;
                        reconstructor.setInventorySlotContents(0, toPut);
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    }
                    //Shush, don't tell anyone!
                    else if(item == Items.RECORD_11){
                        reconstructor.counter++;
                        reconstructor.markDirty();
                        player.addStat(TheAchievements.OBSCURED.chieve);
                    }
                }
                else{
                    ItemStack slot = reconstructor.getStackInSlot(0);
                    if(slot != null){
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, slot.copy());
                        reconstructor.setInventorySlotContents(0, null);
                    }
                }
            }
        }
        return true;
    }


    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityAtomicReconstructor();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.getBlockPos());
        if(tile instanceof TileEntityAtomicReconstructor){
            ItemStack slot = ((TileEntityAtomicReconstructor)tile).getStackInSlot(0);
            String strg;
            if(slot == null){
                strg = StringUtil.localize("info."+ModUtil.MOD_ID+".noLens");
            }
            else{
                strg = slot.getItem().getItemStackDisplayName(slot);

                AssetUtil.renderStackToGui(slot, resolution.getScaledWidth()/2+15, resolution.getScaledHeight()/2-19, 1F);
            }
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg, resolution.getScaledWidth()/2+35, resolution.getScaledHeight()/2-15, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.getFacingFromEntity(pos, player).ordinal();
        world.setBlockState(pos, this.getStateFromMeta(rotation), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    public static class TheItemBlock extends ItemBlockBase{

        private long lastSysTime;
        private int toPick1;
        private int toPick2;

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }


        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool){
            long sysTime = System.currentTimeMillis();

            if(this.lastSysTime+3000 < sysTime){
                this.lastSysTime = sysTime;
                this.toPick1 = Util.RANDOM.nextInt(NAME_FLAVOR_AMOUNTS_1)+1;
                this.toPick2 = Util.RANDOM.nextInt(NAME_FLAVOR_AMOUNTS_2)+1;
            }

            String base = "tile."+ModUtil.MOD_ID+"."+((BlockAtomicReconstructor)this.block).getBaseName()+".info.";
            list.add(StringUtil.localize(base+"1."+this.toPick1)+" "+StringUtil.localize(base+"2."+this.toPick2));
        }
    }
}