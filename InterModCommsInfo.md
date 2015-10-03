Adding Custom Recipes (For Modders only!):
=====

Actually Additions adds an InterModCommunications Feature that allows you to add custom Crafting Recipes using Items from your Mods.

To use these Features, just send an InterModComms Message in your preInit or init like this:
FMLInterModComms.sendMessage("ActuallyAdditions", [X], [Y]);
This Message has to be sent BEFORE FMLPostInitializationEvent!
The two Brackets will have to get replaced with one of the parts of Information below.

##### Crusher Recipes
- Create an NBTTagCompound
- To the Compound, add a String with the name "input" that contains the Input Oredict Name
- To the Compound, add a String with the name "outputOne" that contains the first Output Oredict Name
- To the Compound, add an int with the name "outputOneAmount" the contains the first Output's amount
- To the Compound, add a String with the name "outputTwo" that contains the second Output Oredict Name
- To the Compound, add an int with the name "outputTwoAmount" the contains the second Output's amount
- To the Compound, add an int with the name "secondChance" that contains the Chance for the second Output to appear
- Send the Message with "registerCrusherRecipe" as the [X] Argument, the Compound as the [Y] Argument.

##### Coffee Machine Recipes
- Create an NBTTagCompound
- To the Compound, add an NBTTagCompound with the name "input" that contains the Input ItemStack saved to NBT (To do this, just use ItemStack.writeToNBT)
- To the Compound, add an int with the name "id" that contains the ID of the Effect the Coffee should have (Look up the Effects in Minecraft's Potion Class!)
- To the Compound, add an int with the name "duration" that contains the Duration the Effect should have
- To the Compound, add an int with the name "amplifier" that contains the Amplifier the Effect should have (Remember: 0 = Level 1!)
- To the Compound, add an int with the name "maxAmp" that contains the maximal Amplifier the Effect can have
- Send the Message with "registerCoffeeMachineRecipe" as the [X] Argument, the Compound as the [Y] Argument.

##### Ball of Hair Recipes
- Create an NBTTagCompound
- To the Compound, add an NBTTagCompound with the name "output" that contains the Input ItemStack saved to NBT (To do this, just use ItemStack.writeToNBT)
- To the Compound, add an int with the name "chance" that contains the Chance of the Item appearing
- Send the Message with "registerBallOfHairRecipe" as the [X] Argument, the Compound as the [Y] Argument.

##### Treasure Chest Recipes
- Create an NBTTagCompound
- To the Compound, add an NBTTagCompound with the name "output" that contains the Input ItemStack saved to NBT (To do this, just use ItemStack.writeToNBT)
- To the Compound, add an int with the name "chance" that contains the Chance of the Item appearing
- To the Compound, add an int with the name "minAmount" that contains the minimum size of the ItemStack
- To the Compound, add an int with the name "maxAmount" that contains the maximum size of the ItemStack
- Send the Message with "registerTreasureChestRecipe" as the [X] Argument, the Compound as the [Y] Argument.
