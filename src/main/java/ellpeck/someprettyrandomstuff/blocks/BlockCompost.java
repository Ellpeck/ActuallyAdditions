package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ellpeck.someprettyrandomstuff.achievement.InitAchievements;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.ItemFertilizer;
import ellpeck.someprettyrandomstuff.items.ItemMisc;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.tile.TileEntityCompost;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class BlockCompost extends BlockContainerBase implements IName{

    public BlockCompost(){
        super(Material.wood);
        this.setCreativeTab(CreativeTab.instance);
        this.setBlockName(Util.getNamePrefix() + this.getName());
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int f6, float f7, float f8, float f9){
        if(!world.isRemote){
            ItemStack stackPlayer = player.getCurrentEquippedItem();
            TileEntityCompost tile = (TileEntityCompost)world.getTileEntity(x, y, z);
            //Add items to be composted
            if(stackPlayer != null && stackPlayer.getItem() instanceof ItemMisc && stackPlayer.getItemDamage() == TheMiscItems.MASHED_FOOD.ordinal() && (tile.slots[0] == null || (!(tile.slots[0].getItem() instanceof ItemFertilizer) && tile.slots[0].stackSize < tile.amountNeededToConvert))){
                if(tile.slots[0] == null) tile.slots[0] = new ItemStack(stackPlayer.getItem(), 1, TheMiscItems.MASHED_FOOD.ordinal());
                else tile.slots[0].stackSize++;
                if(!player.capabilities.isCreativeMode) player.inventory.getCurrentItem().stackSize--;
            }
            //Add Fertilizer to player's inventory
            else if(tile.slots[0] != null && (stackPlayer == null || (stackPlayer.getItem() instanceof ItemFertilizer && stackPlayer.stackSize <= stackPlayer.getMaxStackSize() - tile.slots[0].stackSize)) && tile.slots[0].getItem() instanceof ItemFertilizer){
                if(stackPlayer == null) player.inventory.setInventorySlotContents(player.inventory.currentItem, tile.slots[0].copy());
                else player.getCurrentEquippedItem().stackSize+=tile.slots[0].stackSize;
                player.addStat(InitAchievements.achievementCraftFertilizer, 1);
                tile.slots[0] = null;
            }
        }
        return true;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity collidingEntity){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public void setBlockBoundsForItemRender(){
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCompost();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return "blockCompost";
    }
}
