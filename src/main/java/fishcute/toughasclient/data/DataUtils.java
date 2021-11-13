package fishcute.toughasclient.data;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.status_effect.*;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class DataUtils {
    public static String toString(IClientStatusEffect i) {
        return (i.getClass().getName() + "#" + i.getTicks() + "#" + i.getAmplifier());
    }
    public static IClientStatusEffect fromString(String i) {
        try {
            String[] a = i.split("#");
            String name = a[0];
            int ticks = Integer.parseInt(a[1]);
            int amplifier = Integer.parseInt(a[2]);
            return type(name, ticks, amplifier);
        }
        catch (Exception ignored) {}
        return null;
    }
    public static ArrayList<String> toStringArrayList(ArrayList<IClientStatusEffect> e) {
        ArrayList<String> result = new ArrayList<>();
        if (e.size()>0)
            for (IClientStatusEffect a : e)
                result.add(toString(a));
            if (!result.contains(null))
            return result;
            else {
                SaveInstance.sendErrorStatusToast("Could not save data", "Something went wrong");
                return new ArrayList<>();
            }
    }
    public static ArrayList<IClientStatusEffect> fromStringArrayList(ArrayList<String> e) {
        ArrayList<IClientStatusEffect> result = new ArrayList<>();
        if (e.size()>0)
            for (String a : e)
                result.add(fromString(a));
            if (!result.contains(null))
        return result;
            else {
                SaveInstance.sendErrorStatusToast("Using default stats", "Could not load data");
                SaveInstance.resetCurrentStats();
                return new ArrayList<>();
            }
    }
    public static IClientStatusEffect type(String i, int ticks, int amplifier) {
        try {
            return (IClientStatusEffect) Class.forName(i).getConstructor(new Class[]{int.class, int.class}).newInstance(ticks, amplifier);
        }
        catch (Exception ignored) {

        }
        return null;
    }
    public static void sendAlert(StatusToastType i) {
        switch (i) {
            case FAILURE_LOAD_DATA:
                SaveInstance.sendErrorStatusToast(Utils.translate("tough_as_client.status.failure_load_2"), Utils.translate("tough_as_client.status.failure_load_1"));
                ToughAsClientMod.log("Something went wrong while loading data!", Level.FATAL);
                break;
            case FAILURE_SAVE_DATA:
                SaveInstance.sendErrorStatusToast(Utils.translate("tough_as_client.status.failure_save_2"), Utils.translate("tough_as_client.status.failure_save_1"));
                ToughAsClientMod.log("Something went wrong while saving data!", Level.FATAL);
                break;
        }
    }
    public static void sendStatus(StatusToastType i, String side) {
        if (side.equals("world"))
            side = Utils.translate("tough_as_client.status.world");
        else if (side.equals("server"))
            side = Utils.translate("tough_as_client.status.server");
        switch (i) {
            case SAVE_DATA:
                ClientUtils.client().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, Text.of(Utils.translate("tough_as_client.status.save_1")),
                Text.of(Utils.translateReplace("tough_as_client.status.save_2", "%s", side))));
                break;
            case LOAD_DATA:
                ClientUtils.client().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, Text.of(Utils.translate("tough_as_client.status.load_1")),
                Text.of(Utils.translateReplace("tough_as_client.status.load_2", "%s", side))));
                break;
        }
    }
    public enum StatusToastType {
        LOAD_DATA,
        SAVE_DATA,
        FAILURE_LOAD_DATA,
        FAILURE_SAVE_DATA
    }
}
