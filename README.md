<p align="center">
    <img src="https://github.com/czQery/ToolKit/blob/master/banner.png?raw=true">
</p>

[![Version](https://img.shields.io/badge/version-v2.6-informational.svg)](https://github.com/czQery/ToolKit/releases)
[![Releases](https://img.shields.io/badge/download-1.17-brightgreen.svg)](https://github.com/czQery/ToolKit/releases/latest/download/ToolKit-2.6.jar)

## Commands

| Command           | Permission            | Protection                | Description                                   |
| ----------------- | --------------------- | ------------------------- | --------------------------------------------- |
| /crasher          | toolkit.crasher       | toolkit.crasher.bypass    | Lag/Crash players game                        |
| /crawl            | toolkit.crawl         | null                      | Allows the player to crawl                    |
| /sit              | toolkit.sit           | null                      | Allows the player to sit down                 |
| /skick            | toolkit.skick         | toolkit.skick.bypass      | Badlion users cannot exit kick screen         |
| /troll            | toolkit.troll         | toolkit.troll.bypass      | Just troll command :)                         |
| /toolkit          | null                  | null                      | Show info about plugin                        |
| /pinfo            | toolkit.pinfo         | null                      | Show info about player (even their client)    |
| /rp               | toolkit.rp            | null                      | Set player's resource pack                    |
| /gmc              | toolkit.gmc           | null                      | Switch player's gamemode to CREATIVE          |
| /gms              | toolkit.gms           | null                      | Switch player's gamemode to SURVIVAL          |
| /gma              | toolkit.gma           | null                      | Switch player's gamemode to ADVENTURE         |
| /gmsp             | toolkit.gmsp          | null                      | Switch player's gamemode to SPECTATOR         |

## Trolls

- Sneak
- Sleep
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
```
