package fishcute.toughasclient.status_message;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StatusMessageManager {
    public static void playStatusMessage(StatusMessage e, String id, int min, int max, int value) {
        if (value <= min && StatusMessage.statusMessageData.containsKey(id)&&!StatusMessage.statusMessageData.get(id)) {
            e.play();
            StatusMessage.dataSetValue(id, true);
        }
        else if (value>max)
            StatusMessage.dataSetValue(id, false);
    }
    public static void playStatusMessageIfLess(StatusMessage e, String id, int a, int value) {
        if (value <= a && StatusMessage.statusMessageData.containsKey(id)&&!StatusMessage.statusMessageData.get(id)) {
            e.play();
            StatusMessage.dataSetValue(id, true);
        }
        else if (value>a)
            StatusMessage.dataSetValue(id, false);
    }
    public static void playStatusMessageIfGreater(StatusMessage e, String id, int a, int value) {
        if (value >= a && StatusMessage.statusMessageData.containsKey(id)&&!StatusMessage.statusMessageData.get(id)) {
            e.play();
            StatusMessage.dataSetValue(id, true);
        }
        else if (value<a)
            StatusMessage.dataSetValue(id, false);
    }
    public static void sendMessages() {
        StatusMessageManager.playStatusMessage(new StatusMessage(Utils.translate("tough_as_client.message.tired"), 600, 200, 16734464),
                "tired", 10, 25, DataManager.stamina);
        StatusMessageManager.playStatusMessageIfLess(new StatusMessage(Utils.translate("tough_as_client.message.thirsty"), 800, 200, 41666),
                "thirst", 15, DataManager.thirst);
        StatusMessageManager.playStatusMessageIfLess(new StatusMessage(Utils.translate("tough_as_client.message.cold"), 600, 200, 9690357),
                "cold", 8, DataManager.temperature);
        StatusMessageManager.playStatusMessageIfGreater(new StatusMessage(Utils.translate("tough_as_client.message.hot"), 600, 200, 9043968),
                "hot", 74, DataManager.temperature);
    }
}
