package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.util.ClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Thirst extends IClientStatusEffect {
    public Thirst(int ticks, int amplifier) {
        super(ticks, amplifier);
    }

    @Override
    public DefaultParticleType particleType() {
        return ClientInit.NONE;
    }

    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/thirst.png");
    }

    @Override
    public String getName() {
        return ClientStatusEffects.THIRST;
    }

    @Override
    public void tick() {
        ClientUtils.client().player.setSprinting(false);
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onStart() {
    }
}
