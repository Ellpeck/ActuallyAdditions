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
