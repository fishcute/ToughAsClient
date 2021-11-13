package fishcute.toughasclient.custom_item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class InstantiableCustomItem extends ICustomItem {
    private String identifier;
    private String itemType;
    private HashMap<String, Integer> overrides;
    private ArrayList<String> lore;
    public InstantiableCustomItem(String identifier, String itemType, HashMap<String, Integer> overrides, ArrayList<String> lore) {
        this.identifier = identifier;
        this.itemType = itemType;
        this.overrides = overrides;
        this.lore = lore;
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
    public void tick() {
    }
}
