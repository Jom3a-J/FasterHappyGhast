# Attribute modifiers are saved onto the entity, so removing the datapack alone would leave every
# ghast permanently boosted. Run this FIRST, while the pack is still loaded, then delete the pack.
# Only ghasts in loaded chunks can be cleaned, so fly near any you have tamed before running it.
data modify storage faster_happy_ghast:config enabled set value 0b
execute as @e[type=minecraft:happy_ghast,tag=fhg_boosted] run attribute @s minecraft:flying_speed modifier remove faster_happy_ghast:speed
tag @e[type=minecraft:happy_ghast] remove fhg_boosted
tellraw @a "[Faster Happy Ghast] Disabled and cleaned up. You can remove the datapack now."
