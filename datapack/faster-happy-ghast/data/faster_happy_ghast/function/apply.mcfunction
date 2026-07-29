# Ridden flight speed scales with the SQUARE of flying_speed: the game multiplies the attribute
# into both the rider's input vector and the travel speed. So the stored multiplier is sqrt(N)-1
# for an N-times speed increase, applied as add_multiplied_base.
$attribute @s minecraft:flying_speed modifier add faster_happy_ghast:speed $(mult) add_multiplied_base
tag @s add fhg_boosted
