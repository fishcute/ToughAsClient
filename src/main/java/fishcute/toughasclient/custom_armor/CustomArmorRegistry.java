package fishcute.toughasclient.custom_armor;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.custom_item.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class CustomArmorRegistry {
    public static ArrayList<ICustomArmor> registry = new ArrayList<>();
    public static boolean equals(ItemStack i, ICustomArmor e, ArmorPiece p) {
        try {
            String text = Formatting.strip(i.getName().asString());
            if (p == ArmorPiece.BOOTS)
                return (i != null && i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.bootsName().equals((text)));
            else if (p == ArmorPiece.LEGGINGS)
                return (i != null && i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.leggingsName().equals((text)));
            if (p == ArmorPiece.CHESTPLATE)
                return (i != null && i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.chestplateName().equals((text)));
            if (p == ArmorPiece.HELMET)
                return (i != null && i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.helmetName().equals((text)));
        }
        catch (Exception ex) {
            return false;
        }
        return false;
    }
    public static boolean equalsAnyType(ItemStack i, ICustomArmor e) {
        try {
            String text = Formatting.strip(i.getName().asString());
            return (i != null && i.getItem().getTranslationKey().contains(e.armorTier()) &&
                    e.bootsName().equals((text))||e.leggingsName().equals((text))||
                    e.chestplateName().equals((text))||e.helmetName().equals((text)));
        }
        catch (Exception ex) {
            return false;
        }
    }
    public static boolean isCustomArmor(ItemStack i) {
        for (ICustomArmor e : registry)
            if (i.hasCustomName()&&equalsAnyType(i, e))
                return true;
        return false;
    }
    public static ICustomArmor valueOf(ItemStack i) {
        for (ICustomArmor e : registry)
            if (equalsAnyType(i, e))
                return e;
        return null;
    }
    public static void register(ICustomArmor i) {
        registry.add(i);
    }
    public static ICustomArmor getItem(String i) {
        for (ICustomArmor j : registry) {
            if (j.baseIdentifier().equals(i))
                return j;
        }
        return null;
    }
    public static void tickCustomArmor() {
        for (ICustomArmor i : registry)
            i.tick();
    }
    public static void registerCustomArmor(ICustomArmor i) {
        registry.add(i);
        registerArmorItems(i);
    }
    static void registerArmorItems(ICustomArmor i) {
        HashMap<String, Integer> base = new HashMap<>();
        base.put("default", i.override());
        if (i.bootsName()!=null)
        CustomItemRegistry.register(new InstantiableCustomItem(
                i.bootsName(), i.armorTier(), base, i.lore()));
        if (i.chestplateName()!=null)
        CustomItemRegistry.register(new InstantiableCustomItem(
                i.leggingsName(), i.armorTier(), base, i.lore()));
        if (i.leggingsName()!=null)
        CustomItemRegistry.register(new InstantiableCustomItem(
                i.chestplateName(), i.armorTier(), base, i.lore()));
        if (i.helmetName()!=null)
        CustomItemRegistry.register(new InstantiableCustomItem(
                i.helmetName(), i.armorTier(), base, i.lore()));
    }
    public static boolean wearing(CustomArmorRegistry.ArmorPiece i, ICustomArmor a) {
        for (ItemStack e : ClientUtils.e().getArmorItems())
            if (e.getItem().getTranslationKey().contains(a.armorTier())&&CustomArmorRegistry.ArmorPiece.value(e.getTranslationKey())==i
            &&e.hasCustomName()&&Formatting.strip(e.getName().getString()).equals(a.armorPieceName(i)))
                return true;
        return false;
    }
    public static int amountWearing(ICustomArmor a) {
        int i = 0;
        if (wearing(CustomArmorRegistry.ArmorPiece.BOOTS, a))
            i++;
        if (wearing(CustomArmorRegistry.ArmorPiece.LEGGINGS, a))
            i++;
        if (wearing(CustomArmorRegistry.ArmorPiece.CHESTPLATE, a))
            i++;
        if (wearing(CustomArmorRegistry.ArmorPiece.HELMET, a))
            i++;
        return i;
    }
    public enum ArmorPiece {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS;
        public static ArmorPiece value(String i) {
            if (i.contains("boots"))
                return ArmorPiece.BOOTS;
            else if (i.contains("leggings"))
                return ArmorPiece.LEGGINGS;
            else if (i.contains("chestplate"))
                return ArmorPiece.CHESTPLATE;
            else if (i.contains("helmet"))
                return ArmorPiece.HELMET;
            return ArmorPiece.BOOTS;
        }
    }
}
