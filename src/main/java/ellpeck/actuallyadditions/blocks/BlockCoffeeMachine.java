package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityCoffeeMachine;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class BlockCoffeeMachine extends BlockContainerBase implements INameableItem{

    public BlockCoffeeMachine(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);

        float f = 1/16F;
        this.setBlockBounds(f, 0F, f, 1F-f, 1F-2*f, 1F-f);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 0);
        if(rotation == 1) world.setBlockMetadataWithNotify(x, y, z, 1, 3);
        if(rotation == 2) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        if(rotation == 3) world.setBlockMetadataWithNotify(x, y, z, 3, 3);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntityCoffeeMachine machine = (TileEntityCoffeeMachine)world.getTileEntity(x, y, z);
            if (machine != null) player.openGui(ActuallyAdditions.instance, GuiHandler.COFFEE_MACHINE_ID, world, x, y, z);
            return true;
        }
        return true;
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.coal_block.getIcon(0, 0);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCoffeeMachine();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return "blockCoffeeMachine";
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
            return EnumRarity.rare;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            BlockUtil.addInformation(theBlock, list, 5, "");
            BlockUtil.addPowerUsageInfo(list, TileEntityCoffeeMachine.energyUsePerTick);
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
