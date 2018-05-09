# I have listed changes i forgot to put in description here. Read it before you do stuff
# top-down-shooter

Changes:
- balanced weapons more(go ahead and change if u want)

property | variable name
--- | ---
bullet speed | speedMultiplier (in bullet class)
fire rate | firerate (in gun class)
damage | damage (in bullet class)


- moved explosion method to bullet class

- ZombieBoss added
  - 3 attacks (spawn ZombieMobs, speed up mobs, growAndShrink)
  - Knocks back player on spawn and collision
  - added Boss class
- Mob class changed
  - isBoss boolean added
- Player has collideWithBoss method, will run if isBoss == true
  
New:
- sniper
- machinegun
- ZombieBoss

To do(rough sketch):
- implement ammo system

