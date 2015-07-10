package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.recipe.TreasureChestHandler;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockTreasureChest extends Block implements INameableItem{

    private IIcon topIcon;
    private IIcon onIcon;
    private IIcon frontIcon;

    public BlockTreasureChest(){
        super(Material.wood);
        this.setHarvestLevel("axe", 0);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeWood);
        this.setTickRandomly(true);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (rotation == 0) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        if (rotation == 1) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        if (rotation == 2) world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        if (rotation == 3) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
    }

    @Override
    public Item getItemDropped(int par1, Random rand, int par3){
        return null;
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 1) return this.topIcon;
        if(side == 3) return this.frontIcon;
        return this.blockIcon;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side == 1) return this.topIcon;
        if(side == meta+2 && meta <= 3) return this.frontIcon;
        else if(side == meta-2 && meta > 3) return this.onIcon;
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Top");
        this.onIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "On");
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Front");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            world.playSoundAtEntity(player, "random.chestopen", 0.2F, new Random().nextFloat()*0.1F+0.9F);
            this.dropItems(world, x, y, z);
            world.setBlockToAir(x, y, z);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    private void dropItems(World world, int x, int y, int z){
        Random rand = new Random();
        for(int i = 0; i < MathHelper.getRandomIntegerInRange(rand, 3, 6); i++){
            TreasureChestHandler.Return theReturn = (TreasureChestHandler.Return)WeightedRandom.getRandomItem(rand, TreasureChestHandler.returns);
            ItemStack itemStack = theReturn.returnItem.copy();
            itemStack.stackSize = MathHelper.getRandomIntegerInRange(rand, theReturn.minAmount, theReturn.maxAmount);

            float dX = rand.nextFloat()*0.8F+0.1F;
            float dY = rand.nextFloat()*0.8F+0.1F;
            float dZ = rand.nextFloat()*0.8F+0.1F;
            EntityItem entityItem = new EntityItem(world, x+dX, y+dY, z+dZ, itemStack.copy());
            if(itemStack.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
            float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian()*factor;
            entityItem.motionY = rand.nextGaussian()*factor+0.2F;
            entityItem.motionZ = rand.nextGaussian()*factor;
            world.spawnEntityInWorld(entityItem);
            itemStack.stackSize = 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand){
        //TODO Bubble Particles if in water
    }

    @Override
    public String getName(){
        return "blockTreasureChest";
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
            return EnumRarity.epic;
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
}
