package fishcute.toughasclient.util;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.custom_armor.CustomArmorRegistry;
import fishcute.toughasclient.custom_armor.SnowArmor;
import fishcute.toughasclient.status_effect.ClientStatusEffects;
import fishcute.toughasclient.status_effect.Hyperthermia;
import fishcute.toughasclient.status_effect.Hypothermia;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.world.LightType;

@Environment(EnvType.CLIENT)
public class Temperature {
    static int temperatureTick = 0;
    static int immediateTemperatureTick = 0;
    static int temperature = 0;
    static int tick = 0;
    public static void updateTemperature() {
        tick++;
        effects();
        if (temperatureTick>0)
            temperatureTick--;
        update();
        temperature = overallTemperature();

        if (e().isInLava())
            DataManager.temperature++;

        updateImmediateTemperature();

        PlayerParticleManager.tickBreath();
    }
    static void updateImmediateTemperature() {
        //TODO: Show immediate temperature
        if (immediateTemperatureTick>0)
            immediateTemperatureTick--;
        updateImmediate();
        if (e().isInLava())
            DataManager.immediateTemperature++;
    }
    static void update() {
        if (temperatureTick<=0) {
            temperatureTick = (int) (100 + Math.round((Utils.distance(DataManager.temperature, temperature))/1.2));
            int ptemp = DataManager.temperature;
            if (ptemp>temperature)
                DataManager.temperature--;
            else if (ptemp<temperature)
                DataManager.temperature++;
        }
    }
    static void updateImmediate() {
        //TODO: Make sure this works
        if (immediateTemperatureTick<=0) {
            int temp = overallTemperature();
            immediateTemperatureTick = 5;
            int ptemp = DataManager.immediateTemperature;
            if (ptemp>temp)
                DataManager.immediateTemperature--;
            else if (ptemp<temp)
                DataManager.immediateTemperature++;
        }
    }
    static void effects() {
        int temp = DataManager.temperature;
        if (temp<8)
            StatusEffectManager.addStatusEffect(new Hypothermia(Utils.minutesToTicks(3), 0));
        else if (temp>74)
            StatusEffectManager.addStatusEffect(new Hyperthermia(Utils.minutesToTicks(3), 0));
        //else if (status>0)
            //heat
    }
    static int overallTemperature() {
        int res = 0;
        if (daylightWarmth())
            res = (int) (lightLevel()*1.5);
        if (daylightWarmth())
            res += (int) (biomeTemperature()*20);
        else
            res += (int) (biomeTemperature()*30);
        res = res + armorWarmth()*10;
        res += localLightLevel();

        res = (int) (res/1.2);

        if (DataManager.stamina<=25)
            res += 5;
        if (DataManager.stamina<=10)
            res += 10;

        if (StatusEffectManager.contains(ClientStatusEffects.DRENCHED))
            res -= (10+(biomeTemperature()/6));
        if (StatusEffectManager.contains(ClientStatusEffects.HYPOTHERMIA))
            res -= 8;

        res-=environmentalCooling();

        if (res<0)
            res = 0;
        if (res>82)
            res = 82;

        return res;
    }
    static int armorWarmth() {
        int warmth = 0;
        for (ItemStack e : e().getArmorItems()) {
            if (e.getItem().getTranslationKey().contains("leather"))
                warmth++;
        }
        int snowArmor = CustomArmorRegistry.amountWearing(new SnowArmor());
        if (snowArmor>0) return snowArmor;
        else return (warmth/2);
        //return (warmth/2) + CustomArmorRegistry.amountWearing(new SnowArmor());
    }
    static boolean inCoolingArea() {
        String a = world().getBlockState(e().getBlockPos()).getBlock().getTranslationKey();
        if (a.contains("air")) {
            a = world().getBlockState(e().getBlockPos().add(0, -1, 0)).getBlock().getTranslationKey();
        }
        return a.contains("snow") || a.contains("ice");
    }
    static int environmentalCooling() {
        int res = 0;
        if (e().isTouchingWaterOrRain()&&!e().isTouchingWater())
            res++;
        if (e().isTouchingWaterOrRain())
            res += 2;
        if (inCoolingArea())
            res += 2;
        return res;
    }
    static boolean daylightWarmth() {
        return world().getTimeOfDay()<13000;
    }
    static int lightLevel() {
        return world().getLightLevel(e().getBlockPos());
    }
    static int localLightLevel() {
        return world().getLightLevel(LightType.BLOCK, e().getBlockPos());
    }
    static float biomeTemperature() {
        return world().getBiome(e().getBlockPos()).getTemperature();
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
