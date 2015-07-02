package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.BlockPlant;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class ItemSeed extends ItemSeeds implements INameableItem{

    public Block plant;
    public String name;
    public String oredictName;

    public ItemSeed(String name, String oredictName, Block plant, Item returnItem, int returnMeta){
        super(plant, Blocks.farmland);
        this.name = name;
        this.oredictName = oredictName;
        this.plant = plant;
        ((BlockPlant)this.plant).seedItem = this;
        ((BlockPlant)this.plant).returnItem = returnItem;
        ((BlockPlant)this.plant).returnMeta = returnMeta;
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
}