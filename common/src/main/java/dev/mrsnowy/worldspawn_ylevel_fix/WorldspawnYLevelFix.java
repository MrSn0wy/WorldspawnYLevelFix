package dev.mrsnowy.worldspawn_ylevel_fix;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import java.util.Set;

public class WorldspawnYLevelFix {
	public static String MOD_LOADER;
    private static final Set<String> unsafeCollisionFreeBlocks = Set.of("block.minecraft.lava", "block.minecraft.flowing_lava", "block.minecraft.end_portal", "block.minecraft.end_gateway","block.minecraft.fire", "block.minecraft.soul_fire", "block.minecraft.powder_snow", "block.minecraft.nether_portal");

	public static void initializeMod() {
		Constants.LOGGER.info("Worldspawn Y-Level Fix (V{}) Loaded! Hello {}!", Constants.VERSION, MOD_LOADER);
	}

    // checks if a BlockPos is safe, used by the teleportSafetyChecker.
    public static boolean isBlockPosSafe(BlockPos bottomPlayer, ServerLevel world) {

        // get the block below the player
        BlockPos belowPlayer = new BlockPos(bottomPlayer.getX(), bottomPlayer.getY() -1, bottomPlayer.getZ()); // below the player
        String belowPlayerId = world.getBlockState(belowPlayer).getBlock().getDescriptionId(); // below the player

        // get the bottom of the player
        String BottomPlayerId = world.getBlockState(bottomPlayer).getBlock().getDescriptionId(); // bottom of player

        // get the top of the player
        BlockPos TopPlayer = new BlockPos(bottomPlayer.getX(), bottomPlayer.getY() + 1, bottomPlayer.getZ()); // top of player
        String TopPlayerId = world.getBlockState(TopPlayer).getBlock().getDescriptionId(); // top of player


        // check if the block position isn't safe
        if ((belowPlayerId.equals("block.minecraft.water") || !world.getBlockState(belowPlayer).getCollisionShape(world, belowPlayer).isEmpty()) // check if the player is going to fall on teleport
                && (world.getBlockState(bottomPlayer).getCollisionShape(world, bottomPlayer).isEmpty() && !unsafeCollisionFreeBlocks.contains(BottomPlayerId)) // check if it is a collision free block that isn't dangerous
                && (!unsafeCollisionFreeBlocks.contains(TopPlayerId))) // check if it is a dangerous collision free block, if it is solid then the player crawls
        {
            return true; // it's safe
        }
        return false; // it's not safe!
    }
}