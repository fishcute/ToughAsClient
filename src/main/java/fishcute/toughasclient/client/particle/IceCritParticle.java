
package fishcute.toughasclient.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class IceCritParticle extends SpriteBillboardParticle {
	protected IceCritParticle(ClientWorld clientWorld, double x, double y, double z, double d, double e, double f, SpriteProvider spriteProvider) {
		super(clientWorld, x, y, z, 0.0D, 0.0D, 0.0D);
		this.velocityX *= 0.10000000149011612D;
		this.velocityY *= 0.10000000149011612D;
		this.velocityZ *= 0.10000000149011612D;
		this.velocityX += d * 0.4D;
		this.velocityY += e * 0.4D;
		this.velocityZ += f * 0.4D;
		this.scale *= 0.75F;
		this.maxAge = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
		this.collidesWithWorld = false;
		this.tick();
	}

	public float getSize(float tickDelta) {
		return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge) {
			this.markDead();
		} else {
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			this.colorBlue = (float)((double)this.colorBlue - 0.01D);
			this.colorGreen = (float)((double)this.colorGreen - 0.01D);
			this.colorRed = (float)((double)this.colorRed - 0.01D);
			this.velocityX *= 0.699999988079071D;
			this.velocityY *= 0.699999988079071D;
			this.velocityZ *= 0.699999988079071D;
			this.velocityY -= 0.019999999552965164D;
			if (this.onGround) {
				this.velocityX *= 0.699999988079071D;
				this.velocityZ *= 0.699999988079071D;
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

		public DefaultFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			IceCritParticle critParticle = new IceCritParticle(clientWorld, d, e, f, g, h + 1.0D, i, this.spriteProvider);
			critParticle.setMaxAge(20);
			critParticle.setSprite(this.spriteProvider);
			return critParticle;
		}
	}
}
