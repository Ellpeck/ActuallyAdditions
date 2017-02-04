/*
 * This file ("CommonCapsUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.SlotlessItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.cyclops.commoncapabilities.api.capability.itemhandler.DefaultSlotlessItemHandlerWrapper;
import org.cyclops.commoncapabilities.api.capability.itemhandler.ISlotlessItemHandler;

public final class CommonCapsUtil{

    public static ISlotlessItemHandler createSlotlessItemViewerHandler(final TileEntityItemViewer tile, IItemHandler normalHandler){
        return new DefaultSlotlessItemHandlerWrapper(normalHandler){
            @Override
            public ItemStack insertItem(ItemStack stack, boolean simulate){
                ItemStack remain = StackUtil.validateCopy(stack);
                for(SlotlessItemHandlerInfo handler : tile.slotlessInfos){
                    if(handler.isLoaded() && tile.isWhitelisted(handler, stack, false)){
                        if(handler.handler instanceof ISlotlessItemHandler){
                            remain = ((ISlotlessItemHandler)handler.handler).insertItem(stack, simulate);

                            if(!ItemStack.areItemStacksEqual(remain, stack) && !simulate){
                                tile.markDirty();
                                tile.doItemParticle(stack, handler.relayInQuestion.getPos(), tile.connectedRelay.getPos());
                            }

                            if(!StackUtil.isValid(remain)){
                                return StackUtil.getNull();
                            }
                        }
                    }
                }
                return super.insertItem(remain, simulate);
            }

            @Override
            public ItemStack extractItem(int amount, boolean simulate){
                for(SlotlessItemHandlerInfo handler : tile.slotlessInfos){
                    if(handler.isLoaded()){
                        if(handler.handler instanceof ISlotlessItemHandler){
                            ISlotlessItemHandler slotless = (ISlotlessItemHandler)handler.handler;

                            ItemStack would = slotless.extractItem(amount, true);
                            if(StackUtil.isValid(would)){
                                if(tile.isWhitelisted(handler, would, true)){
                                    ItemStack has;
                                    if(simulate){
                                        has = would;
                                    }
                                    else{
                                        has = slotless.extractItem(amount, false);
                                    }

                                    if(StackUtil.isValid(has) && !simulate){
                                        tile.markDirty();
                                        tile.doItemParticle(has, tile.connectedRelay.getPos(), handler.relayInQuestion.getPos());
                                    }

                                    return has;
                                }
                            }
                        }
                    }
                }
                return super.extractItem(amount, simulate);
            }

            @Override
            public ItemStack extractItem(ItemStack matchStack, int matchFlags, boolean simulate){
                for(SlotlessItemHandlerInfo handler : tile.slotlessInfos){
                    if(handler.isLoaded()){
                        if(handler.handler instanceof ISlotlessItemHandler){
                            ISlotlessItemHandler slotless = (ISlotlessItemHandler)handler.handler;

                            ItemStack would = slotless.extractItem(matchStack, matchFlags, true);
                            if(StackUtil.isValid(would)){
                                if(tile.isWhitelisted(handler, would, true)){
                                    if(simulate){
                                        return would;
                                    }
                                    else{
                                        return slotless.extractItem(matchStack, matchFlags, false);
                                    }
                                }
                            }
                        }
                    }
                }
                return super.extractItem(matchStack, matchFlags, simulate);
            }
        };
    }

}
