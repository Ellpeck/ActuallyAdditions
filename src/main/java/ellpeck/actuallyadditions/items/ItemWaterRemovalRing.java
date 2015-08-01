package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemWaterRemovalRing extends ItemEnergy implements INameableItem{

    private static final int RANGE = ConfigIntValues.WATER_RING_RANGE.getValue();
    private static final int ENERGY_USED_PER_BLOCK = ConfigIntValues.WATER_RING_ENERGY_USE.getValue();

    public ItemWaterRemovalRing(){
        super(1000000, 5000, 2);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!(entity instanceof EntityPlayer) || world.isRemote) return;

        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getCurrentEquippedItem();

        if(equipped != null && equipped == stack && this.getEnergyStored(stack) >= ENERGY_USED_PER_BLOCK){

            //Setting everything to air
            for(int x = -RANGE; x < RANGE+1; x++){
                for(int z = -RANGE; z < RANGE+1; z++){
                    for(int y = -RANGE; y < RANGE+1; y++){
                        int theX = (int)player.posX+x;
                        int theY = (int)player.posY+y;
                        int theZ = (int)player.posZ+z;
                        if(this.getEnergyStored(stack) >= ENERGY_USED_PER_BLOCK){
                            //Remove Water
                            if(world.getBlock(theX, theY, theZ) == Blocks.water || world.getBlock(theX, theY, theZ) == Blocks.flowing_water){
                                world.setBlockToAir(theX, theY, theZ);

                                if(!player.capabilities.isCreativeMode){
                                    this.extractEnergy(stack, ENERGY_USED_PER_BLOCK, false);
                                }
                            }
                            //Remove Lava
                            else if(world.getBlock(theX, theY, theZ) == Blocks.lava || world.getBlock(theX, theY, theZ) == Blocks.flowing_lava){
                                world.setBlockToAir(theX, theY, theZ);

                                if(!player.capabilities.isCreativeMode){
                                    this.extractEnergy(stack, ENERGY_USED_PER_BLOCK*2, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public String getName(){
        return "itemWaterRemovalRing";
    }
}
