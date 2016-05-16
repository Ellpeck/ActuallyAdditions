package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner.EnumBannerPattern;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Locale;

public class BannerHelper{

    public static void init(){
        addCraftingPattern("Drill", new ItemStack(InitItems.itemDrill));
        addCraftingPattern("LeafBlo", new ItemStack(InitItems.itemLeafBlower));
        addCraftingPattern("PhanCon", new ItemStack(InitItems.itemPhantomConnector));
        addCraftingPattern("Book", new ItemStack(InitItems.itemBooklet));
    }

    /**
     * (Excerpted from Additional Banners by Darkhax with permission, thanks!)
     *
     * Adds a new banner pattern to the game. This banner pattern will be applied by using the
     * provided item in a crafting recipe with the banner.
     *
     * @param name          The name of the banner pattern. This is used for the texture file, and is
     *                      also converted into upper case and used for the enum entry. Given how this
     *                      system works, it's critical that this value is unique, consider adding the
     *                      mod id to the name.
     * //@param id            A small string used to represent the pattern without taking up much space. An
     *                      example of this is "bri". Given how the system works, it is critical that
     *                      this is a unique value. please consider adding the mod id to the pattern id.
     * @param craftingStack An ItemStack which is used in the crafting recipe for this pattern.
     *                      An example of this would be the creeper skull being used for the creeper
     *                      pattern.
     * @return EnumBannerPattern: A reference to the new EnumBannerPattern entry that has been
     * created.
     */
    public static EnumBannerPattern addCraftingPattern(String name, ItemStack craftingStack){
        Class<?>[] paramTypes = {String.class, String.class, ItemStack.class};
        Object[] paramValues = {ModUtil.MOD_ID+name, ModUtil.MOD_ID+name, craftingStack};
        return EnumHelper.addEnum(EnumBannerPattern.class, (ModUtil.MOD_ID+"_"+name).toUpperCase(Locale.ROOT), paramTypes, paramValues);
    }

}
