/*
 * This file ("BlockBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class BlockBase extends Block{

    private String name;

    public BlockBase(Material material, String name){
        super(material);
        this.name = name;

        this.register();
    }

    private void register(){
        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName(){
        return this.name;
    }

    protected ItemBlockBase getItemBlock(){
        return new ItemBlockBase(this);
    }

    public boolean shouldAddCreative(){
        return true;
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.COMMON;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getMetaProperty() == null ? super.getStateFromMeta(meta) : this.getDefaultState().withProperty(this.getMetaProperty(), meta);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return this.getMetaProperty() == null ? super.getMetaFromState(state) : state.getValue(this.getMetaProperty());
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return this.getMetaProperty() == null ? super.createBlockState() : new BlockStateContainer(this, this.getMetaProperty());
    }

    protected PropertyInteger getMetaProperty(){
        return null;
    }
}
