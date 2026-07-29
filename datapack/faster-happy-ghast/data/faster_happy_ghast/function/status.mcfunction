# Shows the stored config, plus the live attribute on the nearest adult ghast so you can confirm
# the modifier actually landed. Vanilla flying_speed is 0.05.
tellraw @s "[Faster Happy Ghast] Stored datapack config:"
data get storage faster_happy_ghast:config
execute as @e[type=minecraft:happy_ghast,limit=1,sort=nearest,predicate=faster_happy_ghast:is_adult] run attribute @s minecraft:flying_speed get
