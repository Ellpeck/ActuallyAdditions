/*
 * This file ("BlockInputter.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockInputter extends BlockContainerBase{

    public static final int NAME_FLAVOR_AMOUNTS = 15;

    public boolean isAdvanced;

    public BlockInputter(boolean isAdvanced, String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
        this.isAdvanced = isAdvanced;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isAdvanced ? new TileEntityInputter.TileEntityInputterAdvanced() : new TileEntityInputter();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return this.blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityInputter inputter = (TileEntityInputter)world.getTileEntity(x, y, z);
            if(inputter != null){
                player.openGui(ActuallyAdditions.instance, this.isAdvanced ? GuiHandler.GuiTypes.INPUTTER_ADVANCED.ordinal() : GuiHandler.GuiTypes.INPUTTER.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(!world.isRemote){
            TileEntity aTile = world.getTileEntity(x, y, z);
            if(aTile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tile = (TileEntityInventoryBase)aTile;
                this.dropSlotFromInventory(0, tile, world, x, y, z);
            }
        }
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
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

            return StringUtil.localize(this.getUnlocalizedName()+".name")+" ("+StringUtil.localize("tile."+ModUtil.MOD_ID_LOWER+".blockInputter.add."+this.toPick+".name")+")";
        }
    }
}
