# I have listed changes i forgot to put in description here. Read it before you do stuff
# top-down-shooter

Changes:
-made pistol weaker(damage 3 -> 2)

New:
- flamethrower
- detects ammo
- enemy gets knockback according to bullet's knockback(all have been defaulted to 20 except for flamethrower)

To do(rough sketch):
- limit flame range
- balance gun
- display ammo (not displaying correctly)

If you want to balance guns:

property   -    variable name
[bullet speed - speedMultplier(in bullet class)]
[fire rate - firerate(in gun class)]
[damage - damage(in bullet class)]
[knockback mobs - knockBack(in bullet class)}