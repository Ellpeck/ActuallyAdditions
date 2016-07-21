/*
 * This file ("ClientEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.inventory.gui.EnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEvents{

    private static final String ADVANCED_INFO_TEXT_PRE = TextFormatting.DARK_GRAY+"     ";
    private static final String ADVANCED_INFO_HEADER_PRE = TextFormatting.GRAY+"  -";

    private static EnergyDisplay energyDisplay;

    public ClientEvents(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTooltipEvent(ItemTooltipEvent event){
        //Advanced Item Info
        if(event.getItemStack().getItem() != null){
            if(ConfigBoolValues.CTRL_EXTRA_INFO.isEnabled()){
                if(GuiScreen.isCtrlKeyDown()){
                    event.getToolTip().add(TextFormatting.DARK_GRAY+""+TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".extraInfo.desc")+":");

                    //OreDict Names
                    int[] oreIDs = OreDictionary.getOreIDs(event.getItemStack());
                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".oredictName.desc")+":");
                    if(oreIDs.length > 0){
                        for(int oreID : oreIDs){
                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+OreDictionary.getOreName(oreID));
                        }
                    }
                    else{
                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".noOredictNameAvail.desc"));
                    }

                    //Code Name
                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".codeName.desc")+":");
                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+Item.REGISTRY.getNameForObject(event.getItemStack().getItem()));

                    //Base Item's Unlocalized Name
                    String baseName = event.getItemStack().getItem().getUnlocalizedName();
                    if(baseName != null){
                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".baseUnlocName.desc")+":");
                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+baseName);
                    }

                    //Metadata
                    int meta = event.getItemStack().getItemDamage();
                    int max = event.getItemStack().getMaxDamage();
                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".meta.desc")+":");
                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+meta+(max > 0 ? "/"+max : ""));

                    //Unlocalized Name
                    String metaName = event.getItemStack().getItem().getUnlocalizedName(event.getItemStack());
                    if(metaName != null && baseName != null && !metaName.equals(baseName)){
                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".unlocName.desc")+":");
                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+metaName);
                    }

                    //NBT
                    NBTTagCompound compound = event.getItemStack().getTagCompound();
                    if(compound != null && !compound.hasNoTags()){
                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".nbt.desc")+":");
                        if(GuiScreen.isShiftKeyDown()){
                            List<String> strgList = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(compound.toString(), 200);
                            for(String strg : strgList){
                                event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+strg);
                            }
                        }
                        else{
                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+TextFormatting.ITALIC+"["+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".pressShift.desc")+"]");
                        }
                    }

                    //Disabling Info
                    event.getToolTip().addAll(Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".disablingInfo.desc"), 200));

                }
                else{
                    if(ConfigBoolValues.CTRL_INFO_FOR_EXTRA_INFO.isEnabled()){
                        event.getToolTip().add(TextFormatting.DARK_GRAY+""+TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".ctrlForMoreInfo.desc"));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Post event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL && Minecraft.getMinecraft().currentScreen == null){
            Minecraft minecraft = Minecraft.getMinecraft();
            Profiler profiler = minecraft.mcProfiler;
            EntityPlayer player = minecraft.thePlayer;
            RayTraceResult posHit = minecraft.objectMouseOver;
            FontRenderer font = minecraft.fontRendererObj;
            ItemStack stack = player.getHeldItemMainhand();

            profiler.startSection(ModUtil.MOD_ID+"Hud");

            if(stack != null){
                if(stack.getItem() instanceof IHudDisplay){
                    profiler.startSection("ItemHudDisplay");
                    ((IHudDisplay)stack.getItem()).displayHud(minecraft, player, stack, posHit, profiler, event.getResolution());
                    profiler.endSection();
                }
            }

            if(posHit != null && posHit.getBlockPos() != null){
                Block blockHit = minecraft.theWorld.getBlockState(posHit.getBlockPos()).getBlock();
                TileEntity tileHit = minecraft.theWorld.getTileEntity(posHit.getBlockPos());

                if(blockHit instanceof IHudDisplay){
                    profiler.startSection("BlockHudDisplay");
                    ((IHudDisplay)blockHit).displayHud(minecraft, player, stack, posHit, profiler, event.getResolution());
                    profiler.endSection();
                }

                if(tileHit instanceof TileEntityBase){
                    TileEntityBase base = (TileEntityBase)tileHit;
                    if(base.isRedstoneToggle()){
                        profiler.startSection("RedstoneToggleHudDisplay");

                        String strg = "Redstone Mode: "+TextFormatting.DARK_RED+(base.isPulseMode ? "Pulse" : "Deactivation")+TextFormatting.RESET;
                        font.drawStringWithShadow(strg, event.getResolution().getScaledWidth()/2+5, event.getResolution().getScaledHeight()/2+5, StringUtil.DECIMAL_COLOR_WHITE);

                        if(stack != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockRedstoneTorch){
                            String expl = TextFormatting.GREEN+"Right-Click to toggle!";
                            font.drawStringWithShadow(expl, event.getResolution().getScaledWidth()/2+5, event.getResolution().getScaledHeight()/2+15, StringUtil.DECIMAL_COLOR_WHITE);
                        }

                        profiler.endSection();
                    }
                }

                if(tileHit instanceof IEnergyDisplay){
                    IEnergyDisplay display = (IEnergyDisplay)tileHit;
                    if(!display.needsHoldShift() || player.isSneaking()){
                        profiler.startSection("EnergyDisplay");

                        if(energyDisplay == null){
                            energyDisplay = new EnergyDisplay(0, 0, null);
                        }
                        energyDisplay.setData(2, event.getResolution().getScaledHeight()-96, display.getEnergyStorage(), true, true);

                        GlStateManager.pushMatrix();
                        GlStateManager.color(1F, 1F, 1F, 1F);
                        energyDisplay.draw();
                        GlStateManager.popMatrix();

                        profiler.endSection();
                    }
                }
            }

            profiler.endSection();
        }
    }

}
