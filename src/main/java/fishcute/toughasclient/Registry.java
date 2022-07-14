package fishcute.toughasclient;

import fishcute.toughasclient.armor.*;
import fishcute.toughasclient.items.*;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class Registry {
    public static void registerCustomArmor(IClientArmor i) {
        ClientArmorRegistry.registerCustomArmor(i);
    }
    public static void registerCustomItem(IClientItem i) {
        ClientItemRegistry.register(i);
    }
    public static void registerBaseItems() {
        registerCustomItem(new CursedLantern());
        registerCustomItem(new UVSterilizer());
        registerCustomItem(new Thermometer());
        registerCustomItem(new WaterTestingKit());
        registerCustomItem(new IcePack());
        registerCustomItem(new PackedIcePack());
        registerCustomItem(new BlueIcePack());
        registerCustomArmor(new SnowArmor());
        registerCustomArmor(new RunningShoes());
        registerCustomArmor(new CoolingArmor());
        registerCustomArmor(new ClearsightSpectacles());
        FanItems.registerFans();
        UmbrellaItems.registerUmbrellas();
        ToughAsClientMod.log("Registered custom items", Level.INFO);
    }
    public static void reloadBaseItems() {
        ClientArmorRegistry.registry.clear();
        ClientItemRegistry.registry.clear();
        UmbrellaItems.lore =
                new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.umbrella.description")));
        FanItems.lore =
                new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.fan.description")));
        UmbrellaItems.reloadUmbrellas();
        FanItems.reloadFans();
        registerBaseItems();
        ToughAsClientMod.log("Reloading custom items", Level.INFO);
    }
}
