# sqrt(8) - 1. Fast enough to outrun chunk loading on slower machines.
data modify storage faster_happy_ghast:config mult set value 1.82842712
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 8x vanilla (about 26 blocks/sec)."
