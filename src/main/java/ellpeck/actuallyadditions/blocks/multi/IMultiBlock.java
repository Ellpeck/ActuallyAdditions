package ellpeck.actuallyadditions.blocks.multi;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public interface IMultiBlock{

    /**
     * @return The types of Block the Multiblock can contain
     */
    Block[] getNeededBlocks();

    /**
     * @return The Core TileEntity storing the MultiBlock's Data
     */
    TileEntity getCore();

    /**
     * @return The horizontal size of the MultiBlock
     */
    int getSizeHor();

    /**
     * @return The vertical size of the MultiBlock
     */
    int getSizeVer();
}
