package de.ellpeck.actuallyadditions.mod.items;

import cofh.api.energy.IEnergyContainerItem;
import de.canitzp.rarmor.api.InventoryBase;
import de.canitzp.rarmor.api.modules.IRarmorModule;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.lens.LensConversion;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.lens.Lenses;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import java.util.ArrayList;
import java.util.List;

@Optional.Interface(modid = "rarmor", iface = "de.canitzp.rarmor.api.modules.IRarmorModule")
public class ItemRarmorModuleReconstructor extends ItemBase implements IRarmorModule{

    public ItemRarmorModuleReconstructor(String name){
        super(name);

        this.setMaxStackSize(1);
    }

    @Override
    public String getUniqueName(){
        return this.getRegistryName().toString();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public ModuleType getModuleType(){
        return ModuleType.ACTIVE;
    }

    @Override
    public List<String> getGuiHelp(){
        List text = new ArrayList<String>();
        text.add("Read more about this in the");
        text.add("Actually Additions Manual!");
        return text;
    }

    @Override
    public void onModuleTickInArmor(World world, EntityPlayer player, ItemStack armorChestplate, ItemStack module, InventoryBase inventory){
        if(!world.isRemote && player.isSneaking() && player.onGround){
            if(world.getTotalWorldTime()%50 == 0){
                RayTraceResult result = WorldUtil.getNearestPositionWithAir(world, player, Lenses.LENS_CONVERSION.getDistance());
                if(result != null){
                    BlockPos pos = result.getBlockPos();
                    if(pos != null){
                        IAtomicReconstructor fake = this.getFakeReconstructor(world, player, armorChestplate);

                        int energyUse = TileEntityAtomicReconstructor.ENERGY_USE*2;
                        if(fake.getEnergy() >= energyUse){
                            Lenses.LENS_CONVERSION.invoke(world.getBlockState(pos), pos, fake);

                            EnumFacing hit = result.sideHit;
                            TileEntityAtomicReconstructor.shootLaser(world, player.posX-player.width/2, player.posY+player.getYOffset()+player.getEyeHeight()/2, player.posZ-player.width/2, pos.getX()+hit.getFrontOffsetX(), pos.getY()+hit.getFrontOffsetY(), pos.getZ()+hit.getFrontOffsetZ(), Lenses.LENS_CONVERSION);

                            fake.extractEnergy(energyUse);
                        }
                    }
                }
            }
        }
    }

    private IAtomicReconstructor getFakeReconstructor(final World world, final EntityPlayer player, final ItemStack armorChestplate){
        return new IAtomicReconstructor(){
            @Override
            public int getX(){
                return MathHelper.floor_double(player.posX);
            }

            @Override
            public int getY(){
                return MathHelper.floor_double(player.posY);
            }

            @Override
            public int getZ(){
                return MathHelper.floor_double(player.posZ);
            }

            @Override
            public World getWorldObject(){
                return world;
            }

            @Override
            public void extractEnergy(int amount){
                Item item = armorChestplate.getItem();
                if(item instanceof IEnergyContainerItem){
                    ((IEnergyContainerItem)item).extractEnergy(armorChestplate, amount, false);
                }
            }

            @Override
            public int getEnergy(){
                Item item = armorChestplate.getItem();
                if(item instanceof IEnergyContainerItem){
                    return ((IEnergyContainerItem)item).getEnergyStored(armorChestplate);
                }
                else{
                    return 0;
                }
            }

            @Override
            public Lens getLens(){
                return Lenses.LENS_CONVERSION;
            }
        };
    }
}
