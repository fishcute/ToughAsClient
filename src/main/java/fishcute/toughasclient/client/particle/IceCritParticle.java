
package fishcute.toughasclient.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class IceCritParticle extends SpriteBillboardParticle {
	protected IceCritParticle(ClientWorld clientWorld, double x, double y, double z, double d, double e, double f, SpriteProvider provider) {
		super(clientWorld, x, y, z, 0.0D, 0.0D, 0.0D);
		velocityX *= 0.10000000149011612D;
		velocityY *= 0.10000000149011612D;
		velocityZ *= 0.10000000149011612D;
		velocityX += d * 0.4D;
		velocityY += e * 0.4D;
		velocityZ += f * 0.4D;
		scale *= 0.75F;
		maxAge = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
		collidesWithWorld = false;
		tick();
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
			colorBlue = (float)((double)colorBlue - 0.01D);
			colorGreen = (float)((double)colorGreen - 0.01D);
			colorRed = (float)((double)colorRed - 0.01D);
			velocityX *= 0.699999988079071D;
			velocityY *= 0.699999988079071D;
			velocityZ *= 0.699999988079071D;
			velocityY -= 0.019999999552965164D;
			if (onGround) {
				velocityX *= 0.699999988079071D;
				velocityZ *= 0.699999988079071D;
			}

		}
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@Environment(EnvType.CLIENT)
	public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public DefaultFactory(SpriteProvider provider) {
			spriteProvider = provider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			IceCritParticle critParticle = new IceCritParticle(clientWorld, d, e, f, g, h + 1.0D, i, spriteProvider);
			critParticle.setMaxAge(20);
			critParticle.setSprite(spriteProvider);
			return critParticle;
		}
	}
}
