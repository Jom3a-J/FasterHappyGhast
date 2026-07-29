# sqrt(1.5) - 1
data modify storage faster_happy_ghast:config mult set value 0.22474487
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Top speed set to 1.5x vanilla (about 5 blocks/sec)."
