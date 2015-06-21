package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.BlockPlant;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;

import java.util.List;

public class ItemSeed extends ItemSeeds implements INameableItem, IFactoryPlantable{

    public Block plant;
    public Block soilBlock;
    public EnumPlantType type;
    public String name;

    public ItemSeed(String name, Block plant, Block soilBlock, EnumPlantType type, Item returnItem, int returnMeta){
        super(plant, soilBlock);
        this.name = name;
        this.plant = plant;
        this.soilBlock = soilBlock;
        this.type = type;
        ((BlockPlant)this.plant).seedItem = this;
        ((BlockPlant)this.plant).returnItem = returnItem;
        ((BlockPlant)this.plant).returnMeta = returnMeta;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ){
        if(this.type == EnumPlantType.Water || hitSide != 1) return false;
        else if(player.canPlayerEdit(x, y, z, hitSide, stack) && player.canPlayerEdit(x, y + 1, z, hitSide, stack)){
            if(((BlockPlant)this.plant).canPlaceBlockOn(world.getBlock(x, y, z)) && world.isAirBlock(x, y + 1, z)){
                world.setBlock(x, y + 1, z, this.plant);
                stack.stackSize--;
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(this.type == EnumPlantType.Water){
            MovingObjectPosition pos = this.getMovingObjectPositionFromPlayer(world, player, true);
            if(pos != null){
                if(pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
                    int i = pos.blockX;
                    int j = pos.blockY;
                    int k = pos.blockZ;

                    if(player.canPlayerEdit(i, j, k, pos.sideHit, stack)){
                        if(world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k)){
                            BlockSnapshot snap = BlockSnapshot.getBlockSnapshot(world, i, j+1, k);
                            world.setBlock(i, j + 1, k, this.plant);
                            if(ForgeEventFactory.onPlayerBlockPlace(player, snap, ForgeDirection.UP).isCanceled()){
                                snap.restore(true, false);
                                return super.onItemRightClick(stack, world, player);
                            }
                            stack.stackSize--;
                        }
                    }
                }
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        ItemUtil.addInformation(this, list, 1, "");
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
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z){
        return this.type;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z){
        return this.plant;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z){
        return 0;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }

    @Override
    public Item getSeed(){
        return this;
    }

    @Override
    public boolean canBePlanted(ItemStack stack, boolean forFermenting){
        return true;
    }

    @Override
    public ReplacementBlock getPlantedBlock(World world, int x, int y, int z, ItemStack stack){
        return new ReplacementBlock(this.plant);
    }

    @Override
    public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack){
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z) && ((BlockPlant)this.plant).canPlaceBlockOn(world.getBlock(x, y-1, z));
    }

    @Override
    public void prePlant(World world, int x, int y, int z, ItemStack stack){

    }

    @Override
    public void postPlant(World world, int x, int y, int z, ItemStack stack){

    }
}