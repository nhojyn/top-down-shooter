# I have listed changes i forgot to put in description here. Read it before you do stuff
# top-down-shooter

- basework for future new mobs made
- Mob class made abstract,
- ZombieMob is class for old mob
- LaserMachine is new mob (read comments at the top of the class)
- Added MobProjectiles (and Laser, which is a child class)
- Added checker for MobProjectiles (in collisionChecker() in game class)
- spawn swarm button changed to spawn LaserMachine instead of ZombieMob (temporary change)

- body for Mob is now a Shape, no longer a rectangle
- new interface "TrueBounds" to make sure that every mob returns the height and width of the body and not their pane (to deal with projectiles)
- check interface for method names and stuff
- there might be other stuff im missing so if i missed something, ask me
