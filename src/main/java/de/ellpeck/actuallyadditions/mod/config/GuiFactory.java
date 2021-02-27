// TODO: [port][note] forge does not support this atm
///*
// * This file ("GuiFactory.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.config;
//
//import java.util.Set;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraftforge.fml.client.IModGuiFactory;
//
//public class GuiFactory implements IModGuiFactory {
//
//    @Override
//    public void initialize(Minecraft minecraftInstance) {
//
//    }
//
//    @Override
//    public boolean hasConfigGui() {
//        return true;
//    }
//
//    @Override
//    public GuiScreen createConfigGui(GuiScreen parentScreen) {
//        return new GuiConfiguration(parentScreen);
//    }
//
//    @Override
//    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
//        return null;
//    }
//}
