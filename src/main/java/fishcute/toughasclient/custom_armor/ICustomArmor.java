package fishcute.toughasclient.custom_armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public abstract class ICustomArmor {
    public abstract String baseIdentifier();
    public abstract Identifier layerOneIdentifier();
    public abstract Identifier layerTwoIdentifier();

    public abstract String helmetName();
    public abstract String chestplateName();
    public abstract String leggingsName();
    public abstract String bootsName();

    public abstract String armorTier();

    public abstract ArrayList<String> lore();

    public abstract void tick();

    public abstract int override();

    public String armorPieceName(CustomArmorRegistry.ArmorPiece i) {
        switch (i) {
            case BOOTS: return this.bootsName();
            case LEGGINGS: return this.leggingsName();
            case CHESTPLATE: return this.chestplateName();
            case HELMET: return this.helmetName();
        };
        return "";
    }
}
