/*
 * This file ("ItemBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBooklet extends ItemBase implements IHudDisplay{

    @SideOnly(Side.CLIENT)
    public static EntrySet forcedEntry;

    public ItemBooklet(String name){
        super(name);
        this.setMaxStackSize(16);
        this.setMaxDamage(0);
    }


    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ){
        if(player.isSneaking()){
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            ItemStack blockStack = new ItemStack(block, 1, block.getMetaFromState(state));
            BookletPage page = BookletUtils.getFirstPageForStack(blockStack);
            if(page != null){
                if(world.isRemote){
                    forcedEntry = new EntrySet(page, page.getChapter(), page.getChapter().getEntry(), ActuallyAdditionsAPI.BOOKLET_ENTRIES.indexOf(page.getChapter().getEntry())/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1);
                }
                this.onItemRightClick(stack, world, player, hand);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.BOOK.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);

        if(!world.isRemote){
            player.addStat(TheAchievements.OPEN_BOOKLET.chieve);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+"."+this.getBaseName()+".desc"));
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, Profiler profiler, ScaledResolution resolution){
        if(posHit != null && posHit.getBlockPos() != null){
            IBlockState state = minecraft.theWorld.getBlockState(posHit.getBlockPos());
            Block block = state.getBlock();
            if(block != null && !block.isAir(minecraft.theWorld.getBlockState(posHit.getBlockPos()), minecraft.theWorld, posHit.getBlockPos())){
                ItemStack blockStack = new ItemStack(block, 1, block.getMetaFromState(state));
                int height = resolution.getScaledHeight()/5*3;
                if(player.isSneaking()){
                    BookletPage page = BookletUtils.getFirstPageForStack(blockStack);
                    if(page != null){
                        String strg1 = page.getChapter().getLocalizedName();
                        String strg2 = "Page "+page.getID();
                        String strg3 = "Right-Click to open...";

                        AssetUtil.renderStackToGui(page.getChapter().getDisplayItemStack() != null ? page.getChapter().getDisplayItemStack() : new ItemStack(InitItems.itemBooklet), resolution.getScaledWidth()/2-10, height+41, 1F);
                        minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg1, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg1)/2, height+20, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg2, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg2)/2, height+30, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.GOLD+strg3, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg3)/2, height+60, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                    else{
                        String strg = TextFormatting.DARK_RED+"No Info available! Sorry :(";
                        minecraft.fontRendererObj.drawStringWithShadow(strg, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg)/2, height+60, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                }
                else{
                    String strg = TextFormatting.DARK_GREEN+""+TextFormatting.ITALIC+"Sneak!";
                    minecraft.fontRendererObj.drawStringWithShadow(strg, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg)/2, height+60, StringUtil.DECIMAL_COLOR_WHITE);
                }
            }
        }
    }
}
