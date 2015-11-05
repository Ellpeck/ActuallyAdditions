/*
 * This file ("ItemLeafBlower.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;

public class ItemLeafBlower extends Item implements IActAddItemOrBlock{

    private final boolean isAdvanced;

    public ItemLeafBlower(boolean isAdvanced){
        this.isAdvanced = isAdvanced;
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        //Cuz you won't hold it for that long right-clicking anyways
        return Integer.MAX_VALUE;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.isAdvanced ? EnumRarity.epic : EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int time){
        if(!player.worldObj.isRemote){
            if(time <= getMaxItemUseDuration(stack) && (this.isAdvanced || time%3 == 0)){
                //Breaks the Blocks
                this.breakStuff(player.worldObj, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
                //Plays a Minecart sounds (It really sounds like a Leaf Blower!)
                if(ConfigBoolValues.LEAF_BLOWER_SOUND.isEnabled()){
                    player.worldObj.playSoundAtEntity(player, "minecart.base", 0.3F, 0.001F);
                }
            }
        }
    }

    /**
     * Breaks (harvests) Grass and Leaves around
     *
     * @param world The World
     * @param x     The X Position of the Player
     * @param y     The Y Position of the Player
     * @param z     The Z Position of the Player
     */
    public void breakStuff(World world, int x, int y, int z){
        ArrayList<WorldPos> breakPositions = new ArrayList<WorldPos>();

        for(int reachX = -ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue(); reachX < ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue()+1; reachX++){
            for(int reachZ = -ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue(); reachZ < ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue()+1; reachZ++){
                for(int reachY = (this.isAdvanced ? -ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue() : -ConfigIntValues.LEAF_BLOWER_RANGE_UP.getValue()); reachY < (this.isAdvanced ? ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue()+1 : ConfigIntValues.LEAF_BLOWER_RANGE_UP.getValue()+1); reachY++){
                    //The current Block to break
                    Block block = world.getBlock(x+reachX, y+reachY, z+reachZ);
                    if(block != null && (block instanceof BlockBush || (this.isAdvanced && block.isLeaves(world, x+reachX, y+reachY, z+reachZ)))){
                        breakPositions.add(new WorldPos(world, x+reachX, y+reachY, z+reachZ));
                    }
                }
            }
        }

        if(!breakPositions.isEmpty()){
            Collections.shuffle(breakPositions);

            WorldPos theCoord = breakPositions.get(0);
            Block theBlock = world.getBlock(theCoord.getX(), theCoord.getY(), theCoord.getZ());

            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            int meta = world.getBlockMetadata(theCoord.getX(), theCoord.getY(), theCoord.getZ());
            //Gets all of the Drops the Block should have
            drops.addAll(theBlock.getDrops(world, theCoord.getX(), theCoord.getY(), theCoord.getZ(), meta, 0));

            //Deletes the Block
            world.setBlockToAir(theCoord.getX(), theCoord.getY(), theCoord.getZ());
            //Plays the Breaking Sound
            world.playAuxSFX(2001, theCoord.getX(), theCoord.getY(), theCoord.getZ(), Block.getIdFromBlock(theBlock)+(meta << 12));

            for(ItemStack theDrop : drops){
                //Drops the Items into the World
                world.spawnEntityInWorld(new EntityItem(world, theCoord.getX()+0.5, theCoord.getY()+0.5, theCoord.getZ()+0.5, theDrop));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getName(){
        return this.isAdvanced ? "itemLeafBlowerAdvanced" : "itemLeafBlower";
    }
}
