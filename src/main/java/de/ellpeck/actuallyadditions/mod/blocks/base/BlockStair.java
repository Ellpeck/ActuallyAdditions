/*
 * This file ("BlockStair.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class BlockStair extends BlockStairs{

    private final String name;

    public BlockStair(Block block, String name){
        this(block, name, 0);
    }

    public BlockStair(Block block, String name, int meta){
        super(block.getStateFromMeta(meta));
        this.name = name;
        this.setLightOpacity(0);

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
}
