/*
 * This file ("BlockPlant.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.base;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.creative.CreativeTab;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

public class BlockPlant extends BlockCrops{

    public Item seedItem;
    public Item returnItem;
    public int returnMeta;
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;
    private int stages;
    private String name;
    private int minDropAmount;
    private int addDropAmount;

    public BlockPlant(String name, int stages, int minDropAmount, int addDropAmount){
        this.name = name;
        this.stages = stages;
        this.minDropAmount = minDropAmount;
        this.addDropAmount = addDropAmount;

        this.register();
    }

    private void register(){
        this.setBlockName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerBlock(this, this.getItemBlock(), this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
    }

    protected String getBaseName(){
        return this.name;
    }

    protected Class<? extends ItemBlockBase> getItemBlock(){
        return ItemBlockBase.class;
    }

    public boolean shouldAddCreative(){
        return true;
    }

    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z){
        return EnumPlantType.Crop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(meta < 7){
            if(meta == 6){
                meta = 5;
            }
            return this.textures[meta >> 1];
        }
        else{
            return this.textures[this.textures.length-1];
        }
    }

    @Override
    public Item func_149866_i(){
        return this.seedItem;
    }

    @Override
    public Item func_149865_P(){
        return this.returnItem;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int par3){
        return meta >= 7 ? this.func_149865_P() : this.func_149866_i();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.textures = new IIcon[this.stages];
        for(int i = 0; i < this.textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Stage"+(i+1));
        }
    }

    @Override
    public int damageDropped(int meta){
        return this.returnMeta;
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z){
        return 0;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random){
        return meta >= 7 ? random.nextInt(addDropAmount)+minDropAmount : super.quantityDropped(meta, fortune, random);
    }
}