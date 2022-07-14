package fishcute.toughasclient.util;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.armor.ClientArmorRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;

@Environment(EnvType.CLIENT)
public class Stamina {
    static int staminaTick = 0;
    static int bowTick = 0;
    static int staminaRegenTick = 0;
    static int jumpWaitTick = 0;
    static int swingWaitTick = 0;
    static int timesPressed = 0;
    public static double destinedFOVValue = 1;
    public static void staminaDebuff() {
        int stamina = DataManager.stamina;
        if (stamina<=26) {
            e().setSprinting(false);
            Utils.surroundParticle(ParticleTypes.FALLING_WATER, e(), 0.93, 0.5);
        }
        if (stamina<=11) {
            client().player.input.sneaking = true;
        }
    }
    public static boolean jump() {
        return client().options.keyJump.wasPressed();
    }
    public static void updateAll() {
        if (jumpWaitTick>0)
            jumpWaitTick--;
        if (staminaRegenTick>0)
            staminaRegenTick--;
        if (staminaTick>0)
            staminaTick--;
        if (swingWaitTick>0)
            swingWaitTick--;
        if (bowTick>0)
            bowTick--;
    }
    static boolean swing() {
        return timesPressed == 1;
    }
    public static void setStaminaRegen(int defaultInt) {
        //Useless, but too lazy to revert
        staminaRegenTick = defaultInt;
    }
    public static void setStaminaRegenShiftAffected(int defaultInt) {
        if (client().options.keySneak.isPressed())
            staminaRegenTick = (defaultInt-(defaultInt/2));
        else staminaRegenTick = defaultInt;
    }
    public static void decreaseStamina(int defaultInt) {
        if (ClientUtils.client().player.hasStatusEffect(StatusEffects.STRENGTH))
            DataManager.stamina = DataManager.stamina - defaultInt/2;
        else
            DataManager.stamina = DataManager.stamina - defaultInt;
    }
    static void handleAttacks() {
        if (ClientUtils.client().options.keyAttack.isPressed())
            timesPressed++;
        else
            timesPressed=0;
    }
    public static void stamina() {
        handleAttacks();
        staminaDebuff();
        if (DataManager.stamina > 0) {
            if (e().isSprinting() && staminaTick <= 0 && !ClientUtils.client().player.hasStatusEffect(StatusEffects.SPEED)) {
                if (ClientArmorRegistry.wearing(ClientArmorRegistry.ArmorPiece.BOOTS, ClientArmorRegistry.getItem("Running Shoes")))
                    cStaminaTick(14);
                else
                    cStaminaTick(8);
                setStaminaRegen(20);
                DataManager.stamina--;
            }
            if ((jump() && jumpWaitTick<=0)&&!(e().isTouchingWater())) {
                decreaseStamina(8);
                jumpWaitTick = 20;
                setStaminaRegen(20);
            }
            if (client().interactionManager.isBreakingBlock() && staminaTick <= 0) {
                cStaminaTick(6);
                setStaminaRegen(20);
                DataManager.stamina--;
                swingWaitTick = 20;
            }
            else if (swing()&&swingWaitTick<=0) {
                decreaseStamina(6);
                swingWaitTick = 20;
                setStaminaRegen(20);
            }
            if (ClientUtils.client().options.keyUse.isPressed()&&(isUsingItem(Hand.MAIN_HAND)||isUsingItem(Hand.OFF_HAND))&&bowTick<=0) {
                cStaminaTick(10);
                setStaminaRegen(20);
                DataManager.stamina--;
                bowTick = 7;
            }
            if (ClientUtils.client().options.keyUse.isPressed()&&(isBlockingWithItem(Hand.MAIN_HAND)||isBlockingWithItem(Hand.OFF_HAND))&&bowTick<=0) {
                cStaminaTick(10);
                setStaminaRegen(20);
                DataManager.stamina--;
                bowTick = 20;
            }
        }
        if (staminaRegenTick<=0&&DataManager.stamina<82) {
                DataManager.stamina++;
        }
        staminaDrawback();
        updateAll();
    }
    static boolean isUsingItem(Hand i) {
        return action(i)!=null&&(action(i)==UseAction.BOW||action(i)==UseAction.CROSSBOW||action(i)==UseAction.SPEAR);
    }
    static boolean isBlockingWithItem(Hand i) {
        return action(i)!=null&&(action(i)==UseAction.BLOCK);
    }
    static UseAction action(Hand i) {
        return ClientUtils.client().player.getStackInHand(i).getUseAction();
    }
    public static void staminaDrawback() {
        int stamina = DataManager.stamina;
        if (staminaRegenTick<=0) {
            if (ClientUtils.client().player.hasStatusEffect(StatusEffects.STRENGTH))
                setStaminaRegen(2);
            else {
                setStaminaRegenShiftAffected(8);
                if (stamina <= 10) {
                    setStaminaRegenShiftAffected(60);
                } else if (stamina <= 25) {
                    setStaminaRegenShiftAffected(30);
                }
            }
        }
        updateFov();
    }
    static void updateFov() {
        int stamina = DataManager.stamina;
        if (stamina<=11)
            destinedFOVValue = 0.7;
        else if (stamina<=27)
            destinedFOVValue = 0.82;
        else
            destinedFOVValue = 1;
    }
    public static void cStaminaTick(int a) {
        if (staminaTick>0)
            staminaTick = staminaTick - a;
        else staminaTick = a;

        if (staminaTick<0)
            staminaTick = 0;
    }
    public static Entity e() {
        return client().player;
    }
    public static MinecraftClient client() {
        return MinecraftClient.getInstance();
    }
}
