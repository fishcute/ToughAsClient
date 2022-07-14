package fishcute.toughasclient.items;

import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class BlueIcePack extends IClientItem {
    @Override
    public String identifier() {
        return "Ice Pack";
    }

    @Override
    public String itemType() {
        return "blue_ice";
    }

    @Override
    public HashMap<String, Integer> overrides() {
        HashMap<String, Integer> a = new HashMap<>();
        a.put("default", 1);
        return a;
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Arrays.asList(
                Formatting.GRAY + Utils.translate("tough_as_client.item.ice_pack.description"),
                Formatting.AQUA + Utils.translate("tough_as_client.item.ice_pack.tier.blue_ice")
        ));
    }

    @Override
    public void handTick() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {

    }
}
