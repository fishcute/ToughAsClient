
package fishcute.toughasclient.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class HypothermiaParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	protected HypothermiaParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider provider) {
		super(clientWorld, x, y, z, vx, vy, vz);
		spriteProvider = provider;
		setBoundingBoxSpacing((float) 0.1, (float) 0.1);
		scale *= (float) 0.5;
		maxAge = 36;
		gravityStrength = (float) 0.2;
		collidesWithWorld = true;
		velocityX = vx * 1;
		velocityY = vy * 1;
		velocityZ = vz * 1;
		setSprite(spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
	}
	@Environment(EnvType.CLIENT)
	public static class CustomParticleFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		public CustomParticleFactory(SpriteProvider provider) {
			spriteProvider = provider;
		}

		public Particle createParticle(DefaultParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
				double zSpeed) {
			return new HypothermiaParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider);
		}
	}
}
