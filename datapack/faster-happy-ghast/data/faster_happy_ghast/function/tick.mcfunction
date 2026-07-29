# Ghasts are tagged once boosted, so this only touches ones that just spawned or just loaded.
execute unless data storage faster_happy_ghast:config {enabled:0b} as @e[type=minecraft:happy_ghast,tag=!fhg_boosted,predicate=faster_happy_ghast:is_adult] run function faster_happy_ghast:apply with storage faster_happy_ghast:config
