/*
 * This file ("SmileyCloudEasterEggs.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc.cloud;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class SmileyCloudEasterEggs{

    public static final ArrayList<ISmileyCloudEasterEgg> cloudStuff = new ArrayList<ISmileyCloudEasterEgg>();

    static{
        //Glenthor
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"glenthor", "glenthorlp", "twoofeight"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(true, new ItemStack(Items.dye, 1, 2));
                renderHeadBlock(InitBlocks.blockHeatCollector, 0, 5F);
            }
        });
        //Ellpeck
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"ellpeck", "ellopecko", "peck"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(InitItems.itemPhantomConnector));
                renderHeadBlock(InitBlocks.blockPhantomLiquiface, 0, 25F);
            }
        });
        //Tyrex
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"tyrex", "lord_tobinho", "tobinho"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.fishing_rod));
                renderHoldingItem(true, new ItemStack(Items.fish));
            }
        });
        //Hose
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"dqmhose", "xdqmhose", "hose"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.reeds));
                renderHeadBlock(Blocks.torch, 0, 15F);
            }
        });
        //Tobi
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"jemx", "jemxx", "jemxxx", "spielertobi200"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(true, new ItemStack(Items.milk_bucket));
                renderHeadBlock(Blocks.lit_redstone_lamp, 0, 35F);
            }
        });
        //Vazkii
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"vazkii", "vaski", "waskie"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(true, new ItemStack(Items.dye, 1, 15));
                renderHeadBlock(Blocks.red_flower, 5, 20F);
            }
        });
        //Kitty
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"kitty", "kiddy", "kittyvancat", "kittyvancatlp"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(true, new ItemStack(Items.fish));
                renderHoldingItem(false, new ItemStack(Items.milk_bucket));
                renderHeadBlock(Blocks.wool, 10, 15F);
            }
        });
        //Canitzp
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"canitz", "canitzp", "kannnichts", "kannnichtsp"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.wooden_sword));
                renderHeadBlock(Blocks.chest, 0, 70F);
            }
        });
        //Lari
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"lari", "larixine", "xine", "laxi", "lachsirine", "lala", "lalilu"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.iron_helmet));
                renderHeadBlock(InitBlocks.blockBlackLotus, 0, 28F);
            }
        });
        //RotesDing
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"rotesding", "dotesring"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.milk_bucket));
                renderHoldingItem(true, new ItemStack(Items.dye, 1, 1));
                renderHeadBlock(Blocks.wool, 14, 18F);
            }
        });
        //Bande
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"bande", "bandelenth"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.diamond_pickaxe));
                renderHeadBlock(Blocks.wool, 4, 18F);
            }
        });
        //Wolle
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"wolle", "wuitoi"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.string));
                renderHeadBlock(Blocks.wool, 0, 18F);
            }
        });
        //Pakto
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"pakto", "paktosan", "paktosanlp"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.dye, 1, 9));
                renderHeadBlock(InitBlocks.blockColoredLampOn, 6, 18F);
            }
        });
        //Honka
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"honka", "honkalonka", "lonka", "lonki"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(InitItems.itemLeafBlowerAdvanced, 1, 9));
                renderHeadBlock(Blocks.hay_block, 0, 74F);
            }
        });
        //Acid
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"acid", "acid_blues", "acidblues"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()));
                renderHeadBlock(Blocks.bookshelf, 0, 27F);
            }
        });
        //Jasin
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"jasin", "jasindow"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.written_book));
                renderHeadBlock(Blocks.web, 0, 56F);
            }
        });
        //ShadowNinjaCat
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"shadowninjacat", "ninja", "tl"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.diamond_sword));
                renderHeadBlock(Blocks.diamond_block, 0, 26F);
            }
        });
        //NihonTiger
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"nihon", "nihontiger", "tiger"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.stone_pickaxe));
                renderHoldingItem(true, new ItemStack(Items.poisonous_potato));
                renderHeadBlock(Blocks.gravel, 0, 47F);
            }
        });
        //FrauBaerchen
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"fraubaerchen", "baerchen", "nina"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.cookie));
                renderHoldingItem(true, new ItemStack(Items.paper));
                renderHeadBlock(Blocks.coal_block, 0, 60F);
            }
        });
        //Diddi
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"0xdd", "didi", "diddi", "theultimatehose"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(true, new ItemStack(InitItems.itemDrill));
                renderHeadBlock(Blocks.redstone_block, 0, 30F);
            }
        });
        //MineLoad
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"mineload", "miney", "loady", "mineyloady"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(InitItems.itemMagnetRing));
                renderHeadBlock(Blocks.crafting_table, 0, 35F);
            }
        });
        //Kilobyte (When I asked him if he liked the mod, he just looked at the code. Maybe he'll find this eventually.)
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"kilobyte", "kilo", "byte"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DRILL_CORE.ordinal()));
                renderHeadBlock(Blocks.redstone_ore, 0, 80F);
            }
        });
        //XDjackieXD
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"jackie", "xdjackiexd", "xdjackie", "jackiexd"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.enchanted_book));
                renderHeadBlock(InitBlocks.blockDirectionalBreaker, 0, 40F);
            }
        });
        //Little Lampi (I still can't get over it)
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"lampi", "littlelampi", "little lampi"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.glowstone_dust));
                renderHeadBlock(InitBlocks.blockColoredLampOn, 4, 40F);
            }
        });
        //AtomSponge
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"sponge", "atomsponge", "atom", "explosions", "explosion"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(Items.gunpowder));
                renderHeadBlock(Blocks.sponge, 0, 20F);
            }
        });
        //Mattzimann
        register(new ISmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"mattzimann", "mattziman", "matziman", "marzipan", "mattzi"};
            }

            @Override
            public void renderExtra(float f){
                renderHoldingItem(false, new ItemStack(InitItems.itemSwordEmerald));
                renderHeadBlock(InitBlocks.blockCoffeeMachine, 0, 35F);
            }
        });
    }

    private static void register(ISmileyCloudEasterEgg egg){
        cloudStuff.add(egg);
    }

    private static void renderHoldingItem(boolean leftHand, ItemStack stack){
        GL11.glPushMatrix();

        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(270F, 0F, 1F, 0F);
        GL11.glTranslatef(0F, -1.5F, 0F);
        GL11.glTranslatef(-0.5F, 0.2F, leftHand ? 0.55F : -0.5F);
        GL11.glScalef(0.75F, 0.75F, 0.75F);

        AssetUtil.renderItem(stack, 0);

        GL11.glPopMatrix();
    }

    private static void renderHeadBlock(Block block, int meta, float rotation){
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef(-0.015F, 0.6F, 0.075F);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        GL11.glRotatef(rotation, 0F, 1F, 0F);

        AssetUtil.renderBlock(block, meta);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
