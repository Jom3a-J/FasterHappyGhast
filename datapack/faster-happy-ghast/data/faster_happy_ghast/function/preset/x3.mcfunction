# sqrt(3) - 1. Matches the mod's default.
data modify storage faster_happy_ghast:config mult set value 0.73205081
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 3x vanilla (about 9.9 blocks/sec)."
