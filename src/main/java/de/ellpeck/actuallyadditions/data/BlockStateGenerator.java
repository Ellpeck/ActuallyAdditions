package de.ellpeck.actuallyadditions.data;


import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStateGenerator extends BlockStateProvider {
    
    public BlockStateGenerator(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, ActuallyAdditions.MODID, existingFileHelper);
    }
    
    @Override
    protected void registerStatesAndModels(){
        for(RegistryObject<Block> blockEntry : InitBlocks.BLOCKS.getEntries()){
            blockEntry.ifPresent(block -> {
                // todo check if the blocks aren't "simple" and provide a different model,
                //  probably with the help of a interface the block implements, like naturesaura does
                this.simpleBlock(block);
            });
        }
    }
}
