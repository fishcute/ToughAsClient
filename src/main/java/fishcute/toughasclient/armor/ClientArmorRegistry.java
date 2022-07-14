package fishcute.toughasclient.armor;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.items.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ClientArmorRegistry {
    public static ArrayList<IClientArmor> registry = new ArrayList<>();
    public static boolean equals(ItemStack i, IClientArmor e, ArmorPiece p) {
        try {
            String text = Formatting.strip(i.getName().asString());
            if (p == ArmorPiece.BOOTS)
                return (i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.bootsName().equals((text)));
            else if (p == ArmorPiece.LEGGINGS)
                return (i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.leggingsName().equals((text)));
            if (p == ArmorPiece.CHESTPLATE)
                return (i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.chestplateName().equals((text)));
            if (p == ArmorPiece.HELMET)
                return (i.getItem().getTranslationKey().contains(e.armorTier()) &&
                        e.helmetName().equals((text)));
        }
        catch (Exception ex) {
            return false;
        }
        return false;
    }
    public static boolean equalsAnyType(ItemStack i, IClientArmor e) {
        try {
            return (equals(i, e, ArmorPiece.BOOTS) || equals(i, e, ArmorPiece.LEGGINGS) || equals(i, e, ArmorPiece.CHESTPLATE) || equals(i, e, ArmorPiece.HELMET));
        }
        catch (Exception ex) {
            return false;
        }
    }
    public static boolean isCustomArmor(ItemStack i) {
        for (IClientArmor e : registry)
            if (i.hasCustomName()&&equalsAnyType(i, e))
                return true;
        return false;
    }
    public static IClientArmor valueOf(ItemStack i) {
        for (IClientArmor e : registry)
            if (equalsAnyType(i, e))
                return e;
        return null;
    }
    public static void register(IClientArmor i) {
        registry.add(i);
    }
    public static IClientArmor getItem(String i) {
        for (IClientArmor j : registry) {
            if (j.baseIdentifier().equals(i))
                return j;
        }
        return null;
    }

    public static ItemStack getItemStack(ArmorPiece p, String i) {
        if (ClientUtils.client().player != null)
            for (ItemStack a : ClientUtils.client().player.inventory.armor) {
                if (isCustomArmor(a)) {
                    IClientArmor armor = valueOf(a);
                    if (armor.baseIdentifier().equals(i) && ArmorPiece.value(a.getTranslationKey()).equals(p))
                        return a;
                }
            }
        return null;
    }
    public static void tickCustomArmor() {
        for (IClientArmor i : registry)
            i.tickBefore();
    }
    public static void registerCustomArmor(IClientArmor i) {
        registry.add(i);
        i.init();
        registerArmorItems(i);
    }
    static void registerArmorItems(IClientArmor i) {
        HashMap<String, Integer> base = new HashMap<>();
        base.put("default", i.override());
        if (i.bootsName()!=null)
            i.registerArmorItem(new InstantiableCustomItem(
                i.bootsName(), i.armorTier() + "_boots", base, i.lore(), i.nameColor));
        if (i.chestplateName()!=null)
            i.registerArmorItem(new InstantiableCustomItem(
                i.leggingsName(), i.armorTier()+ "_leggings", base, i.lore(), i.nameColor));
        if (i.leggingsName()!=null)
            i.registerArmorItem(new InstantiableCustomItem(
                i.chestplateName(), i.armorTier()+ "_chestplate", base, i.lore(), i.nameColor));
        if (i.helmetName()!=null)
            i.registerArmorItem(new InstantiableCustomItem(
                i.helmetName(), i.armorTier()+ "_helmet", base, i.lore(), i.nameColor));
    }
    public static boolean wearing(ClientArmorRegistry.ArmorPiece i, IClientArmor a) {
        if (ToughAsClientMod.isIngame())
            for (ItemStack e : ClientUtils.e().getArmorItems())
                if (e.getItem().getTranslationKey().contains(a.armorTier())&& ClientArmorRegistry.ArmorPiece.value(e.getTranslationKey())==i
                &&e.hasCustomName()&&Formatting.strip(e.getName().getString()).equals(a.armorPieceName(i)))
                    return true;
        return false;
    }
    public static int amountWearing(IClientArmor a) {
        int i = 0;
        if (wearing(ClientArmorRegistry.ArmorPiece.BOOTS, a))
            i++;
        if (wearing(ClientArmorRegistry.ArmorPiece.LEGGINGS, a))
            i++;
        if (wearing(ClientArmorRegistry.ArmorPiece.CHESTPLATE, a))
            i++;
        if (wearing(ClientArmorRegistry.ArmorPiece.HELMET, a))
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
