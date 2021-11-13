package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Hypernatremia extends IClientStatusEffect {
    public Hypernatremia(int ticks, int amplifier) {
        super(ticks, amplifier);
    }

    @Override
    public DefaultParticleType particleType() {
        return ClientInit.NONE;
    }

    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/hypernatremia.png");
    }

    @Override
    public String getName() {
        return ClientStatusEffects.HYPERNATREMIA;
    }

    @Override
    public void tick() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onStart() {

    }
}
