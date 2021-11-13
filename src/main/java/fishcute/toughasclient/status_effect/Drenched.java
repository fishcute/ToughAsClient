package fishcute.toughasclient.status_effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Drenched extends IClientStatusEffect {
    public Drenched(int ticks, int amplifier) {
        super(ticks, amplifier);
    }
    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/drenched.png");
    }
    @Override
    public DefaultParticleType particleType() {
        return ParticleTypes.FALLING_WATER;
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
        return ClientStatusEffects.DRENCHED;
    }
}
