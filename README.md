# Worldspawn Y-Level Fix <img alt="Worldspawn Y-Level Fix Logo" src="https://raw.githubusercontent.com/MrSn0wy/WorldspawnYLevelFix/brr/common/src/main/resources/worldspawn_ylevel_fix.png" width="30"/>

A server-side mod that fixes players respawning at the top of the world instead of at the normal worldspawn.

Here is the [Changelog](https://github.com/MrSn0wy/WorldspawnYLevelFix/blob/brr/CHANGELOG.md)

## What does this do?
Minecraft for some reason ignores the Y level that you define when running `/setworldspawn`, instead it will respawn you at the highest block.

This makes an underground worldspawn or anything where a block is above you impossible.

This mod creates a mixin that modifies that stupid behaviour.
Now as long as there is enough room at the worldspawn, the player will just teleport there. If there isn't enough room, it will return to the normal behaviour.

I also made it work for Entities and when you teleport from things like the end portal, which should make the behavior fairly consistent.



## How to build
### Getting the correct environment
If you are on nixos you can simply go into the folder of where you cloned the repo, and run `nix develop .`. This will give you the environment I use (apart from the IDE) :3.

On any other linux distro, just install the jetbrains jdk, or try openjdk21.

On windows probably go to the openjdk website and install the 21 version? idk goodluck.

### Building
Then on linux just do `./gradlew build` and to make it in a single mod jar `./gradlew mergeJars`.

Or on windows, just do `.\gradlew.bat build` and `.\gradlew.bat mergeJars`.
Note that this isn't tested for windows, but I think that is how it works.

### Getting the jars
Then you can find your jars in `fabric/build/libs/` (for fabric), `neoforge/build/libs/` (for neoforge) or `merged/build/libs/` (if you made the merged jar file).

If you have any issues just make an issue or contact me on Discord `@mrsnowy_`
