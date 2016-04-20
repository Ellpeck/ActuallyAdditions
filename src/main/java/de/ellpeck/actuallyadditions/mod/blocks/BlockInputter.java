/*
 * This file ("BlockInputter.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockInputter extends BlockContainerBase{

    public static final int NAME_FLAVOR_AMOUNTS = 15;

    public boolean isAdvanced;

    public BlockInputter(boolean isAdvanced, String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
        this.isAdvanced = isAdvanced;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isAdvanced ? new TileEntityInputter.TileEntityInputterAdvanced() : new TileEntityInputter();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityInputter inputter = (TileEntityInputter)world.getTileEntity(pos);
            if(inputter != null){
                player.openGui(ActuallyAdditions.instance, this.isAdvanced ? GuiHandler.GuiTypes.INPUTTER_ADVANCED.ordinal() : GuiHandler.GuiTypes.INPUTTER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        if(!world.isRemote){
            TileEntity aTile = world.getTileEntity(pos);
            if(aTile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tile = (TileEntityInventoryBase)aTile;
                this.dropSlotFromInventory(0, tile, world, pos);
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    public static class TheItemBlock extends ItemBlockBase{

        private long lastSysTime;
        private int toPick;

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
        public String getItemStackDisplayName(ItemStack stack){
            long sysTime = System.currentTimeMillis();

            if(this.lastSysTime+5000 < sysTime){
                this.lastSysTime = sysTime;
                this.toPick = Util.RANDOM.nextInt(NAME_FLAVOR_AMOUNTS)+1;
            }

            return StringUtil.localize(this.getUnlocalizedName()+".name")+" ("+StringUtil.localize("tile."+ModUtil.MOD_ID+".blockInputter.add."+this.toPick+".name")+")";
        }
    }
}
