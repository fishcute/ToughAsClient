package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Darkness extends IClientStatusEffect {
    float savedBrightness = 0;
    float goal = 0;
    float current = 0;

    public Darkness(int ticks, int amplifier) {
        super(ticks, amplifier);
    }

    @Override
    public DefaultParticleType particleType() {
        return ClientInit.NONE;
    }

    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/darkness.png");
    }

    @Override
    public String getName() {
        return ClientStatusEffects.DARKNESS;
    }

    @Override
    public void tick() {
        boolean ending = (this.getTicks()<= Utils.secondsToTicks(5));
        if (this.current<this.goal)
            this.current+=0.1;
        else if (this.current>this.goal)
            this.current-=0.1;


        if (!ending&&(this.goal==0||Math.random()>0.99))
            if (Math.random()<0.5)
                this.goal = (int) ((Math.random()*-100));
            else
                this.goal = (int) this.savedBrightness;
        else if (ending)
            this.goal = this.savedBrightness;

        ClientUtils.client().options.gamma = this.current/10;
    }

    @Override
    public void onEnd() {
        ClientUtils.client().options.gamma = this.savedBrightness;
    }

    @Override
    public void onStart() {
        this.savedBrightness = (float) ClientUtils.client().options.gamma;
    }
}
