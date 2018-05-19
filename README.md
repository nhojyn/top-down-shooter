# Top Down Shooter (5/18/2018)
Added: 
- new classes
  - PickUp, AmmoPickup, HealthPickup
- rare chance to get ammo and health
- ammocap,addAmmo
Bug: 
- heals over the cap, so the health bar goes off screen

Read the comments if you have questions/troubles
********************************************************************************
# Top Down Shooter (5/12/2018) (Round Generation)
Added: 
- new classes
  - Round, BossRound, RoundsList
- Automatic round spawning (press the button to start it)
- backbone for future rounds
- kill all mobs button (for testing)

Plan to add:
- upgrade round
- future round's spawns and stuff

Read the comments if you have questions/troubles
********************************************************************************

# Top Down Shooter (5/11/2018)
Added:
- limit for flame range
Changes:
- adjusted gun sizes + bullet sizes
- bazooka explosion size is bigger
- changed some of the math for setLocation
	- the math doesn't work perfectly but it doesn't look bad(the bullets spawn slightly off depending on the angle)
********************************************************************************

# Top Down Shooter (5/9/2018)
Added:
- added Splitter mob
  - Splitter mob has 2 methods to create, the method that has 4 parameters is the one that is called to "split" the mob the other method is used to create an inital mob
- small bug fixes (UI fixes, player cannot move off screen)
To do(Will be in next patch):
- limit flame range
- fix knockback on mobs

## Make sure to check the unzip the folder inside
********************************************************************************

# Top Down Shooter (5/9/2018)
Changes:
- Shotgun only shoots one bullet per shot instead of 5
- Cleaned up UI
- GOT RID OF STATUS CLASS (ill remove the class next patch if there are no issues)
- added Boss HP counter
  - All Boss must create a hp bar when spawned (IMPORTANT)
- added blink ability (needs to be fine tuned)
********************************************************************************

# Top Down Shooter (5/8/2018)
Changes:
- made pistol weaker(damage 3 -> 2)
- balanced weapons more(go ahead and change if u want)

property | variable name
--- | ---
bullet speed | speedMultiplier (in bullet class)
fire rate | firerate (in gun class)
damage | damage (in bullet class)
knockback mobs | knockBack (in bullet class)

- moved explosion method to bullet class

- ZombieBoss added
  - 3 attacks (spawn ZombieMobs, speed up mobs, ```growAndShrink()```)
  - Knocks back player on spawn and collision
  - added Boss class
- Mob class changed
  - ```isBoss``` boolean added
- Player has ```collideWithBoss()``` method, will run if ```isBoss == true```
  
New:
- flamethrower
- detects ammo
- enemy gets knockback according to bullet's knockback(all have been defaulted to 20 except for flamethrower)
- sniper
- machinegun
- ZombieBoss

To do(rough sketch):
- limit flame range
- balance gun
- display ammo (not displaying correctly)

