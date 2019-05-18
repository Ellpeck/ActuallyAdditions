/*
 * This file ("MethodHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl;

import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.internal.IMethodHandler;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.BlockLaserRelay;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapter;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapterTrials;
import de.ellpeck.actuallyadditions.mod.booklet.page.PageCrafting;
import de.ellpeck.actuallyadditions.mod.booklet.page.PageFurnace;
import de.ellpeck.actuallyadditions.mod.booklet.page.PagePicture;
import de.ellpeck.actuallyadditions.mod.booklet.page.PageTextOnly;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class MethodHandler implements IMethodHandler {

    @Override
    public boolean addEffectToStack(ItemStack stack, CoffeeIngredient ingredient) {
        boolean worked = false;
        if (ingredient != null) {
            PotionEffect[] effects = ingredient.getEffects();
            if (effects != null && effects.length > 0) {
                for (PotionEffect effect : effects) {
                    PotionEffect effectHas = this.getSameEffectFromStack(stack, effect);
                    if (effectHas != null) {
                        if (effectHas.getAmplifier() < ingredient.getMaxAmplifier() - 1) {
                            this.addEffectProperties(stack, effect, false, true);
                            worked = true;
                        }
                    } else {
                        this.addEffectToStack(stack, effect);
                        worked = true;
                    }
                }
            }
        }
        return worked;
    }

    @Override
    public PotionEffect getSameEffectFromStack(ItemStack stack, PotionEffect effect) {
        PotionEffect[] effectsStack = this.getEffectsFromStack(stack);
        if (effectsStack != null && effectsStack.length > 0) {
            for (PotionEffect effectStack : effectsStack) {
                if (effect.getPotion() == effectStack.getPotion()) { return effectStack; }
            }
        }
        return null;
    }

    @Override
    public void addEffectProperties(ItemStack stack, PotionEffect effect, boolean addDur, boolean addAmp) {
        PotionEffect[] effects = this.getEffectsFromStack(stack);
        stack.setTagCompound(new NBTTagCompound());
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].getPotion() == effect.getPotion()) {
                effects[i] = new PotionEffect(effects[i].getPotion(), effects[i].getDuration() + (addDur ? effect.getDuration() : 0), effects[i].getAmplifier() + (addAmp ? effect.getAmplifier() > 0 ? effect.getAmplifier() : 1 : 0));
            }
            this.addEffectToStack(stack, effects[i]);
        }
    }

    @Override
    public void addEffectToStack(ItemStack stack, PotionEffect effect) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
        }

        int prevCounter = tag.getInteger("Counter");
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("ID", Potion.getIdFromPotion(effect.getPotion()));
        compound.setInteger("Duration", effect.getDuration());
        compound.setInteger("Amplifier", effect.getAmplifier());

        int counter = prevCounter + 1;
        tag.setTag(counter + "", compound);
        tag.setInteger("Counter", counter);

        stack.setTagCompound(tag);
    }

    @Override
    public PotionEffect[] getEffectsFromStack(ItemStack stack) {
        ArrayList<PotionEffect> effects = new ArrayList<>();
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            int counter = tag.getInteger("Counter");
            while (counter > 0) {
                NBTTagCompound compound = (NBTTagCompound) tag.getTag(counter + "");
                PotionEffect effect = new PotionEffect(Potion.getPotionById(compound.getInteger("ID")), compound.getInteger("Duration"), compound.getByte("Amplifier"));
                effects.add(effect);
                counter--;
            }
        }
        return effects.size() > 0 ? effects.toArray(new PotionEffect[effects.size()]) : null;
    }

    @Override
    public boolean invokeConversionLens(IBlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (hitBlock != null) {
            int range = 1;
            int rangeX = 0;
            int rangeY = 0;
            int rangeZ = 0;

            EnumFacing facing = tile.getOrientation();
            if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
                rangeY = range;

                if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                    rangeX = range;
                } else {
                    rangeZ = range;
                }
            } else {
                rangeX = range;
                rangeZ = range;
            }

            //Converting the Blocks
            for (int reachX = -rangeX; reachX <= rangeX; reachX++) {
                for (int reachZ = -rangeZ; reachZ <= rangeZ; reachZ++) {
                    for (int reachY = -rangeY; reachY <= rangeY; reachY++) {
                        BlockPos pos = new BlockPos(hitBlock.getX() + reachX, hitBlock.getY() + reachY, hitBlock.getZ() + reachZ);
                        if (!tile.getWorldObject().isAirBlock(pos)) {
                            IBlockState state = tile.getWorldObject().getBlockState(pos);
                            if (state.getBlock() instanceof BlockLaserRelay) continue;
                            LensConversionRecipe recipe = LensRecipeHandler.findMatchingRecipe(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), tile.getLens());
                            if (recipe != null && tile.getEnergy() >= recipe.getEnergyUsed()) {
                                ItemStack output = recipe.getOutput();
                                if (StackUtil.isValid(output)) {
                                    tile.getWorldObject().playEvent(2001, pos, Block.getStateId(state));
                                    recipe.transformHook(ItemStack.EMPTY, state, pos, tile);
                                    if (output.getItem() instanceof ItemBlock) {
                                        Block toPlace = Block.getBlockFromItem(output.getItem());
                                        IBlockState state2Place = toPlace.getStateForPlacement(tile.getWorldObject(), pos, facing, 0, 0, 0, output.getMetadata(), FakePlayerFactory.getMinecraft((WorldServer) tile.getWorldObject()), EnumHand.MAIN_HAND);
                                        tile.getWorldObject().setBlockState(pos, state2Place, 2);
                                    } else {
                                        EntityItem item = new EntityItem(tile.getWorldObject(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, output.copy());
                                        tile.getWorldObject().spawnEntity(item);
                                        tile.getWorldObject().setBlockToAir(pos);
                                    }

                                    tile.extractEnergy(recipe.getEnergyUsed());
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            //Converting the Items
            AxisAlignedBB aabb = new AxisAlignedBB(tile.getPosition().getX(), tile.getPosition().getY(), tile.getPosition().getZ(), hitBlock.getX() + 1, hitBlock.getY() + 1, hitBlock.getZ() + 1);
            Vec3i dir = tile.getOrientation().getDirectionVec();
            aabb = aabb.grow(0.02, 0.02, 0.02).expand(dir.getX(), dir.getY(), dir.getZ());
            List<EntityItem> items = tile.getWorldObject().getEntitiesWithinAABB(EntityItem.class, aabb);
            for (EntityItem item : items) {
                ItemStack stack = item.getItem();
                if (!item.isDead && StackUtil.isValid(stack)) {
                    LensConversionRecipe recipe = LensRecipeHandler.findMatchingRecipe(stack, tile.getLens());
                    if (recipe != null) {
                        int itemsPossible = Math.min(tile.getEnergy() / recipe.getEnergyUsed(), stack.getCount());

                        if (itemsPossible > 0) {
                            recipe.transformHook(item.getItem(), null, item.getPosition(), tile);
                            item.setDead();

                            if (stack.getCount() - itemsPossible > 0) {
                                ItemStack stackCopy = stack.copy();
                                stackCopy.shrink(itemsPossible);

                                EntityItem inputLeft = new EntityItem(tile.getWorldObject(), item.posX, item.posY, item.posZ, stackCopy);
                                tile.getWorldObject().spawnEntity(inputLeft);
                            }

                            ItemStack outputCopy = recipe.getOutput().copy();
                            outputCopy.setCount(itemsPossible);

                            EntityItem newItem = new EntityItem(tile.getWorldObject(), item.posX, item.posY, item.posZ, outputCopy);
                            tile.getWorldObject().spawnEntity(newItem);

                            tile.extractEnergy(recipe.getEnergyUsed() * itemsPossible);
                            break;
                        }
                    }
                }
            }
            return !hitState.getBlock().isAir(hitState, tile.getWorldObject(), hitBlock);
        }
        return false;
    }

    @Override
    public boolean invokeReconstructor(IAtomicReconstructor tile) {
        if (tile.getEnergy() >= TileEntityAtomicReconstructor.ENERGY_USE) {
            EnumFacing sideToManipulate = tile.getOrientation();
            Lens currentLens = tile.getLens();
            if (currentLens.canInvoke(tile, sideToManipulate, TileEntityAtomicReconstructor.ENERGY_USE)) {
                tile.extractEnergy(TileEntityAtomicReconstructor.ENERGY_USE);

                int distance = currentLens.getDistance();
                for (int i = 0; i < distance; i++) {
                    BlockPos hitBlock = tile.getPosition().offset(sideToManipulate, i + 1);

                    if (currentLens.invoke(tile.getWorldObject().getBlockState(hitBlock), hitBlock, tile) || i >= distance - 1) {
                        TileEntityAtomicReconstructor.shootLaser(tile.getWorldObject(), tile.getX(), tile.getY(), tile.getZ(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                        break;
                    }
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addCrusherRecipes(List<ItemStack> inputs, List<ItemStack> outputOnes, int outputOneAmounts, List<ItemStack> outputTwos, int outputTwoAmounts, int outputTwoChance) {
        boolean hasWorkedOnce = false;
        for (ItemStack input : inputs) {
            if (StackUtil.isValid(input) && CrusherRecipeRegistry.getRecipeFromInput(input) == null) {
                for (ItemStack outputOne : outputOnes) {
                    if (StackUtil.isValid(outputOne) && !CrusherRecipeRegistry.hasBlacklistedOutput(outputOne, ConfigStringListValues.CRUSHER_OUTPUT_BLACKLIST.getValue())) {
                        ItemStack outputOneCopy = outputOne.copy();
                        outputOneCopy.setCount(outputOneAmounts);

                        if (outputTwos.isEmpty()) {
                            ActuallyAdditionsAPI.addCrusherRecipe(input, outputOneCopy, StackUtil.getEmpty(), 0);
                            hasWorkedOnce = true;
                        } else {
                            for (ItemStack outputTwo : outputTwos) {
                                if (StackUtil.isValid(outputTwo) && !CrusherRecipeRegistry.hasBlacklistedOutput(outputTwo, ConfigStringListValues.CRUSHER_OUTPUT_BLACKLIST.getValue())) {
                                    ItemStack outputTwoCopy = outputTwo.copy();
                                    outputTwoCopy.setCount(outputTwoAmounts);

                                    ActuallyAdditionsAPI.addCrusherRecipe(input, outputOneCopy, outputTwoCopy, outputTwoChance);
                                    hasWorkedOnce = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return hasWorkedOnce;
    }

    @Override
    public boolean addCrusherRecipes(List<ItemStack> inputs, ItemStack outputOne, int outputOneAmount, ItemStack outputTwo, int outputTwoAmount, int outputTwoChance) {
        boolean hasWorkedOnce = false;
        for (ItemStack input : inputs) {
            if (StackUtil.isValid(input) && CrusherRecipeRegistry.getRecipeFromInput(input) == null) {
                if (StackUtil.isValid(outputOne) && !CrusherRecipeRegistry.hasBlacklistedOutput(outputOne, ConfigStringListValues.CRUSHER_OUTPUT_BLACKLIST.getValue())) {
                    ItemStack outputOneCopy = outputOne.copy();
                    outputOneCopy.setCount(outputOneAmount);

                    if (!StackUtil.isValid(outputTwo)) {
                        ActuallyAdditionsAPI.addCrusherRecipe(input, outputOneCopy, StackUtil.getEmpty(), 0);
                        hasWorkedOnce = true;
                    } else if (StackUtil.isValid(outputTwo) && !CrusherRecipeRegistry.hasBlacklistedOutput(outputTwo, ConfigStringListValues.CRUSHER_OUTPUT_BLACKLIST.getValue())) {
                        ItemStack outputTwoCopy = outputTwo.copy();
                        outputTwoCopy.setCount(outputTwoAmount);

                        ActuallyAdditionsAPI.addCrusherRecipe(input, outputOneCopy, outputTwoCopy, outputTwoChance);
                        hasWorkedOnce = true;
                    }
                }
            }
        }
        return hasWorkedOnce;
    }

    @Override
    public IBookletPage generateTextPage(int id) {
        return this.generateTextPage(id, 0);
    }

    @Override
    public IBookletPage generatePicturePage(int id, ResourceLocation resLoc, int textStartY) {
        return this.generatePicturePage(id, resLoc, textStartY, 0);
    }

    @Override
    public IBookletPage generateCraftingPage(int id, IRecipe... recipes) {
        return this.generateCraftingPage(id, 0, recipes);
    }

    @Override
    public IBookletPage generateFurnacePage(int id, ItemStack input, ItemStack result) {
        return this.generateFurnacePage(id, input, result, 0);
    }

    @Override
    public IBookletChapter generateBookletChapter(String identifier, IBookletEntry entry, ItemStack displayStack, IBookletPage... pages) {
        return this.generateBookletChapter(identifier, entry, displayStack, 0, pages);
    }

    @Override
    public IBookletPage generateTextPage(int id, int priority) {
        return new PageTextOnly(id, priority);
    }

    @Override
    public IBookletPage generatePicturePage(int id, ResourceLocation resLoc, int textStartY, int priority) {
        return new PagePicture(id, resLoc, textStartY, priority);
    }

    @Override
    public IBookletPage generateCraftingPage(int id, int priority, IRecipe... recipes) {
        return new PageCrafting(id, priority, recipes);
    }

    @Override
    public IBookletPage generateFurnacePage(int id, ItemStack input, ItemStack result, int priority) {
        return new PageFurnace(id, result, priority);
    }

    @Override
    public IBookletChapter generateBookletChapter(String identifier, IBookletEntry entry, ItemStack displayStack, int priority, IBookletPage... pages) {
        return new BookletChapter(identifier, entry, displayStack, priority, pages);
    }

    @Override
    public IBookletChapter createTrial(String identifier, ItemStack displayStack, boolean textOnSecondPage) {
        return new BookletChapterTrials(identifier, displayStack, textOnSecondPage);
    }
}
