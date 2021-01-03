package de.ellpeck.actuallyadditions.common.tiles;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ActuallyTiles {
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ActuallyAdditions.MOD_ID);

    public static final RegistryObject<TileEntityType<FeederTileEntity>> FEEDER_TILE = TILES.register("feeder_tile", () -> TileEntityType.Builder.create(FeederTileEntity::new, ActuallyBlocks.FEEDER.get()).build(null));
}
