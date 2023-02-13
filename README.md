<p align="center">
    <img src="https://github.com/czQery/ToolKit/blob/master/banner.png?raw=true" alt="Logo">
</p>

[![Version](https://img.shields.io/badge/version-v3.8-informational.svg)](https://github.com/czQery/ToolKit/releases)
[![Releases](https://img.shields.io/badge/download-1.19.3-brightgreen.svg)](https://github.com/czQery/ToolKit/releases/latest/download/ToolKit-3.8.jar)

## Commands

| Command   | Permission           | Protection                  | Description                           |
|-----------|----------------------|-----------------------------|---------------------------------------|
| /crash    | toolkit.crash        | toolkit.crash.bypass        | Lag/Crash players game                |
| /skick    | toolkit.skick        | toolkit.skick.bypass        | Badlion users cannot exit kick screen |
| /troll    | toolkit.troll        | toolkit.troll.bypass        | Just troll command :)                 |
| /cmdblock | toolkit.commandblock | toolkit.commandblock.bypass | Allows to block specific commands     |

| Command | Permission    | Other               | Description                           |
|---------|---------------|---------------------|---------------------------------------|
| /crawl  | toolkit.crawl | toolkit.crawl.other | Allows the player to crawl            |
| /sit    | toolkit.sit   | toolkit.sit.other   | Allows the player to sit down         |
| /gmc    | toolkit.gmc   | toolkit.gmc.other   | Switch player's gamemode to CREATIVE  |
| /gms    | toolkit.gms   | toolkit.gms.other   | Switch player's gamemode to SURVIVAL  |
| /gma    | toolkit.gma   | toolkit.gma.other   | Switch player's gamemode to ADVENTURE |
| /gmsp   | toolkit.gmsp  | toolkit.gmsp.other  | Switch player's gamemode to SPECTATOR |
| /spawn  | toolkit.spawn | toolkit.spawn.other | Teleports player to spawn             |
| /fly    | toolkit.fly   | toolkit.fly.other   | Allows the player to fly              |
| /wc     | toolkit.wc    | toolkit.wc.other    | Clear weather                         |
| /ic     | toolkit.ic    | toolkit.ic.other    | Clear inventory                       |

| Command  | Permission     | Description                                |
|----------|----------------|--------------------------------------------|
| /vanish  | toolkit.vanish | Allows the player to be hidden             |
| /pinfo   | toolkit.pinfo  | Show info about player (even their client) |
| /rp      | toolkit.rp     | Set player's resource pack                 |
| /lunar   | toolkit.lunar  | Lunar tools                                |
| /toolkit | null           | Show info about plugin                     |

## Lunar

- Waypoints
- Disabled mods
- Automatically kick non-lunar players

## Trolls

- Sneak
- Sleep (it must be night, and you must stand on the bed)
- Close
- Glow
- PickUp
- Freeze
- FakeOp
- Flip
- Thor
- FakeDemo

## Dynmap (1.19.3)
- Lunar waypoints
- Vanish

## Events

- Join message
- Leave message

## Spawn

- Teleport to spawn on join
- Teleport to spawn on death
- Teleport to spawn using /spawn

## Client detection

- /pinfo to show players client
- ------------------------------
- Forge
- Fabric
- LiteLoader
- WorldDownloader
- Rift
- LunarClient
- FeatherClient

## Config

```yml
spawn:
  world: world
  join: true
  death: true
join:
  alert: true
  message: '&8[&cSERVER&8]&6 %player%&7 joined!'
leave:
  alert: true
  message: '&8[&cSERVER&8]&6 %player%&7 disconnected!'
#custom chat colors
color:
  bracket: '&8'
  name: '&c'
  text: '&7'
  highlight: '&6'
lunar:
  #kick non lunar players
  kick: false
  kick_message: '&8[&cSERVER&8]&7 This is lunar only server!'
  #lunar waypoints
  waypoints:
    - Spawn:
        color: '#ba1e0d'
        world: world
        x: 0
        y: 100
        z: 0
        
  #disabled lunar mods
  disabled_mods:
    - "freelook"
#block commands
commandblock:
  message: '&8[&cSERVER&8]&7 You''re not allowed to do this!'
  list:
    - pl
    - plugins
    - help
    - ver
    - version
    - about
    - '?'
    - bukkit


# ALL LUNAR MODS
# skyblockAddons - Skyblock Addons
# toggleSneak - Toggle Sneak/Sprint
# hypixel_mod - Hypixel Mods
# armorstatus - Armor Status
# keystrokes - Key Strokes
# coords - Coordinates
# crosshair - Crosshair
# potioneffects - Potion Effects
# directionhud - Direction HUD
# waypoints - Waypoints
# scoreboard - Scoreboard
# potion_counter - Potion Counter
# ping - Ping
# motionBlur - Motion Blur
# chat - Chat
# scrollable_tooltips - Scrollable Tooltips
# uhc_overlay - UHC Overlay
# particleMultiplierMod - Particle Multiplier
# cooldowns - Cooldowns
# worldedit_cui - WorldEdit CUI
# clock - Clock
# stopwatch - Stopwatch
# memory - Memory Usage
# combo - Combo Counter
# range - Reach Display
# time_changer - Time Changer
# serverAddressMod - Server Address
# saturation - Saturation
# itemPhysic - Item Physics
# itemTrackerChild - Item Tracker
# shinyPots - Shiny Pots
# block_outline - Block Outline
# screenshot - Screenshot Uploader
# fov_mod - FOV Mod
# textHotKey - Auto Text Hot Key
# netgraph - Net Graph
# mumble-link - Mumble Link
# bossbar - Boss Bar
# freelook - Freelook
```
