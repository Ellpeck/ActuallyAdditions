package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityShockSuppressor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MODID)
public class BlockShockSuppressor extends BlockContainerBase {

    public BlockShockSuppressor() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(20.0f, 2000.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            List<BlockPos> affectedBlocks = event.getAffectedBlocks();
            List<Entity> affectedEntities = event.getAffectedEntities();

            int rangeSq = TileEntityShockSuppressor.RANGE * TileEntityShockSuppressor.RANGE;
            int use = TileEntityShockSuppressor.USE_PER;

            for (TileEntityShockSuppressor suppressor : TileEntityShockSuppressor.SUPPRESSORS) {
                if (!suppressor.isRedstonePowered) {
                    BlockPos supPos = suppressor.getPos();

                    List<Entity> entitiesToRemove = new ArrayList<>();
                    List<BlockPos> posesToRemove = new ArrayList<>();

                    for (BlockPos pos : affectedBlocks) {
                        if (pos.distanceSq(supPos) <= rangeSq) {
                            posesToRemove.add(pos);
                        }
                    }
                    for (Entity entity : affectedEntities) {
                        if (entity.getPositionVector().squareDistanceTo(supPos.getX(), supPos.getY(), supPos.getZ()) <= rangeSq) {
                            entitiesToRemove.add(entity);
                        }
                    }

                    Collections.shuffle(entitiesToRemove);
                    Collections.shuffle(posesToRemove);

                    for (BlockPos pos : posesToRemove) {
                        if (suppressor.storage.getEnergyStored() >= use) {
                            suppressor.storage.extractEnergyInternal(use, false);
                            affectedBlocks.remove(pos);
                        } else {
                            break;
                        }
                    }
                    for (Entity entity : entitiesToRemove) {
                        if (suppressor.storage.getEnergyStored() >= use) {
                            suppressor.storage.extractEnergyInternal(use, false);
                            affectedEntities.remove(entity);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityShockSuppressor();
    }
}
