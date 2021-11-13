package fishcute.toughasclient.util;

import fishcute.toughasclient.data.SaveEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceReload;

@Environment(EnvType.CLIENT)
public class Tick {
    public static ResourceReload reload;
    public static void tick() {
        SaveEvents.tick();
        if (reload!=null)
            tickReload();
    }
    static void tickReload() {
        if (reload.isComplete()) {
            Utils.reloadResources();
            reload = null;
        }
    }
}
