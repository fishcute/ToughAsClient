package fishcute.toughasclient.items;

import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class UVSterilizer extends IClientItem {
    @Override
    public String identifier() {
        return "UV Sterilizer";
    }

    @Override
    public String itemType() {
        return "comparator";
    }

    @Override
    public HashMap<String, Integer> overrides() {
        currentOverride = 1;
        HashMap<String, Integer> a = new HashMap<>();
        a.put("uv_sterilizer", 1);
        a.put("uv_sterilizer_on", 2);
        return a;
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.uv_sterilizer.description")));
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
