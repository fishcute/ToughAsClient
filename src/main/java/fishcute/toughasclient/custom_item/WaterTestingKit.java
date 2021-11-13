package fishcute.toughasclient.custom_item;

import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.util.Water;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class WaterTestingKit extends ICustomItem {
    public Water.WaterType result = Water.WaterType.NOT_FOUND;
    @Override
    public String identifier() {
        return "Water Testing Kit";
    }

    @Override
    public String itemType() {
        return "observer";
    }

    @Override
    public HashMap<String, Integer> overrides() {
        HashMap<String, Integer> a = new HashMap<>();
        a.put("default", 1);
        return a;
    }

    @Override
    public ArrayList<String> lore() {
        ArrayList<String> lore = new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.water_testing_kit.description")));
        if (result!= Water.WaterType.NOT_FOUND)
            lore.add(Formatting.GRAY + Utils.translateReplace("tough_as_client.item.water_testing_kit.lore", "%s", toDisplay(result)));
        return lore;
    }
    public String toDisplay(Water.WaterType w) {
        switch (w) {
            case LAKE: return Formatting.GREEN + Utils.translate("tough_as_client.item.water_testing_kit.lore.lake");
            case OCEAN: return Formatting.YELLOW + Utils.translate("tough_as_client.item.water_testing_kit.lore.ocean");
            case SWAMP: return Formatting.DARK_GREEN + Utils.translate("tough_as_client.item.water_testing_kit.lore.swamp");
            case NORMAL: return Formatting.WHITE + Utils.translate("tough_as_client.item.water_testing_kit.lore.normal");
            case SANITARY: return Formatting.WHITE + Utils.translate("tough_as_client.item.water_testing_kit.lore.sanitary");
            default: return "Something went wrong";
        }
    }

    @Override
    public void tick() {

    }
}
