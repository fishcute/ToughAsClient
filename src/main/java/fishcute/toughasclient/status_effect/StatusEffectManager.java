package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.DataManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Iterator;

@Environment(EnvType.CLIENT)
public class StatusEffectManager {
    public static void tickStatusEffects() {
        if (DataManager.effects.size()>0)
            for (Iterator<IClientStatusEffect> iterator = DataManager.effects.iterator(); iterator.hasNext();) {
                IClientStatusEffect a = iterator.next();
                if(a.getTicks()<0) {
                    a.onEndInit();
                    iterator.remove();
                }
                else a.tickInit();
            }
    }

    public static void addStatusEffect(IClientStatusEffect e) {
        if (contains(e.getName()))
            update(e.getName(), e);
        else {
            e.onStart();
            DataManager.effects.add(e);
        }
    }
    public static void removeStatusEffect(String name, boolean onEnd) {
        if (DataManager.effects.size()>0)
            for (Iterator<IClientStatusEffect> iterator = DataManager.effects.iterator(); iterator.hasNext();) {
                IClientStatusEffect b = iterator.next();
                if(b.getName().equals(name)) {
                    if (onEnd) b.onEndInit();
                    iterator.remove();
                }
            }
    }
    public static void clear() {
        if (DataManager.effects.size()>0)
            for (Iterator<IClientStatusEffect> iterator = DataManager.effects.iterator(); iterator.hasNext();) {
                IClientStatusEffect b = iterator.next();
                b.onEndInit();
                iterator.remove();
            }
    }
    public static void update(String name, IClientStatusEffect e) {
        if (DataManager.effects.size()>0)
            for (IClientStatusEffect b : DataManager.effects) {
                if (b.getName().equals(name)) {
                    b.update(e.getTicks(), e.getAmplifier());
                }
            }
    }
    public static boolean contains(String name) {
        if (DataManager.effects.size()>0)
            for (IClientStatusEffect b : DataManager.effects) {
                if (b != null && b.getName().equals(name)) {
                    return true;
                }
            }
        return false;
    }
}
