package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemMinecartAA extends ItemBase{

    private static final IBehaviorDispenseItem MINECART_DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem(){
        private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

        @Override
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack){
            EnumFacing facing = BlockDispenser.getFacing(source.getBlockMetadata());
            World world = source.getWorld();
            double d0 = source.getX()+(double)facing.getFrontOffsetX()*1.125D;
            double d1 = Math.floor(source.getY())+(double)facing.getFrontOffsetY();
            double d2 = source.getZ()+(double)facing.getFrontOffsetZ()*1.125D;
            BlockPos pos = source.getBlockPos().offset(facing);
            IBlockState state = world.getBlockState(pos);
            BlockRailBase.EnumRailDirection direction = state.getBlock() instanceof BlockRailBase ? state.getValue(((BlockRailBase)state.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            double d3;

            if(BlockRailBase.isRailBlock(state)){
                if(direction.isAscending()){
                    d3 = 0.6D;
                }
                else{
                    d3 = 0.1D;
                }
            }
            else{
                if(state.getMaterial() != Material.AIR || !BlockRailBase.isRailBlock(world.getBlockState(pos.down()))){
                    return this.behaviourDefaultDispenseItem.dispense(source, stack);
                }

                IBlockState state1 = world.getBlockState(pos.down());
                BlockRailBase.EnumRailDirection direction1 = state1.getBlock() instanceof BlockRailBase ? state1.getValue(((BlockRailBase)state1.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;

                if(facing != EnumFacing.DOWN && direction1.isAscending()){
                    d3 = -0.4D;
                }
                else{
                    d3 = -0.9D;
                }
            }

            EntityMinecart cart = ((ItemMinecartAA)stack.getItem()).createCart(world, d0, d1+d3, d2);

            if(stack.hasDisplayName()){
                cart.setCustomNameTag(stack.getDisplayName());
            }

            world.spawnEntityInWorld(cart);
            stack.splitStack(1);
            return stack;
        }

        @Override
        protected void playDispenseSound(IBlockSource source){
            source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
        }
    };

    public ItemMinecartAA(String name){
        super(name);
        this.maxStackSize = 1;

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, MINECART_DISPENSER_BEHAVIOR);
    }

    public abstract EntityMinecart createCart(World world, double x, double y, double z);

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        IBlockState state = worldIn.getBlockState(pos);

        if(!BlockRailBase.isRailBlock(state)){
            return EnumActionResult.FAIL;
        }
        else{
            if(!worldIn.isRemote){
                BlockRailBase.EnumRailDirection direction = state.getBlock() instanceof BlockRailBase ? state.getValue(((BlockRailBase)state.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
                double d0 = 0.0D;

                if(direction.isAscending()){
                    d0 = 0.5D;
                }

                EntityMinecart cart = ((ItemMinecartAA)stack.getItem()).createCart(worldIn, (double)pos.getX()+0.5D, (double)pos.getY()+0.0625D+d0, (double)pos.getZ()+0.5D);

                if(stack.hasDisplayName()){
                    cart.setCustomNameTag(stack.getDisplayName());
                }

                worldIn.spawnEntityInWorld(cart);
            }

            stack.stackSize--;
            return EnumActionResult.SUCCESS;
        }
    }
}
