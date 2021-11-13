package fishcute.toughasclient.data;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.util.ClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class SaveEvents {
    public static boolean prevJoin = false;
    public static boolean join = false;
    public static boolean isServer = false;
    static ClientWorld world;
    public static String serverName = "null";
    static ClientPlayerEntity e;
    public static String worldName = "null";
    public static void onJoin() {
        isServer = isServer();
        e = ClientUtils.client().player;
        if (!isServer) {
            world = ClientUtils.world();
            if (SaveInstance.doesPathExist(false, worldName))
                SaveInstance.loadData(worldName, false);
            else
                sendFirstTimeMessage();
        }
        else {
            if (SaveInstance.doesPathExist(true, serverName))
                SaveInstance.loadData(serverName, true);
            else
                sendFirstTimeMessage();
        }
    }
    public static void onLeave() {
        if (isServer)
            SaveInstance.saveData(serverName, true);
        else
            SaveInstance.saveData(worldName, false);
        SaveInstance.resetCurrentStats();
    }
    static void sendFirstTimeMessage() {
        ClientUtils.client().player.sendMessage(Text.Serializer.fromJson("[\"\",{\"text\":\"" + ToughAsClientMod.getAlertPrefix() + " Welcome to Tough as Client! Refer to the \",\"color\":\"yellow\"}, {\"text\":\"wiki\",\"underlined\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://github.com/fishcute/ToughAsClient/wiki\"}},{\"text\":\" for help.\",\"color\":\"yellow\"}]"), false);
    }
    static boolean isServer() {
        return !ClientUtils.client().isInSingleplayer();
    }
    public static void tick() {

        join = ClientUtils.client().world!=null;
        if (prevJoin!=join) {
            prevJoin = join;
            if (join)
                onJoin();
            else
                onLeave();
        }
    }
}
