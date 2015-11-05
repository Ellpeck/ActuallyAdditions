/*
 * This file ("BlockFurnaceSolar.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntityFurnaceSolar;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFurnaceSolar extends BlockContainerBase implements IActAddItemOrBlock{

    public BlockFurnaceSolar(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setBlockBounds(0F, 0F, 0F, 1F, 3F/16F, 1F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFurnaceSolar();
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.FURNACE_SOLAR_RENDER_ID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityFurnaceSolar generator = (TileEntityFurnaceSolar)world.getTileEntity(x, y, z);
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
        this.blockIcon = Blocks.daylight_detector.getIcon(0, 0);
    }

    @Override
    public String getName(){
        return "blockFurnaceSolar";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.uncommon;
    }
}
