package fishcute.toughasclient.util;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.block.CleanedWater;
import fishcute.toughasclient.status_effect.*;
import fishcute.toughasclient.status_message.StatusMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class Water {
    static int tick = 0;
    static int messageTick = 0;
    public static void updateWater() {
        if (!StatusEffectManager.contains(ClientStatusEffects.REFRESHED)) tick--;
        if (tick <= 0 && DataManager.thirst > 0) {
            DataManager.thirst--;
            tick = thirstTicks();
        }
        if (messageTick>0)
            messageTick--;
        if (DataManager.thirst<=15)
            StatusEffectManager.addStatusEffect(new Thirst(Utils.secondsToTicks(6), 0));
    }
    static int thirstTicks() {
        int i = 300 - (Temperature.overallTemperature()/5);
        if (StatusEffectManager.contains(ClientStatusEffects.HYPERNATREMIA))
            i= i-300;
        if (StatusEffectManager.contains(ClientStatusEffects.DYSENTERY))
            i= i-200;
        if (i<=20)
            i = 20;
        return i;
    }
    public static void manageEffects(WaterType type) {
        boolean harmful = ToughAsClientMod.CONFIG.dangerousWater;
        switch (type) {
            case OCEAN:
                if (harmful)
                    StatusEffectManager.addStatusEffect(new Hypernatremia(Utils.minutesToTicks(1), 0));
                break;
            case SWAMP:
                if (Math.random()>0.6&&harmful)
                    StatusEffectManager.addStatusEffect(new Dysentery(Utils.minutesToTicks(10), 0));
                break;
            case LAKE:
                if (Math.random()>0.9&&harmful)
                    StatusEffectManager.addStatusEffect(new Dysentery(Utils.minutesToTicks(10), 0));
                break;
            case NORMAL:
                if (Math.random()>0.995&&harmful)
                    StatusEffectManager.addStatusEffect(new Dysentery(Utils.minutesToTicks(10), 0));
                break;
            case SANITARY:
                StatusEffectManager.addStatusEffect(new Refreshed(Utils.secondsToTicks(46), 0));
                break;
        }
    }
    public static void drink(double x, double y, double z) {
        if (DataManager.thirst < 82) {
            WaterType w = waterType(new BlockPos(x, y, z));
            BlockPos b = new BlockPos(x, y, z);
            ClientUtils.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, b, SoundCategory.MASTER, 1, Utils.fromTo(1, 2));
            if (CleanedWater.isCleanedAtLoc(b.add(0, -1, 0))) {
                DataManager.thirst = DataManager.thirst + 30;
                ClientUtils.particleGroup(ParticleTypes.SPLASH, x, y, z, 0.6, 0.6, 0.6, 15);
                ClientUtils.particleGroup(ParticleTypes.SPIT, x, y, z, 0.4, 0.4, 0.4,  5);
                CleanedWater.getCleanedAtLoc(b.add(0, -1, 0)).drinkFrom();
                w = WaterType.SANITARY;
            }
            else {
                DataManager.thirst = DataManager.thirst + 8;
                ClientUtils.particleGroup(ParticleTypes.SPLASH, x, y, z, 0.6, 0.6, 0.6, 15);
                ClientUtils.particleGroup(ParticleTypes.SPIT, x, y, z, 0.4, 0.4, 0.4, 5);
            }
            manageEffects(w);
        }
    }
    public static WaterType waterType(BlockPos b) {
        MinecraftClient client = ClientUtils.client();
        String biome = client.player.clientWorld.getBiome(b).getCategory().asString();
        if (biome.contains("ocean")||biome.contains("beach"))
            return WaterType.OCEAN;
        else if (biome.contains("swamp"))
            return WaterType.SWAMP;
        else if (biome.contains("river")||biome.contains("lake")||biome.contains("jungle"))
            return WaterType.LAKE;
        return WaterType.NORMAL;
    }
    public static void sendThirstMessage() {
        if (messageTick<=0) {
            new StatusMessage("Your mouth feels too dry to eat this", 100, 100, 16577539).play();
            messageTick = 100;
        }
    }
    public enum WaterType {
        OCEAN,
        SWAMP,
        LAKE,
        NORMAL,
        NOT_FOUND,
        SANITARY
    }
}
