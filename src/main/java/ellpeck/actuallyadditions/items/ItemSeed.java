/*
 * This file ("ItemSeed.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.BlockPlant;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class ItemSeed extends ItemSeeds implements IActAddItemOrBlock{

    public Block plant;
    public String name;
    public String oredictName;

    public ItemSeed(String name, String oredictName, Block plant, Item returnItem, int returnMeta){
        super(plant, Blocks.farmland);
        this.name = name;
        this.oredictName = oredictName;
        this.plant = plant;
        ((BlockPlant)this.plant).seedItem = this;
        ((BlockPlant)this.plant).returnItem = returnItem;
        ((BlockPlant)this.plant).returnMeta = returnMeta;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z){
        return this.plant;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z){
        return 0;
    }
}