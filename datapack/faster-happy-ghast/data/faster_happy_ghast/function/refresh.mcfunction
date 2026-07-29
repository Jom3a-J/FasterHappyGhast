# Strip the modifier and the marker tag so the next tick re-applies the current multiplier.
execute as @e[type=minecraft:happy_ghast,tag=fhg_boosted] run attribute @s minecraft:flying_speed modifier remove faster_happy_ghast:speed
tag @e[type=minecraft:happy_ghast] remove fhg_boosted
