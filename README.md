<p align="center">
    <img src="https://github.com/czQery/ToolKit/blob/master/banner.png?raw=true" alt="Logo">
</p>

[![Version](https://img.shields.io/badge/version-v4.4-informational.svg)](https://github.com/czQery/ToolKit/releases)
[![Releases](https://img.shields.io/badge/download-1.20.2-brightgreen.svg)](https://github.com/czQery/ToolKit/releases/latest/download/ToolKit-4.4.jar)

> Dependencies: [Apollo](https://github.com/LunarClient/Apollo), [Dynmap (optional)](https://github.com/webbukkit/dynmap)

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
| /msg     | toolkit.msg    | Send private message to player             |
| /toolkit | null           | Show info about plugin                     |

## Lunar

- Waypoints
- Disabled mods
- Automatically kick non-lunar players

## Trolls

- Sneak
- Sleep (it must be night, and you must stand on the bed)
- Close
- CloseSpam (anti-leave)
- Glow
- PickUp
- Freeze
- FakeOp
- Flip
- Thor
- FakeDemo

## Dynmap
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
- true = enabled, false = disabled
```yml
spawn:
  world: world
  join: true
  death: true
join:
  alert: true
  message: "&8[&cSERVER&8]&6 %player%&7 joined!"
leave:
  alert: true
  message: "&8[&cSERVER&8]&6 %player%&7 disconnected!"
#custom chat colors
color:
  bracket: "&8"
  name: "&c"
  text: "&7"
  highlight: "&6"
lunar:
  #kick non lunar players
  kick: false
  kick_message: "&8[&cSERVER&8]&7 This is lunar only server!"
  #lunar waypoints
  waypoints:
    - Spawn:
        color: "#ba1e0d"
        world: world
        x: 0
        y: 100
        z: 0
        
  #disabled lunar mods
  disabled_mods:
    - Replaymod
    #- OneSevenVisuals
    #- Fps
    #- Cps
    #- Sba
    #- ToggleSneak
    #- Zoom
    #- HypixelMod
    #- HypixelBedwars
    #- Quickplay
    #- Armorstatus
    #- Keystrokes
    #- Coordinates
    #- DayCounter
    #- Crosshair
    #- PotionEffects
    #- DirectionHud
    #- Titles
    #- Waypoints
    #- HitColor
    #- Scoreboard
    #- ItemCounter
    #- Ping
    #- MotionBlur
    #- PackOrganizer
    #- Chat
    #- Tab
    #- Nametag
    #- ShulkerPreview
    #- ScrollableTooltips
    #- UhcOverlay
    #- ParticleChanger
    #- NickHider
    #- Cooldowns
    #- WorldeditCui
    #- Clock
    #- Stopwatch
    #- Playtime
    #- Memory
    #- Combo
    #- ReachDisplay
    #- TimeChanger
    #- ServerAddress
    #- Saturation
    #- ColorSaturation
    #- ItemPhysics
    #- TntCountdown
    #- ItemTracker
    #- ShinyPots
    #- 3dSkins
    #- GlintColorizer
    #- Momentum
    #- BlockOutline
    #- Screenshot
    #- Fov
    #- Fog
    #- AutoTextHotkey
    #- MumbleLink
    #- 2dItems
    #- Bossbar
    #- Freelook
    #- PvpInfo
    #- Snaplook
    #- TeamView
    #- PackDisplay
    #- MenuBlur
    #- Minimap
    #- Hitbox
    #- Lighting
    #- WeatherChanger
    #- ChunkBorders
    #- SoundChanger
    #- Neu
    #- HurtCam
    #- DamageTint
msg:
  sender: "&8[&cMSG&8]&7 To&6 %receiver%&8 >&7 %msg%"
  receiver: "&8[&cMSG&8]&7 From&6 %sender%&8 >&7 %msg%"
#disable individual commands (to prevent collision with plugins with same commands)
commands:
  crash: true
  crawl: true
  skick: true
  sit: true
  troll: true
  pinfo: true
  rp: true
  lunar: true
  cmdblock: true
  vanish: true
  msg: true
  gmc: true
  gms: true
  gma: true
  gmsp: true
  spawn: true
  fly: true
  wc: true
  ic: true
#block commands
commandblock:
  message: "&8[&cSERVER&8]&7 You''re not allowed to do this!"
  list:
    - pl
    - plugins
    - help
    - ver
    - version
    - about
    - "?"
    - bukkit
```
