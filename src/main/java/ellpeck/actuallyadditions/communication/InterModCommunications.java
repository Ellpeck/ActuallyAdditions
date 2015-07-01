package ellpeck.actuallyadditions.communication;

import cpw.mods.fml.common.event.FMLInterModComms;
import ellpeck.actuallyadditions.items.ItemCoffee;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.recipe.HairyBallHandler;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import org.apache.logging.log4j.Level;

import java.util.List;

public class InterModCommunications{

    public static void processIMC(List<FMLInterModComms.IMCMessage> messages){
        for(FMLInterModComms.IMCMessage message : messages){
            if(message.key.equalsIgnoreCase("registerCrusherRecipe")){
                NBTTagCompound compound = message.getNBTValue();
                if(compound != null){
                    ItemStack input = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("input"));
                    ItemStack outputOne = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("outputOne"));
                    ItemStack outputTwo = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("outputTwo"));
                    int secondChance = compound.getInteger("secondChance");

                    if(input != null && outputOne != null){
                        CrusherRecipeManualRegistry.registerRecipe(input, outputOne, outputTwo, secondChance);
                        ModUtil.LOGGER.info("Crusher Recipe that was sent from Mod "+message.getSender()+" has been registered successfully: "+input.toString()+" -> "+outputOne.toString()+(outputTwo != null ? " + "+outputTwo.toString()+", Second Chance: "+secondChance : ""));
                    }
                    else ModUtil.LOGGER.log(Level.ERROR, "Crusher Recipe that was sent from Mod " + message.getSender() + " could not be registered: It's missing an Input or an Output!");
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
                    else ModUtil.LOGGER.log(Level.ERROR, "Coffee Machine Recipe that was sent from Mod " + message.getSender() + " could not be registered: It's missing an Input, a Potion ID, a Duration or a max Amplifier!");
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
                    else ModUtil.LOGGER.log(Level.ERROR, "Ball Of Hair Recipe that was sent from Mod " + message.getSender() + " could not be registered: It's missing an Output or a Chance!");
                }
            }
        }
    }

}
