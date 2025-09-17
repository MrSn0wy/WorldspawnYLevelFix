package dev.mrsnowy.worldspawn_ylevel_fix.mixin;

import com.jcraft.jorbis.Block;
import dev.mrsnowy.worldspawn_ylevel_fix.Constants;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(PlayerRespawnLogic.class)
public class PlayerRespawnLogicMixin {

    private static final Set<String> unsafeCollisionFreeBlocks = Set.of("block.minecraft.lava", "block.minecraft.flowing_lava", "block.minecraft.end_portal", "block.minecraft.end_gateway","block.minecraft.fire", "block.minecraft.soul_fire", "block.minecraft.powder_snow", "block.minecraft.nether_portal");


    @Inject(method = "getOverworldRespawnPos", at = @At("HEAD"), cancellable = true)
    private static void getOverworldRespawnPos(ServerLevel level, int x, int z, CallbackInfoReturnable<BlockPos> callback) {

        BlockPos respawnPos = level.getSharedSpawnPos();

        if (isBlockPosSafe(respawnPos, level)) {
//            Constants.LOGGER.info("{} was safe! || {} {}", respawnPos, x, z);
            callback.setReturnValue(respawnPos);
        }
//        else {
//            Constants.LOGGER.info("{} wasn't safe! || {} {}", respawnPos, x, z);
//        }
    }

    // checks if a BlockPos is safe, used by the teleportSafetyChecker.
    private static boolean isBlockPosSafe(BlockPos bottomPlayer, ServerLevel world) {

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