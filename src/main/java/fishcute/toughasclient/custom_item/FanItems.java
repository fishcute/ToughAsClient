package fishcute.toughasclient.custom_item;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.util.Stamina;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class FanItems {
    static HashMap<String, Integer> base() {
        HashMap<String, Integer> base = new HashMap<>();
        base.put("default", 1);
        return base;
    }

    static HashMap<String, Integer> exquisite() {
        HashMap<String, Integer> base = new HashMap<>();
        base.put("default", 2);
        return base;
    }

    public static ArrayList<String> lore = new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.fan.description")));

    public static ICustomItem whiteFan = new InstantiableCustomItem("White Fan", "white_carpet", base(), lore);

    public static ICustomItem orangeFan = new InstantiableCustomItem("Orange Fan", "orange_carpet", base(), lore);

    public static ICustomItem pinkFan = new InstantiableCustomItem("Pink Fan", "pink_carpet", base(), lore);

    public static ICustomItem purpleFan = new InstantiableCustomItem("Purple Fan", "purple_carpet", base(), lore);

    public static ICustomItem redFan = new InstantiableCustomItem("Red Fan", "red_carpet", base(), lore);

    public static ICustomItem yellowFan = new InstantiableCustomItem("Yellow Fan", "yellow_carpet", base(), lore);

    public static ICustomItem blackFan = new InstantiableCustomItem("Black Fan", "black_carpet", base(), lore);

    public static ICustomItem blueFan = new InstantiableCustomItem("Blue Fan", "blue_carpet", base(), lore);

    public static ICustomItem brownFan = new InstantiableCustomItem("Brown Fan", "brown_carpet", base(), lore);

    public static ICustomItem cyanFan = new InstantiableCustomItem("Cyan Fan", "cyan_carpet", base(), lore);

    public static ICustomItem exquisiteFan = new InstantiableCustomItem("Exquisite Fan", "white_carpet", exquisite(), lore);

    public static ICustomItem grayFan = new InstantiableCustomItem("Gray Fan", "gray_carpet", base(), lore);

    public static ICustomItem greenFan = new InstantiableCustomItem("Green Fan", "green_carpet", base(), lore);

    public static ICustomItem lightBlueFan = new InstantiableCustomItem("Light Blue Fan", "light_blue_carpet", base(), lore);

    public static ICustomItem lightGrayFan = new InstantiableCustomItem("Light Gray Fan", "light_gray_carpet", base(), lore);

    public static ICustomItem limeFan = new InstantiableCustomItem("Lime Fan", "lime_carpet", base(), lore);

    public static ICustomItem magentaFan = new InstantiableCustomItem("Magenta Fan", "magenta_carpet", base(), lore);

    public static void registerFans() {
        CustomItemRegistry.register(whiteFan);
        CustomItemRegistry.register(orangeFan);
        CustomItemRegistry.register(pinkFan);
        CustomItemRegistry.register(purpleFan);
        CustomItemRegistry.register(redFan);
        CustomItemRegistry.register(yellowFan);
        CustomItemRegistry.register(blackFan);
        CustomItemRegistry.register(blackFan);
        CustomItemRegistry.register(blueFan);
        CustomItemRegistry.register(brownFan);
        CustomItemRegistry.register(cyanFan);
        CustomItemRegistry.register(exquisiteFan);
        CustomItemRegistry.register(grayFan);
        CustomItemRegistry.register(greenFan);
        CustomItemRegistry.register(lightBlueFan);
        CustomItemRegistry.register(lightGrayFan);
        CustomItemRegistry.register(limeFan);
        CustomItemRegistry.register(magentaFan);
    }

    public static void reloadFans() {
        whiteFan = new InstantiableCustomItem("White Fan", "white_carpet", base(), lore);
        orangeFan = new InstantiableCustomItem("Orange Fan", "orange_carpet", base(), lore);
        pinkFan = new InstantiableCustomItem("Pink Fan", "pink_carpet", base(), lore);
        purpleFan = new InstantiableCustomItem("Purple Fan", "purple_carpet", base(), lore);
        redFan = new InstantiableCustomItem("Red Fan", "red_carpet", base(), lore);
        yellowFan = new InstantiableCustomItem("Yellow Fan", "yellow_carpet", base(), lore);
        blackFan = new InstantiableCustomItem("Black Fan", "black_carpet", base(), lore);
        blueFan = new InstantiableCustomItem("Blue Fan", "blue_carpet", base(), lore);
        brownFan = new InstantiableCustomItem("Brown Fan", "brown_carpet", base(), lore);
        cyanFan = new InstantiableCustomItem("Cyan Fan", "cyan_carpet", base(), lore);
        exquisiteFan = new InstantiableCustomItem("Exquisite Fan", "white_carpet", exquisite(), lore);
        grayFan = new InstantiableCustomItem("Gray Fan", "gray_carpet", base(), lore);
        greenFan = new InstantiableCustomItem("Green Fan", "green_carpet", base(), lore);
        lightBlueFan = new InstantiableCustomItem("Light Blue Fan", "light_blue_carpet", base(), lore);
        lightGrayFan = new InstantiableCustomItem("Light Gray Fan", "light_gray_carpet", base(), lore);
        limeFan = new InstantiableCustomItem("Lime Fan", "lime_carpet", base(), lore);
        magentaFan = new InstantiableCustomItem("Magenta Fan", "magenta_carpet", base(), lore);
    }

    public static boolean isFanItem(ItemStack i) {
        if (i!=null&&i.getTag()!=null&&i.getTag().contains("display")) {
            NbtCompound c = i.getTag().getCompound("display");
            boolean hasCorrectLore = false;
            if (c.getType("Lore") == 9) {
                NbtList nbtList = c.getList("Lore", 8);
                for (int j = 0; j < nbtList.size(); ++j) {
                    if (Formatting.strip(nbtList.getString(j)).contains(Utils.translate("tough_as_client.item.fan.description")))
                        hasCorrectLore = true;
                }
            } else return false;
            return i.hasCustomName() && i.getName().getString().contains("Fan") && i.getTranslationKey().contains("carpet") && hasCorrectLore;
        }
        return false;
    }

    public static void useFan(Hand h) {
        ClientUtils.swingArm(h);
        DataManager.temperature-=calculateTemperatureChange();
        Stamina.decreaseStamina(2);
        Stamina.setStaminaRegen(20);
    }

    static int calculateTemperatureChange() {
        if (DataManager.temperature<50)
            return 0;
        return Math.round((Utils.distance(DataManager.temperature, 0))/40);
    }
}
