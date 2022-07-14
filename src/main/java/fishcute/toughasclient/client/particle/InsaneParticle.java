package fishcute.toughasclient.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class InsaneParticle extends SpriteBillboardParticle {
    private InsaneParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        velocityX *= 0.009999999776482582D;
        velocityY *= 0.009999999776482582D;
        velocityZ *= 0.009999999776482582D;
        velocityY += 0.1D;
        scale *= 1.5F;
        maxAge = 16;
        collidesWithWorld = false;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public float getSize(float tickDelta) {
        return scale * MathHelper.clamp(((float)age + tickDelta) / (float)maxAge * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
        if (age++ >= maxAge) {
            markDead();
        } else {
            move(velocityX, velocityY, velocityZ);
            if (y == prevPosY) {
                velocityX *= 1.1D;
                velocityZ *= 1.1D;
            }

            velocityX *= 0.8600000143051147D;
            velocityY *= 0.8600000143051147D;
            velocityZ *= 0.8600000143051147D;
            if (onGround) {
                velocityX *= 0.699999988079071D;
                velocityZ *= 0.699999988079071D;
            }

        }
    }
    @Environment(EnvType.CLIENT)
    public static class InsaneFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public InsaneFactory(SpriteProvider provider) {
            spriteProvider = provider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            InsaneParticle particle = new InsaneParticle(clientWorld, d, e + 0.5D, f);
            particle.setSprite(spriteProvider);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}
