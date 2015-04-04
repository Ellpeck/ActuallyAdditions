package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.ConfigValues;
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
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemLeafBlower extends Item implements INameableItem{

    public final int range = ConfigValues.leafBlowerRangeSides;
    public final int rangeUp = ConfigValues.leafBlowerRangeUp;
    public final boolean doesDrop = ConfigValues.leafBlowerDropItems;
    public final boolean hasParticles = ConfigValues.leafBlowerParticles;
    public final boolean hasSound = ConfigValues.leafBlowerHasSound;

    private final boolean isAdvanced;

    public ItemLeafBlower(boolean isAdvanced){
        this.isAdvanced = isAdvanced;
        this.setMaxStackSize(1);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int time){
        if(!player.worldObj.isRemote){
            if(time <= getMaxItemUseDuration(stack) && time % 2 == 0){
                this.breakStuff(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
                if(this.hasSound) player.worldObj.playSoundAtEntity(player, "minecart.base", 0.3F, 0.001F);
            }
        }
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }

    @Override
    public boolean shouldRotateAroundWhenRendering(){
        return false;
    }

    public void breakStuff(World world, int x, int y, int z){
        ArrayList<ChunkCoordinates> theCoords = new ArrayList<ChunkCoordinates>();

        for(int reachX = -range; reachX < range+1; reachX++){
            for(int reachZ = -range; reachZ < range+1; reachZ++){
                for(int reachY = (this.isAdvanced ? -range : -rangeUp); reachY < (this.isAdvanced ? range+1 : rangeUp+1); reachY++){
                    Block block = world.getBlock(x+reachX, y+reachY, z+reachZ);
                    if(block != null && (block instanceof BlockBush || (this.isAdvanced && block instanceof BlockLeavesBase))){
                        theCoords.add(new ChunkCoordinates(x+reachX, y+reachY, z+reachZ));
                    }
                }
            }
        }

        if(theCoords.size() > 0){
            ChunkCoordinates theCoord = theCoords.get(new Random().nextInt(theCoords.size()));
            Block theBlock = world.getBlock(theCoord.posX, theCoord.posY, theCoord.posZ);
            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            int meta = world.getBlockMetadata(theCoord.posX, theCoord.posY, theCoord.posZ);
            drops.addAll(theBlock.getDrops(world, theCoord.posX, theCoord.posY, theCoord.posZ, meta, 0));

            world.setBlockToAir(theCoord.posX, theCoord.posY, theCoord.posZ);
            if(this.hasParticles) world.playAuxSFX(2001, theCoord.posX, theCoord.posY, theCoord.posZ, Block.getIdFromBlock(theBlock)+(meta << 12));

            if(this.doesDrop){
                for(ItemStack theDrop : drops){
                    world.spawnEntityInWorld(new EntityItem(world, theCoord.posX + 0.5, theCoord.posY + 0.5, theCoord.posZ + 0.5, theDrop));
                }
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return 1000000;
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
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + this.getName() + ".desc." + 1));
            list.add(StringUtil.ITALIC + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".itemLeafBlower.desc.2"));
            list.add(StringUtil.ITALIC + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".itemLeafBlower.desc.3"));
            ItemUtil.addOredictName(this, list);
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
