/*
 * This file ("BlockBushBase.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BlockBushBase extends BlockBush{

    private String name;

    public BlockBushBase(String name){
        this.name = name;
        this.setStepSound(soundTypeGrass);

        this.register();
    }

    private void register(){
        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
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

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), new ResourceLocation(ModUtil.MOD_ID_LOWER, this.getBaseName()));
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
    protected BlockState createBlockState(){
        return this.getMetaProperty() == null ? super.createBlockState() : new BlockState(this, this.getMetaProperty());
    }

    protected PropertyInteger getMetaProperty(){
        return null;
    }
}
