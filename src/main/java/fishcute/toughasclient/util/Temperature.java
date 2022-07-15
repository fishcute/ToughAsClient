package fishcute.toughasclient.util;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.armor.ClientArmorRegistry;
import fishcute.toughasclient.armor.CoolingArmor;
import fishcute.toughasclient.armor.SnowArmor;
import fishcute.toughasclient.items.*;
import fishcute.toughasclient.status_effect.ClientStatusEffects;
import fishcute.toughasclient.status_effect.Hyperthermia;
import fishcute.toughasclient.status_effect.Hypothermia;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.LightType;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class Temperature {
    static int temperatureTick = 0;
    static int immediateTemperatureTick = 0;
    static int temperature = 0;
    static int tick = 0;

    static double heatSourceDistance = 0;

    static int checkTick = 0;
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

        updateHeatSourceCheck();

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

    static void updateHeatSourceCheck() {
        //Performance™
        if (checkTick < 10)
            checkTick++;
        else {
            checkTick = 0;
            heatSourceDistance = distanceToNearbyHeatSource();
        }
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
        else if (temp>74 && !ClientUtils.client().player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE))
            StatusEffectManager.addStatusEffect(new Hyperthermia(Utils.minutesToTicks(3), 0));
        //else if (status>0)
            //heat
    }
    static int overallTemperature() {
        //ToughAsClientMod.sendAlert(daylightWarmth() + " < the thing");
        if (ClientItemRegistry.isHoldingItem(new CursedLantern()))
            return 42;
        int res = 0;

        //TODO: have fun figuring this out

        //Daylight warmth is out of 10
        int warmth = (int) (daylightWarmth() + (25 * humidity()));
        //TODO: FIx this

        warmth -= (8 - daylightWarmth()) * (1 - humidity());

        //(8 - 0) * 0.6

        //ToughAsClientMod.sendAlert(humidity() + "");
        //TODO: Remove this ^

        if (warmth > 10)
            warmth = 10;
        if (warmth < -4)
            warmth = -4;

        res += (int) ((biomeTemperature() + 0.1) * (20 + (warmth * 4)));

        //Too lazy to figure out why the thing above isn't working very well.
        //Hopefully this thing below should solve that problem.

        if (res<0)
            res = 0;
        if (res>82)
            res = 82;

        if (heatSourceDistance != -1)
            res += (9 - heatSourceDistance) * 6;
        /*
        If things go wrong, make daylightWarmth() a boolean that checks if it is day
        if (daylightWarmth())
            res = (int) (lightLevel()*2);
        if (daylightWarmth())
            res += (int) (biomeTemperature()*25);
        else
            res += (int) (biomeTemperature()*15);*/
        res += armorWarmth()*10;
        res -= armorCool();
        res -= itemCool();
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
        int snowArmor = ClientArmorRegistry.amountWearing(new SnowArmor());
        if (snowArmor>0) return snowArmor;
        else return (warmth/2);
        //return (warmth/2) + CustomArmorRegistry.amountWearing(new SnowArmor());
    }

    static int itemCool() {
        if (ClientItemRegistry.isHoldingItem(new IcePack()))
            return 4;
        else if (ClientItemRegistry.isHoldingItem(new PackedIcePack()))
            return 6;
        else if (ClientItemRegistry.isHoldingItem(new BlueIcePack()))
            return 8;
        else {
            Item i = ClientUtils.client().player.getMainHandStack().getItem();
            if (i.equals(Items.SNOW) || i.equals(Items.SNOW_BLOCK) || i.equals(Items.SNOWBALL)
                    || i.equals(Items.ICE) || i.equals(Items.PACKED_ICE) || i.equals(Items.BLUE_ICE))
                return 4;
        }
        return 0;
    }

    static int armorCool() {
        int coolArmor = ClientArmorRegistry.amountWearing(new CoolingArmor());
        if (ClientItemRegistry.doesPlayerInventoryContain(new BlueIcePack()))
            return coolArmor * 10;
        else if (ClientItemRegistry.doesPlayerInventoryContain(new PackedIcePack()))
            return coolArmor * 6;
        if (ClientItemRegistry.doesPlayerInventoryContain(new IcePack()))
            return coolArmor * 2;
        return 0;
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
    static int daylightWarmth() {
        if (ClientUtils.world().getDimension().hasFixedTime())
            return 10;
        int a = ClientUtils.getTimeProgress();
        if (!ClientUtils.progressingIntoDay()) {
            a = 12000 - a;
            if (a <= 3000) {
                return a / 300;
            }
            if (a >= 9000)
                return (a - 9000) / 300;
        }
        if (ClientUtils.getTime() <= 12000)
            return 10;
        return 0;
    }
    static int localLightLevel() {
        return world().getLightLevel(LightType.BLOCK, e().getBlockPos());
    }
    static float biomeTemperature() {
        return world().getBiome(e().getBlockPos()).getTemperature() > 0.3 ? world().getBiome(e().getBlockPos()).getTemperature() : (float) 0.3;
    }

    static double distanceToNearbyHeatSource() {
        return ClientUtils.getDistanceToBlockNearby(new ArrayList<>(Arrays.asList(
                Blocks.CAMPFIRE,
                Blocks.SOUL_CAMPFIRE,
                Blocks.LAVA,
                Blocks.FIRE,
                Blocks.SOUL_FIRE,
                Blocks.MAGMA_BLOCK
        )), Utils.toBlockPos(ClientUtils.loc()), 5, 3);
    }

    static float humidity() {
        return world().getBiome(e().getBlockPos()).getDownfall();
    }
    /*
    static float desertCool() {
        world().getBiome(e().getBlockPos()).hasHighHumidity();
    }*/
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
