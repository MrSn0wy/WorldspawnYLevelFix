//package dev.mrsnowy.worldspawn_ylevel_fix.mixin;
//
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.Entity;
////import net.minecraft.world.level.portal.DimensionTransition;
//import net.minecraft.world.level.portal.DimensionTransition;
//import net.minecraft.world.phys.Vec3;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.ModifyArg;
//
//@Mixin(DimensionTransition.class)
//public class DimensionTransitionMixin {
//
//
//    @ModifyArg(
//            method = "missingRespawnBlock",
//            index = 3,
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/world/level/dimension/DimensionTransition;<init>(Lnet/minecraft/server/level/Serverlevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;FFZLnet/minecraft/world/level/portal/DimensionTransition$postDimensionTransition;)Lnet/minecraft/world/level/portal/DimensionTransition"
//            )
//    )
//    private float missingRespawnBlock(float requestedYaw, ServerLevel level, Vec3 vec3, Vec3 empty, float yRot, float xRot, boolean missingRespawnBlock, DimensionTransition.PostDimensionTransition postDimensionTransition) {
//        return level.getSharedSpawnAngle();
//    }
//}
