package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;

public class RecipeUnlocker {
    private static String modtag;
    private static int version;
    private static String MODID;

    public static void register(String modid, IEventBus bus, int recipeversion) {
        modtag = modid + "_unlocked";
        version = recipeversion;
        MODID = modid;
        bus.addListener(RecipeUnlocker::onPlayerLoggedIn);
    }

    private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        CompoundTag tag = player.getPersistentData();
        if (tag.contains(modtag) && tag.getInt(modtag) >= version) {
            return;
        }

        if (player instanceof ServerPlayer) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                var recipes = new ArrayList<>(server.getRecipeManager().getRecipes());
                recipes.removeIf((recipe -> !recipe.id().getNamespace().contains(MODID)));
                player.awardRecipes(recipes);
                tag.putInt(modtag, version);
            }
        }
    }
}
