package fishcute.toughasclient.armor;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class CoolingArmor extends IClientArmor {
    @Override
    public String baseIdentifier() {
        return "Cooling Armor";
    }

    @Override
    public Identifier layerOneIdentifier() {
        if (ClientUtils.inventoryContains(Items.ICE) || ClientUtils.inventoryContains(Items.BLUE_ICE) || ClientUtils.inventoryContains(Items.PACKED_ICE))
            return new Identifier("tough_as_client:textures/armor/cooling_ice_layer_1.png");
        return new Identifier("tough_as_client:textures/armor/cooling_layer_1.png");
    }

    @Override
    public Identifier layerTwoIdentifier() {
        if (ClientUtils.inventoryContains(Items.ICE) || ClientUtils.inventoryContains(Items.BLUE_ICE) || ClientUtils.inventoryContains(Items.PACKED_ICE))
            return new Identifier("tough_as_client:textures/armor/cooling_ice_layer_2.png");
        return new Identifier("tough_as_client:textures/armor/cooling_layer_2.png");
    }

    @Override
    public String helmetName() {
        return "Cooling Helmet";
    }

    @Override
    public String chestplateName() {
        return "Cooling Chestplate";
    }

    @Override
    public String leggingsName() {
        return "Cooling Leggings";
    }

    @Override
    public String bootsName() {
        return "Cooling Boots";
    }

    @Override
    public String armorTier() {
        return "chainmail";
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.armor.cooling_armor.description")));
    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {

    }

    @Override
    public int override() {
        return 1;
    }
}
