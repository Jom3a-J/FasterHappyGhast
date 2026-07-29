# Faster Happy Ghast

Makes the Happy Ghast a mount worth riding, for **Fabric** and **NeoForge** on Minecraft 26.2,
plus a cut-down **datapack** for vanilla servers.

Vanilla's ridden Happy Ghast tops out at roughly **3.3 blocks/second** — slower than walking, and it
climbs at about 1.6 blocks/second. This mod reworks the flight physics while a player is steering.
Wild, unridden ghasts are left alone.

## What it changes

| Setting | Default | What it does |
| --- | --- | --- |
| Top speed | 200% | Top flying speed relative to vanilla. 200% is about 6.6 blocks/sec. |
| Climb / dive speed | 150% | Extra multiplier on vertical movement, on top of top speed. |
| Sprint boost | on | Lets you sprint while riding — vanilla forbids it outright. |
| Sprint boost strength | 130% | Top speed multiplier while sprinting, so about 8.6 blocks/sec. |
| Responsiveness | 50% | How fast the ghast reaches and sheds its speed. Does not change top speed. |
| Turn rate | 15% | How fast the ghast swings towards where you look. Vanilla is 8%. |

The defaults aim for *usable*, not powerful. For scale: vanilla is 3.3 blocks/sec (slower than
walking), a sprinting player is 5.6, and a horse is 9 to 14. The defaults put a cruising ghast just
above a sprinting player and a sprinting ghast just below a horse.

Going overpowered is left to you. Fabric's sliders reach 1000% — roughly 33 blocks/sec, which is
elytra territory and fast enough to outrun chunk loading. NeoForge's reach 350%; that is a platform
limit, not a choice (see below).

Hold **jump** to climb, look down and hold **forward** to dive, hold **sprint** (with forward) to boost.

## Project layout

```
common/     shared source: FhgConfig + HappyGhastMixin (all the actual logic)
fabric/     Fabric entrypoint, JSON config, Cloth Config screen, Mod Menu hook
neoforge/   NeoForge entrypoint, ModConfigSpec config
datapack/   standalone datapack, no mod loader required
```

`common` is a plain shared source directory, not a Gradle subproject. Minecraft 26.2 ships
**unobfuscated** — the version manifest has no `client_mappings`/`server_mappings` entry — so both
loaders see identical class names and the shared sources need no remapping transform. This is also
why both build files use `mappings loom.layered {}` (identity) rather than `officialMojangMappings()`,
which has nothing to download on this version.

Build everything with `./gradlew build`; jars land in `fabric/build/libs/` and `neoforge/build/libs/`.

## Dependencies

**Fabric:** Fabric API, [Cloth Config](https://modrinth.com/mod/cloth-config) (required, powers the
config screen), [Mod Menu](https://modrinth.com/mod/modmenu) (optional, how you reach the screen).

**NeoForge:** none. Uses NeoForge's built-in config system and its generated settings screen.

NeoForge's `ConfigurationScreen` only draws a slider for an integer option whose range spans fewer
than 256 steps — everything else becomes a free-text box. That is why this side stores integer
percentages and caps top speed at 350% where Fabric reaches 1000%. Sliders and a wide range are
mutually exclusive there; sliders won.

## Quilt

There is no separate Quilt build and there does not need to be. Quilt Loader loads Fabric mods
through its Fabric compatibility layer, so the Fabric jar is the Quilt jar. Note that Quilt's own
API (QSL/QFAPI) is dormant — its newest Minecraft support is 1.21, last published August 2024 — so
there will not be a Quilt-native build for 26.2.

## Client or server?

**Install on the client.** A ridden Happy Ghast is client-authoritative: the server does not
simulate its movement, it accepts the position the rider's client reports. The mod therefore only
takes effect where it is installed on the client, and works on servers that lack it.

On a multiplayer server this looks exactly like a movement cheat to anti-cheat plugins. Check with
the admins first.

## Datapack

`datapack/faster-happy-ghast/` needs no mod loader, and works on a vanilla server. It is
deliberately less capable than the mod:

| Feature | Datapack |
| --- | --- |
| Top speed | yes, via the `flying_speed` attribute |
| Climb / dive | scales with top speed, no separate control |
| Sprint boost | no — needs the `canSprint` override |
| Responsiveness | no — the 0.91 drag is a hardcoded constant |
| Turn rate | no — the 0.08 steering lerp is a hardcoded constant |

Two side effects the mod does not have: the attribute belongs to the entity rather than the rider,
so **wild ghasts also fly faster**, and attribute modifiers are **saved into the world**.

### Changing the datapack's settings

It defaults to 2x, the same as the mod. Presets, run as commands:

```
/function faster_happy_ghast:preset/x1_5
/function faster_happy_ghast:preset/x1_8
/function faster_happy_ghast:preset/x2
/function faster_happy_ghast:preset/x3
/function faster_happy_ghast:preset/x4
/function faster_happy_ghast:preset/x5
/function faster_happy_ghast:preset/x8
```

For any other value, pass it directly:

```
/function faster_happy_ghast:set {mult:0.34164}
```

`mult` is `sqrt(N) - 1` for an N-times increase, because ridden flight speed scales with the
*square* of `flying_speed`:

| Speed | mult | | Speed | mult |
| --- | --- | --- | --- | --- |
| 1.5x | 0.22474 | | 4x | 1.00000 |
| 1.8x | 0.34164 | | 5x | 1.23607 |
| 2x | 0.41421 | | 6x | 1.44949 |
| 3x | 0.73205 | | 8x | 1.82843 |

To check what is actually applied, including the live attribute on the nearest ghast:

```
/function faster_happy_ghast:status
```

Vanilla `flying_speed` reads `0.05`; at the default 2x it should read about `0.0707`.

Run `/function faster_happy_ghast:uninstall` **before** deleting the pack, or every ghast keeps its
boost permanently. Only ghasts in loaded chunks can be cleaned up.

The presets store `sqrt(N) - 1`, not `N - 1`, because ridden flight speed scales with the *square*
of `flying_speed` — the game multiplies the attribute into both the rider's input vector and the
travel speed.

## How it works

Four changes to `net.minecraft.world.entity.animal.happyghast.HappyGhast`, all in
`common/src/main/java/com/fhg/mixin/HappyGhastMixin.java`:

- `travel` is replaced while a player is steering. Vanilla accelerates by `input * speed` each tick
  and retains 91% of velocity, settling at `acceleration * 0.91 / 0.09`. Both the acceleration and
  the drag are recomputed, with the acceleration scaled to compensate for the drag change so that
  "responsiveness" and "top speed" stay independent of each other.
- `getRiddenInput` gets its vertical component scaled for the climb/dive multiplier.
- `tickRidden`'s hardcoded 8% steering lerp becomes configurable.
- `canSprint` is added, because `Entity#canSprint` is false by default and the Happy Ghast never
  overrides it — which is why you cannot sprint on one in vanilla. Allowing it means the client's
  own sprint handling drives the boost, with no custom keybind or packet. Camel does the same.

## License

MIT. See [LICENSE](LICENSE).
