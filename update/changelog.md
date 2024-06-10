# 1.2.13+mc1.20.4
* Beta
* Massive update porting the mod to 1.20.4 NeoForge from 1.12.2, list is non-exhaustive, and some features may not be implemented yet.
* The following systems / items are removed.
    * Lush Caves
    * The number 11 lens.
    * Smiley Cloud
    * Solidified XP dropping from mobs.
    * Treasure chests.
    * Composter, bio-mash, biomass, bio coal, and fertilizer.
    * Charcoal blocks.
    * Ender pearl blocks.
    * ESD's
    * Fishing Nets
    * Solar Panels
    * Item Repairer
    * Knives
    * Black lotuses and black dye.
    * Ring of liquid banning.
    * Emerald and obsidian armor and tools.
    * Black quartz armor and tools.
    * Crystal armor and tools.
    * Resonant rice.
    * Jam Villager and jams.
    * Potion rings.
    * Fur balls.
    * Most of the foods.
* Changes
  * All recipes and conversion systems are now Json recipe based, and can be modified with datapacks, negating the need for most of the API.
  * Black quartz ore now behaves like normal ores.
  * New models / textures for almost every block and item in the mod.



# 1.12.2-r152
* CrankySupertoon: Fixed Crystal Flux Localization
* Falcion: Updated russian translation
* Jammehcow: Fixed a rare NPE with other mods on the worm entity

# 1.12.2-r151
* TomyLobo: Improved performance of the item interface.
* iTitus: Added configs for leaf and coal generators.
* Disabled enchanting of Coffee Cups.

# 1.12.2-r150
* Greenhouse glass does not stack.
* Black Quartz is now oredicted in recipes.
* Fixed a crash in the booklet.

# 1.12.2-r149
* Items can now be removed from the bio reactor.
* Mining lens energy use is now a config.
* Items converted by the atomic reconstructor can no longer be further converted until being picked up.

# 1.12.2-r148
* Fixed atomic reconstructor not hitting items on pressure plates.

# 1.12.2-r147
* Sacks should no longer duplicate items when used on multi-slot inventories with slot size limits.
* Updated systems to use IForgeRarity instead of EnumRarity.
* Formatted the everything.
* Plants now try to add the stack to the player inv before spawning it on right click.
* Block Breakers now set the location of the fake player to their location.
* Auto-Placers will no longer spam errors when clicking on a GUI-based block.
* A blacklist has been added for the Item Repairer.
* Atomic Reconstructor beams should now check a slightly larger area (for compat with ItemPhysic).

# 1.12.2-r146
* IrresoluteArkia: Solid experience no longer drops when doMobLoob = false.
* IrresoluteArkia: Black quartz blocks and black dye oredicts updated.
* Cascading would gen should be less prevalent.  May be fixed entirely.
* A few more references to CF have been localized.
* shejery: Added ko_KR.lang
* Crushers and powered furnaces can now be forced into the "on" state with a redstone signal to avoid block updates.
* The Coffee Machine now uses the oredict "cropCoffee" for valid coffee beans.

# 1.12.2-r145
* Sir-Will: Cache KittyVanCat UUID.
* Ore generation can be dimension whitelisted.
* Filters work properly now.  OreDict was completely non-functional.
* Farmer farming area is now configurable.
* Placed laser relays will not be converted by the reconstructor.
* Canola Press and Fermenting Barrel can now be nbt-cleared in a crafting grid.

