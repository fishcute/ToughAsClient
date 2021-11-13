/*
*    MCreator note:
*
*    If you lock base mod element files, you can edit this file and the proxy files
*    and they won't get overwritten. If you change your mod package or modid, you
*    need to apply these changes to this file MANUALLY.
*
*
*    If you do not lock base mod element files in Workspace settings, this file
*    will be REGENERATED on each build.
*
*/
package fishcute.toughasclient;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.client.InsanityOverlay;
import fishcute.toughasclient.client.StatusOverlay;
import fishcute.toughasclient.client.TemperatureOverlay;
import fishcute.toughasclient.client.particle.*;
import fishcute.toughasclient.util.Tick;
import net.minecraft.util.registry.Registry;
import net.minecraft.particle.DefaultParticleType;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ClientModInitializer;

import fishcute.toughasclient.client.ClientOverlay;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
	public static final DefaultParticleType COMFORT_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:comfort",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType HYPOTHERMIA_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:hypothermia",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType ICE_CRIT_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:ice_crit",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType INSANE_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:insane",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType CLEAN_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:clean",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType BREATH_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:breath",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType SICK_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "tough_as_client:sick",
			FabricParticleTypes.simple(false));
	public static final DefaultParticleType NONE = null;
	@Override
	public void onInitializeClient() {
		ParticleFactoryRegistry.getInstance().register(COMFORT_PARTICLE, ComfortParticle.CustomParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(HYPOTHERMIA_PARTICLE, HypothermiaParticle.CustomParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(ICE_CRIT_PARTICLE, IceCritParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(INSANE_PARTICLE, InsaneParticle.InsaneFactory::new);
		ParticleFactoryRegistry.getInstance().register(CLEAN_PARTICLE, CleanParticle.CustomParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(BREATH_PARTICLE, BreathParticle.CustomParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(SICK_PARTICLE, SickParticle.CustomParticleFactory::new);
		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			ClientOverlay.render(matrices, tickDelta);
			TemperatureOverlay.render(matrices, tickDelta);
			StatusOverlay.render(matrices, tickDelta);
			InsanityOverlay.render(matrices, tickDelta);
		});

		ClientTickEvents.END_WORLD_TICK.register((client) -> ClientUtils.tick());
		ClientTickEvents.END_CLIENT_TICK.register((client) -> Tick.tick());

		fishcute.toughasclient.Registry.registerBaseItems();
	}
}
