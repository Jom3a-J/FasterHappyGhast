# sqrt(1.8) - 1. A little gentler than the mod's 2x default.
data modify storage faster_happy_ghast:config mult set value 0.34164079
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 1.8x vanilla (about 6 blocks/sec)."
