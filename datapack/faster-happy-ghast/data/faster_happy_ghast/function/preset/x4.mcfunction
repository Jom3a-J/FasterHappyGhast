# sqrt(4) - 1
data modify storage faster_happy_ghast:config mult set value 1.0
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 4x vanilla (about 13 blocks/sec)."
