/*
 * This file ("BlockInputter.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import ellpeck.actuallyadditions.tile.TileEntityInventoryBase;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockInputter extends BlockContainerBase implements INameableItem{

    public static final int NAME_FLAVOUR_AMOUNTS = 15;

    public boolean isAdvanced;

    public BlockInputter(boolean isAdvanced){
        super(Material.rock);
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
    public IIcon getIcon(int side, int meta){
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityInputter inputter = (TileEntityInputter)world.getTileEntity(x, y, z);
            if (inputter != null) player.openGui(ActuallyAdditions.instance, this.isAdvanced ? GuiHandler.GuiTypes.INPUTTER_ADVANCED.ordinal() : GuiHandler.GuiTypes.INPUTTER.ordinal(), world, x, y, z);
            return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tileEntity = (TileEntityInventoryBase)tile;
                Random rand = new Random();

                ItemStack itemStack = tileEntity.getStackInSlot(0);
                if(itemStack != null && itemStack.stackSize > 0){
                    float dX = rand.nextFloat()*0.8F+0.1F;
                    float dY = rand.nextFloat()*0.8F+0.1F;
                    float dZ = rand.nextFloat()*0.8F+0.1F;
                    EntityItem entityItem = new EntityItem(world, x+dX, y+dY, z+dZ, itemStack.copy());
                    if(itemStack.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian()*factor;
                    entityItem.motionY = rand.nextGaussian()*factor+0.2F;
                    entityItem.motionZ = rand.nextGaussian()*factor;
                    world.spawnEntityInWorld(entityItem);
                    itemStack.stackSize = 0;
                }
            }
        }
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return this.isAdvanced ? "blockInputterAdvanced" : "blockInputter";
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        private long lastSysTime;
        private int toPick;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return ((BlockInputter)theBlock).isAdvanced ? EnumRarity.epic : EnumRarity.rare;
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack){
            Random rand = new Random();
            long sysTime = System.currentTimeMillis();

            if(this.lastSysTime+5000 < sysTime){
                this.lastSysTime = sysTime;
                this.toPick = rand.nextInt(NAME_FLAVOUR_AMOUNTS)+1;
            }

            return StringUtil.localize(this.getUnlocalizedName()+".name") + " (" + StringUtil.localize("tile."+ModUtil.MOD_ID_LOWER+".blockInputter.add."+this.toPick+".name") + ")";
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
