/*
 * This file ("BlockFluidFlowing.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidFlowing extends BlockFluidClassic{

    private String name;

    public BlockFluidFlowing(Fluid fluid, Material material, String unlocalizedName){
        super(fluid, material);
        this.name = unlocalizedName;
        displacements.put(this, false);

        this.register();
    }

    private void register(){
        this.setUnlocalizedName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerBlock(this, this.getItemBlock(), this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }
    }

    protected String getBaseName(){
        return this.name;
    }

    protected Class<? extends ItemBlockBase> getItemBlock(){
        return ItemBlockBase.class;
    }

    public boolean shouldAddCreative(){
        return false;
    }

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos){
        return !Position.fromBlockPos(pos).getMaterial(world).isLiquid() && super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos){
        return !Position.fromBlockPos(pos).getMaterial(world).isLiquid() && super.displaceIfPossible(world, pos);
    }

    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
