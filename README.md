![BANNER](https://cdn.modrinth.com/data/cached_images/4a54f1f3759cb3da3ed351760307e8f818339dfb.png)

Faster Happy Ghast makes the Happy Ghast worth riding: faster flight, quicker climbing, sprinting, and a free-look camera so you can take in the view without veering off course. Wild ghasts drifting around the world are left completely alone.

## The problem

A vanilla ridden Happy Ghast tops out at about **3.3 blocks/second**. Walking is 4.3. Sprinting is 5.6.

You can outrun your own ghast on foot. Climbing is worse — roughly 1.6 blocks/second, so gaining any real altitude means holding jump and waiting.

And it flies wherever you look. Turn your head to admire the view and the ghast turns with it.

This mod reworks the flight physics while a player is steering, and gives you a camera that can look around on its own.

## What changes

### Actually fast

Default is **2x vanilla — about 6.6 blocks/second**, which finally puts a ghast above a sprinting player. Hold sprint for **8.6 blocks/second**, just under a horse.

![Ridden Happy Ghast flying at the boosted speed](https://cdn.modrinth.com/data/cached_images/ef8a588e51b44ff1b0c1df067fc30f8e54ed840b.gif)

### Climbing that doesn't test your patience

Vertical movement gets its own multiplier on top of the speed boost. Hold jump to climb, look down and hold forward to dive.

![Happy Ghast climbing quickly](https://cdn.modrinth.com/data/cached_images/8d277afcdaec5fa0d6e31dc44e5eddab1cb9e29c.gif)

### Sprint while riding

Vanilla flatly forbids sprinting on a Happy Ghast — not a balance decision, just an unimplemented flag. This turns it on, so the vanilla sprint key gives you a burst of speed with no new keybind to learn.

![Sprinting on a Happy Ghast](https://cdn.modrinth.com/data/cached_images/8c32fc10efdd3f8342ed46308f339b6d433a4513.gif)

### Look around without changing course

Hold **Left Alt** and the camera comes loose from your heading. Look behind you, swing round to the ghast's face, follow something on the ground — the ghast keeps flying straight the whole time. Let go and the view eases back to your flight direction.

<!-- Free look GIF: add the Modrinth link here -->

Works in first and third person. Rebind or unbind it under Controls → Movement → "Free Look (Happy Ghast)".

## Defaults

| Setting | Default | Result |
| --- | --- | --- |
| Top speed | 200% | ~6.6 blocks/sec |
| Climb / dive speed | 150% | ~4.9 blocks/sec climbing |
| Sprint boost | On, 130% | ~8.6 blocks/sec |
| Responsiveness | 50% | Noticeably tighter than vanilla drift |
| Turn rate | 15% | Vanilla is 8% |
| Free look | Left Alt | Hold to look around, release to return |

The defaults aim for **usable, not overpowered** — a ghast that's worth riding, not one that trivialises travel.

If you do want it broken, that's your call: Fabric's sliders reach 1000%, roughly 33 blocks/second, which is elytra territory and fast enough to outrun chunk loading.

## Configuration

Everything is adjustable in-game, no restart needed.

- **Fabric** — Mods → Faster Happy Ghast → Config (via Mod Menu)
- **NeoForge** — Mods → Faster Happy Ghast → Config, built in
- **Free look key** — Options → Controls → Movement, like any other keybind

![Config screen](https://cdn.modrinth.com/data/cached_images/c7453ec94141dcb9fe7ab3f5cef29abd31d5824d_0.webp)

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

That also means the speed boost looks exactly like a movement cheat to anti-cheat plugins. **Check with the server's admins before using it there.**

Free look is different: it only moves your camera, and the server never sees it.

## Datapack version

A datapack is included for vanilla servers with no mod loader at all. It's deliberately less capable — it can raise top speed and nothing else, because responsiveness, turn rate and the sprint boost are hardcoded values with no attribute behind them, and free look needs a client mod.

Two differences worth knowing: it speeds up wild ghasts too (the attribute belongs to the entity, not the rider), and it writes into your world save, so run `/function faster_happy_ghast:uninstall` before removing it.

## Compatibility

- Minecraft **26.3**
- Fabric, NeoForge, Quilt (via the Fabric jar)
- Touches only the ridden Happy Ghast's movement and your camera while riding — no world generation, no new items, no registry changes
- Other free-look and camera mods often use Left Alt too. If two of them react to the same key, rebind one.

What changed between releases: [CHANGELOG.md](CHANGELOG.md).

## License

MIT. Do what you like with it.
