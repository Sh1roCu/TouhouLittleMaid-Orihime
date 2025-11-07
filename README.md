## [TouhouLittleMaid](https://github.com/TartaricAcid/TouhouLittleMaid) unofficial Fabric port.
Available on [Modrinth](https://modrinth.com/mod/touhoulittlemaid-orihime) and [CurseForge](https://curseforge.com/minecraft/mc-mods/touhoulittlemaid-orihime).<br>
You can get the detail on TLM's [WIKI](http://page.cfpa.team/TouhouLittleMaid/).<br>

**Note:**
- **This mod requires [Forge Config API Port](https://modrinth.com/mod/forge-config-api-port).**
- **If you want to change some settings in-game, you should install [Cloth Config API](https://modrinth.com/mod/cloth-config).**
- **This mod is still experimental, perhaps there exist some bugs.**
- **If you want to install TACZ-Fabric-1.20.1, you must use [this fork](https://github.com/Sh1roCu/TACZ-Fabric/releases/tag/v1.0.2-hotfix4), or else maids won't send sound.**
- **Compatible with [TACZ-Refabricated](https://github.com/Sh1roCu/TACZ-Refabricated) since version1.20.1-0.1.7.1-(neo)forge1.3.8, and don't support old version of TACZ-Fabric.**

**<br>
If you want to extend this mod, you can add an entry point of type "little_maid_extension" in your fabric.mod.json:**

```
  "entrypoints": {
    "little_maid_extension": [
      "com.example.yourmod.YourMaid"
    ]
  },
```

**and implement** ```ILittleMaid```
