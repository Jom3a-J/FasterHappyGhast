# Set any speed you like, instead of using a preset.
#
#   /function faster_happy_ghast:set {mult:0.34164}
#
# `mult` is sqrt(N) - 1 for an N-times speed increase, because ridden flight speed scales with the
# SQUARE of flying_speed. Common values:
#
#   1.5x -> 0.22474      3x -> 0.73205      6x -> 1.44949
#   1.8x -> 0.34164      4x -> 1.00000      8x -> 1.82843
#   2.0x -> 0.41421      5x -> 1.23607     10x -> 2.16228
#
# Anything above about 5x outruns chunk loading on most machines.
$data modify storage faster_happy_ghast:config mult set value $(mult)
data modify storage faster_happy_ghast:config enabled set value 1b
function faster_happy_ghast:refresh
tellraw @a "[Faster Happy Ghast] Speed updated. Run faster_happy_ghast:status to check it."
