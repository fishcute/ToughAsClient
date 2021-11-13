package fishcute.toughasclient.custom_item;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
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
public class UmbrellaItems {
    static HashMap<String, Integer> base() {
        HashMap<String, Integer> base = new HashMap<>();
        base.put("default", 1);
        return base;
    }

    public static ArrayList<String> lore = new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.item.umbrella.description")));

    public static ICustomItem whiteUmbrella = new InstantiableCustomItem("White Umbrella", "white_banner", base(), lore);

    public static ICustomItem orangeUmbrella = new InstantiableCustomItem("Orange Umbrella", "orange_banner", base(), lore);

    public static ICustomItem pinkUmbrella = new InstantiableCustomItem("Pink Umbrella", "pink_banner", base(), lore);

    public  static ICustomItem yellowUmbrella = new InstantiableCustomItem("Yellow Umbrella", "yellow_banner", base(), lore);

    public static ICustomItem blackUmbrella = new InstantiableCustomItem("Black Umbrella", "black_banner", base(), lore);

    public static ICustomItem blueUmbrella = new InstantiableCustomItem("Blue Umbrella", "blue_banner", base(), lore);

    public static ICustomItem brownUmbrella = new InstantiableCustomItem("Brown Umbrella", "brown_banner", base(), lore);

    public static ICustomItem cyanUmbrella = new InstantiableCustomItem("Cyan Umbrella", "cyan_banner", base(), lore);
    public static ICustomItem grayUmbrella = new InstantiableCustomItem("Gray Umbrella", "gray_banner", base(), lore);

    public static ICustomItem greenUmbrella = new InstantiableCustomItem("Green Umbrella", "green_banner", base(), lore);

    public static ICustomItem lightBlueUmbrella = new InstantiableCustomItem("Light Blue Umbrella", "light_blue_banner", base(), lore);

    public static ICustomItem lightGrayUmbrella = new InstantiableCustomItem("Light Gray Umbrella", "light_gray_banner", base(), lore);

    public static ICustomItem limeUmbrella = new InstantiableCustomItem("Lime Umbrella", "lime_banner", base(), lore);

    public static ICustomItem magentaUmbrella = new InstantiableCustomItem("Magenta Umbrella", "magenta_banner", base(), lore);

    public static ICustomItem purpleUmbrella = new InstantiableCustomItem("Purple Umbrella", "purple_banner", base(), lore);

    public static ICustomItem redUmbrella = new InstantiableCustomItem("Red Umbrella", "red_banner", base(), lore);

    public static void reloadUmbrellas() {
        whiteUmbrella = new InstantiableCustomItem("White Umbrella", "white_banner", base(), lore);
        orangeUmbrella = new InstantiableCustomItem("Orange Umbrella", "orange_banner", base(), lore);
        pinkUmbrella = new InstantiableCustomItem("Pink Umbrella", "pink_banner", base(), lore);
        yellowUmbrella = new InstantiableCustomItem("Yellow Umbrella", "yellow_banner", base(), lore);
        blackUmbrella = new InstantiableCustomItem("Black Umbrella", "black_banner", base(), lore);
        blueUmbrella = new InstantiableCustomItem("Blue Umbrella", "blue_banner", base(), lore);
        brownUmbrella = new InstantiableCustomItem("Brown Umbrella", "brown_banner", base(), lore);
        cyanUmbrella = new InstantiableCustomItem("Cyan Umbrella", "cyan_banner", base(), lore);
        grayUmbrella = new InstantiableCustomItem("Gray Umbrella", "gray_banner", base(), lore);
        greenUmbrella = new InstantiableCustomItem("Green Umbrella", "green_banner", base(), lore);
        lightBlueUmbrella = new InstantiableCustomItem("Light Blue Umbrella", "light_blue_banner", base(), lore);
        lightGrayUmbrella = new InstantiableCustomItem("Light Gray Umbrella", "light_gray_banner", base(), lore);
        limeUmbrella = new InstantiableCustomItem("Lime Umbrella", "lime_banner", base(), lore);
        magentaUmbrella = new InstantiableCustomItem("Magenta Umbrella", "magenta_banner", base(), lore);
        purpleUmbrella = new InstantiableCustomItem("Purple Umbrella", "purple_banner", base(), lore);
        redUmbrella = new InstantiableCustomItem("Red Umbrella", "red_banner", base(), lore);
    }

    public static void registerUmbrellas() {
        CustomItemRegistry.register(whiteUmbrella);
        CustomItemRegistry.register(orangeUmbrella);
        CustomItemRegistry.register(pinkUmbrella);
        CustomItemRegistry.register(purpleUmbrella);
        CustomItemRegistry.register(redUmbrella);
        CustomItemRegistry.register(yellowUmbrella);
        CustomItemRegistry.register(blackUmbrella);
        CustomItemRegistry.register(blackUmbrella);
        CustomItemRegistry.register(blueUmbrella);
        CustomItemRegistry.register(brownUmbrella);
        CustomItemRegistry.register(cyanUmbrella);
        CustomItemRegistry.register(grayUmbrella);
        CustomItemRegistry.register(greenUmbrella);
        CustomItemRegistry.register(lightBlueUmbrella);
        CustomItemRegistry.register(lightGrayUmbrella);
        CustomItemRegistry.register(limeUmbrella);
        CustomItemRegistry.register(magentaUmbrella);
    }

    public static boolean isUmbrellaItem(ItemStack i) {
        if (i!=null&&i.getTag()!=null&&i.getTag().contains("display")) {
            NbtCompound c = i.getTag().getCompound("display");
            boolean hasCorrectlore = false;
                NbtList nbtList = c.getList("Lore", 8);
                for (int j = 0; j < nbtList.size(); ++j) {
                    if (Formatting.strip(nbtList.getString(j)).contains(Utils.translate("tough_as_client.item.umbrella.description")))
                        hasCorrectlore = true;
                }
            return i.hasCustomName() && i.getName().getString().contains("Umbrella") && i.getTranslationKey().contains("banner") && hasCorrectlore;
        }
        return false;
    }

    public static boolean umbrellaInHand(Hand h) {
        ItemStack i = ClientUtils.client().player.getStackInHand(h);
        return i.getTranslationKey().contains("banner")&&isUmbrellaItem(i);
    }
}
