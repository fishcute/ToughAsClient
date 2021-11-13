package fishcute.toughasclient.custom_armor;

import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class SnowArmor extends ICustomArmor{
    @Override
    public String baseIdentifier() {
        return "Snow Armor";
    }

    @Override
    public Identifier layerOneIdentifier() {
        return new Identifier("tough_as_client:textures/armor/snow_layer_1.png");
    }

    @Override
    public Identifier layerTwoIdentifier() {
        return new Identifier("tough_as_client:textures/armor/snow_layer_2.png");
    }

    @Override
    public String helmetName() {
        return "Snow Hat";
    }

    @Override
    public String chestplateName() {
        return "Snow Jacket";
    }

    @Override
    public String leggingsName() {
        return "Snow Pants";
    }

    @Override
    public String bootsName() {
        return "Snow Boots";
    }

    @Override
    public String armorTier() {
        return "leather";
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate("tough_as_client.armor.snow_armor.description")));
    }

    @Override
    public void tick() {

    }

    @Override
    public int override() {
        return 1;
    }
}
