## Make sure to check the Wiki for mp3 files to download

# Top Down Shooter (5/9/2018)
Changes:
- Shotgun only shoots one bullet per shot instead of 5
- Cleaned up UI
- GOT RID OF STATUS CLASS (ill remove the class next patch if there are no issues)
- added Boss HP counter
  - All Boss must create a hp bar when spawned (IMPORTANT)
- added blink ability (needs to be fine tuned)

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

