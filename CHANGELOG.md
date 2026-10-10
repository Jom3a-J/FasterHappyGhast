# Changelog

## 1.1.1 — 2026-10-10

Still Minecraft **26.3**. Flight physics and every default are unchanged, and your config carries over.

### Added

- **Free look.** Hold **Left Alt** while riding a Happy Ghast to look around without steering it — the ghast keeps flying the way it was heading. Let go and the view eases back to your flight direction. Works in first and third person. Rebind or unbind it under Controls → Movement → "Free Look (Happy Ghast)".

### Fixed

- **The datapack actually loads on 26.3 now.** 1.1.0 fixed its pack format but missed that 26.3 also changed the predicate format, so the world refused to load with the pack enabled. If you hit that, update the pack and enable it again with `/datapack enable "file/faster-happy-ghast"`.

## 1.1.0 — 2026-09-16

Minecraft **26.3** support. This is a port, not a feature release — **nothing about how the ghast flies has changed.** Your config carries over and the defaults are identical.

If you're on 26.2, stay on 1.0.0. This build won't load there.

### Updated

- Minecraft 26.2 → **26.3**, on both Fabric and NeoForge
- Fabric API 0.156.0 → 0.160.6, Fabric Loader 0.19.3 → 0.19.5
- NeoForge 26.2.0.37-beta → 26.3.0.3-beta
- Cloth Config 26.2.155 → 26.3.158, Mod Menu 20.0.1 → 21.0.0-beta.1

Grab the 26.3 builds of Fabric API, Cloth Config and Mod Menu — the 26.2 ones won't work alongside this.

### Fixed

- **The datapack now loads on 26.3.** Its pack format was still declared as 26.2's, so 26.3 rejected it outright. If you use the datapack rather than the mod, this was a hard failure, not a cosmetic one.
- NeoForge's mod list shows the icon and banner again, using the properties NeoForge expects now that the old one is deprecated.

### Unchanged

Flight physics, the config screen and every default are untouched. All four movement hooks were verified still applying correctly on 26.3 before release.

## 1.0.0 — 2026-07-30

First release, for Minecraft 26.2 on Fabric and NeoForge.

A vanilla ridden Happy Ghast tops out around 3.3 blocks/second — slower than walking. This reworks the flight physics while a player is steering, and leaves wild ghasts completely alone.

- **Top speed** — 2x vanilla by default, about 6.6 blocks/second
- **Climbing and diving** — its own multiplier on top of the speed boost, so gaining altitude stops being a waiting game
- **Sprinting while riding** — vanilla forbids it through an unimplemented flag rather than a balance decision; this turns it on, reaching about 8.6 blocks/second on the vanilla sprint key
- **Responsiveness and turn rate** — both configurable, tuned tighter than vanilla's drift by default

Everything is adjustable in-game with no restart, through Mod Menu on Fabric and the built-in config screen on NeoForge.

Also ships a datapack for vanilla servers with no mod loader. It can raise top speed and nothing else, since responsiveness, turn rate and the sprint boost have no attribute behind them — and it speeds up wild ghasts too, because the attribute belongs to the entity rather than the rider.
