package fishcute.toughasclient.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class FOVChange {
    float change;
    String identifier;

    public String getIdentifier() {
        return this.identifier;
    }
    public float getChange() {
        return this.change;
    }

    public static ArrayList<FOVChange> list = new ArrayList<>();
    public FOVChange(float f, String id) {
        this.identifier = id;
        this.change = f;
    }
    public static void addChange(FOVChange f) {
        if (containsChange(f.getIdentifier()))
            removeChange(f.getIdentifier());
        list.add(f);
    }
    public static boolean containsChange(String id) {
            for (FOVChange f : list) {
                if (f.getIdentifier().equals(id)) {
                    return true;
                }
            }
            return false;
    }
    public static void removeChange(String id) {
        list.removeIf(b -> b.getIdentifier().equals(id));
    }
    public static float overallChange() {
        float add = 0;
        for (FOVChange f : list)
            add += f.getChange();
        return add;
    }
}
