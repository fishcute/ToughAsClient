
package fishcute.toughasclient.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class CleanParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	protected CleanParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider spriteProvider) {
		super(clientWorld, x, y, z, vx, vy, vz);
		this.spriteProvider = spriteProvider;
		this.setBoundingBoxSpacing((float) 0.2, (float) 0.2);
		this.scale *= (float) 1;
		this.maxAge = 11;
		this.gravityStrength = (float) -0.04;
		this.collidesWithWorld = true;
		this.velocityX = vx * 1;
		this.velocityY = vy * 1;
		this.velocityZ = vz * 1;
		this.setSpriteForAge(spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.dead) {
			this.setSprite(this.spriteProvider.getSprite((this.age / 3) % 4 + 1, 4));
		}
	}
	@Environment(EnvType.CLIENT)
	public static class CustomParticleFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		public CustomParticleFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
				double zSpeed) {
			return new CleanParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteProvider);
		}
	}
}
