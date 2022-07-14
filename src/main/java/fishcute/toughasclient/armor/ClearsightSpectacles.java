package fishcute.toughasclient.armor;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class ClearsightSpectacles extends IClientArmor {

    public static boolean hasAffect = false;
    @Override
    public String baseIdentifier() {
        return "Clearsight Spectacles";
    }

    @Override
    public Identifier layerOneIdentifier() {
        return new Identifier("tough_as_client:textures/armor/goggles_revealing.png");
    }

    @Override
    public Identifier layerTwoIdentifier() {
        return new Identifier("tough_as_client:textures/armor/goggles_revealing.png");
    }

    @Override
    public String helmetName() {
        return "Clearsight Spectacles";
    }

    @Override
    public String chestplateName() {
        return null;
    }

    @Override
    public String leggingsName() {
        return null;
    }

    @Override
    public String bootsName() {
        return null;
    }

    @Override
    public String armorTier() {
        return "golden";
    }

    @Override
    public ArrayList<String> lore() {
        return !hasAffect && ClientArmorRegistry.wearing(ClientArmorRegistry.ArmorPiece.HELMET, new ClearsightSpectacles()) ? new ArrayList<>(Arrays.asList(Formatting.GRAY + Utils.translate(Utils.translate("tough_as_client.armor.goggles_revealing.description")), Formatting.RED + Utils.translate(Utils.translate("tough_as_client.armor.goggles_revealing.cannot_use")))) : new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate(Utils.translate("tough_as_client.armor.goggles_revealing.description"))));
    }

    @Override
    public void tick() {
        hasAffect = hasAffect();
    }

    @Override
    public void init() {
        this.nameColor = Formatting.YELLOW;
    }

    @Override
    public int override() {
        return 1;
    }

    static boolean hasAffect() {
        ItemStack i = ClientArmorRegistry.getItemStack(ClientArmorRegistry.ArmorPiece.HELMET, "Clearsight Spectacles");
        return i != null && !i.hasEnchantments();
    }
}
