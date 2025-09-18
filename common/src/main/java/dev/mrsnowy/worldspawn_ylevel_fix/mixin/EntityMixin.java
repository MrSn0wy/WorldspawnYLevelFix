package dev.mrsnowy.worldspawn_ylevel_fix.mixin;

import dev.mrsnowy.worldspawn_ylevel_fix.Constants;
import dev.mrsnowy.worldspawn_ylevel_fix.WorldspawnYLevelFix;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    // Modify the adjustSpawnLocation() function on the entity to teleport them to the fixed worldspawn location.
    @Inject(method = "adjustSpawnLocation", at = @At("HEAD"), cancellable = true)
    private void adjustSpawnLocation(ServerLevel level, BlockPos pos, CallbackInfoReturnable<BlockPos> callback) {
        Entity entity = (Entity) (Object) this;

        AABB box = entity.getBoundingBox();
        Constants.LOGGER.info(String.valueOf(box));

        // Set box relative to the worldspawn
        {
            double diffX = box.maxX - box.minX;
            double diffY = box.maxY - box.minY;
            double diffZ = box.maxZ - box.minZ;

            Vec3 start = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            Vec3 end = start.add(diffX, diffY, diffZ);

            box = new AABB(start, end);
        }

        Constants.LOGGER.info(String.valueOf(box));
        Constants.LOGGER.info(String.valueOf(pos));

        boolean safe = level.noCollision(box);

        if (safe) {
            callback.setReturnValue(pos);
//            Constants.LOGGER.info("adjustSpawnLocation() on entity hijacked successfully!");
            // If it isn't safe, revert to default mojang behaviour
        } else {
            Constants.LOGGER.info("Worldspawn isn't safe for entity, reverting to standard behaviour.");
        }
    }
}
