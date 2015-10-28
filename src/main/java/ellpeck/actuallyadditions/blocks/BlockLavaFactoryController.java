/*
 * This file ("BlockLavaFactoryController.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntityLavaFactoryController;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockLavaFactoryController extends BlockContainerBase implements IActAddItemOrBlock{

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;

    public BlockLavaFactoryController(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(20.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityLavaFactoryController();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return side == 1 ? this.topIcon : this.blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController)world.getTileEntity(x, y, z);
            if(factory != null){
                int state = factory.isMultiblock();
                if(state == TileEntityLavaFactoryController.NOT_MULTI){
                    player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".factory.notPart.desc")));
                }
                if(state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA){
                    player.addChatComponentMessage(new ChatComponentText(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".factory.works.desc")));
                }
                player.addChatComponentMessage(new ChatComponentText(factory.storage.getEnergyStored()+"/"+factory.storage.getMaxEnergyStored()+" RF"));
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
    }

    @Override
    public String getName(){
        return "blockLavaFactoryController";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }
}
