package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;

public class TileEntityHeatCollector extends TileEntityBase{

    private int randomChance = ConfigIntValues.HEAT_COLLECTOR_LAVA_CHANCE.getValue();
    private int blocksNeeded = ConfigIntValues.HEAT_COLLECTOR_BLOCKS.getValue();

    @Override
    public boolean canUpdate(){
        return false;
    }

    //TODO Reimplement

    /*@Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            ArrayList<Integer> blocksAround = new ArrayList<Integer>();

            for(int i = 1; i <= 5; i++){
                ChunkCoordinates coords = WorldUtil.getCoordsFromSide(i, xCoord, yCoord, zCoord);
                if(coords != null){
                    Block block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);
                    if(block != null && block.getMaterial() == Material.lava && worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ) == 0){
                        blocksAround.add(i);
                    }
                }
            }

            if(blocksAround.size() >= blocksNeeded){
                TileEntity tileAbove = WorldUtil.getTileEntityFromSide(0, worldObj, xCoord, yCoord, zCoord);

                TileEntityFurnaceSolar.givePowerTo(tileAbove);

                Random rand = new Random();
                if(rand.nextInt(randomChance) == 0){
                    int randomSide = blocksAround.get(rand.nextInt(blocksAround.size()));
                    WorldUtil.breakBlockAtSide(randomSide, worldObj, xCoord, yCoord, zCoord);
                }
            }
        }
    }*/
}
