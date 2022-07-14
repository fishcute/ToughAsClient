package fishcute.toughasclient.items;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class CursedLantern extends IClientItem {
    @Override
    public String identifier() {
        return "Cursed Lantern";
    }

    @Override
    public String itemType() {
        return "dragon_breath";
    }

    @Override
    public HashMap<String, Integer> overrides() {
        HashMap<String, Integer> a = new HashMap<>();
        a.put("default", 1);
        return a;
    }

    @Override
    public ArrayList<String> lore() {
        return new ArrayList<>(Arrays.asList(Formatting.DARK_PURPLE + Utils.translate("tough_as_client.item.cursed_lantern.lore"), Formatting.GRAY + Utils.translate("tough_as_client.item.cursed_lantern.description")));
    }

    @Override
    public void handTick() {
        if (Math.random() < 0.1)
            ClientUtils.particleGroup(ClientInit.CURSED_FLAME_PARTICLE, ClientUtils.loc().x,ClientUtils.loc().y, ClientUtils.loc().z, 0.5, 1 ,0.5, 1);
    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {
        this.nameColor = Formatting.LIGHT_PURPLE;
    }
}
