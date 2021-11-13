package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Refreshed extends IClientStatusEffect {
    public Refreshed(int ticks, int amplifier) {
        super(ticks, amplifier);
    }
    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/refreshed.png");
    }
    @Override
    public DefaultParticleType particleType() {
        return ClientInit.NONE;
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

    @Override
    public String getName() {
        return ClientStatusEffects.REFRESHED;
    }
}
