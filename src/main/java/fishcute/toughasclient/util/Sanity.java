package fishcute.toughasclient.util;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.status_effect.ClientStatusEffects;
import fishcute.toughasclient.status_effect.Darkness;
import fishcute.toughasclient.status_effect.Insanity;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.LightType;

import java.util.List;

@Environment(EnvType.CLIENT)
public class Sanity {
    static int sanityTick = 0;
    public static void updateSanity() {
        if (sanityTick>0)
            sanityTick--;
        comfort();
        correct();
        update();
        if (DataManager.sanity<5)
            StatusEffectManager.addStatusEffect(new Insanity(Utils.secondsToTicks(26), 0));
    }
    static void correct() {
        if (DataManager.sanity>82)
            DataManager.sanity = 82;
        if (DataManager.sanity<0)
            DataManager.sanity = 0;
    }
    static void update() {
        if (sanityTick<=0) {
            sanityTick = modifySanity();
        }
        insanityEffects();
    }
    public static float sanityOverlayStatus() {
        int sanity = DataManager.sanity;
        float percent = 0;
        if (sanity<=32)
            percent = ((sanity / 32f) - 1);

        return percent;
    }
    public static int modifySanity() {
        boolean posTrend = true;
        int wait = 320;
        if (!day()&&isNewMoon()&&!Utils.underground()) {
            posTrend = false;
        }
        else if (!isNewMoon()&&!Utils.underground()) {
            DataManager.sanity++;
            return 100;
        }
        if (lightLevel() < 3&&Utils.underground()) {
            posTrend = false;
            wait -= 10;
        }
        if (coldOrHot()) {
            posTrend = false;
            wait -= 30;
        }

        if (e().getHealth()<10) {
            posTrend = false;
            wait -= 160;
        }
        if (lowStats()) {
            posTrend = false;
            wait -= 80;
        }

        if (localLightLevel()>=6) {
            posTrend = true;
            wait = 280;
        }
        if (comfort()) {
            posTrend = true;
            wait = 280;
        }

        if (posTrend)
            DataManager.sanity++;
        else
            DataManager.sanity--;
        return wait;
    }
    static boolean lowStats() {
        return (e().getHungerManager().getFoodLevel()<10||DataManager.thirst<22);
    }
    static boolean coldOrHot() {
        return (DataManager.temperature<22||DataManager.temperature>60);
    }
    static boolean comfort() {
        BlockPos loc = e().getBlockPos();
        double dist = 6;
        List<Entity> e = world().getOtherEntities(e(), new Box(loc.getX()-dist, loc.getY()-dist, loc.getZ()-dist, loc.getX()+dist, loc.getY()+dist, loc.getZ()+dist));

        boolean comfort = false;

        for (Entity ent : e) {
            if (e().canSee(ent)&&isComforting(ent.getType())) {
                comfort = true;
                if (DataManager.sanity<82)
                Utils.surroundParticle(ClientInit.COMFORT_PARTICLE, ent, 0.98, 0.5);
            }
        }

        return comfort;
    }
    static boolean isNewMoon() {
        return world().getMoonPhase()==4;
    }
    static boolean isComforting(EntityType e) {
        return (e.equals(EntityType.WOLF)||e.equals(EntityType.CAT)||e.equals(EntityType.FOX)||e.equals(EntityType.VILLAGER)||e.equals(EntityType.WANDERING_TRADER)||e.equals(EntityType.PLAYER)||e.equals(EntityType.IRON_GOLEM));
    }
    public static boolean shouldShow() {
        return (e().getY()<30||(lightLevel()<5&&Utils.underground())||DataManager.sanity<82||isNewMoon());
    }
    static void insanityEffects() {
        if (!StatusEffectManager.contains(ClientStatusEffects.DARKNESS)&&StatusEffectManager.contains(ClientStatusEffects.INSANITY)&&Math.random()>0.995)
            StatusEffectManager.addStatusEffect(new Darkness(Utils.secondsToTicks(26), 0));
    }
    static boolean day() {
        return world().getTimeOfDay()<=13000;
    }
    static int lightLevel() {
        return world().getLightLevel(e().getBlockPos());
    }
    static int localLightLevel() {
        return world().getLightLevel(LightType.BLOCK, e().getBlockPos());
    }
    static MinecraftClient client() {
        return MinecraftClient.getInstance();
    }
    static ClientPlayerEntity e() {
        return client().player;
    }
    static ClientWorld world() {
        return client().world;
    }
}
