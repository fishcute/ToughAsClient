package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class IClientStatusEffect {
    int ticks;
    int amplifier;
    public boolean active = false;
    public final String name = this.getName();
    public IClientStatusEffect(int ticks, int amplifier) {
        this.ticks = ticks;
        this.amplifier = amplifier;
    }
    public void update(int ticks, int amplifier) {
        this.ticks = ticks;
        this.amplifier = amplifier;
    }

    public String formattedTicksRemaining() {
        int time = ticks/80;
        int minutes = time / (60);
        int seconds = (time) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
    public int getAmplifier() {
        return this.amplifier;
    }
    public abstract DefaultParticleType particleType();
    public abstract Identifier getIcon();
    public abstract String getName();
    public abstract void tick();
    public void tickInit() {
        this.active = true;
        this.ticks--;
        this.playParticle();
        this.tick();
    }

    public int getTicks() {
        return this.ticks;
    }

    public void remove() {
        for (IClientStatusEffect e : DataManager.effects) {
            if (e.getName().equals(this.getName()))
                DataManager.effects.remove(this);
        }
    }

    public void playParticle() {
        if (this.particleType()!=ClientInit.NONE)
            Utils.surroundParticle(this.particleType(), MinecraftClient.getInstance().player, 0.93, 0.5);
    }
    public void onEndInit() {
        this.active = false;
        this.onEnd();
    }
    public abstract void onEnd();
    public abstract void onStart();
}
