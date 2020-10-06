package de.ellpeck.actuallyadditions.common.misc.apiimpl;

import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.internal.IMethodHandler;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.common.blocks.BlockLaserRelay;
import de.ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import de.ellpeck.actuallyadditions.booklet.chapter.BookletChapterTrials;
import de.ellpeck.actuallyadditions.booklet.page.PageCrafting;
import de.ellpeck.actuallyadditions.booklet.page.PageFurnace;
import de.ellpeck.actuallyadditions.booklet.page.PagePicture;
import de.ellpeck.actuallyadditions.booklet.page.PageTextOnly;
import de.ellpeck.actuallyadditions.common.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.common.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.common.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.common.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.util.FakePlayerFactory;

public class MethodHandler implements IMethodHandler {

    @Override
    public boolean addEffectToStack(ItemStack stack, CoffeeIngredient ingredient) {
        boolean worked = false;
        if (ingredient != null) {
            EffectInstance[] effects = ingredient.getEffects();
            if (effects != null && effects.length > 0) {
                for (EffectInstance effect : effects) {
                    EffectInstance effectHas = this.getSameEffectFromStack(stack, effect);
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
    public EffectInstance getSameEffectFromStack(ItemStack stack, EffectInstance effect) {
        EffectInstance[] effectsStack = this.getEffectsFromStack(stack);
        if (effectsStack != null && effectsStack.length > 0) {
            for (EffectInstance effectStack : effectsStack) {
                if (effect.getPotion() == effectStack.getPotion()) { return effectStack; }
            }
        }
        return null;
    }

    @Override
    public void addEffectProperties(ItemStack stack, EffectInstance effect, boolean addDur, boolean addAmp) {
        EffectInstance[] effects = this.getEffectsFromStack(stack);
        stack.setTag(new CompoundNBT());
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].getPotion() == effect.getPotion()) {
                effects[i] = new EffectInstance(effects[i].getPotion(), effects[i].getDuration() + (addDur ? effect.getDuration() : 0), effects[i].getAmplifier() + (addAmp ? effect.getAmplifier() > 0 ? effect.getAmplifier() : 1 : 0));
            }
            this.addEffectToStack(stack, effects[i]);
        }
    }

    @Override
    public void addEffectToStack(ItemStack stack, EffectInstance effect) {
        CompoundNBT tag = stack.getOrCreateTag();

        int prevCounter = tag.getInt("Counter");
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("ID", Potion.getIdFromPotion(effect.getPotion()));
        compound.putInt("Duration", effect.getDuration());
        compound.putInt("Amplifier", effect.getAmplifier());

        int counter = prevCounter + 1;
        tag.put(counter + "", compound);
        tag.putInt("Counter", counter);

        stack.setTag(tag);
    }

    @Override
    public EffectInstance[] getEffectsFromStack(ItemStack stack) {
        ArrayList<EffectInstance> effects = new ArrayList<>();
        CompoundNBT tag = stack.getOrCreateTag();
        int counter = tag.getInt("Counter");
        while (counter > 0) {
            CompoundNBT compound = (CompoundNBT) tag.get(counter + "");
            EffectInstance effect = new EffectInstance(Potion.getPotionById(compound.getInt("ID")), compound.getInt("Duration"), compound.getByte("Amplifier"));
            effects.add(effect);
            counter--;
        }
        return effects.size() > 0 ? effects.toArray(new EffectInstance[effects.size()]) : null;
    }

    @Override
    public boolean invokeConversionLens(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (hitBlock != null) {
            int range = 1;
            int rangeX = 0;
            int rangeY = 0;
            int rangeZ = 0;

            Direction facing = tile.getOrientation();
            if (facing != Direction.UP && facing != Direction.DOWN) {
                rangeY = range;

                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
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
                            BlockState state = tile.getWorldObject().getBlockState(pos);
                            if (state.getBlock() instanceof BlockLaserRelay) continue;
                            LensConversionRecipe recipe = LensRecipeHandler.findMatchingRecipe(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), tile.getLens());
                            if (recipe != null && tile.getEnergy() >= recipe.getEnergyUsed()) {
                                ItemStack output = recipe.getOutput();
                                if (StackUtil.isValid(output)) {
                                    tile.getWorldObject().playEvent(2001, pos, Block.getStateId(state));
                                    recipe.transformHook(ItemStack.EMPTY, state, pos, tile);
                                    if (output.getItem() instanceof ItemBlock) {
                                        Block toPlace = Block.getBlockFromItem(output.getItem());
                                        BlockState state2Place = toPlace.getStateForPlacement(tile.getWorldObject(), pos, facing, 0, 0, 0, output.getMetadata(), FakePlayerFactory.getMinecraft((WorldServer) tile.getWorldObject()), EnumHand.MAIN_HAND);
                                        tile.getWorld().setBlockState(pos, state2Place, 2);
                                    } else {
                                        ItemEntity item = new ItemEntity(tile.getWorldObject(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, output.copy());
                                        tile.getWorld().addEntity(item);
                                        tile.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
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
            List<ItemEntity> items = tile.getWorldObject().getEntitiesWithinAABB(ItemEntity.class, aabb);
            for (ItemEntity item : items) {
                ItemStack stack = item.getItem();
                if (item.isAlive() && StackUtil.isValid(stack) && !item.getEntityData().getBoolean("aa_cnv")) {
                    LensConversionRecipe recipe = LensRecipeHandler.findMatchingRecipe(stack, tile.getLens());
                    if (recipe != null) {
                        int itemsPossible = Math.min(tile.getEnergy() / recipe.getEnergyUsed(), stack.getCount());

                        if (itemsPossible > 0) {
                            recipe.transformHook(item.getItem(), null, item.getPosition(), tile);
                            item.remove();

                            if (stack.getCount() - itemsPossible > 0) {
                                ItemStack stackCopy = stack.copy();
                                stackCopy.shrink(itemsPossible);

                                ItemEntity inputLeft = new ItemEntity(tile.getWorldObject(), item.posX, item.posY, item.posZ, stackCopy);
                                tile.getWorld().addEntity(inputLeft);
                            }

                            ItemStack outputCopy = recipe.getOutput().copy();
                            outputCopy.setCount(itemsPossible);

                            ItemEntity newItem = new ItemEntity(tile.getWorldObject(), item.posX, item.posY, item.posZ, outputCopy);
                            newItem.getEntityData().setBoolean("aa_cnv", true);
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
            Direction sideToManipulate = tile.getOrientation();
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
