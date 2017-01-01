/*
 * This file ("BlockAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        ItemStack heldItem = player.getHeldItem(hand);
        if(this.tryToggleRedstone(world, pos, player)){
            return true;
        }
        if(!world.isRemote){
            TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor)world.getTileEntity(pos);
            if(reconstructor != null){
                if(StackUtil.isValid(heldItem)){
                    Item item = heldItem.getItem();
                    if(item instanceof ILensItem && !StackUtil.isValid(reconstructor.slots.getStackInSlot(0))){
                        ItemStack toPut = heldItem.copy();
                        toPut = StackUtil.setStackSize(toPut, 1);
                        reconstructor.slots.setStackInSlot(0, toPut);
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    }
                    //Shush, don't tell anyone!
                    else if(item == Items.RECORD_11){
                        reconstructor.counter++;
                        reconstructor.markDirty();
                    }
                }
                else{
                    ItemStack slot = reconstructor.slots.getStackInSlot(0);
                    if(StackUtil.isValid(slot)){
                        player.setHeldItem(hand, slot.copy());
                        reconstructor.slots.setStackInSlot(0, StackUtil.getNull());
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
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
        if(tile instanceof TileEntityAtomicReconstructor){
            ItemStack slot = ((TileEntityAtomicReconstructor)tile).slots.getStackInSlot(0);
            String strg;
            if(!StackUtil.isValid(slot)){
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
        int rotation = EnumFacing.getDirectionFromEntityLiving(pos, player).ordinal();
        world.setBlockState(pos, this.getStateFromMeta(rotation), 2);

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror){
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
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
                this.toPick1 = player.world.rand.nextInt(NAME_FLAVOR_AMOUNTS_1)+1;
                this.toPick2 = player.world.rand.nextInt(NAME_FLAVOR_AMOUNTS_2)+1;
            }

            String base = "tile."+ModUtil.MOD_ID+"."+((BlockAtomicReconstructor)this.block).getBaseName()+".info.";
            list.add(StringUtil.localize(base+"1."+this.toPick1)+" "+StringUtil.localize(base+"2."+this.toPick2));
        }
    }
}