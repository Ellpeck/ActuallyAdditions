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
