package ellpeck.someprettyrandomstuff.blocks;

import ellpeck.someprettyrandomstuff.achievement.InitAchievements;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.ItemFertilizer;
import ellpeck.someprettyrandomstuff.items.ItemMisc;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.tile.TileEntityCompost;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class BlockCompost extends BlockContainerBase{

    public BlockCompost(){
        super(Material.wood);
        this.setCreativeTab(CreativeTab.instance);
        this.setUnlocalizedName("blockCompost");
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeWood);
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!world.isRemote){
            ItemStack stackPlayer = player.getCurrentEquippedItem();
            TileEntityCompost tile = (TileEntityCompost)world.getTileEntity(pos);
            //Add items to be composted
            if(stackPlayer != null && stackPlayer.getItem() instanceof ItemMisc && stackPlayer.getMetadata() == TheMiscItems.MASHED_FOOD.ordinal() && (tile.slots[0] == null || (!(tile.slots[0].getItem() instanceof ItemFertilizer) && tile.slots[0].stackSize < tile.amountNeededToConvert))){
                if(tile.slots[0] == null) tile.slots[0] = new ItemStack(stackPlayer.getItem(), 1, TheMiscItems.MASHED_FOOD.ordinal());
                else tile.slots[0].stackSize++;
                if(!player.capabilities.isCreativeMode) player.inventory.getCurrentItem().stackSize--;
            }
            //Add Fertilizer to player's inventory
            else if(stackPlayer == null && tile.slots[0] != null && tile.slots[0].getItem() instanceof ItemFertilizer){
                player.inventory.setInventorySlotContents(player.inventory.currentItem, tile.slots[0].copy());
                player.addStat(InitAchievements.achievementCraftFertilizer, 1);
                tile.slots[0] = null;
            }
        }
        return true;
    }

    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
    }

    public void setBlockBoundsForItemRender(){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube(){
        return false;
    }

    public boolean isFullCube(){
        return false;
    }

    public int getRenderType(){
        return 3;
    }

    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCompost();
    }
}
