/*
 * This file ("ItemSpawnerChanger.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSpawnerChanger extends ItemBase{

    public ItemSpawnerChanger(String name){
        super(name);
        this.setMaxStackSize(1);
    }


    @Override
    public EnumActionResult onItemUse(ItemStack aStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!world.isRemote){
            ItemStack stack = player.getHeldItemMainhand();
            if(player.canPlayerEdit(pos.offset(facing), facing, stack)){
                TileEntity tile = world.getTileEntity(pos);
                if(tile instanceof TileEntityMobSpawner){
                    String entity = this.getStoredEntity(stack);
                    if(entity != null){
                        MobSpawnerBaseLogic logic = ((TileEntityMobSpawner)tile).getSpawnerBaseLogic();
                        logic.setEntityName(entity);

                        tile.markDirty();

                        IBlockState state = world.getBlockState(pos);
                        world.notifyBlockUpdate(pos, state, state, 3);

                        ItemPhantomConnector.clearStorage(stack);

                        if(!player.capabilities.isCreativeMode){
                            stack.stackSize--;
                        }

                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack aStack, EntityPlayer player, EntityLivingBase entity, EnumHand hand){
        if(!player.worldObj.isRemote){
            ItemStack stack = player.getHeldItemMainhand();
            if(this.getStoredEntity(stack) == null){
                if(this.storeClickedEntity(stack, entity)){
                    entity.setDead();
                }
            }
            return true;
        }
        return false;
    }

    private boolean storeClickedEntity(ItemStack stack, EntityLivingBase entity){
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }

        if(!(entity instanceof EntityPlayer)){
            String entityName = EntityList.getEntityString(entity);
            if(entityName != null && !entityName.isEmpty()){
                for(String name : ConfigStringListValues.SPAWNER_CHANGER_BLACKLIST.getValue()){
                    if(entityName.equals(name)){
                        return false;
                    }
                }

                stack.getTagCompound().setString("Entity", entityName);
                return true;
            }
        }
        return false;
    }

    private String getStoredEntity(ItemStack stack){
        if(stack.hasTagCompound()){
            String entity = stack.getTagCompound().getString("Entity");
            if(entity != null && !entity.isEmpty()){
                return entity;
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        String entity = this.getStoredEntity(stack);
        if(entity != null){
            list.add("Entity: "+entity);
            list.add(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".clearStorage.desc"));
        }
    }
}
