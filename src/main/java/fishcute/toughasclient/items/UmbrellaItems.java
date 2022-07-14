package fishcute.toughasclient.items;

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

    public static IClientItem whiteUmbrella = new InstantiableCustomItem("White Umbrella", "white_banner", base(), lore);

    public static IClientItem orangeUmbrella = new InstantiableCustomItem("Orange Umbrella", "orange_banner", base(), lore);

    public static IClientItem pinkUmbrella = new InstantiableCustomItem("Pink Umbrella", "pink_banner", base(), lore);

    public  static IClientItem yellowUmbrella = new InstantiableCustomItem("Yellow Umbrella", "yellow_banner", base(), lore);

    public static IClientItem blackUmbrella = new InstantiableCustomItem("Black Umbrella", "black_banner", base(), lore);

    public static IClientItem blueUmbrella = new InstantiableCustomItem("Blue Umbrella", "blue_banner", base(), lore);

    public static IClientItem brownUmbrella = new InstantiableCustomItem("Brown Umbrella", "brown_banner", base(), lore);

    public static IClientItem cyanUmbrella = new InstantiableCustomItem("Cyan Umbrella", "cyan_banner", base(), lore);
    public static IClientItem grayUmbrella = new InstantiableCustomItem("Gray Umbrella", "gray_banner", base(), lore);

    public static IClientItem greenUmbrella = new InstantiableCustomItem("Green Umbrella", "green_banner", base(), lore);

    public static IClientItem lightBlueUmbrella = new InstantiableCustomItem("Light Blue Umbrella", "light_blue_banner", base(), lore);

    public static IClientItem lightGrayUmbrella = new InstantiableCustomItem("Light Gray Umbrella", "light_gray_banner", base(), lore);

    public static IClientItem limeUmbrella = new InstantiableCustomItem("Lime Umbrella", "lime_banner", base(), lore);

    public static IClientItem magentaUmbrella = new InstantiableCustomItem("Magenta Umbrella", "magenta_banner", base(), lore);

    public static IClientItem purpleUmbrella = new InstantiableCustomItem("Purple Umbrella", "purple_banner", base(), lore);

    public static IClientItem redUmbrella = new InstantiableCustomItem("Red Umbrella", "red_banner", base(), lore);

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
        ClientItemRegistry.register(whiteUmbrella);
        ClientItemRegistry.register(orangeUmbrella);
        ClientItemRegistry.register(pinkUmbrella);
        ClientItemRegistry.register(purpleUmbrella);
        ClientItemRegistry.register(redUmbrella);
        ClientItemRegistry.register(yellowUmbrella);
        ClientItemRegistry.register(blackUmbrella);
        ClientItemRegistry.register(blackUmbrella);
        ClientItemRegistry.register(blueUmbrella);
        ClientItemRegistry.register(brownUmbrella);
        ClientItemRegistry.register(cyanUmbrella);
        ClientItemRegistry.register(grayUmbrella);
        ClientItemRegistry.register(greenUmbrella);
        ClientItemRegistry.register(lightBlueUmbrella);
        ClientItemRegistry.register(lightGrayUmbrella);
        ClientItemRegistry.register(limeUmbrella);
        ClientItemRegistry.register(magentaUmbrella);
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
