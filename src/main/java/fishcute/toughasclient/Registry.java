package fishcute.toughasclient;

import fishcute.toughasclient.custom_armor.CustomArmorRegistry;
import fishcute.toughasclient.custom_armor.ICustomArmor;
import fishcute.toughasclient.custom_armor.RunningShoes;
import fishcute.toughasclient.custom_armor.SnowArmor;
import fishcute.toughasclient.custom_item.*;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class Registry {
    public static void registerCustomArmor(ICustomArmor i) {
        CustomArmorRegistry.registerCustomArmor(i);
    }
    public static void registerCustomItem(ICustomItem i) {
        CustomItemRegistry.register(i);
    }
    public static void registerBaseItems() {
        registerCustomItem(new UVSterilizer());
        registerCustomItem(new Thermometer());
        registerCustomItem(new WaterTestingKit());
        registerCustomArmor(new SnowArmor());
        registerCustomArmor(new RunningShoes());
        FanItems.registerFans();
        UmbrellaItems.registerUmbrellas();
        ToughAsClientMod.log("Registered custom items", Level.INFO);
    }
    public static void reloadBaseItems() {
        CustomArmorRegistry.registry.clear();
        CustomItemRegistry.registry.clear();
        UmbrellaItems.lore =
                new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.umbrella.description")));
        FanItems.lore =
                new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.fan.description")));
        UmbrellaItems.reloadUmbrellas();
        FanItems.reloadFans();
        registerCustomItem(new UVSterilizer());
        registerCustomItem(new Thermometer());
        registerCustomItem(new WaterTestingKit());
        registerCustomArmor(new SnowArmor());
        registerCustomArmor(new RunningShoes());
        FanItems.registerFans();
        UmbrellaItems.registerUmbrellas();
        ToughAsClientMod.log("Reloading custom items", Level.INFO);
    }
}
