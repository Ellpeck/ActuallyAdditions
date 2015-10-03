/*
 * This file ("InterModCommunications.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.communication;

import cpw.mods.fml.common.event.FMLInterModComms;
import ellpeck.actuallyadditions.items.ItemCoffee;
import ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import ellpeck.actuallyadditions.recipe.HairyBallHandler;
import ellpeck.actuallyadditions.recipe.TreasureChestHandler;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

import java.util.List;


public class InterModCommunications{

    public static void processIMC(List<FMLInterModComms.IMCMessage> messages){
        for(FMLInterModComms.IMCMessage message : messages){
            if(message.key.equalsIgnoreCase("registerCrusherRecipe")){
                NBTTagCompound compound = message.getNBTValue();
                if(compound != null){
                    String input = compound.getString("input");
                    String outputOne = compound.getString("outputOne");
                    int outputOneAmount = compound.getInteger("outputOneAmount");
                    String outputTwo = compound.getString("outputTwo");
                    int outputTwoAmount = compound.getInteger("outputTwoAmount");
                    int secondChance = compound.getInteger("secondChance");

                    if(input != null && outputOne != null){
                        CrusherRecipeRegistry.addRecipe(input, outputOne, outputOneAmount, outputTwo, outputTwoAmount, secondChance);
                        ModUtil.LOGGER.info("Crusher Recipe that was sent from Mod "+message.getSender()+" has been registered successfully: "+input+" -> "+outputOne+(outputTwo != null && !outputTwo.isEmpty() ? " + "+outputTwo+", Second Chance: "+secondChance : ""));
                    }
                    else{
                        ModUtil.LOGGER.error("Crusher Recipe that was sent from Mod "+message.getSender()+" could not be registered: It's missing an Input or an Output!");
                    }
                }
            }

            if(message.key.equalsIgnoreCase("registerCoffeeMachineRecipe")){
                NBTTagCompound compound = message.getNBTValue();
                if(compound != null){
                    ItemStack input = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("input"));
                    int potionID = compound.getInteger("id");
                    int duration = compound.getInteger("duration");
                    int amplifier = compound.getInteger("amplifier");
                    int maxAmp = compound.getInteger("maxAmp");

                    if(input != null && potionID > 0 && duration > 0 && maxAmp > 0){
                        PotionEffect effect = new PotionEffect(potionID, duration, amplifier);
                        ItemCoffee.registerIngredient(new ItemCoffee.Ingredient(input, new PotionEffect[]{effect}, maxAmp));
                        ModUtil.LOGGER.info("Coffee Machine Recipe that was sent from Mod "+message.getSender()+" has been registered successfully: "+input.toString()+" -> "+effect.toString());
                    }
                    else{
                        ModUtil.LOGGER.error("Coffee Machine Recipe that was sent from Mod "+message.getSender()+" could not be registered: It's missing an Input, a Potion ID, a Duration or a max Amplifier!");
                    }
                }
            }

            if(message.key.equalsIgnoreCase("registerBallOfHairRecipe")){
                NBTTagCompound compound = message.getNBTValue();
                if(compound != null){
                    ItemStack output = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("output"));
                    int chance = compound.getInteger("chance");

                    if(output != null && chance > 0){
                        HairyBallHandler.addReturn(output, chance);
                        ModUtil.LOGGER.info("Ball Of Hair Recipe that was sent from Mod "+message.getSender()+" has been registered successfully: "+output.toString()+", Chance: "+chance);
                    }
                    else{
                        ModUtil.LOGGER.error("Ball Of Hair Recipe that was sent from Mod "+message.getSender()+" could not be registered: It's missing an Output or a Chance!");
                    }
                }
            }

            if(message.key.equalsIgnoreCase("registerTreasureChestRecipe")){
                NBTTagCompound compound = message.getNBTValue();
                if(compound != null){
                    ItemStack output = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("output"));
                    int chance = compound.getInteger("chance");
                    int minAmount = compound.getInteger("minAmount");
                    int maxAmount = compound.getInteger("maxAmount");

                    if(output != null && chance > 0 && minAmount > 0 && maxAmount >= minAmount){
                        TreasureChestHandler.addReturn(output, chance, minAmount, maxAmount);
                        ModUtil.LOGGER.info("Treasure Chest Recipe that was sent from Mod "+message.getSender()+" has been registered successfully: "+output.toString()+", Chance: "+chance+", Min Amount: "+minAmount+", Max Amount: "+maxAmount);
                    }
                    else{
                        ModUtil.LOGGER.error("Treasure Chest Recipe that was sent from Mod "+message.getSender()+" could not be registered: It's missing an Output, a Chance or minimum and maximum Amounts!");
                    }
                }
            }
        }
    }

}
