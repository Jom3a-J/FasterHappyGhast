# sqrt(2) - 1. Matches the mod's shipped default.
data modify storage faster_happy_ghast:config mult set value 0.41421356
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 2x vanilla (about 6.6 blocks/sec)."
