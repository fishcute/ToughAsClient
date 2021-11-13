package fishcute.toughasclient.custom_armor;

import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class RunningShoes extends ICustomArmor {
    @Override
    public String baseIdentifier() {
        return "Running Shoes";
    }

    @Override
    public Identifier layerOneIdentifier() {
        return new Identifier("tough_as_client:textures/armor/running_shoes.png");
    }

    @Override
    public Identifier layerTwoIdentifier() {
        return new Identifier("tough_as_client:textures/armor/running_shoes.png");
    }

    @Override
    public String helmetName() {
        return null;
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
        return "Running Shoes";
    }

    @Override
    public String armorTier() {
        return "leather";
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Collections.singletonList(Formatting.GRAY + Utils.translate(Utils.translate("tough_as_client.armor.running_shoes.description"))));
    }

    @Override
    public void tick() {

    }

    @Override
    public int override() {
        return 2;
    }
}
