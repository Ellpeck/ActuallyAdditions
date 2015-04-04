package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.items.InitItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolCrafting{

    public static void init(){

        if(ConfigValues.enableToolEmeraldRecipe){
            //Pickaxe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPickaxeEmerald),
                    "EEE", " S ", " S ",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Sword
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemSwordEmerald),
                    "E", "E", "S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Axe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemAxeEmerald),
                    "EE", "ES", " S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Shovel
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemShovelEmerald),
                    "E", "S", "S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));

            //Hoe
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemHoeEmerald),
                    "EE", " S", " S",
                    'E', "gemEmerald",
                    'S', new ItemStack(Items.stick)));
        }

        if(ConfigValues.enableToolObsidianRecipe){
            //Pickaxe
            GameRegistry.addRecipe(new ItemStack(InitItems.itemPickaxeObsidian),
                    "EEE", " S ", " S ",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Sword
            GameRegistry.addRecipe(new ItemStack(InitItems.itemSwordObsidian),
                    "E", "E", "S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Axe
            GameRegistry.addRecipe(new ItemStack(InitItems.itemAxeObsidian),
                    "EE", "ES", " S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Shovel
            GameRegistry.addRecipe(new ItemStack(InitItems.itemShovelObsidian),
                    "E", "S", "S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));

            //Hoe
            GameRegistry.addRecipe(new ItemStack(InitItems.itemHoeObsidian),
                    "EE", " S", " S",
                    'E', new ItemStack(Blocks.obsidian),
                    'S', new ItemStack(Items.stick));
        }
    }

}
