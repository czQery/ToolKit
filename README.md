<div align="center">
    <img src="https://github.com/czQery/ToolKit/blob/master/banner.png?raw=true" alt="Logo">
</div>

[![Version](https://img.shields.io/badge/version-v4.7-informational.svg)](https://github.com/czQery/ToolKit/releases)
[![Releases](https://img.shields.io/badge/download-1.21.11-brightgreen.svg)](https://github.com/czQery/ToolKit/releases/latest/download/ToolKit-4.7.jar)

>
Optional dependencies: [Apollo](https://github.com/LunarClient/Apollo), [Pl3xMap](https://github.com/webbukkit/dynmap)

ToolKit is a lightweight plugin for Spigot and Paper servers that provides a range of useful tools for server
administrators.<br>
Suitable for all servers that require basic player management tools.

## Key Features:

> *`.bypass` permission suffix is for restricting anyone to use that command on you.<br>
`.other` permission suffix is for option to apply the command to other players.*

- **/crash \<player>**: Force-quits a selected player's Minecraft client
    - permission: `toolkit.crash`
        - bypass: `toolkit.crash.bypass`
- **/skick \<player>**: Disconnects the player from the game with a very long message, making it impossible to click on
  the button to return to the main menu
    - permission: `toolkit.skick`
        - bypass: `toolkit.skick.bypass`

---

- **/crawl \<player>**: Forces a selected player to crawl on ground
    - permission: `toolkit.crawl`
        - other: `toolkit.crawl.other`
- **/sit \<player>**: Forces a selected player to sit on ground or any other block
    - permission: `toolkit.sit`
        - other: `toolkit.sit.other`

---

- **/waypoint \<add/remove/list>**: Command for creating waypoints compatible with Lunar & Pl3xMap
  checkpoints (width dynmap support) or banning certain mods
    - permission: `toolkit.waypoint`

---

- **/vanish \<player/list>**: Makes a player invisible to other players
    - permission: `toolkit.vanish`
- **/pinfo \<player>**: Shows the player's IP address and the client they are connected with
    - permission: `toolkit.pinfo`
    - clients:
        - Forge
        - Fabric
        - LiteLoader
        - WorldDownloader
        - Rift
        - LunarClient
        - FeatherClient
- **/cmdblock**: Allows to block specified server commands
    - permission: `toolkit.cmdblock`
        - bypass: `toolkit.cmdblock.bypass`
- **/rp \<player> \<url>**: Forcefully orders the player to download the given resource pack
    - permission: `toolkit.rp`
- **/msg \<player> \<message>**: Sends a private message to the given player.
    - permission: `toolkit.msg`

---

- **/troll \<player> \<troll>**: Brings a touch of mischievous fun
    - permission: `toolkit.troll`
        - bypass: `toolkit.troll.bypass`
    - trolls:
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

## Aliases:

> *All Aliases have permission in this format `toolkit.<command>` with option to add `toolkit.<command>.other` suffix*

- **/gmc**: Switch player's gamemode to CREATIVE
- **/gms**: Switch player's gamemode to SURVIVAL
- **/gma**: Switch player's gamemode to ADVENTURE
- **/gmsp**: Switch player's gamemode to SPECTATOR
- **/spawn**: Teleports player to spawn
- **/fly**: Allows the player to fly
- **/wc**: Clear weather
- **/ic**: Clear inventory

## Additional Features:

- **Custom join message**
- **Custom leave message**

---

- **Teleport to spawn on join**
- **Teleport to spawn on death**
- **Teleport to spawn using /spawn**
- **Pl3xMap integration to hide players in vanish and show Lunar waypoints**
- **Block all players without Lunar client**

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
  wp: true
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
