/*
 * This file ("BlockHeatCollector.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import ellpeck.actuallyadditions.tile.TileEntityHeatCollector;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockHeatCollector extends BlockContainerBase{

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;

    public BlockHeatCollector(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(2.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityHeatCollector();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return side == 1 ? this.topIcon : (side == 0 ? this.bottomIcon : this.blockIcon);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityHeatCollector generator = (TileEntityHeatCollector)world.getTileEntity(x, y, z);
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
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Side");
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Top");
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Bottom");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.uncommon;
    }
}
