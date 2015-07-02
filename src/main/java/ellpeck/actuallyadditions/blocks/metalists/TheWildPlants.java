package ellpeck.actuallyadditions.blocks.metalists;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;

public enum TheWildPlants implements INameableItem{

    CANOLA("Canola", EnumRarity.rare, InitBlocks.blockCanola),
    FLAX("Flax", EnumRarity.rare, InitBlocks.blockFlax),
    RICE("Rice", EnumRarity.rare, InitBlocks.blockRice),
    COFFEE("Coffee", EnumRarity.rare, InitBlocks.blockCoffee);

    public final String name;
    public final EnumRarity rarity;
    public final Block wildVersionOf;

    TheWildPlants(String name, EnumRarity rarity, Block wildVersionOf){
        this.name = name;
        this.rarity = rarity;
        this.wildVersionOf = wildVersionOf;
    }

    @Override
    public String getName(){
        return this.name;
    }
}