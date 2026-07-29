![BANNER](https://cdn.modrinth.com/data/cached_images/4a54f1f3759cb3da3ed351760307e8f818339dfb.png)
## The problem

A vanilla ridden Happy Ghast tops out at about **3.3 blocks/second**. Walking is 4.3. Sprinting is 5.6.

You can outrun your own ghast on foot. Climbing is worse — roughly 1.6 blocks/second, so gaining any real altitude means holding jump and waiting.

This mod reworks the flight physics while a player is steering. Wild ghasts drifting around the world are left completely alone.

## What changes

### Actually fast

Default is **2x vanilla — about 6.6 blocks/second**, which finally puts a ghast above a sprinting player. Hold sprint for **8.6 blocks/second**, just under a horse.

![speed](https://cdn.modrinth.com/data/cached_images/ef8a588e51b44ff1b0c1df067fc30f8e54ed840b.gif)

### Climbing that doesn't test your patience

Vertical movement gets its own multiplier on top of the speed boost. Hold jump to climb, look down and hold forward to dive.

![climp](https://cdn.modrinth.com/data/cached_images/8d277afcdaec5fa0d6e31dc44e5eddab1cb9e29c.gif)

### Sprint while riding

Vanilla flatly forbids sprinting on a Happy Ghast — not a balance decision, just an unimplemented flag. This turns it on, so the vanilla sprint key gives you a burst of speed with no new keybind to learn.

![sprinting](https://cdn.modrinth.com/data/cached_images/8c32fc10efdd3f8342ed46308f339b6d433a4513.gif)

## Defaults

| Setting | Default | Result |
| --- | --- | --- |
| Top speed | 200% | ~6.6 blocks/sec |
| Climb / dive speed | 150% | ~4.9 blocks/sec climbing |
| Sprint boost | On, 130% | ~8.6 blocks/sec |
| Responsiveness | 50% | Noticeably tighter than vanilla drift |
| Turn rate | 15% | Vanilla is 8% |

The defaults aim for **usable, not overpowered** — a ghast that's worth riding, not one that trivialises travel.

If you do want it broken, that's your call: Fabric's sliders reach 1000%, roughly 33 blocks/second, which is elytra territory and fast enough to outrun chunk loading.

## Configuration

Everything is adjustable in-game, no restart needed.

- **Fabric** — Mods → Faster Happy Ghast → Config (via Mod Menu)
- **NeoForge** — Mods → Faster Happy Ghast → Config, built in

![config](https://cdn.modrinth.com/data/cached_images/c7453ec94141dcb9fe7ab3f5cef29abd31d5824d_0.webp)

## Installation

**Fabric**
- [Fabric API](https://modrinth.com/mod/fabric-api) — required
- [Cloth Config](https://modrinth.com/mod/cloth-config) — required
- [Mod Menu](https://modrinth.com/mod/modmenu) — optional, but it's how you open the config screen

**NeoForge**
- No dependencies

**Quilt**
- Use the Fabric jar. Quilt Loader runs it through its Fabric compatibility layer.

## Multiplayer

**Install this on the client.** A ridden Happy Ghast is client-authoritative — the server doesn't simulate its movement, it accepts the position your client reports. So the mod works on servers that don't have it installed.

That also means it looks exactly like a movement cheat to anti-cheat plugins. **Check with the server's admins before using it there.**

## Datapack version

A datapack is included for vanilla servers with no mod loader at all. It's deliberately less capable — it can raise top speed and nothing else, because responsiveness, turn rate and the sprint boost are hardcoded values with no attribute behind them.

Two differences worth knowing: it speeds up wild ghasts too (the attribute belongs to the entity, not the rider), and it writes into your world save, so run `/function faster_happy_ghast:uninstall` before removing it.

## Compatibility

- Minecraft **26.2**
- Fabric, NeoForge, Quilt (via the Fabric jar)
- Touches only the ridden Happy Ghast's movement — no world generation, no new items, no registry changes

## License

MIT. Do what you like with it.
