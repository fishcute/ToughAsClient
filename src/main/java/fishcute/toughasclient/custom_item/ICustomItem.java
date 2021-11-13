package fishcute.toughasclient.custom_item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public abstract class ICustomItem {
    public abstract String identifier();
    public abstract String itemType();
    public abstract HashMap<String, Integer> overrides();
    public abstract ArrayList<String> lore();
    public abstract void tick();
    public int currentOverride = -1;
    public int modelData() {
        if (this.currentOverride==-1)
            return this.overrides().values().iterator().next();
        else return this.currentOverride;
    }
    public void setOverride(String i) {
        this.currentOverride = overrides().get(i);
    }
}