
package fishcute.toughasclient.client.particle;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.Particle;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.particle.ParticleTypes;

@Environment(EnvType.CLIENT)
public class ComfortParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	protected ComfortParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider spriteProvider) {
		super(clientWorld, x, y, z, vx, vy, vz);
		this.spriteProvider = spriteProvider;
		this.setBoundingBoxSpacing((float) 0.2, (float) 0.2);
		this.scale *= (float) 1;
		this.maxAge = 24;
		this.gravityStrength = (float) -.05;
		this.collidesWithWorld = false;
		this.velocityX = vx * 1;
		this.velocityY = vy * 1;
		this.velocityZ = vz * 1;
		this.setSprite(spriteProvider);
	}

	@Override
	public int getBrightness(float tint) {
		return 15728880;
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_LIT;
	}

	@Override
	public void tick() {
		super.tick();
	}
	@Environment(EnvType.CLIENT)
	public static class CustomParticleFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		public CustomParticleFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
				double zSpeed) {
			return new ComfortParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteProvider);
		}
	}
}
