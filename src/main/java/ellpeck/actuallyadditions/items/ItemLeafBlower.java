package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemLeafBlower extends Item implements INameableItem{

    public final int range = ConfigIntValues.LEAF_BLOWER_RANGE_SIDES.getValue();
    public final int rangeUp = ConfigIntValues.LEAF_BLOWER_RANGE_UP.getValue();
    public final boolean hasSound = ConfigBoolValues.LEAF_BLOWER_SOUND.isEnabled();

    private final boolean isAdvanced;

    public ItemLeafBlower(boolean isAdvanced){
        this.isAdvanced = isAdvanced;
        this.setMaxStackSize(1);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int time){
        if(!player.worldObj.isRemote){
            if(time <= getMaxItemUseDuration(stack) && time % 2 == 0){
                //Breaks the Blocks
                this.breakStuff(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
                //Plays a Minecart sounds (It really sounds like a Leaf Blower!)
                if(this.hasSound) player.worldObj.playSoundAtEntity(player, "minecart.base", 0.3F, 0.001F);
            }
        }
    }

    /**
     * Breaks (harvests) Grass and Leaves around
     * @param world The World
     * @param x The X Position of the Player
     * @param y The Y Position of the Player
     * @param z The Z Position of the Player
     */
    public void breakStuff(World world, int x, int y, int z){
        for(int reachX = -range; reachX < range+1; reachX++){
            for(int reachZ = -range; reachZ < range+1; reachZ++){
                for(int reachY = (this.isAdvanced ? -range : -rangeUp); reachY < (this.isAdvanced ? range+1 : rangeUp+1); reachY++){
                    //The current Block to break
                    Block block = world.getBlock(x+reachX, y+reachY, z+reachZ);
                    if(block != null && (block instanceof BlockBush || (this.isAdvanced && block instanceof BlockLeavesBase))){
                        WorldPos theCoord = new WorldPos(world, x+reachX, y+reachY, z+reachZ);
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
                            world.spawnEntityInWorld(new EntityItem(world, theCoord.getX() + 0.5, theCoord.getY() + 0.5, theCoord.getZ() + 0.5, theDrop));
                        }

                        return;
                    }
                }
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        //Cuz you won't hold it for that long right-clicking anyways
        return Integer.MAX_VALUE;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.isAdvanced ? EnumRarity.epic : EnumRarity.rare;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(KeyUtil.isShiftPressed()){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+".desc."+1));
            list.add(StringUtil.ITALIC + StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".itemLeafBlower.desc.2"));
            list.add(StringUtil.ITALIC + StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".itemLeafBlower.desc.3"));
        }
        else list.add(ItemUtil.shiftForInfo());
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public String getName(){
        return this.isAdvanced ? "itemLeafBlowerAdvanced" : "itemLeafBlower";
    }
}
