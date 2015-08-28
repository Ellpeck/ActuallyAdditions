package ellpeck.actuallyadditions.blocks;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockStair extends BlockStairs implements INameableItem{

    private String name;

    public BlockStair(Block block, String name){
        super(block, 0);
        this.name = name;
        this.setLightOpacity(0);
    }

    @Override
    public String getName(){
        return this.name;
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
        public int getMetadata(int meta){
            return meta;
        }
    }
}
