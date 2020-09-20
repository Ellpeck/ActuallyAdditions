package de.ellpeck.actuallyadditions.common.misc.cloud;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.common.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.common.items.InitItems;
import de.ellpeck.actuallyadditions.common.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.common.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class SmileyCloudEasterEggs {

    public static final List<ISmileyCloudEasterEgg> CLOUD_STUFF = new ArrayList<>();

    static {
        //Glenthor
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "glenthor", "glenthorlp", "twoofeight" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(true, new ItemStack(Items.GREEN_DYE, 1));
                renderHeadBlock(InitBlocks.blockHeatCollector.get(), 0, 5F);
            }
        });
        //Ellpeck
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "ellpeck", "ellopecko", "peck" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(InitItems.itemPhantomConnector));
                renderHeadBlock(InitBlocks.blockPhantomLiquiface.get(), 0, 25F);
            }
        });
        //Tyrex
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "tyrex", "lord_tobinho", "tobinho" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.FISHING_ROD));
                renderHoldingItem(true, new ItemStack(Items.TROPICAL_FISH));
            }
        });
        //Hose
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "dqmhose", "xdqmhose", "hose" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.KELP));
                renderHeadBlock(Blocks.TORCH, 0, 15F);
            }
        });
        //Tobi
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "jemx", "jemxx", "jemxxx", "spielertobi200" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(true, new ItemStack(Items.MILK_BUCKET));
                renderHeadBlock(Blocks.REDSTONE_LAMP, 0, 35F);
            }
        });
        //Vazkii
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "vazkii", "vaski", "waskie" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(true, new ItemStack(Items.BONE_MEAL, 1));
                renderHeadBlock(Blocks.POPPY, 0, 20F);
            }
        });
        //Kitty
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "kitty", "kiddy", "kittyvancat", "kittyvancatlp" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(true, new ItemStack(Items.TROPICAL_FISH));
                renderHoldingItem(false, new ItemStack(Items.MILK_BUCKET));
                renderHeadBlock(Blocks.PURPLE_WOOL, 0, 15F);
            }
        });
        //Canitzp
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "canitz", "canitzp", "kannnichts", "kannnichtsp" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.WOODEN_SWORD));
                renderHeadBlock(Blocks.CHEST, 0, 70F);
            }
        });
        //Lari
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "lari", "larixine", "xine", "laxi", "lachsirine", "lala", "lalilu" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.IRON_HELMET));
                renderHeadBlock(InitBlocks.blockBlackLotus.get(), 0, 28F);
            }
        });
        //RotesDing
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "rotesding", "dotesring" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.MILK_BUCKET));
                renderHoldingItem(true, new ItemStack(Items.RED_DYE, 1));
                renderHeadBlock(Blocks.RED_WOOL, 0, 18F);
            }
        });
        //Bande
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "bande", "bandelenth" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.DIAMOND_PICKAXE));
                renderHeadBlock(Blocks.YELLOW_WOOL, 0, 18F);
            }
        });
        //Wolle
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "wolle", "wuitoi" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.STRING));
                renderHeadBlock(Blocks.WHITE_WOOL, 0, 18F);
            }
        });
        //Pakto
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "pakto", "paktosan", "paktosanlp" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.PINK_DYE, 1));
                renderHeadBlock(InitBlocks.blockColoredLampOn.get(), 6, 18F);
            }
        });
        //Honka
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "honka", "honkalonka", "lonka", "lonki" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(InitItems.itemLeafBlowerAdvanced, 1));
                renderHeadBlock(Blocks.HAY_BLOCK, 0, 74F);
            }
        });
        //Acid
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "acid", "acid_blues", "acidblues" };
            }

            @Override
            public void renderExtra(float f) {
                // todo: replace with proper item name
                renderHoldingItem(false, new ItemStack(InitItems.itemFoods, 1));
                renderHeadBlock(Blocks.BOOKSHELF, 0, 27F);
            }
        });
        //Jasin
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "jasin", "jasindow" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.WRITTEN_BOOK));
                renderHeadBlock(Blocks.COBWEB, 0, 56F);
            }
        });
        //ShadowNinjaCat
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "shadowninjacat", "ninja", "tl" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.DIAMOND_SWORD));
                renderHeadBlock(Blocks.DIAMOND_BLOCK, 0, 26F);
            }
        });
        //NihonTiger
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "nihon", "nihontiger", "tiger" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.STONE_PICKAXE));
                renderHoldingItem(true, new ItemStack(Items.POISONOUS_POTATO));
                renderHeadBlock(Blocks.GRAVEL, 0, 47F);
            }
        });
        //FrauBaerchen
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "fraubaerchen", "baerchen", "nina" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.COOKIE));
                renderHoldingItem(true, new ItemStack(Items.PAPER));
                renderHeadBlock(Blocks.COAL_BLOCK, 0, 60F);
            }
        });
        //Diddi
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "0xdd", "didi", "diddi", "theultimatehose" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(true, new ItemStack(InitItems.itemDrill));
                renderHeadBlock(Blocks.REDSTONE_BLOCK, 0, 30F);
            }
        });
        //MineLoad
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "mineload", "miney", "loady", "mineyloady" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(InitItems.itemMagnetRing));
                renderHeadBlock(Blocks.CRAFTING_TABLE, 0, 35F);
            }
        });
        //Kilobyte (When I asked him if he liked the mod, he just looked at the code. Maybe he'll find this eventually.)
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "kilobyte", "kilo", "byte" };
            }

            @Override
            public void renderExtra(float f) {
                // todo: replace this with the right thing
                renderHoldingItem(false, new ItemStack(InitItems.itemMisc, 1));// TheMiscItems.DRILL_CORE.ordinal()));
                renderHeadBlock(Blocks.REDSTONE_ORE, 0, 80F);
            }
        });
        //XDjackieXD
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "jackie", "xdjackiexd", "xdjackie", "jackiexd" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.ENCHANTED_BOOK));
                renderHeadBlock(InitBlocks.blockDirectionalBreaker.get(), 0, 40F);
            }
        });
        //Little Lampi (I still can't get over it)
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "lampi", "littlelampi", "little lampi" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.GLOWSTONE_DUST));
                renderHeadBlock(InitBlocks.blockColoredLampOn.get(), 4, 40F);// todo: fix meta
            }
        });
        //AtomSponge
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "sponge", "atomsponge", "atom", "explosions", "explosion" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.GUNPOWDER));
                renderHeadBlock(Blocks.SPONGE, 0, 20F);
            }
        });
        //Mattzimann
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "mattzimann", "mattziman", "matziman", "marzipan", "mattzi" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(InitItems.itemSwordEmerald));
                renderHeadBlock(InitBlocks.blockCoffeeMachine.get(), 0, 35F);
            }
        });
        //Etho
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "etho", "ethoslab" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.REDSTONE));
                renderHeadBlock(Blocks.REDSTONE_BLOCK, 0, 48F);
            }
        });
        //Direwolf20
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "direwolf", "dire", "direwolf20", "dw20" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(Items.BONE));
                renderHeadBlock(Blocks.BONE_BLOCK, 0, 48F);
            }
        });
        //Jessassin
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "jessassin", "thejessassin" };
            }

            @Override
            public void renderExtra(float f) {
                renderHeadBlock(InitBlocks.blockLaserRelayItem.get(), 0, 27F);
                renderHoldingItem(false, new ItemStack(InitItems.itemLaserWrench));
            }
        });
        //Biffa
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "biffa", "biffatech", "biffaplays", "biffa2001" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(InitItems.itemCoffee));
            }
        });
        //Xisuma
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "xisuma", "xisumavoid" };
            }

            @Override
            public void renderExtra(float f) {
                renderHeadBlock(Blocks.REDSTONE_BLOCK, 0, 93F);
                renderHoldingItem(false, new ItemStack(Items.ELYTRA));
            }
        });
        //Welsknight
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "welsknight", "wels" };
            }

            @Override
            public void renderExtra(float f) {
                renderHeadBlock(Blocks.STONE_BRICK_STAIRS, 0, 10F);
                renderHoldingItem(false, new ItemStack(Items.DIAMOND_PICKAXE));
            }
        });
        //xbony2
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "bony", "xbony", "xbony2" };
            }

            @Override
            public void renderExtra(float f) {
                renderHoldingItem(false, new ItemStack(InitItems.itemBooklet));
                renderHeadBlock(InitBlocks.blockSmileyCloud.get(), 0, 13F);
            }
        });
        //MattaBase
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "themattabase", "mattabase", "matt", "mad" };
            }

            @Override
            public void renderExtra(float f) {
                renderHeadBlock(Blocks.GREEN_WOOL, 0, 35F);
                renderHoldingItem(false, new ItemStack(InitItems.itemSwordQuartz));
                renderHoldingItem(true, new ItemStack(Items.SHIELD));
            }
        });
        //Cloudy
        register(new ISmileyCloudEasterEgg() {
            @Override
            public String[] getTriggerNames() {
                return new String[] { "cloudy", "cloudhunter" };
            }

            @Override
            public void renderExtra(float f) {
                renderHeadBlock(Blocks.REDSTONE_BLOCK, 0, 17F);
                //other hand is for fapping
                renderHoldingItem(true, new ItemStack(Items.BOW));
            }
        });
    }

    private static void register(ISmileyCloudEasterEgg egg) {
        CLOUD_STUFF.add(egg);
    }

    // todo: migrate to matrix
    private static void renderHoldingItem(boolean leftHand, ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.rotatef(180F, 0F, 0F, 1F);
        GlStateManager.rotatef(90, 0, 1, 0);
        GlStateManager.translatef(-0.15f, -1F, leftHand ? -0.525F : 0.525F);
        GlStateManager.scalef(0.75F, 0.75F, 0.75F);

        AssetUtil.renderItemInWorld(stack);

        GlStateManager.popMatrix();
    }

    private static void renderHeadBlock(Block block, int meta, float rotation) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translatef(-0.015F, 0.625F, 0.04F);
        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        GlStateManager.rotatef(180F, 1F, 0F, 0F);
        GlStateManager.rotatef(rotation, 0F, 1F, 0F);

        AssetUtil.renderBlockInWorld(block, meta);

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