# 1.12.2-r144
* Phantom Breakers should function properly again.
* Greenhouse Glass functions again (wasn't broken, but it works now).

# 1.12.2-r143
* Greenhouse Glass will now work with any non-opaque block below it, instead of only air below it.  Yes, this means they can stack.
* Greenhouse Glass should no longer crash if it is over the void.
* Solidified experience now defaultly does not spawn orbs, but adds the value to the player's xp.  This can be configured back to original behavior.
* Greenhouse Glass no longer crashes if the state changes blocks during the growth process.

# 1.12.2-r142
* Coal Gen go fast now.

# 1.12.2-r141
* Fixed Lens of the Killer not providing player-only drops.
* Fixed Greenhouse Glass skipping steps on certain hard to grow crops.
* Fixed Coffee Machine.  Whole thing was broken.
* Made the weight of lush caves 10k, if other mods are still generating later it'll have to be moved to an event.
* Fixed the crusher search case blacklist not being applied to default recipes.

# 1.12.2-r140
* Fixed empowerer ignoring energy costs.

# 1.12.2-r139
* Added a crash check to figure out who is causing TheWildPlants to be loaded before block init.
* Fixed the farmer (and other inventories) failing to add items if all items have a stacksize of 1.

# 1.12.2-r138
* DiamondKnight23: Clarify the text for chest upgrades.
* Fixed farmer behaviors, mainly resolves pumpkins and melons being planted incorrectly.
* Removed the tooltip on the Traveller's Sack, as it is now invalid.
* Coal generator properly updates container item.
* Added redstone sensitivity / comparator output to Bio Reactor.
* Added a config option to allow the Lens of Color to rotate dyes based on oredict, instead of only the vanilla dye item.

# 1.12.2-r137
* Should resolve all issues introduced by r136.
* Removes legacy world data loader.  It wasn't used for the entire life of 1.12, so this won't matter.
* Fixes matching logic in the Empowerer so that recipes using the same ingredient instance work properly.
* The farmer will now only plant from seed slots.
* The farmer will now only attempt to plant if the current block can be replaced.
* Fixes issues with the farmer overriding existing blocks.
* AutoPlacer now properly fires every event it can.
* The disableable item configs now properly only read from english, these will be reworked in 1.13.
* API version updated.  Some compat may be broken.  Modtweaker has already updated, Agricraft has updated, IE has not.
* Resolves an unusual interaction between the ESD and the vanilla furnace.
* All recipe systems have been rewritten to use Ingredient.
* Compost now uses a custom IBakedModel instead of a TESR.
* The crusher is no longer animated when not crushing.

# 1.12.2-r136
* Fixed a crash in BlockWildPlant.
* Prevented torch placement on laser relays.
* Auto breaker no longer dupes clay.
* Traveller's Sack should no longer crash with NBT-heavy items inside.
* Items with Curse of Binding can no longer be removed inside the Energizer gui.
* Crusher recipes no longer allow duplicates, and use Ingredient for inputs.  Hopefully modtweaker compat was maintainted.
* Massive overhaul to the inventory system of AA.  Some things might be broken, but overall the underlying structure fixed a few things.

# 1.12.2-r135
* Added armor toughness to a few AA armors.
* Resolved a mod interaction with RealBench.  This fix only functions if FastWorkbench is present.
* Walls now drop the proper item form.
* Resolves an edge-case crash if you were kicked with the booklet open.
* Konstantin: Added ru_RU.lang

# 1.12.2-r134
* Worm should no longer attempt to post a UseHoeEvent on farmland.
* Only items with FE capability can be placed in the energizer.
* Water bowl in the offhand is no longer a weapon of mass destruction.
* Oil generator outputs now properly scale with configured values.
* AA banner patterns now work properly.

# 1.12.2-r133
* Fixed info text not showing on unbreakable village machines.
* Breaker should no longer crash with property errors.
* Small bit of code cleanup.
* Fixes the portable crafter not working on servers.

# 1.12.2-r132
* Added compat with FastWorkbench for the portable crafter.
* Made the logging of the crusher recipes a debug, instead of info (less log spam).
* The\_Fireplace: added en_UD.lang

# 1.12.2-r131
* Vertical Digger no longer uses getMetaFromState and now uses getPickBlock.
* Farmer no longer breaks stem blocks.
* Config for atomic reconstructor battery size.
* Made a note on the oil generator that values are per 50 mB.

# 1.12.2-r130
* Fixes the drill not breaking blocks of similar hardness in 3x3 or 5x5 mode.
* Fixes the default farmer casting every IPlantable to a BlockBush.

# 1.12.2-r129
* Dalapo: Added an extra null check to canFillFluidType
* Biocoal burn time is now 800 again, instead of 80.  Whoops.
* Worms shouldn't show up from areas that cannot be tilled.
* (Hopefully) fixed desync issues with the 5x5 and 3x3 drill.
* Feeder has gained +5 INT.
* Config added for the fur ball drop chance.
* Greenhouse Glass recipe now makes 2 glass, and uses a single empowered palis crystal.

# 1.12.2-r128
* Removed a debug line in Greenhouse Glass
* Fixed the firework box recipe displaying wrongly in JEI

# 1.12.2-r127
* Removed explicit tesla support.
* The AIOT will now accept sword enchantments.
* Greenhouse Glass is no longer a tile entity, this should significantly improve performance.  It will be a bit slower in terms of plant growth though.
* Canitzp: Fixes a duplication bug with MeeCreeps and AA containers.
* Fixes a log error with Coal Dust and JEI.

# 1.12.2-r126
* New feature!  The atomic reconstructor can now split enchanted books with multiple enchantments!
* Leomil72: Added it_IT.lang

# 1.12.2-r125
* The colored lamps now no longer require sneaking to turn them on/off
* Viveleroi: Fixed a few typos in the booklet 
* TartaricAcid: Update zh_CN.lang
* Require holding the traveller's sack (prevents a dupe)
* Adds a blacklist for the traveller's sack
* Makes the Precision Dropper update comparator output when the gui closes
* Blocks the harvesting of XU2 crops to prevent deletion until it is fixed on XU's end

# 1.12.2-r124
* Made the booklet not crash

# 1.12.2-r123
* Made things crash less (null itemstack issues)

# 1.12.2-r122
* Artemish: Added JEI support for compost recipes
* Added a recipe for smelting spawner shards into iron ingots
* Made the magnet not grab items on IE conveyor belts
* Torches can no longer be placed on the sides of display stands
* Batteries will now only charge items with a stacksize of 1
* Thiakil: AA's itemstack handler now returns a copy during simulate

# 1.12.1-r121
* Fix crashes relating to the breakers
* Fix the jam zombie having invisible arms
* Wings of the Bats no longer works in spectator mode

# 1.12.1-r120
* Fix crate upgrades not being consumed
* Fix the lens of color crashing on invalid input
* Updated to new getDrops method for Farmer.  **May cause issues with other mods overriding the wrong method**

# 1.12.1-r119
* Fix the Crusher crashing servers
* Make the magnet ring only work in survival mode

# 1.12-r118
* Added magma blocks as additional sources for the heat collector
* Fix Experience Solidifier not working with automation
* Added config option to completely disable tools
* Fix normal torches attaching to the sides of tiny torches
* Fix Nether Coal from BWM being made from grinding coal in the Crusher
* Added Comparator support for the Atomic Reconstructor
* Fix the Drill progress being reset while it recharges
* Fix lush caves having blocks from other mods (like marble or limestone) inside of them

# 1.12-r117
* Resolve a dupe glitch with the advanced precision dropper
* Made the worm be able to hoe BOP grasses
* Fix Nether Wart not working in the farmer with NetherEx
* Fix the farmer destroying sugar cane
* Various codebase improvements that should hopefully not crash your servers

# 1.12-r116
* Fix the game randomly crashing on load sometimes
* Added 11 config options
* Make shift clicking out of crates mimic vanilla
* Make the Farmer re-plant right after it harvests *(I didn't want this but Shadows did it so I can't argue with him)*
* Fix Knife not working in Crafting Table On A Stick

# 1.12-r115
#### Shadows_of_Fire has been doing all the maintaining and fixing for 1.12 so far and I really appreciate it. So thank him for making these fixes possible <3
* Update the name of oil (the stuff that you get when refining canola oil) in the fluid dictionary along with its block's name. **This change will remove any existing oil fluid blocks along with any existing oil in tanks etc. from your world**
* Resolve a random NPE that would rarely trigger on leaving/joining a server
* Made Quark chests work with the Chest To Crate Upgrade

# 1.12-r114
* Fix the Crafting Table On A Stick not working
* Fix worms not rendering when placed in the world
* Make lamps only be toggleable with an empty hand
* Make lamps' light update smoother
* Fix Laser Relays sometimes crashing when they're transferring energy
* Fix the game crashing if any of the villagers is disabled in the config

# 1.12-r113
* Fixed recipes not showing up in the manual (thanks Shadows >_>)

# 1.12-r112
* Fixed the mod for newer versions of Forge
* Actually Shadows-of-Fire did all the work so yea, thanks

# 1.12-r111
* Did the thing

# 1.11.2-r110
* Fix config typo (sindrefag)
* Add a config for oil generator values
* Remove the after:BuildCraft dependency because it's obsolete
* Make water bowl spilling not happen when you're standing (Making Darko proud)

# 1.11.2-r109
* Don't check for insertability into machines of items that can't actually fit, increases performance
* Change lists for sets in Laser Relays to increase performance
* Add crafting table on a stick compat foro InvTweaks
* Change unimportant feedback from the laser wrench and phantom connector to the action bar
* Fix ESD not working with changed lower bounds
* Made the water bowl spill
* Fixed default spawner changer config allowing Villager Golems to pass through

# 1.11.2-r108
* Added Red Orchid and Ender Lilly farmer compatibility
* Ceil Phantomface distance so that it displays correctly
* Added config option for tiny coal and tiny charcoal
* Fixed item laser display not working if the player is too far away from the input point
* Fix the chest to crate upgrade not working with some modded functionality (again)
* Re-worded laser connection info to avoid confusion with disconnections
* Make phantomfaces output comparator signals
* Remove a legacy statement in the manual stating that phantom liquifaces use redstone

# 1.11.2-r107
* Small update to french localizations (MisterPeModder44)
* Add Crafting Tweaks 
* Fix a thing with boring mode (starg09)
* Fixed the water bowl picking up stuff it shouldn't
* Fix drill, filter and bag being openable in offhand slot
* Fixed treasure chest having null instead of air as a drop item
* Added a config for crystal clusters
* Added the ability to disconnect laser relays
* Possibly fixed Laser Relays sometimes disconnecting on their own when reloading chunks
* Added blaze rod crushing
* Set the harvest level for all tool types correctly to work with some modded functionality
* Fixed some mods not working with the chest to crate upgrade causing item loss or duplication

# 1.11.2-r106
* Made horse armor yield less when crushing it
* Fixed crusher achievement descriptions being messed up
* Remove the manual from dungeon chests
* Added a config to change the redstone torch and compass configuration items
* Fixed a bunch of grammatical issues in the manual
* Fixed cascading world gen caused by lush caves and plants (performance improvements! \o/)
* Generate village chests even if dungeon loot is disabled
* Fixed a crash issue with the smiley cloud on weirded out worlds

# 1.10.2-r105
* Added a Redstone Mode toggle to the Ranged Collector
* Fixed missing textures on item laser relay manual pages
* Supposedly fixed the bag "leaking" items with certain other mods installed

# 1.10.2-r104
* Stop the storage crate from syncing its NBT
* Make the drill digging packet a config option that is off by default
* Make the coffee maker store its coffee cache when breaking it
* Added emptying recipes for fluid placer and collector
* Made the magnet ring not use energy when not picking something up
* Fire block harvesting events when automatically mining things
* Backported a bunch of quotes and booklet editions
* Fix an exception in the booklet with broken furnace recipes
* mMake the leaf blower drop blocks on their own
* Fix pickup achievements
* Make the smiley clouds render special stuff
* Make horse armor yield less when crushing
* Fix crusher achievement descritipons being wrong
* Added a config to change the configuration items (compass and redstone torch)
* Updated the RF API (This was the actual reason this whole update was made, so blame (or thank) KingLemming. Also, I'm only putting this bit of text here because [he told me to](http://puu.sh/uDhoP/08b063b698.png).)
* Remove the black quartz made from coal and quartz recipe (<3)

# 1.11.2-r103
* Added Trials to the manual. They're like challenges that should motivate you to do more stuffs. Check it out.
* Fixed Crystal Clusters giving the wrong pick block
* Made furnace and crusher have an animated texture again when being activated
* Fixed a bunch of places still saying RF instead of CF
* Made the farmer be able to plant and harvest pumpkins and melons
* Updated french translation (MisterPeModder44)

# 1.11.2-r102
* Fixed dedicated servers crashing

# 1.11.2-r101
* Added Laser Relay Insibility Upgrade
* Added Laser Relay Range Upgrade
* Added Engineer's Goggles
* Added Engineer's Infrared Goggles
* Added Crystal Clusters
* Made Smiley Clouds render Patreon reward floating items
* Fixed pickup achievements not working
* Fixed player data sometimes not updating properly
* Fixed an exception in the booklet due to broken furnace recipes
* Fixed a lot of typos in fr_FR.lang (MisterPeModder44)
* Added a bunch of quotes to the booklet
* Implemented charset carrying for most blocks
* Added redstone mode to the ranged collector

# 1.11.2-r100
#### I wanted to do something really cool for r100, but I didn't. So the best change in this version is that worms now go forwards again. So that's nice, I guess.
* Implemented slotless item handler compatibility. If you use Colossal Chests, you should install Common Capabilities to make Actually Additions stuff work nicely with that.
* Make the coffee maker store its coffee cache on breaking
* Add a recipe to empty fluid placers/collectors
* Make the drill not use energy in creative
* Added french translations (MisterPeModder44)
* Fix the ranged collector deleting items when shift-clicking
* Made bio-mash recipes be dependent on the food amount that items give
* Fix the magnet ring using energy even when not picking anything up
* Add a tooltip that displays when breaking machines in the villager house
* Added sugar cane support to the farmer (dbMansfield)
* Post a break event when auto-mining things
* Fix inventory switching sounds with the placer and drill

# 1.11.2-r99
* Make the ESD less performance intensive by making it a bit slower
* Fix the Player Probe not properly clearing its data
* The Bat Bat
* Make Fluid Lasers actively pull fluids
* Added quartz dust to the OreDictionary as dustNetherQuartz
* Added a config option to change the booklet's font size
* Added Laser Relay network caching to reduce server load
* Add a bunch of chunk loaded checks to hopefully avoid loading chunks inadvertently
* Stop the storage crate from syncing its NBT
* Make the drill digging packet a config option that is off by default

# 1.10.2-r98
* Fix the Player Probe not clearing its data properly
* Add Laser Relay input/output only config
* Made Fluid Laser pull fluids on input mode
* Added Laser Relay network caching to reduce server load
* Add a bunch of chunk loaded checks to hopefully avoid loading chunks inadvertently

# 1.10.2-r97
* Fixed an Exception with Solidified Experience, Balls of Fur and Resonant Rice
* Fix the Item Laser Relay achievement being impossible to get
* Made the ESD a lot less performance intensive and gave it a cooldown

# 1.11.2-r96
#### This is the New Year's Eve Update. The Firework Box can now be completely customized via the newly added GUI. If you're into easy and pretty Fireworks, you should definitely check it out!
* Fixed the bag extraction into chests ignoring the first four slots
* Added Battery Box comparator output
* Make the Laser Relay recipe only give 4 ones instead of 6
* Add compatibility with Blood Magic farming stuff (which isn't updated I know, but the compat is there)
* Changed the way world-specific data is stored. __Make a backup of your world before you update. If any Laser Relay connections are lost, submit a report on the Issue Tracker with the full log.__
* Make Phantomfaces only support the capability they advertise
* Made the changelog title above sound like a cheesy advertisement. O_o

# 1.11.2-r95
### This is 1.11.2. Not 1.10.2. Yes, this is confusing I know.
* Added a fancy renderer to Item Laser Relays transferring items 
* Fix Item Laser Relay achievement being impossible to get
* Hopefully prevent a crash with the Farmer
* Blacklist Laser Relays from being used with Phantomfaces
* Change some oil generator values around
* Made Mod Mode ignore all other filter settings by default
* Added a config option to remove colored item names
* Made Filter items not be respected as a whitelisted item in filtered machines
* Fix Spawner Changer crashing with some mobs like Wither Skeletons
* Fixed creative consumption of the worms (xbony2)
* Gender neutral language (xbony2)

# 1.10.2-r94
* Make Laser Relay crafting use the Atomic Reconstructor instead of the Crafting Table
* Made Filter Settings not treat the Filter item as a setting
* Blacklist Laser Relays from being used with Phantomfaces to avoid crash loops :v
* Made Mod mode ignore other filter settings like Meta and NBT by default

# 1.10.2-r93
### This is a backport of some of the features present in the new 1.11 versions. So have fun with that. <3
* Replace particles with laser renderers on multiple machines
* Make the Leaf Blower work on all shearable things including BoP grass
* Make the Reconstructor convert without needing blocks in its sight
* Added comparator outputs to a bunch of things (check the book)
* Added Elucent's nice Laser renderer
* Add drops to event list instead of dropping directly
* Fix disenchanting lens deleting books
* Make Laser Relays ignore full containers
* Tone down loot generation amounts a bit
* Backport Traveller's sack item count tooltip (number5)
* Removed the Item Distributor :(

# 1.11-r92
* Added Battery Box to store power
* Replace particles on Empowerer, Leaf Generator, Atomic Reconstructor and Miner with Laser Renderer
* Added Hopping Item Interface (Item Interface + Hopper as one)
* Rewrote Item Laser Relay documentation a bit
* Fluid Placer/Collector how has comparator output
* Changed Laser Relay recipes to use the Reconstructor
* Tone down dungeon loot a bit
* Add mob drops to events' lists instead of dropping them directly
* Minor grammar corrections in the book (xbony2)
* Fix a crash with the chest to crate upgrade
* Fix the Disenchanting lens deleting books if using more than one
* Made Laser Relays ignore full containers for inputting

# 1.11-r91
* Added Direwolf20's spotlight to the video introduction page of the book
* Fixed Item Laser Relay page pictures being broken
* Make the Leaf Blower work with all shearable things (Including BoP grass!)
* Turn down Empowerer particles a bit more
* Made the Vertical Digger lighter on server resources
* Laser Rendering Improvements (Elucent)
* Canola Seed Texture Improvements (Elucent)
* You can now change the booklet's font size in the manual
* Fixed the dropper dropping into unloaded chunks
* Fix the item distributor sometimes either voiding or duping items
* Fix a crash with the Item Interface and ESD on the latest forge version

# 1.10.2-r90
* Fixed the Item Distributor sometimes either voiding or duping items
* Make the ESD automatically assign to things like chests again

# 1.11-r89
* Made Filters be consumed when placing them into filter slots
* Added Laser Relay in/out config. Hold a Laser Wrench for more info.
* Fix a crash with the miner
* Fix the XP Solidifier texture
* Fix the reconstructor never using more than 1000 RF
* Fix a crash that sometimes happens when placing down container blocks
* Fix the Fermenting Barrel crashing with a missing compound
* Made the crusher quieter
* Made the reconstructor convert without needing blocks in front
* Added more booklet quotes ~O_O~
* Fix Diamatine armor not having a texture
* Fix Jam Villager sometimes producing infinite emeralds
* Added the ability to farm cactus in the Farmer
* Added the ability to farm Nether Wart in the Farmer
* Fix the placer not working
* Make fluid handling blocks side independent
* Added the ability to add custom farmer behaviors to the API
* Dropped IInventory Support

# 1.10.2-r88
* Fix a crash with the Phantomface on blocks that don't support IInventory
* Fix Laser Relay crash with Tesla integration and some machines from other mods

# 1.10.2-r87
* Fix Laser Relays sometimes not updating their caches causing in possible loss of things
* Fixed the compost sometimes accepting too many items (It will now accept full stacks at all time)
* Fixed corrupt NBT Tags on Items causing TileEntities to crash in certain situations

# 1.11-r86
* Fix a crash with Energy Phantomfaces and Tesla
* Fix the farmer rotations being wrong
* Made the power bar on items colorful too
* Make machines in villager houses disappear once you break them
* Made the coal generator turnoffable with redstone
* Oil Generators respond to comparators
* Coal Generators respond to comparators
* Fermenting Barrels respond to comparators
* Make the heat collector more rare to pick up lava
* Re-added Patreon support renderer for 1.11
* Fixed a bug with Laser Relays breaking some renderers
* Fix bags duping with other mods sometimes
* Made item Intertion on the farmer work with all types of seeds
* Made advanced tooltips only show with F3+H modde enabled
* Removed crafting configs (just use MineTweaker)
* Fix a dupe bug with some energy items in the Energizer
* Made Laser Relays not try to input back into the place they got energy from
* Made the double furnace accept more energy per tick so that it doesn't run out when smelting two items

# 1.10.2-r85
* Make only coffee machine and coal generator return container items on empty slot (Fixes dupe bug with RFTools)
* Made Laser Relays not try to input back into the place they got energy from (Should fix energy distribution inconsistency)
* Made advanced tooltips only show with the F3+H mode enabled

# 1.10.2-r84
* Fix a bug with EnderIO side config going blank when having a Laser Relay attached
* Fixed item pickup happening multiple times with other mods installed
* Made the farmer put seeds back into the seeds slot (backport from 1.11)

# 1.11-r83
* Fix an exception during loading some tileentities causing them to lose their data
* Improve performance of Patreon display code

# 1.11-r82
* Made Filter Slots be ignored by Comparators (all filter settings that currently exist will be lost, so check your filters!)
* Buffed oil a little
* Added enabling/disabling the magnet ring
* Made Greenhouse glass twice as fast
* Fixed a crash with the Firework box in unloaded chunks
* Fixed the Item Repairer crashing with certain items
* Removed the ability to put batteries inside of drills because of them being able to be put into discharge mode
* Dropped Redstone Flux Support
* Added Crystal Flux. Works the same as Tesla and Forge Units and is compatible with all of them. Just a different name. Also a nice looking energy bar.
* Support Forge Units
* Fix a crash with trying to input items into filter slots
* Added slot fill amount tool tips to bags
* Fix Phantom Laser Relay connections when breaking Relays connected to Item Interfaces
* Stated in the booklet that energy loss for Laser Relays is per transfer

# 1.10.2-r81
* Fixed phantom connections left behind when breaking an Item Laser Relay connected to an Item Interface
* Fixed some ores not working in the Crusher even though they should

# 1.11-r80
* This versioning system is all over the place now. But it works.
* Made some Crusher and Empowerer recipes work again that weren't working because of registry order
* Made Advanced Item Laser relays be viewable by just right-clicking
* Fix Fluid Containers emptying giving you back nothing
* Added Mining Lens white- and blacklist
* Made machines use proper amounts of power again
* Fix Batteries duping energy infinitely if two are on discharge mode in one inventory

# 1.10.2-r79
* Fixed Batteries duping energy infinitely if two are on discharge mode in one inventory
* Fixed the Lens of the Killer not actually dropping player-only drops
* Added confusing versioning that doesn't really work when maintaining multiple versions at once :v

# 1.11-r78
* Added Engineers' House to villages. You should definitely give it a look.
* Added Engineer and Crystallizer villager
* Made the Storage Crate more expensive
* Added flight limit to wings of the bats, can be regenerated by flying up to the ceiling or landing on the ground.
* Made the oil generator scale power per time, not per energy
* Rebalanced some energy items
* Rebalanced some energy blocks
* Made dungeon loot config also disable village loot
* Make Item Repairer and Miner use a little less power
* Made the oil generator turn-off-able with Redstone
* Made potion rings require blaze powder to work
* Made potion rings work in the off hand
* Made the Storage Crate less expensive
* Made the Lens Of The Killer actually do player only drops
* Fixed the drill replacing itself when using the placement upgrade

# 1.11-r77
* Made the Farmer add seeds back into the seed input slots
* Made the XP solidifier save and update correctly
* Made the lens of death disablable
* Made the achievements and config button appear right away after starting the tutorial
* Named all containers properly
* Made the amount of loot generated in chests a little higher
* Replace chests in village houses and lush caves with storage crates
* Fixed the villager texture being missing

# 1.11-r76
* This is the first release of Actually Additions for Minecraft 1.11. It hasn't been tested much, so issues might occur. Use at your own risk, but remember: I can use all the help in testing I can get! So if you find a bug, please report it on the issue tracker!
* Updated to Minecraft 1.11. Old worlds should be compatible.
* Snake_cased all items and their names. All custom textures will be broken.
* Added draconium ore to the mining lens list
* Added dungeon loot to woodland mansions

# 1.10.2-r75
* To people new to this mod: No, this isn't my regular updating schedule. Things have just been a bit rough this week I guess. In my hastily preparation for 1.11 some things that should've been accounted for sort of got lost - at least this teaches me to be more careful in the future. Sorry to people that don't like frequent updates, but updates are better than staying bugs - especially with the possibility of modpacks having bugged mod versions!
* Fixed a bug with energy loss from Laser Relays
* Added loot to nether fortress and end city dungeons
* Fixed the bag "leaking" stone
* Rebalanced food items a bit
* Gave crystallized and empowered oil blocks a proper name
* Fixed an Empowerer dupe bug when taking items out

# 1.10.2-r74
* Fix Laser Relays crashing servers
* Fix the coffee machine creating rather than using items

# 1.10.2-r73
* Made textures and lang work again. I apparently forgot how Minecraft loads its resources outside of a dev environment. Yup, I'm smart.
* Made the book tutorial button not get overlayed by config and achievements buttons
* I changed a lot of the way ItemStacks are handled in preparation for 1.11. Some stuff might be broken, so if you have the time, it'd be very nice and lovely of you if you could go through all of the items and blocks and see if they're broken. If so, please report that on the Github issue tracker. All help is appreciated <3

# 1.10.2-r72
* Added Laser Relay priority system
* Added mod filter to filtered machines
* Added back config, achievements and view online button to the booklet
* Added quotes to the booklet
* Added updates and infos section to the booklet (contains some useful links and stuff)
* Fixed picture pages sometimes having completely black borders instead of faded ones
* Fix a bug with the compost sometimes deleting held items
* Change the description text of Phantomfaces to be less confusing
* Made the Empowerer particles respond to the less particles video setting
* Rename all the assets from capital to lowercase in preparation for 1.11, so custom textures will break
* Made the booklet JEI handler show all recipes, not just vanilla crafting ones
* Stop cashing the parent screen of the manual to avoid infinite JEI loops
* Tweaked the Laser Relay renderer to work with lighting
* Added ore to gem crushing if ore to dust crushing cannot be found
* Make the Teleport staff use vanilla cooldown (garantiertnicht)
* Added back addTextReplacement to the API

# 1.10.2-r71
* Totally reworked how the Actually Additions Manual looks. It now has two pages open instead of one and looks a lot fancier in general.
* Added a new Laser Rendering system that is much fancier
* Added a new Laser Relay model (canitzp)
* Added a dedicated chapter for Laser Relays into the booklet
* Removed Laser Wrench display options
* Allow plants to be planted on blocks other than farmland in the farmer
* Make the Handheld Filler work with blocks that only have meta when placed (garantiertnicht)
* Made the AIOT work on Spider Webs
* Made lush caves generate higher up too again
* Added some more easter egg names to the smiley cloud
* Made XP created by Solidified XP not be picked up by the solidifier
* Made the magnet ring even more useful by making it pick up items directly
* Make clear that the Spawner Changer is one time use only
* Removed Performance config options
* Made the ranged collector show a little poof particle when picking items up
* Made the precision dropper correctly respond to comparators
* Made works work with other grass and dirt types

# 1.10.2-r70
* Added the handheld filler, an item that can fill big areas with blocks
* Added the lens of the killer that will drop XP and player-kill items when killing mobs
* Re-added the villager that sells jam
* Made oil stuff generate a *lot* more RF
* Made the Item Repairer a lot more expensive and slow
* Made the Reconstructor reconstruct even if it can't reconstruct the entire stack. Reconstruct reconstruct reconstruct reconstruct
* Made Laser Relays a bit less performance intensive again
* Made the magnet ring's range a bit bigger
* Made Reconstructor recipes show how much additional power they need
* Make Crystals generate in lush caves
* Made the booklet always show the search bar
* Add some more checks to the farmer so that it only plants what can be planted
* Rewrote the book tutorial page and added introductory bookmarks
* Move the Booklet GUI up a bit to make bookmarks not be off screen
* Added some more ores to the mining lens list
* Tweaked some dungeon loot amounts
* Make the AIOT able to make both farmland and paths
* Redid config texts (Joshwoo70)

# 1.10.2-r69
* Added the Mining lens, a lens that will convert stone and netherrack blocks to random ores
* Added the Farmer, a block that can farm crops like wheat or canola
* Made some changes to the JEI integration visuals
* Added a "what people think" page to the manual
* Made tiny torches less useless by giving them a bit bigger of a range
* Fixed the empowerer not allowing extractions
* Added a method to the API to figure out which type a Laser Relay has

# 1.10.2-r68
* Fixed the player interface not saving its data
* Made the empowerer work with all of its recipes
* make it clear the energy mode doesn't change anything
* Fixed minor typos in en_US.lang (WesCook)
* Significantly reduce performance loss when using Item Laser Relays with ESDs
* Added digit seperators for large numbers
* Fixed AIOTs having wrong harvest levels
* Made empowering possible with any dyes, not just vanilla ones
* Changed the way crusher recipes are registered to make the blacklist work properly
* Added the loot from 1.7.10 back
* Added back the jam house loot chest
* Added lush cave loot chests that generate in the trees
* Added fluids to booklet pages for searching and JEI handling

# 1.10.2-r67
* Fixed horses not being able to be fed with the feeder
* Made energy items not lose their power when used for crafting
* Made batteries store more power the higher their tiers are
* Added crusher output blacklist config option
* Fixed the spawner changer sometimes keeping old mob data
* Made fishing net accept chests on all sides
* Added description to the booklet to explain what it is
* Changed the "sneak!" info to be less confusing
* Added visual indication of where the reconstructor hits
* Add info to redstone control machines that they require a torch to be changed
* Fixed recursive chunk generation with lush caves (Barteks2x)

# 1.10.2-r66
* Fixed the Advanced Item Laser Relays' whitelist never being recognized with the new Item System
* Fixed Inbound/Outbound being switched around on Laser Relays connected directly to Item Interfaces (check your setups there, something might have changed)
* Changed the cheese recipe to stop it clashing with HarvestCraft

# 1.10.2-r65
* Fixed a crash that sometimes occured when lush caves tried to generate through loot chests

# 1.10.2-r64
* Fixed player probe spamming the chat when it is being noticed
* Added tool materials to tools to fix some tool related bugs
* Added a recipe to clear the oil generator's internal liquid storage out
* Make sure caves don't generate through bedrock
* Make the drill banner pattern take the default drill (xbony2)
* Corrected some names in the booklet (xbony2)
* Use new item handler system for item laser relays to make them work with Storage Drawers properly
### A stack of releases!

# 1.10.2-r63
* Added more different kinds of oils that the oil generator can use (Check Oil chapter in booklet for info!)
* Added Item Distributor that can distribute items evenly around it
* Added BioReactor that uses food and plants to generate power
* Increased the performance of TileEntities significantly
* Added information to the booklet on how to remove a worm
* Removed console spam about syncing player data
* Changed the color of page buttons to be more visible
* Made the Lamp Controller toggle entire areas
* Make solar panels work through non-solid blocks but become less efficient in the process
* Fixed Phantom Energyface not working for certain tesla machines
* Fixed Phantom Liquiface crashing with some machines
* Fixed the booklet crashing if you had a recipe disabled
### I just want to take the time to pitch a mod I've been working on. It's called Rarmor and it's about an electrical RF/Tesla-powered armor! If you fancy something like that, look at it by [clicking here](http://minecraft.curseforge.com/projects/rarmor)! :3

# 1.10.2-r62
* Fixed a MASSIVE derpup breaking basically all of the things that ever existed ever. _Dang, I was hoping I could get a version out without needing a fix a day after for once. Whatevs. ¯\_(ツ)_/¯_
* Implemented new system of detemining the type of laser relay connection. There might be a bit of lag occuring when you join a world for the first time with this version.
* Added an extra check to block placing mechanisms so that they don't crash with weird semi-fluids

# 1.10.2-r61
* Made machines split their energy/fluids when putting them into other blocks
* Removed worm's bounding box as it was showing up on some HUD mods
* Move JEI booklet handler so that people notice the other JEI handlers and don't just click away
* Made empowerer recipes take different items, so they're sort of a little bit like Botania runes now, yay
* Made empowerer recipes take less energy and time
* Made the display stand always show its RF, not just by sneaking
* Fixed crash with null facing in tesla integration
* Stopped the breaker from mining liquids because it doesn't even make any sense anyway
* Fixed the phantom placer just crashing in pulse mode .-.
* Made water vaporize in the nether with the Fluid Placer
* New Fermenting barrel texture and model
* New Canola press texture and model
* Fixed mushrooms creating nether wart infinitely with the Reconstructor
* Use that weird new runnable thing now that apparently prevents crashes in some way
* Made the smiley cloud not crash with ItemBlock-less blocks on them
* Use Oredictionary Chests for crafting
* Finally hopefully fix weird syncing bugs
* New Oil Gen texture/model
* Tweaked empowerer and display stand models

# 1.10.2-r60
* Fixed infinite worm tilling
* Made the grinder not crash you once opening it
* Fixed a stupid error with items randomly disappearing out of containers sometimes
* Made Player data less world independent but still world dependent. If you're a server owner or have a console open in Singleplayer, try to look at that from time to time to see if ActAdd throws any errors. From my testing, though, it shouldn't. I hope. Yes. :v

# 1.10.2-r59
#### This update mainly consists of new models and textures that were done by the lovely [BootyToast](https://www.youtube.com/user/toastacle) (yes, the name's still weird, I know). Notable model/texture changes incluse the storage crate, the reconstructor, coal generator, display stand, bag and void bag (which are now called sacks), however he is planning on redoing most of the textures. So: Overhaul time! Awesome. Definitely check out the new models and textures as they're pretty sweet. Also, he does awesome videos on Youtube. So go check those out too. But also check out this update. It's pretty nice as well. Yea.
* Made the XP Solidifier pick up any XP orbs nearby when not powered by redstone
* Fixed a massive dupe bug with the XP solidifier :v
* Added the ability to put items back into the XP solidifier
* Added an auto-split button to the double furnace and double crusher
* Fixed some items removing their entire NBT tag on clearing their storage, so you can now use them with the Morph-o-Tool mod again
* Made the Phantom Booster a bit less annoying to craft
* Made the greenhouse glass a bit less useless
* Made the crafting recipe for the advanced item laser relay not use an empowered crystal becasue that's just madness
* Added info about transport from A to B to the Item Laser Relay chapter
* Fixed the booklet showing achievements about a million times when an item involved is wildcard
* Made the empowerer react to the less particles option. If you're lagging, turn that on in the Config GUI.
* Added a config option to make worms die after a certain amount of time (It's off by default, don't worry.)

# 1.10.2-r58
* Added more Storage Crate Upgrades
* Added a lot more Achievements
* Made bags be able to be right-clicked onto chests to make them empty out into the chest
* Fixed a stupd ConcurrentModificationException crash with Laser Relays that was putting the world into a crash loop whenever something was constantly updating the Laser Relay (for whatever reason it was doing that)
* Made the Reconstructor use the right amount of energy for item transformation recipes
* Turned some recipe ordering around in the booklet for it to be easier to find
* Re-did the Laser Relay checking system so that it will never target one block twice. I think.
* Made a note in the Bag GUI that the auto-pickup will only work when the bag is not in hand
* Fixed the coffee machine ignoring the last 2 slots
* Removed Seasonal Mode
* Renamed the Double Furnace to Powered Furnace
* Added keep data recipes, meaning batteries keep their power, drills keep their upgrades and storage crates keep their items when they are used in crafting recipes
* Nerfed Greenhouse Glass and Worms
* Made the Crusher a bit quieter
* Fixed an exception being thrown on the server when sneak-right-clicking a block with the booklet
* Fixed the client crashing when being kicked out of the world while the booklet is open

# 1.10.2-r57
##### This update introduces a new mechanic called the Empowerer. Most of the mid- to endgame crafting recipes have been changed to use items created via the Empowerer. Because of this mechanic being very new, there might be a couple of bugs. Please report them to the issue tracker accordingly.
* Added Empowerer
* Added Bag and Void Bag
* Fixed Display Stand's bounding box
* Added a character limit to the NBT display of the advanced tooltip, changeable in the config
* Added Creative Tab search bar
* Made the Reconstructor book entry show every crystal recipe seperately
* Fixed wild rice popping off of water when updated
* Added an info for the priority system to the Item Laser Relay booklet entry
* Fixed Water Bowls sometimes doing their action twice
* Made the Update Checker not break when on an unstable build
* Made booklet save its data via identifiers instead of number ids. This should mean that saved pages don't get offset anymore when adding new items
* Fix ActAdd now being incompatible with Tesla because of modid casing
* Hopefully finally fix spawners resetting from time to time when used with a Spawner Changer
* Made the Drill's 5x5 mode mine one block higher
* Added charcoal block to Ore Dictionary
* Add some more null checks to the drill's block harvesting to prevent crashes with weird blocks
* Changed the description of the introduction video page a bit
* Adder super hard crafting recipes mode
* [API] Lots of changes, check the Github commit history for more info

# 1.10.2-r56
* Made batteries have an output mode activated by sneak-right-clicking them
* Added Oredictionary-based checking to ESDs, Advanced Item Relays and Ranged Collectors
* Added Tesla Support for Phantom Energyfaces and Laser Relays
* Added Tesla Support for items and the Energizer/Enervator
* Made Magnet Ring pull items upwards
* Fixed a bug with the manual sometimes making recipes using wildcards not use wildcards anymore
* Made the miner use more power (again) because people were complaining (again)
* Made Smiley Cloud nameable again
* Add some null checks to packets so that storage crates don't crash with FTBUtils (whatever..)
* Made the advanced ESD respect its whitelist again
* Fixed Laser Relays sometimes not properly dealing with sides

# 1.10.2-r55
* The _balanced farming buttons-Update!
* Added worms found by tilling the ground. When placed on dirt or grass, they till, irrigate and fertilize it
* Added NBT and Metadata respecting options to ESDs, Ranged Collectors and Whitelist Item Lasers
* Made Greenhouse glass a bit faster because it was ineffective
* Made food less OP
* Fixed armor values a bit
* Fixed a crash when placing an ESD next to a non-IInventory block
* Added config option for tiny coal and charcoal block
* Fixed the energy bar turning green when holding a redstone torch

# 1.10.2-r54
* Fixed Tesla power system integration.
* Yay for update spam. Or something to that effect.

# 1.10.2-r53
* Made the player able to click in GUIs again. Pretend this never happened please.

# 1.10.2-r52
* Added lens of disenchanting. It's awesome, I promise.
* Awesome new energy and fluid displays in GUIs
* Added the ability to change between RF and Tesla display modes in energy GUIs
* Changed advanced coil and ender star recipes a bit
* Fixed log spam when switching worlds
* Made JEI know about the drills so that people don't not know how to craft them.
* Disallow bosses to be spawner changed
* Fixed a crash when breaking soulshards spawners with a drill (hopefully)
* Reconstructed book (ha ha) to have a reconstruction section
* Made ESD use new capability item system. It might be broken in your world now, go look at all of the ones you're using and fix their settings if that's the case.
* Fixed bowls of water being hideous because they weren't going into the right slots

# 1.10.2-r51
* Made TiCon Rapier be able to be repaired in the Item Repairer
* Added Player Probe
* Renamed Mashed Food to bio mash
* Made the booklet search bar search a whole item's tooltip and not just the name
* Added different Energy Laser Relays for different amounts of power transfer
* Added Tiny Torch
* Booklet now states that the drill needs previous tiers of upgrades
* Made drill coloring not use all of the nbt, meaning you can color a drill without the upgrades being gone
* Made ActAdd tools work in hopefully most automatic farms etc.

# 1.10.2-r50
* Changed the name of the coffee machine to coffee maker because that's apparently a more proper name
* Fixed the drill sometimes acting up on redstone and glowstone
* Fixed the particles of breaking with the drill being delayed making it look weird
* Fixed a crash with the InvTweaks sorting buttons in a storage crate
* TiCon tools now lose their broken tag when repairing them with the item repairer
* Added a config option for Solid XP dropping
* Fixed a bug with the game sometimes crashing when looking at an entity with the booklet in hand
* Fixed Drill, Crafter and Filter having an unlocalized name in the GUIs

# 1.10.2-r49
* Fixed a dube bug that caused the reconstructor to create about a thousand bajillion more items and blocks than it should.
* Yea. I'm pretty great. >_<

# 1.10.2-r48
* Added Shock Suppressor, a block that will cause explosions not to happen
* Added Phantom Placer Siding rule button
* Memes
* Made some more blocks store some more information when broken
* Make the miner have the drill's harvest level (4)
* Made display stand only display RF when sneaking
* Remove NEI Integration because it's not getting updated anymore anyways
* Fixed a crash with the phantom redstoneface
* Fixed a dupe bug with the drill and skulls
* Removed booklet stand crafting recipe and doc

# 1.10.2-r47
* Updated to 1.10.2. Could've guessed that by the name.
* Fixed a crash when searching the booklet
* Made empty buckets be able to get pulled out of the coal generator
* Added biomass and biocoal made from canola seeds and made canola edible
* Added medium and large storage crates

# 1.10-r46
* Updated to 1.10. Obviously.
* Made booklet actually save its bookmarks
* Remove custom fake player in favor of vanilla one
* Made fluid collector not input and output fluids all the time causing infinite loops

# 1.9.4-r45
* Added Tesla Energy System support to all RF-using and generating blocks
* Added video introduction page to booklet
* Added Laser Wrench mode switch. Just right-click with one at some point.
* Fixed XP Solidifier values being off
* Made crafter on a stick not randomly create stacks with no items in them
* Changed the way fluids and RF are handled making them both pull AND push to other tiles
* Removed Rarmor integration (for now!) as the mod is in the process of being re-written
* Made Reconstructor and Display stand not keep their items on the client when having them removed
* Updated banner in the mods screen~
* Fixed nullpointer caused by getting capabilities with a null facing sometimes
* Made the Miner less carry-around-y.

# 1.9.4-r44
* Fixed a weird crash with Item Laser Relays.
* Fixed drills sometimes crashing when breaking certain blocks.
###### I am extremely sorry for the amount of updates in the last couple of days. This is hopefully the last one for a few weeks as the most significant bugs with the new features have now been fixed. I hope this doesn't diminish your liking for the mod in any way.

# 1.9.4-r43
### This update increases the performance of some blocks by a significant bit. If you are experiencing lag in a world with many Laser Relays, install this.
* More proper laser relay bounding boxes
* Significantly upped the performance of Item Laser Relays
* Increased ESD performance
* Made Energy Laser Relays less performance intensive
* Changed energy handling system a bit, should be the same in-game
* Made coal and oil generator not randomly appear to be on even though they're not

# 1.9.4-r42
### The shut-up-about-EnderIO Update.
* Changed ESD to allow higher numbers of slots and have no limit on input values so that you can basically make it pull from infintely many slots if the amount of slots present is able to change
* Only add things to smart item laser relay whitelist when they aren't on it already
* Re-added IFluidHandler since apparently everyone's moaning about EnderIO not having updated and my stuff breaking because of it or something like that.

# 1.9.4-r41
* Changed white- and blacklists of laser relays and ESDs to be for putting and pulling. Check them in your world, they might all not work like you want them to anymore!
* Added item filters
* Made ESD's whitelist be a blacklist by default when placing it down
* Added display stand
* Made leaf blower work on display stand
* Made potion rings work on display stand
* Re-add invtweaks support for the storage crate
* Fixed a crash when having phantom liquifaces connected to a non-tileentity
* Made player interface display the amount of RF it has stored
* Made damage source from Reconstructor display properly
* Added a config option to only display the laser relay particles when holding a laser wrench
* Fixed miner sometimes crashing when placed next to certain blocks

# 1.9.4-r40
* Fixed a bug with Laser Relays sometimes randomly disconnecting on chunk load
### You only need this when you have been or are wanting to use Laser Relays of any sort. This version fixes a hideous bug that caused them to randomly disconnect when re-loading chunks for a reason that makes absolutely no sense to me and the people I asked. If you want to know what the bug was, [click here](https://github.com/Ellpeck/ActuallyAdditions/commit/d46bb306d87da57ea180afcd9e6cc00fc96637ef). Again, sorry for the amount of updates, this has just been driving me crazy.

# 1.9.4-r39
* Updated to forge's new fluid transfer and storage system
* Removed a possible memory leak source with world saving
* Added Player Interface
* Fixed configs resetting randomly sometimes
### Sorry for this amount of updates. I've been trying to fix a lot of bugs with them, but it just hasn't been going the way I wanted.

# 1.9.4-r38
* Added custom system to save world data because the Minecraft one always failed on me.
* Note: This WILL disconnect all of your laser relays, sorry. Just reconnect them again. If you find any bugs with the new system (Laser Relay connections), leave an issue on github. Thanks!

# 1.9.4-r37
* Fixed rendering bug with Greenhouse Glass
* Removed unfinished feature tags in booklet
* Added water bowl
* Fixed block breaking effects playing just the sound (drill, leaf blower etc.)
* Fixed aiots not having four times the durability of their base tools
* Made compost only acceppt the current recipe's amount of items
* Made shovels make path blocks instead of farmland
* Changed world data save system to hopefully make laser relays not disconnect on certain occasions but only on the server and I don't get why this is happening
* Made tools be held like tools and not like normal items in third person
* Fixed a syncing bug with tileentities on world join

# 1.9.4-r36
* Added a config option to print all of the booklet's text into a file on startup
* Made booklet words and characters not be more than they should be because everything was being done twice
* Added view online button to booklet
* Fixed a bug with some other mods causing NullPointerExceptions in ActAdd TileEntities
* Fixed ArmorMaterial (sokratis12GR)
* Removed ModelResourceLocation on server making servers not crash on startup anymore.

# 1.9.4-r35
* Added lush caves that spawn randomly in the overworld. Turn them off or change their spawn rate in the config if you don't like them.
* Changed some world data mechanics. Might break some Laser Relay Networks. Sorry about that, just reconnect them.
* Fixed a massive bug with TileEntities not syncing properly when re-entering unloaded chunks
* Re-added booklet stand. Is now a thing on the wall. But works the same.

# 1.9.4-r34
### MINECRAFT 1.9.4!!
* Made special drops dependent on the looting level you have
* Made drop chances for wings, solid xp and cobweb more common
* Added spawner changer to creative tab
* API Version 14 because of multiple changes

# 1.9-r33
* Made Growth Ring performance better
* Centered page number in booklet
* Made placers be able to place anything again
* Made Item Laser Relays that have a whitelist have more priority than those who don't
* Fixed a massive console spam issue of Whitelisted Item Laser Relays trying to send client update packages FROM the client
* The laser relay has less particles again now
* Added custom banner patterns
* Added custom shield patterns
* Remove InvTweaks integration
* Added a smart whitelist button to the whitelisted laser relay that automatically adds items in adjacent inventories to the filter
* Lots of API restructuring
* Bump API Version to 12
* Put circles in front of the booklet entry buttons on the front page because it looks nice

# 1.9-r32
* Added Spawner Changer
* Added Spawner Shards
* Added Item Laser Relay Networks with Item Laser Relays, Advanced Item Laser Relays and Item Interfaces
* Fixed booklet sometimes not typing a letter into the search box
* Fixed sounds not working on servers
* Use universal buckets instead of custom ones
* Replaced bucket slots in GUIs with right click functionality
* Re-implemented village structures. No Villagers though.
* Fixed a bug with fluid placers replacing fluids in front of them
* Added max damage display to the advanced tooltip

# 1.9-r31
* Added Phantom Redstoneface
* Changed storage crate upgrade recipe because it was clashing with Storage Drawers
* Added Reconstruction Module as Rarmor integration. Install Canitzp's Rarmor to find out what it is and does.
* Added Patreon button to the booklet because I wantz all teh moneyz

# 1.9-r30
* Fixed mcmod.info being broken
* Fixed OreDictionary creating empty entries when searching for names
* Fixed laser relays making no particles when the less particle setting is on
* Made HarvestDropsEvents be posted when mining blocks automatically
* Rename itemPotionRIng to itemPotionRing so that it has a proper model and texture
* Rename itemCrystalLightBLue to itemCrystalLightBlue so that it has a proper model and texture
* Added the ender start which is a new crafting ingredient
* Made double furnace and double crusher use the double amount of energy when smelting/crushing two items at once
* Added a default extra whitelist config option for the Item Repairer
* Made directional breaker need energy in pulse mode. Duh.
* Added InvWrapper Capabilities so that inventories work with pipes from mods like NeoTech

# 1.9-r29
* Fixed blocks dropping twice when broken with the drill
* Made drill GUI be openable again (how did I even miss this?)
* Made XP drops from drill dependent on the forge event
* Fixed and re-implemented sounds
* Raised the amount of particles from the laser relay
* Made the knife not give extra attack damage in armor slots
* Fixed a couple of bugs with items that need to be held in hand not working
* Fixed all item models so that they're not held like shields
* Made slab placing a bit more proper
* Fixed most bounding boxes
* Fixed equipment slots in energizer and enervator being off
* Fixed fluid placer not being able to place
* Made fake player not persist into different worlds causing coordinate issues
* Fixed all block models of custom rendered stuff

# 1.9-r28
* Fixed a crash when trying to fill a bucket
##### Because it was pretty much gamebreaking.

# 1.9-r27
### Yes, really. This update pushes the mod's version to 1.9, however it probably is very unstable. There is a few notable bugs and things missing, but I wanted to push this version because a lot of people wanted it.
## If you encounter any bugs, please post them on the Issue Tracker here: https://github.com/Ellpeck/ActuallyAdditions/issues
* Updated the Mod to 1.9
* Did some other stuff
* All of the items, when holding them in your hands, also look like shields right now. I couldn't be bothered to change the JSONs yet, so you'll have to live with it.

# 1.8.9-r26
### This update was done by canitzp. Thanks for helping me out <3
* Made the knife not repairable
* Changed download link for updates to redirect to the proper page
* added click area to JEI for easy recipe access
* Set ESD incomplete. Because it's incomplete.
* Haphazardly revert implementation of new item system for ESD as it was completely broken
* Updated to official RF API.
* Fixed tool JSONs
* Reactivated Atomic Reconstructor Lens Renderer
* Rewrote Smiley Cloud renderer
* Hopefully fixed a minor miner bug (ha ha ha)
* Fixed Double Plant crash
* Fixed JEI booklet page rendering
* New compost rendering
* [API] Removed lens registry

# 1.8.9-r25
* Fixed assets derping because of wrong capitalization
* Ignore NBT Tags of Energy items in JEI
* Added some fun naming stuff to the Reconstructor that makes it have fun stuffz
* Make the floaty special thingie move more awesome! (xdjackiexd)
* Cheap workaround for glitching into some blocks with a smaller hitbox causing graphical glitches and damanging the player
* Fix Smiley Cloud bounding box
* Fix right-clicking never returned any seeds from plants
* Ignore some Items in JEI
* Fix Coffee Machine bounding box
* Added metadata and NBT to the Control Info for ItemStacks
* Added performance settings for bad PCs
* Remove NEI Recipe Handlers until it gets properly updated
* Rebalanced canola oil
* Made a placer be able to apply bonemeal and do other awesome stuff
* Fixed compost and reconstructor syncing with hoppers going wrong
* Updated stuff for the new item system [This is not important for you if you don't make mods!]

# 1.8.9-r24
* Fixed texture names of quartz tools (ssblur)
* Added right-click harvest functionality to all crops. (ssblur)
* Made word count reload when reloading resources
* Fixed Lenses not syncing correctly on the server when converting items
* JEI Implementation for booklet, crusher, coffee machine and reconstructor
* Fixed the drill's 3x3 and 5x5 ming acting weird on servers
* red mushroom -> nether wart in Reconstructor
* Custom packet handler for 98.7% less stack traces when joining worlds
* [API] Removed INEIRecipeHandler
* [API] Removed IHudDisplay
* [API] Removed IEnergyDisplay

# 1.8.9-r23
* Fixed a ginormous bug that screwed over crafting
#### DON'T EXPECT EVERYTHING TO WORK PERFECTLY. Seriously, I break lots of stuff probably.

# 1.8.9-r22
* 1.8.9 PORT!
#### DON'T EXPECT EVERYTHING TO WORK PERFECTLY. THIS IS A BETA. Also: If you look through the booklet, you will see a hover text on some chapters' buttons saying the item or block doesn't fully work. RESPECT THAT. Thanks.

# 1.7.10-r21
* Made looking up the double furnace in the booklet possible
* The magnet ring now only uses RF when actually sucking up items
* The growth ring now only uses RF when growing stuff
* Made the growth ring a little more expensive
* Balanced the magnet a bit
* Made the solar panel thing cheaper
* German translation started (Kristian295)
* New update checker
#### This will probably the last 1.7.10 version before I move on to 1.8.9. The port is almost done and will be coming out soon. There will be an update notification that shows the update, but if you only want to be notified for further 1.7.10 patches, change the config option for the update checker to be version-specific.

# 1.7.10-r20
* Fixed another stupid obfuscation bug. Great. /shrugs

# 1.7.10-r19
* Added API and Dev version as seperate jars
* Fixed a nasty bug with obfuscation causing some booklet pages to crash the game
* [API] Increased version to 3

# 1.7.10-r18
* Added Bacon (Don't name it Ellspeck. Just don't.)
* Added an API.
* [API] Increased version to 2
* [API] Added Crusher Recipe adding
* [API] Added Treasure Chest Recipe adding
* [API] Added Ball Of Fur Recipe adding
* [API] Added Booklet Page/Chapter/Entry adding
* [API] Added Lens adding

# 1.7.10-r17
* Changed Laser Relay particle rate yet again
* Fixed booklet achievement info not showing for some items in some occasions
* Fixed a problem with OreDict entries getting added for items with stack sizes bigger than 1
* Fix pulsable blocks being block update detectors (xdjackiexd)
* Fixed Quartz and Obsidian Tools being the same in the config
* Fixed green and white decor blocks being turnoffable in the config
* Added armor & tools for every gem
* Fix Iguana Tweaks fucking everything up
* Removed special drops and added Solid XP as its own item (That means all solid XP will be gone from your world. I'm very sorry for that.)
* Made Spiders dropping cobwebs less rare (I've never seen that happen before and it's been in the mod from the start!)
* Miner Blacklist and whitelist config
* Booklet Info for Miner's range
* Miner is now in Ores Mode by default so that it doesn't wreck as much when you place it down while it has power
* Removed InterModComms
* Reworked a lot of textures
### At this point, I want to thank Glenthor again. He does all of the awesome textures for ActAdd without actually wanting anything back for it. Thank you so much for that <3
* Made Breakers and Placers cheaper
* Added a Jam House Info Page to the booklet
* Added a disclaimer for noobs about lenses
* Removed the Stone Casing
* Removed ability to close booklet with inventory key - it made John Cena unhappy

# 1.7.10-r16
##### THE GREAT BTM HOTFIX UPDATE ~ ~ ~
* Fixed FastCraft fucking up the booklet if the "bigger unicode font" option is enabled
* #BlameShadowfacts (Seriously, he is to blame.)
* Fixed blocks activating too often in redstone pulse mode
* Fixed Fake Player
* Placers can now place redstone
* Fixed Smiley Cloud's and Laser Relay's Bounding Box sometimes messing up
* Made Laser Relay's particles render less heavily - but also look a bit less good :(
* Made Slabs become right double slabs >_>
* Made Lava Factory update RF properly
* Made the compost update when putting in things with automation (damn.)

# 1.7.10-r15
* Added a fancy hud display instead of chat messages to Reconstructor, Phantomfaces, Manual Stand, Composts and more
* Sneak-Right-Clicking the booklet on an ActAdd block will now open its chapter
* Rebalanced canola to produce more oil
* Fixed Plants sometimes dropping completely nonsensical seed metas
* Added a Page to the Booklet explaining Redstone Flux
* Fixed Reconstructor and Firework Box Redstone Mode
* Added a Disclaimer to the Phantomface Chapter because I was annoyed people were calling it "Short-Range Tesseracts"
* Made Dungeon Loot less common
* Made Reconstructor store less power
* Fixed Coal Generator using infinite resources even when full
* Fix things appearing on Creative Tabs they shouldn't appear on
* Removed Green Dye. It wasn't used and even I had forgotten it existed.
###### This update is also known as the "Sorry that I'm such an idiot asie and everybody else on BTM"-update.

# 1.7.10-r14
* Fixed servers crashing if using anything with particles (Damn.)
* Made Reconstructor react to redstone mode changing
* Made Firework Box react to redstone mode changing
* Fixed Booklet Stands crashing if they are loaded without an entry (duh)

# 1.7.10-r13
* Added Firework Box which shoots out random fireworks
* Added Crate Keeper which allows you to keep items in a Crate you break
* Added Glowstone/Redstone conversion recipes to Reconstructor
* Added a Redstone Pulse Responding mode to some machines
* New Booklet Main Page design
* Ignore missing mappings for foreign paxels when removed
* Fix some bugs with the crusher
* Phantom Placers now have particles
* More Phantom Device particles and animation
* Leaf Gen Particles
* Fixed the compost taking seconds to convert
* Smiley Cloud hearts
* Made Seeds not show up in the misc tab but where they actually should
* Made Fluids save in blocks when breaking them
* Made some animations more smooth
* Made the coal generator more wasteful
* Inform console about fake player
* Fixed some blocks not respecting energy and fluid saving
* Made Tooltips render over main text in booklet (duh)
* Black Quartz decor blocks
* New Changelog (it's awesome, but you won't see it until the next update :()
##### Another reminder: If you want to localize anything in ActAdd, just feel free to submit a pull request on github at https://github.com/Ellpeck/ActuallyAdditions

# 1.7.10-r12
* Crusher, Coffee Machine & Reconstructor SOUNDS! \o/
* Added HoMeCo Jam
* Made TileEntities save if they're redstone powered so that they won't be unpowered on re-entering chunks
* Made Blocks store their energy when broken
* Made Miner usable by making it ignore liquids (Whoops)
* Reconstructor NEI Handler now shows you that you need a lens of color when you do
* Made Jam always edible

# 1.7.10-r11
* Added Vertical Digger
* Added more Achievements
* Added Mouse Controls to the booklet (last page with right mouse button, flip through pages by scrolling)
* Rebalanced Reconstructor a bit
* Made Booklet closable with Inventory Key
* Fixed a Crash with Greenhouse Glass
* Fixed a load of little bugs that were put in during changing of a lot of things (I actually discovered all of these while showing the mod to a friend, so thanks friend!)
* Fixed Christmas seasonal being broken (dang)

# 1.7.10-r10
* Made Redstone power only be determined when something changes (makes performance a lot better)
* Made the damage lens use more energy
* Made the color lens use more energy
* Made the color lens pass through blocks
* Changed recipe of green ethetic quartz
* Fixed bug with the booklet crashing if a crafting recipe is turned off

# 1.7.10-r9
* Added Explosion Lens
* Added Lens of Certain Death
* Lenses now render on the Atomic Reconstructor
* Rotten Flesh -> Leather in Atomic Reconstructor
* Added Colorable Drills
* Added Green Dye made from fern/grass in a Crusher
* DUH DUH DUH DUUUH
* Made Laser Relays smaller and gave them a proper bounding box (stop moaning, jackie!)
* Removed unnecessary things from the OreDict that were added just for crafting recipes
* Fixed booklet stand not working on servers (Kind of defeated its purpose. Maybe I should've tested it before.)
* Made booklet stand actually be wooden (Harvestable with axe & wood sounds)
* Fixed a bug where sometimes NEI would crash when looking at recipes before opening the booklet for the first time in a world
* Fix some bugs with the NEI Integration of the Booklet
* Added page display to the NEI Integration of the Booklet
* Fixed bug with Villager trade handler (how did that even happen in the first place?)
* Reorganized the booklet a bit
* Removed lots of config options (yes, really.)
* Greenhouse Glass Performance improvement

# 1.7.10-r8
* Added Lens Mechanic for the Reconstructor
* Added Color Cycling Recipes for wool, stained clay and stained glass for the Reconstructor
* Added Booklet Stand
* Made Drill fire block breaking event
* Fix some blocks not working with the Drill
* Fixed a bug where items would be deleted when exiting the booklet sometimes
* Fixed Solar Panel working even though blocked
* Made text for bugged items less dumb
* Removed german lang file as it's neither getting used nor updated
##### (If you want to localize ActAdd in any language yourself, just make a pull request!)
* Fix Reconstructor not using the right amount of energy for items
* Made Update Checker show after a certain amount of time
* Made Drill harvest slowly instead of not at all when out of energy

# 1.7.10-r7
* Made the Atomic Reconstructor save its power
* Added Automation Tutorial for Atomic Reconstructor
* Added Background Graphic for current chapter's name in booklet
* Added Bookmarks
* Made Magnet Ring Energy balancing better (Might need to reset configs!)
* Made Phantom Device Range being tested better (Check your Phantomfaces, they might not be in range anymore now!)
* Sneaking breaks rings' ability now (Magnet Ring, Growth Ring, Water Removal Ring)
* Made TileEntities update less often to reduce server traffic
* Made ActAdd Fluid Names not clash with other mods' ones
* Made Wild Plants not generate on Mycelium and plop off right away
* Added OreGen and Plant Gen Dimension Blacklist
* Atomic Reconstructor now uses Energy depending on the recipe
* Added misc. reconstructor Recipes
* Added Ethetic Quartz, a decor block and wall, slab & stair variants of them

# 1.7.10-r6
* Added Atomic Reconstructor
* Added Crystals replacing Items like Redstone & Iron Ingots in most crafting recipes
* Seriously fixed Laser Relays disconnecting on their own now, promise! (THE DESPERATION..)
* Fix memory leaks
* Fixed Blocks having an Item Renderer in Inventory
* Updated Smiley Clouds
* New Cheeves! (More to come!)
* Achievement Information for the booklet
* Emerald & Purple Drill
* Added Dungeon Loot

# 1.7.10-r5
* Fixed some unnecessary performance use when transfering power with laser relays
* Fixed Laser Relays disconnecting themselves when
    -they're not edited the entire time a world is loaded
    -a world is loaded wrongly
    -a player leaves an area on servers sometimes
* //Sorry for this update, it's a really important fix though//

# 1.7.10-r4
* Fixed the Drill not being able to mine Obsidian correctly when its harvest level was set higher than 4
* AtomSponge Smiley Cloud
* TB Stairs, Walls, Slabs and Blocks. Unused as of yet, however you can build with them.
* Moved Villager Registring to PostInit to prevent OreDictionary derpups with items getting changed
* Drill + Torches = Instant 3x3 Mining? Nope!
* Misc Crusher Recipes
* Added Word and Character Counter to the booklet. Doesn't do anything, it's just kinda fun
* Update Smiley Cloud Easter Eggs
* Hopefully fixed Laser Relay Connections sometimes breaking when quitting a world or server

# 1.7.10-r3
* Fixed the Heat Collector not outputting power if its internal buffer is full
* Fixed Crash when the manual was open during a world crash or kick
* Removed seasonal booklet page changes
* Changed the way special overhead rendering for special people is handled (preparing for something coming up in the future!)
* Added Black Lotus that spawns in the world
* Added black dye that is craftable from the Black Lotus
* Fixed a potential bug with booklet crafting page crashing if a Crafting Recipe doesn't have inputs
* Added some more smiley clouds

# 1.7.10-r2
* Fix a Crash on Server with the Laser Relay
* Gave the Laser Relay an Energy Transfer Cap
* Added Preview Pictures to the booklet (Thanks to KittyVanCat for help!)
* Fix Slabs
* Fixed Bug with Double Furnace and Double Crusher not crushing if both slots are filled (Thanks to KittyVanCat)
* Remove the Magnetic Miner

# 1.7.10-r1
* Finally out of beta! ~
* Added the Laser Relay, a block that works like a cable to transfer RF, only without the cable but with LAZORZ
* Added Laser Wrench (Texture by MineLoad)
* Moved server-side Player Data to WorldSavedData because ExtendedEntityProperties doesn't work properly
* Made PersistentClientData less write-y
* Added Booklet Priority Colors & Changed Selection Design
* Added Valentine's Day Seasonal
* Chocolate Toast (canitzp)
* New AIOT Textures
* AIOT Integration for SimpleOres 2
* Changed Ball Of Hair Name to "Ball Of Fur", added Booklet Page
* Added Iron Casing & Drill Core
