/*
 * This file ("BlockCompost.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.items.ItemFertilizer;
import ellpeck.actuallyadditions.items.ItemMisc;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.tile.TileEntityCompost;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class BlockCompost extends BlockContainerBase implements IHudDisplay{

    public BlockCompost(String name){
        super(Material.wood, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeWood);

        this.setBlockBoundsForItemRender();
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.compostRenderId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
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
        this.setBlockBounds(1.0F-f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F-f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int f6, float f7, float f8, float f9){
        if(!world.isRemote){
            ItemStack stackPlayer = player.getCurrentEquippedItem();
            TileEntityCompost tile = (TileEntityCompost)world.getTileEntity(x, y, z);
            //Add items to be composted
            if(stackPlayer != null && stackPlayer.getItem() instanceof ItemMisc && stackPlayer.getItemDamage() == TheMiscItems.MASHED_FOOD.ordinal() && (tile.slots[0] == null || (!(tile.slots[0].getItem() instanceof ItemFertilizer) && tile.slots[0].stackSize < TileEntityCompost.AMOUNT))){
                if(tile.slots[0] == null){
                    tile.slots[0] = new ItemStack(stackPlayer.getItem(), 1, TheMiscItems.MASHED_FOOD.ordinal());
                }
                else{
                    tile.slots[0].stackSize++;
                }
                if(!player.capabilities.isCreativeMode){
                    player.inventory.getCurrentItem().stackSize--;
                }
                tile.sendUpdate();
            }

            //Add Fertilizer to player's inventory
            else if(tile.slots[0] != null && (stackPlayer == null || (stackPlayer.getItem() instanceof ItemFertilizer && stackPlayer.stackSize <= stackPlayer.getMaxStackSize()-tile.slots[0].stackSize)) && tile.slots[0].getItem() instanceof ItemFertilizer){
                if(stackPlayer == null){
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, tile.slots[0].copy());
                }
                else{
                    player.getCurrentEquippedItem().stackSize += tile.slots[0].stackSize;
                }
                tile.slots[0] = null;
                tile.sendUpdate();
            }
        }
        return true;
    }

    @Override
    public void setBlockBoundsForItemRender(){
        float f = 1.0F/16.0F;
        this.setBlockBounds(f, 0.0F, f, 1.0F-f, 1.0F, 1.0F-f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.planks.getIcon(0, 0);
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
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.uncommon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, MovingObjectPosition posHit, Profiler profiler, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.blockX, posHit.blockY, posHit.blockZ);
        if(tile instanceof TileEntityCompost){
            ItemStack slot = ((TileEntityCompost)tile).getStackInSlot(0);
            String strg;
            if(slot == null){
                strg = "Empty";
            }
            else{
                strg = slot.getItem().getItemStackDisplayName(slot);

                BookletPage.renderItem(null, slot, resolution.getScaledWidth()/2+15, resolution.getScaledHeight()/2-29, 1F);
            }
            minecraft.fontRenderer.drawStringWithShadow(EnumChatFormatting.YELLOW+""+EnumChatFormatting.ITALIC+strg, resolution.getScaledWidth()/2+35, resolution.getScaledHeight()/2-25, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}
