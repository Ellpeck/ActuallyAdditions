/*
 * This file ("BlockLeafGenerator.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntityLeafGenerator;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockLeafGenerator extends BlockContainerBase implements IActAddItemOrBlock{

    private IIcon topIcon;
    private IIcon bottomIcon;

    public BlockLeafGenerator(){
        super(Material.iron);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeMetal);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityLeafGenerator();
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return side <= 1 ? (side == 0 ? this.bottomIcon : this.topIcon) : this.blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityLeafGenerator generator = (TileEntityLeafGenerator)world.getTileEntity(x, y, z);
            if(generator != null){
                player.addChatComponentMessage(new ChatComponentText(generator.storage.getEnergyStored()+"/"+generator.storage.getMaxEnergyStored()+" RF"));
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Top");
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Bottom");
    }

    @Override
    public String getName(){
        return "blockLeafGenerator";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }
}
