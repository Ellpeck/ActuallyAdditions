package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.List;

public class BlockFluidFlowing extends BlockFluidClassic implements INameableItem{

    private String name;

    public IIcon stillIcon;
    public IIcon flowingIcon;

    public BlockFluidFlowing(Fluid fluid, Material material, String unlocalizedName){
        super(fluid, material);
        this.name = unlocalizedName;
        this.setRenderPass(1);
        displacements.put(this, false);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z){
        return !world.getBlock(x, y, z).getMaterial().isLiquid() && super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z){
        return !world.getBlock(x, y, z).getMaterial().isLiquid() && super.displaceIfPossible(world, x, y, z);
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return side <= 1 ? this.stillIcon : this.flowingIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.stillIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Still");
        this.flowingIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Flowing");
        this.definedFluid.setIcons(this.stillIcon, this.flowingIcon);
    }

    @Override
    public String getName(){
        return this.name;
    }

    private String getOredictName(){
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
}
