/*
 * This file ("BlockXPSolidifier.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.tile.TileEntityXPSolidifier;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockXPSolidifier extends BlockContainerBase implements IActAddItemOrBlock{

    private IIcon topIcon;
    private IIcon frontIcon;

    public BlockXPSolidifier(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(2.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityXPSolidifier();
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side == 1 || side == 0){
            return this.topIcon;
        }
        if(side == meta+2){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 1 || side == 0){
            return this.topIcon;
        }
        if(side == 3){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityXPSolidifier solidifier = (TileEntityXPSolidifier)world.getTileEntity(x, y, z);
            if(solidifier != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.XP_SOLIDIFIER.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        if(rotation == 1){
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if(rotation == 2){
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }
        if(rotation == 3){
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Top");
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Front");
    }

    @Override
    public String getName(){
        return "blockXPSolidifier";
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntityXPSolidifier){
            TileEntityXPSolidifier solidifier = (TileEntityXPSolidifier)tile;
            if(solidifier.amount > 0){
                int stacks = solidifier.amount/64;
                int rest = solidifier.amount%64;
                for(int i = 0; i < stacks; i++){
                    this.spawnItem(world, x, y, z, new ItemStack(InitItems.itemSpecialDrop, 64, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()));
                }
                this.spawnItem(world, x, y, z, new ItemStack(InitItems.itemSpecialDrop, rest, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()));
                solidifier.amount = 0;
            }
        }

        super.breakBlock(world, x, y, z, block, par6);
    }

    private void spawnItem(World world, int x, int y, int z, ItemStack stack){
        float dX = world.rand.nextFloat()*0.8F+0.1F;
        float dY = world.rand.nextFloat()*0.8F+0.1F;
        float dZ = world.rand.nextFloat()*0.8F+0.1F;
        EntityItem entityItem = new EntityItem(world, x+dX, y+dY, z+dZ, stack);
        float factor = 0.05F;
        entityItem.motionX = world.rand.nextGaussian()*factor;
        entityItem.motionY = world.rand.nextGaussian()*factor+0.2F;
        entityItem.motionZ = world.rand.nextGaussian()*factor;
        world.spawnEntityInWorld(entityItem);
    }

    public static class TheItemBlock extends ItemBlock{

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
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.epic;
        }
    }
}
