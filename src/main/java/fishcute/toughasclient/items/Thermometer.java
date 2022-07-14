package fishcute.toughasclient.items;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class Thermometer extends IClientItem {
    @Override
    public String identifier() {
        return "Thermometer";
    }

    @Override
    public String itemType() {
        return "glass_bottle";
    }

    @Override
    public HashMap<String, Integer> overrides() {
        HashMap<String, Integer> a = new HashMap<>();
        a.put("1", 1);
        a.put("2", 2);
        a.put("3", 3);
        a.put("4", 4);
        a.put("5", 5);
        a.put("6", 6);
        a.put("7", 7);
        a.put("8", 8);
        return a;
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Arrays.asList(Formatting.GRAY + Utils.translate("tough_as_client.item.thermometer.description"),
                Formatting.GRAY + Utils.translateReplace("tough_as_client.item.thermometer.lore", "%s", temperatureLore())));
    }

    @Override
    public void handTick() {

    }

    int roundedTemp() {
        int round = Math.round(DataManager.immediateTemperature/8);
        if (round > 8)
            round = 8;
        else if (round < 1)
            round = 1;
        return round;
    }


    @Override
    public void tick() {
        setOverride(roundedTemp() + "");
    }

    @Override
    public void init() {

    }

    String temperatureLore() {
        switch (roundedTemp()) {
            case 1: return Formatting.WHITE + Utils.translate("tough_as_client.item.thermometer.lore.1");
            case 2: return Formatting.AQUA + Utils.translate("tough_as_client.item.thermometer.lore.2");
            case 3: return Formatting.DARK_AQUA + Utils.translate("tough_as_client.item.thermometer.lore.3");
            case 4:
            case 5: return Formatting.BLUE + Utils.translate("tough_as_client.item.thermometer.lore.4");
            case 6: return Formatting.DARK_PURPLE + Utils.translate("tough_as_client.item.thermometer.lore.5");
            case 7: return Formatting.RED + Utils.translate("tough_as_client.item.thermometer.lore.6");
            case 8: return Formatting.DARK_RED + Utils.translate("tough_as_client.item.thermometer.lore.7");
            default: return "Something went wrong";
        }
    }
}
