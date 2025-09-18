package dev.mrsnowy.worldspawn_ylevel_fix.mixin;

import dev.mrsnowy.worldspawn_ylevel_fix.Constants;
import dev.mrsnowy.worldspawn_ylevel_fix.WorldspawnYLevelFix;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    // Make sure the yaw is correct.
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;moveTo(Lnet/minecraft/world/phys/Vec3;FF)V"))
    private void init(ServerPlayer player, Vec3 pos, float yRot, float xRot) {
//        Constants.LOGGER.info("silly");

        ServerLevel level = player.serverLevel();
        float yaw = level.getSharedSpawnAngle();

//        Constants.LOGGER.info("yaw: " + yaw);

        // We don't need to modify the pos here, since we inject adjustSpawnLocation().
        // adjustSpawnLocation() Gets called by the original self.moveTo arguments.
        player.moveTo(pos, yaw, xRot);
    }



    // Modify the adjustSpawnLocation() function on the player to teleport you to the fixed worldspawn location.
    @Inject(method = "adjustSpawnLocation", at = @At("HEAD"), cancellable = true)
    private void adjustSpawnLocation(ServerLevel level, BlockPos pos, CallbackInfoReturnable<BlockPos> callback) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        // Check if spawn radius is disabled, it doesn't work when spawn radius is enabled
        if (player.server.getSpawnRadius(level) == 0) {
            boolean safe = WorldspawnYLevelFix.isBlockPosSafe(pos, level);
//        Constants.LOGGER.info(String.valueOf(pos));
            if (safe) {
                callback.setReturnValue(pos);
//            Constants.LOGGER.info("adjustSpawnLocation() on player hijacked successfully!");
            } else {
                Constants.LOGGER.info("Worldspawn isn't safe for player, reverting to standard behaviour.");
            }
        } else {
            Constants.LOGGER.info("Gamerule spawnRadius is more than 0, if you want to use this mod then please set it to 0!");
        }


    }
}