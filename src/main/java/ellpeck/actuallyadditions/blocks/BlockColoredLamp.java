package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.metalists.TheColoredLampColors;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Random;

public class BlockColoredLamp extends Block implements INameableItem{

    public static TheColoredLampColors[] allLampTypes = TheColoredLampColors.values();

    private IIcon[] textures = new IIcon[allLampTypes.length];

    public boolean isOn;

    public BlockColoredLamp(boolean isOn){
        super(Material.redstoneLight);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(3.0F);
        this.isOn = isOn;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return this.isOn ? 15 : 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        //Turning On
        if(player.isSneaking()){
            if(!world.isRemote){
                world.setBlock(x, y, z, this.isOn ? InitBlocks.blockColoredLamp : InitBlocks.blockColoredLampOn, world.getBlockMetadata(x, y, z), 2);
            }
            return true;
        }

        //Changing Colors
        int[] oreIDs = OreDictionary.getOreIDs(player.getCurrentEquippedItem());
        if(oreIDs.length > 0){
            for(int oreID : oreIDs){
                String name = OreDictionary.getOreName(oreID);
                TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                if(color != null){
                    if(world.getBlockMetadata(x, y, z) != color.ordinal()){
                        if(!world.isRemote){
                            world.setBlockMetadataWithNotify(x, y, z, color.ordinal(), 2);
                            if(!player.capabilities.isCreativeMode) player.inventory.decrStackSize(player.inventory.currentItem, 1);
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String getName(){
        return this.isOn ? "blockColoredLampOn" : "blockColoredLamp";
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return meta >= allLampTypes.length ? null : textures[meta];
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for (int j = 0; j < allLampTypes.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public Item getItemDropped(int par1, Random rand, int par3){
        return Item.getItemFromBlock(InitBlocks.blockColoredLamp);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z){
        return Item.getItemFromBlock(InitBlocks.blockColoredLamp);
    }

    @Override
    public ItemStack createStackedBlock(int meta){
        return new ItemStack(InitBlocks.blockColoredLamp, 1, meta);
    }

    @Override
    public int damageDropped(int meta){
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        for(int i = 0; i < allLampTypes.length; i++){
            this.textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + ((INameableItem)InitBlocks.blockColoredLamp).getName() + allLampTypes[i].name + (isOn ? "On" : ""));
        }
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.rare;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return InitBlocks.blockColoredLamp.getUnlocalizedName() + allLampTypes[stack.getItemDamage()].getName();
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack){
            if(stack.getItemDamage() >= allLampTypes.length) return null;
            return StringUtil.localize(this.getUnlocalizedName(stack)+".name") + (((BlockColoredLamp)this.theBlock).isOn ? " (" + StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".onSuffix.desc") + ")" : "");
        }
    }
}