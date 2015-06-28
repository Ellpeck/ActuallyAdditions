package ellpeck.actuallyadditions.blocks.metalists;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;

public enum TheWildPlants implements INameableItem{

    CANOLA("Canola", EnumRarity.rare, "blockCanolaWild", InitBlocks.blockCanola),
    FLAX("Flax", EnumRarity.rare, "blockFlaxWild", InitBlocks.blockFlax),
    RICE("Rice", EnumRarity.rare, "blockRiceWild", InitBlocks.blockRice),
    COFFEE("Coffee", EnumRarity.rare, "blockCoffeeWild", InitBlocks.blockCoffee);

    public final String name;
    public final String oredictName;
    public final EnumRarity rarity;
    public final Block wildVersionOf;

    TheWildPlants(String name, EnumRarity rarity, String oredictName, Block wildVersionOf){
        this.name = name;
        this.rarity = rarity;
        this.oredictName = oredictName;
        this.wildVersionOf = wildVersionOf;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return this.oredictName;
    }
}