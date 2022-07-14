
package fishcute.toughasclient.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class BreathParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	protected BreathParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider provider) {
		super(clientWorld, x, y, z, vx, vy, vz);
		spriteProvider = provider;
		setBoundingBoxSpacing((float) 0.1, (float) 0.1);
		scale *= (float) 0.6;
		maxAge = 15;
		gravityStrength = (float) -0.04;
		collidesWithWorld = true;
		velocityX = vx * 1;
		velocityY = vy * 1;
		velocityZ = vz * 1;
		setSpriteForAge(spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		if (!dead) {
			//getSprite((age / #Duration per frame) % #Frames + 1, #Frames))
			setSprite(spriteProvider.getSprite((age / 2) % 8 + 1, 8));
		}
	}
	@Environment(EnvType.CLIENT)
	public static class CustomParticleFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		public CustomParticleFactory(SpriteProvider provider) {
			spriteProvider = provider;
		}

		public Particle createParticle(DefaultParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
				double zSpeed) {
			return new BreathParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider);
		}
	}
}
