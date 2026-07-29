# sqrt(5) - 1
data modify storage faster_happy_ghast:config mult set value 1.23606798
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 5x vanilla (about 16.5 blocks/sec)."
