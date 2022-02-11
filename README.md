<p align="center">
    <img src="https://github.com/czQery/ToolKit/blob/master/banner.png?raw=true">
</p>

[![Version](https://img.shields.io/badge/version-v3.0-informational.svg)](https://github.com/czQery/ToolKit/releases)
[![Releases](https://img.shields.io/badge/download-1.18-brightgreen.svg)](https://github.com/czQery/ToolKit/releases/latest/download/ToolKit-2.8.jar)

## Commands

| Command           | Permission                  | Protection                  | Description                                   |
| ----------------- | --------------------------- | --------------------------- | --------------------------------------------- |
| /crash            | toolkit.crash               | toolkit.crash.bypass        | Lag/Crash players game                        |
| /crawl            | toolkit.crawl               | null                        | Allows the player to crawl                    |
| /sit              | toolkit.sit                 | null                        | Allows the player to sit down                 |
| /skick            | toolkit.skick               | toolkit.skick.bypass        | Badlion users cannot exit kick screen         |
| /troll            | toolkit.troll               | toolkit.troll.bypass        | Just troll command :)                         |
| /toolkit          | null                        | null                        | Show info about plugin                        |
| /pinfo            | toolkit.pinfo               | null                        | Show info about player (even their client)    |
| /rp               | toolkit.rp                  | null                        | Set player's resource pack                    |
| /gmc              | toolkit.gmc                 | null                        | Switch player's gamemode to CREATIVE          |
| /gms              | toolkit.gms                 | null                        | Switch player's gamemode to SURVIVAL          |
| /gma              | toolkit.gma                 | null                        | Switch player's gamemode to ADVENTURE         |
| /gmsp             | toolkit.gmsp                | null                        | Switch player's gamemode to SPECTATOR         |
| /lunar            | toolkit.lunar               | null                        | Lunar tools                                   |
| /cmdblock         | toolkit.toolkit.commandblock| toolkit.commandblock.bypass | Allows to block specific commands             |

## Lunar

- Waypoints
- Disabled mods
- Automatically kick non lunar players

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

## Events

- Join message
    - Teleports players to spawn
- Leave message

## Client detection

- Forge
- Fabric
- LiteLoader
- WorldDownloader
- Rift
- LunarClient

## Config

```yml
join:
  alert: true
  message: '&8[&cSERVER&8]&6 %player%&7 joined!'
  teleport: true
  world: world
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
        name: Spawn
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
