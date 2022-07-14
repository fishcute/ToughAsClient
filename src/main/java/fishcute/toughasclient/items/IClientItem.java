package fishcute.toughasclient.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public abstract class IClientItem {
    public abstract String identifier();
    public abstract String itemType();
    public abstract HashMap<String, Integer> overrides();
    public abstract ArrayList<String> lore();
    public abstract void handTick();
    public abstract void tick();

    public void tickBefore() {
        //Stuff might go here in the future
        this.tick();
    }

    public abstract void init();
    public int currentOverride = -1;

    public Formatting nameColor = Formatting.WHITE;
    public int modelData() {
        if (currentOverride==-1)
            return overrides().values().iterator().next();
        else return currentOverride;
    }
    public void setOverride(String i) {
        currentOverride = overrides().get(i);
    }
}