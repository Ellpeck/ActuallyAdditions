package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.ItemSeed;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockPlant extends BlockCrops implements INameableItem, IFactoryHarvestable, IFactoryFertilizable{

    private IIcon[] textures;
    private String name;
    public Item seedItem;
    public ItemStack returnItem;
    private int minDropAmount;
    private int addDropAmount;

    public BlockPlant(String name, int stages, int minDropAmount, int addDropAmount){
        this.name = name;
        this.textures = new IIcon[stages];
        this.minDropAmount = minDropAmount;
        this.addDropAmount = addDropAmount;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z){
        return y > 0 && y < 256 && world.getBlock(x, y-1, z).getMaterial() == ((ItemSeed)this.seedItem).soilBlock.getMaterial();
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
        if(metadata >= 7){
            for(int i = 0; i < 3; ++i){
                if(world.rand.nextInt(6) == 0) ret.add(new ItemStack(this.seedItem));
            }

            ItemStack stack = this.returnItem.copy();
            stack.stackSize = new Random().nextInt(addDropAmount)+minDropAmount;
            ret.add(stack);
        }
        else ret.add(new ItemStack(this.seedItem));

        return ret;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int i){
        return null;
    }

    @Override
    public boolean canPlaceBlockOn(Block block){
        return block == ((ItemSeed)this.seedItem).soilBlock;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(meta < 7){
            if (meta == 6) meta = 5;
            return this.textures[meta >> 1];
        }
        else return this.textures[this.textures.length-1];
    }

    @Override
    public Item func_149866_i(){
        return this.seedItem;
    }

    @Override
    public Item func_149865_P(){
        return this.returnItem.getItem();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        for (int i = 0; i < this.textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Stage" + (i+1));
        }
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.uncommon;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            BlockUtil.addInformation(theBlock, list, 1, "");
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }

    @Override
    public Block getPlant(){
        return this;
    }

    @Override
    public boolean canFertilize(World world, int x, int y, int z, FertilizerType fertilizerType){
        return true;
    }

    @Override
    public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType){
        if (this.func_149851_a(world, x, y, z, world.isRemote)){
            if(!world.isRemote){
                if(this.func_149852_a(world, world.rand, x, y, z)){
                    this.func_149853_b(world, world.rand, x, y, z);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public HarvestType getHarvestType(){
        return HarvestType.Normal;
    }

    @Override
    public boolean breakBlock(){
        return true;
    }

    @Override
    public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) >= 7;
    }

    @Override
    public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z){
        return this.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    }

    @Override
    public void preHarvest(World world, int x, int y, int z){

    }

    @Override
    public void postHarvest(World world, int x, int y, int z){

    }
}