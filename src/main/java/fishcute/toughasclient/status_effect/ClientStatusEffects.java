package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientStatusEffects {
    public static String DARKNESS = Utils.translate("tough_as_client.effect.darkness");
    public static String DRENCHED = Utils.translate("tough_as_client.effect.drenched");
    public static String DYSENTERY = Utils.translate("tough_as_client.effect.dysentery");
    public static String HYPERNATREMIA = Utils.translate("tough_as_client.effect.hypernatremia");
    public static String HYPERTHERMIA = Utils.translate("tough_as_client.effect.hyperthermia");
    public static String HYPOTHERMIA = Utils.translate("tough_as_client.effect.hypothermia");
    public static String INSANITY = Utils.translate("tough_as_client.effect.insanity");
    public static String REFRESHED = Utils.translate("tough_as_client.effect.refreshed");
    public static String THIRST = Utils.translate("tough_as_client.effect.thirst");
    public static void reloadEffects() {
        ClientStatusEffects.DARKNESS = Utils.translate("tough_as_client.effect.darkness");
        ClientStatusEffects.DRENCHED = Utils.translate("tough_as_client.effect.drenched");
        ClientStatusEffects.DYSENTERY = Utils.translate("tough_as_client.effect.dysentery");
        ClientStatusEffects.HYPERNATREMIA = Utils.translate("tough_as_client.effect.hypernatremia");
        ClientStatusEffects.HYPERTHERMIA = Utils.translate("tough_as_client.effect.hyperthermia");
        ClientStatusEffects.HYPOTHERMIA = Utils.translate("tough_as_client.effect.hypothermia");
        ClientStatusEffects.INSANITY = Utils.translate("tough_as_client.effect.insanity");
        ClientStatusEffects.REFRESHED = Utils.translate("tough_as_client.effect.refreshed");
        ClientStatusEffects.THIRST = Utils.translate("tough_as_client.effect.thirst");
    }
}
