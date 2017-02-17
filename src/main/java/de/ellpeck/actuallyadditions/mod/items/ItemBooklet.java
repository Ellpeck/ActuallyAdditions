/*
 * This file ("ItemBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
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
    public static IBookletPage forcedPage;

    public ItemBooklet(String name){
        super(name);
        this.setMaxStackSize(16);
        this.setMaxDamage(0);
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ){
        if(player.isSneaking()){
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            ItemStack blockStack = new ItemStack(block, 1, block.damageDropped(state));
            IBookletPage page = BookletUtils.findFirstPageForStack(blockStack);
            if(page != null){
                if(world.isRemote){
                    forcedPage = page;
                }
                this.onItemRightClick(world, player, hand);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.BOOK.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);

        if(!world.isRemote){
            TheAchievements.OPEN_BOOKLET.get(player);
            TheAchievements.OPEN_BOOKLET_MILESTONE.get(player);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID+"."+this.getBaseName()+".desc"));

        for(int i = 1; i <= 4; i++){
            String format = i == 4 ? TextFormatting.GOLD.toString()+TextFormatting.ITALIC : TextFormatting.RESET.toString();
            list.add(format+StringUtil.localize("tooltip."+ModUtil.MOD_ID+"."+this.getBaseName()+".sub."+i));
        }
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        if(posHit != null && posHit.getBlockPos() != null){
            IBlockState state = minecraft.world.getBlockState(posHit.getBlockPos());
            Block block = state.getBlock();
            if(block != null && !block.isAir(minecraft.world.getBlockState(posHit.getBlockPos()), minecraft.world, posHit.getBlockPos())){
                ItemStack blockStack = new ItemStack(block, 1, block.getMetaFromState(state));
                int height = resolution.getScaledHeight()/5*3;
                if(player.isSneaking()){
                    IBookletPage page = BookletUtils.findFirstPageForStack(blockStack);
                    if(page != null){
                        String strg1 = page.getChapter().getLocalizedName();
                        String strg2 = StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.hudDisplay.page")+" "+(page.getChapter().getPageIndex(page)+1);
                        String strg3 = StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.hudDisplay.open");

                        AssetUtil.renderStackToGui(StackUtil.isValid(page.getChapter().getDisplayItemStack()) ? page.getChapter().getDisplayItemStack() : new ItemStack(InitItems.itemBooklet), resolution.getScaledWidth()/2-10, height+41, 1F);
                        minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg1, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg1)/2, height+20, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg2, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg2)/2, height+30, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.GOLD+strg3, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg3)/2, height+60, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                    else{
                        String strg1 = TextFormatting.DARK_RED+StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.hudDisplay.noInfo");
                        String strg2 = TextFormatting.DARK_GREEN+""+TextFormatting.ITALIC+StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.hudDisplay.noInfo.desc.1");
                        String strg3 = TextFormatting.DARK_GREEN+""+TextFormatting.ITALIC+StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.hudDisplay.noInfo.desc.2");

                        minecraft.fontRendererObj.drawStringWithShadow(strg1, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg1)/2, height+30, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRendererObj.drawStringWithShadow(strg2, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg2)/2, height+50, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRendererObj.drawStringWithShadow(strg3, resolution.getScaledWidth()/2-minecraft.fontRendererObj.getStringWidth(strg3)/2, height+60, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                }
            }
        }
    }
}
