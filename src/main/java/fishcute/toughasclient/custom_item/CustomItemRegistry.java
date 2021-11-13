package fishcute.toughasclient.custom_item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class CustomItemRegistry {
    public static ArrayList<ICustomItem> registry = new ArrayList<>();
    public static boolean equals(ItemStack i, ICustomItem e) {
        try {
            NbtCompound nbtCompound = i.getSubTag("display");
            String text = Formatting.strip(i.getName().asString());
            return (i != null && i.getItem().getTranslationKey().contains(e.itemType()) && e.identifier().equals((text)));
        }
        catch (Exception ex) {
            return false;
        }
    }
    public static boolean isCustomItem(ItemStack i) {
        for (ICustomItem e : registry)
            if (i.hasCustomName()&&equals(i, e))
                return true;
        return false;
    }
    public static ICustomItem valueOf(ItemStack i) {
        for (ICustomItem e : registry)
            if (equals(i, e))
                return e;
            return null;
    }
    public static void register(ICustomItem i) {
        registry.add(i);
    }
    public static ICustomItem getItem(String i) {
        for (ICustomItem j : registry) {
            if (j.identifier().equals(i))
                return j;
        }
        return null;
    }
    public static void tickCustomItems() {
        for (ICustomItem i : registry)
            i.tick();
    }
}
