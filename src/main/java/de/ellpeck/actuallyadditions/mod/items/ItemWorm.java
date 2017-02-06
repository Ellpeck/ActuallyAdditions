/*
 * This file ("ItemWorm.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.entity.EntityWorm;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemWorm extends ItemBase{

    public ItemWorm(String name){
        super(name);

        MinecraftForge.EVENT_BUS.register(this);

        this.addPropertyOverride(new ResourceLocation(ModUtil.MOD_ID, "snail"), new IItemPropertyGetter(){
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World world, EntityLivingBase entity){
                return "snail mail".equalsIgnoreCase(stack.getDisplayName()) ? 1F : 0F;
            }
        });
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float par8, float par9, float par10){
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        if(EntityWorm.canWormify(world, pos, state)){
            List<EntityWorm> worms = world.getEntitiesWithinAABB(EntityWorm.class, new AxisAlignedBB(pos.getX()-1, pos.getY(), pos.getZ()-1, pos.getX()+2, pos.getY()+1, pos.getZ()+2));
            if(worms == null || worms.isEmpty()){
                if(!world.isRemote){
                    EntityWorm worm = new EntityWorm(world);
                    worm.setPosition(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
                    worm.setCustomNameTag(stack.getDisplayName());
                    world.spawnEntity(worm);

                    if(!player.capabilities.isCreativeMode){
                        player.setHeldItem(hand, StackUtil.addStackSize(stack, -1));
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, world, pos, hand, side, par8, par9, par10);
    }

    @SubscribeEvent
    public void onHoe(UseHoeEvent event){
        if(ConfigBoolValues.WORMS.isEnabled()){
            World world = event.getWorld();
            if(!world.isRemote){
                BlockPos pos = event.getPos();
                if(world.isAirBlock(pos.up())){
                    IBlockState state = world.getBlockState(pos);
                    if(state.getBlock() instanceof BlockGrass && world.rand.nextFloat() >= 0.95F){
                        ItemStack stack = new ItemStack(InitItems.itemWorm, world.rand.nextInt(2)+1);
                        EntityItem item = new EntityItem(event.getWorld(), pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, stack);
                        world.spawnEntity(item);
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }
}
