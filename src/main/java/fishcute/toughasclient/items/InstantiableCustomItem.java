package fishcute.toughasclient.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class InstantiableCustomItem extends IClientItem {
    private final String identifier;
    private final String itemType;
    private final HashMap<String, Integer> overrides;
    private ArrayList<String> lore;
    public InstantiableCustomItem(String identifier, String itemType, HashMap<String, Integer> overrides, ArrayList<String> lore, Formatting nameColor) {
        this.identifier = identifier;
        this.itemType = itemType;
        this.overrides = overrides;
        this.lore = lore;
        this.nameColor = nameColor;
    }

    public InstantiableCustomItem(String identifier, String itemType, HashMap<String, Integer> overrides, ArrayList<String> lore) {
        this.identifier = identifier;
        this.itemType = itemType;
        this.overrides = overrides;
        this.lore = lore;
    }

    public void setLore(ArrayList<String> newLore) {
        this.lore = newLore;
    }

    @Override
    public String identifier() {
        return this.identifier;
    }

    @Override
    public String itemType() {
        return this.itemType;
    }

    @Override
    public HashMap<String, Integer> overrides() {
        return this.overrides;
    }

    @Override
    public ArrayList<String> lore() {
        return this.lore;
    }

    @Override
    public void handTick() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {
    }
}
