package fishcute.toughasclient.util;

import fishcute.toughasclient.Registry;
import fishcute.toughasclient.status_effect.ClientStatusEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Language;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.RaycastContext;

import java.util.concurrent.ThreadLocalRandom;

@Environment(EnvType.CLIENT)
public class Utils {
    public static void surroundParticle(DefaultParticleType a, Entity e, double frequency, double range) {
        if (Math.random()>frequency)
            ClientUtils.particleGroup(a, e.getX(), e.getY() + (e.getStandingEyeHeight()/2), e.getZ(), range, e.getStandingEyeHeight()/2, range, 1);
    }
    public static boolean underground() {
        return world().getLightLevel(LightType.SKY, e().getBlockPos())<1;
    }
    static ClientPlayerEntity e() {
        return client().player;
    }
    static ClientWorld world() {
        return client().world;
    }
    static MinecraftClient client() {
        return MinecraftClient.getInstance();
    }
    public static int minutesToTicks(double minutes) {
        return (int) (minutes * 4800);
    }
    public static int secondsToTicks(double seconds) {
        return (int) (seconds * 80);
    }
    public static String name() {
        return "Tough as Client";
    }
    public static BlockPos toBlockPos(Vec3d d) {
        return new BlockPos(d.getX(), d.getY(), d.getZ());
    }
    public static int distance(int i, int j) {
        return (int) Math.sqrt(i*i+j*j);
    }
    public static float fromTo(float i, float j) {
        return (float) ThreadLocalRandom.current().nextDouble(i, j);
    }
    public static Vec3d posInFront(double i) {
        ClientPlayerEntity e = ClientUtils.client().player;
        VectorDirection vecDir = new VectorDirection(e.pitch, e.yaw).mult(i);
        return vecDir.getVec3d().add(e.getPos().getX(), e.getPos().getY()+e.getEyeHeight(e.getPose()), e.getPos().getZ());
    }
    public static String translate(String key) {
        return Language.getInstance().get(key);
    }
    public static String translateReplace(String key, String from, String to) {
        return Language.getInstance().get(key).replaceAll(from, to);
    }
    public static void reloadResources() {
        Registry.reloadBaseItems();
        ClientStatusEffects.reloadEffects();
    }
    public static BlockState raycastFromPlayerGetBlock(boolean fluids) {
        ClientPlayerEntity e = ClientUtils.client().player;
        double playerReach = ClientUtils.client().interactionManager.getReachDistance();
        float tickDelta = ClientUtils.client().getTickDelta();
        Vec3d eyePosition = e.getCameraPosVec(tickDelta);
        Vec3d lookVector = e.getRotationVec(tickDelta);
        Vec3d traceEnd = eyePosition.add(lookVector.x * playerReach, lookVector.y * playerReach, lookVector.z * playerReach);

        RaycastContext.FluidHandling fluidHandling;
        if (fluids)
            fluidHandling = RaycastContext.FluidHandling.ANY;
        else
            fluidHandling = RaycastContext.FluidHandling.NONE;

        RaycastContext context = new RaycastContext(eyePosition, traceEnd, RaycastContext.ShapeType.OUTLINE, fluidHandling, e);

        return e.getEntityWorld().getBlockState(e.getEntityWorld().raycast(context).getBlockPos());
    }
}
