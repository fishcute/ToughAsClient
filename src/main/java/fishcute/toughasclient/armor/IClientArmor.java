package fishcute.toughasclient.armor;

import fishcute.toughasclient.items.ClientItemRegistry;
import fishcute.toughasclient.items.InstantiableCustomItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public abstract class IClientArmor {
    ArrayList<InstantiableCustomItem> armorItems = new ArrayList<>();
    public abstract String baseIdentifier();
    public abstract Identifier layerOneIdentifier();
    public abstract Identifier layerTwoIdentifier();

    public abstract String helmetName();
    public abstract String chestplateName();
    public abstract String leggingsName();
    public abstract String bootsName();

    public abstract String armorTier();

    public abstract ArrayList<String> lore();

    public Formatting nameColor = Formatting.WHITE;

    public abstract void tick();
    public abstract void init();

    void tickBefore() {
        for (InstantiableCustomItem i : this.armorItems)
            i.setLore(this.lore());
        this.tick();
    }

    void registerArmorItem(InstantiableCustomItem i) {
        ClientItemRegistry.register(i);
        armorItems.add(i);
    }

    public abstract int override();

    public String armorPieceName(ClientArmorRegistry.ArmorPiece i) {
        switch (i) {
            case BOOTS: return bootsName();
            case LEGGINGS: return leggingsName();
            case CHESTPLATE: return chestplateName();
            case HELMET: return helmetName();
        };
        return "";
    }
}
