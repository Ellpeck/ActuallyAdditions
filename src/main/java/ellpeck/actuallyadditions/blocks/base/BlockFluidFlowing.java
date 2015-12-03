/*
 * This file ("BlockFluidFlowing.java") is part of the Actually Additions Mod for Minecraft.
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
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidFlowing extends BlockFluidClassic{

    @SideOnly(Side.CLIENT)
    public IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    public IIcon flowingIcon;

    private String name;

    public BlockFluidFlowing(Fluid fluid, Material material, String unlocalizedName){
        super(fluid, material);
        this.name = unlocalizedName;
        this.setRenderPass(1);
        displacements.put(this, false);

        this.register();
    }

    private void register(){
        this.setBlockName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerBlock(this, this.getItemBlock(), this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
    }

    public boolean shouldAddCreative(){
        return true;
    }

    protected String getBaseName(){
        return this.name;
    }

    protected Class<? extends ItemBlockBase> getItemBlock(){
        return ItemBlockBase.class;
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z){
        return !world.getBlock(x, y, z).getMaterial().isLiquid() && super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z){
        return !world.getBlock(x, y, z).getMaterial().isLiquid() && super.displaceIfPossible(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return side <= 1 ? this.stillIcon : this.flowingIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.stillIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Still");
        this.flowingIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Flowing");
        this.definedFluid.setIcons(this.stillIcon, this.flowingIcon);
    }

    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }
}
