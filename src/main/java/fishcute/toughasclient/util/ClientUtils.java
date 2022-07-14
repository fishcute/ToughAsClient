package fishcute.toughasclient.util;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.armor.ClearsightSpectacles;
import fishcute.toughasclient.armor.ClientArmorRegistry;
import fishcute.toughasclient.fluid.CleanedWaterManager;
import fishcute.toughasclient.fluid.ClientFluidManager;
import fishcute.toughasclient.fluid.ClientFluids;
import fishcute.toughasclient.items.*;
import fishcute.toughasclient.status_effect.Drenched;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import fishcute.toughasclient.status_message.StatusMessage;
import fishcute.toughasclient.status_message.StatusMessageManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Mouse;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ClientUtils {
	static int interactWaitTick = 0;
	static boolean warned = false;
	static boolean wasSleeping = false;
	public static void tick() {
		if (client().isPaused())
			return;

		updateAll();
		correctAll();

		if (!warned) {
			onStart();
			warned = true;
		}
		ClientItemRegistry.tickCustomItems();
		ClientArmorRegistry.tickCustomArmor();
		if (CleanedWaterManager.isFiltering())
			CleanedWaterManager.filterTick();
		ClientFluidManager.tick();

		if (client().player.isDead())
			resetAll();
		sleepTick();
	}

	static void sleepTick() {
		if (sleeping())
			wasSleeping = true;
		else if (wasSleeping) {
			wasSleeping = false;
			sleepStats();
		}
	}

	static void sleepStats() {
		DataManager.stamina = 82;
		DataManager.thirst -= 30;
		DataManager.sanity += 30;
		StatusEffectManager.clear();
	}

	static boolean sleeping() {
		return e().getPose().equals(EntityPose.SLEEPING);
	}

	static void resetAll() {
		DataManager.thirst = 82;
		DataManager.stamina = 82;
		DataManager.temperature = 20;
		DataManager.immediateTemperature = 40;
		DataManager.sanity = 82;
		StatusEffectManager.clear();
	}
	static void onStart() {
		//Might be used in the future
	}
	static void updateAll() {
		if (interactWaitTick>0)
			interactWaitTick--;
		if (ToughAsClientMod.CONFIG.stamina) Stamina.stamina();
		interaction();
		if (ToughAsClientMod.CONFIG.temperature) Temperature.updateTemperature();
		if (ToughAsClientMod.CONFIG.thirst) Water.updateWater();
		if (ToughAsClientMod.CONFIG.sanity&&Sanity.shouldShow()) Sanity.updateSanity();
		StatusEffectManager.tickStatusEffects();
		StatusMessage.tickAll();
		StatusMessageManager.sendMessages();
		drenchUpdate();
	}

	static void correctAll() {
		//stamina
		if (DataManager.stamina>82)
			DataManager.stamina = 82;
		if (DataManager.stamina<0)
			DataManager.stamina = 0;
		//thirst
		if (DataManager.thirst>82)
			DataManager.thirst = 82;
		if (DataManager.thirst<0)
			DataManager.thirst = 0;
		//temperature
		if (DataManager.temperature>82)
			DataManager.temperature = 82;
		if (DataManager.temperature<0)
			DataManager.temperature = 0;

		//immediate temperature
        if (DataManager.immediateTemperature>82)
            DataManager.immediateTemperature = 82;
        if (DataManager.immediateTemperature<0)
            DataManager.immediateTemperature = 0;
	}

	/*
	        int stamina = DataManager.stamina;
        MinecraftClient inst = MinecraftClient.getInstance();
        ClientPlayerEntity e = inst.player;
        Vec3d loc = e.getPos();
        if (stamina<=26) {
            inst.player.setSprinting(false);
            if (Math.random() > 0.9)
                ClientUtils.particleGroup(ParticleTypes.FALLING_WATER, loc.x, loc.y + 0.7, loc.z, 0.2, 0.6, 0.2, 1);
        }
        if (stamina<=11) {
            inst.interactionManager.cancelBlockBreaking();
            inst.options.keyJump.setPressed(false);
        }
	 */

	public static float overlayStatus() {
		int temp = DataManager.temperature;
		float percent = 0;
		int a = temp-60;

		if (temp>=60)
			percent = (a / 22f);
		else if (temp<=22)
			percent = ((temp / 22f) - 1);

		return percent;
	}

	static int getTime() {
		int a = 0;
		if (world().getTimeOfDay() > 24000)
			a = (int) Math.floor(world().getTimeOfDay() / 24000f);
		return (int) (world().getTimeOfDay() - (a * 24000));
	}

	static int getTimeProgress() {
		int a = 0;
		if (world().getTimeOfDay() > 12000)
			a = (int) Math.floor(world().getTimeOfDay() / 12000f);
		return (int) (world().getTimeOfDay() - (a * 12000));
	}

	static boolean progressingIntoDay() {
		return getTime() <= 12000;
	}

	static boolean isBlockNearby(Block block, BlockPos loc, int distance, int height) {
		for (int i = -distance; i <= distance; i++)
			for (int j = -distance; j <= distance; j++)
				for (int k = -height; k <= height; k++) {
					if (world().getBlockState(loc.add(i, k, j)).getBlock().equals(block))
						return true;
				}
		return false;
	}

	static double getDistanceToBlockNearby(ArrayList<Block> blocks, BlockPos loc, int distance, int height) {
		for (int i = -distance; i <= distance; i++)
			for (int j = -distance; j <= distance; j++)
				for (int k = -height; k <= height; k++) {
					BlockPos pos = loc.add(i, k, j);
					for (Block b : blocks)
						if (world().getBlockState(pos).getBlock().equals(b))
							return pos.getManhattanDistance(loc);
				}
		return -1;
	}

	public static boolean inventoryContains(Item item) {
		return client().player.inventory.contains(item.getDefaultStack());
	}

	public static ItemStack getItemInMainHand() {
		return client().player.getMainHandStack();
	}

	public static ItemStack getItemInOffHand() {
		return client().player.getOffHandStack();
	}

	static void drenchUpdate() {
		if (e().isTouchingWater()||(isBeingRainedOn()&&!UmbrellaItems.umbrellaInHand(Hand.MAIN_HAND)&&!UmbrellaItems.umbrellaInHand(Hand.OFF_HAND)))
			StatusEffectManager.addStatusEffect(new Drenched(Utils.minutesToTicks(1), 0));
	}

	static boolean isBeingRainedOn() {
		BlockPos blockPos = e().getBlockPos();
		return e().world.hasRain(blockPos) || e().world.hasRain(new BlockPos(blockPos.getX(), e().getBoundingBox().maxY, blockPos.getZ()));
	}

	public static void interaction() {
		Mouse m = client().mouse;
		Vec3d vec = client().crosshairTarget.getPos();
		BlockPos pos = Utils.toBlockPos(vec);
		BlockState b = world().getBlockState(new BlockPos(vec.x, vec.y, vec.z));
		if (m.wasRightButtonClicked()&&interactWaitTick<=0) {
			if (b.getBlock().getTranslationKey().equals("block.minecraft.water")&&e().isSneaking()
					&&client().player.getStackInHand(Hand.MAIN_HAND).getItem() == Items.AIR
					&&client().player.getStackInHand(Hand.OFF_HAND).getItem() == Items.AIR) {
				swingArm(Hand.MAIN_HAND);
				interactWaitTick = 10;
				Water.drink(vec.x, Math.round(vec.y+0.5), vec.z);
			}
			if (b.getBlock().getTranslationKey().equals("block.minecraft.water")&& ClientItemRegistry.equals(client().player.inventory.getMainHandStack(), new UVSterilizer())) {
				if (ClientFluidManager.canBePlacedAt(pos, client().world)) {
					if (!CleanedWaterManager.isFiltering())
						if (!ClientFluidManager.instanceOfFluidAt(pos, ClientFluids.CLEANED_WATER))
							CleanedWaterManager.prepare(pos);
						else new StatusMessage(Utils.translate("tough_as_client.message.uv_sterilizer.water_cleaned"), 100, 100, 16577539).play();
				}
				else new StatusMessage(Utils.translate("tough_as_client.message.uv_sterilizer.water_too_large"), 100, 100, 16577539).play();
				interactWaitTick = 20;
			}
			if (b.getBlock().getTranslationKey().equals("block.minecraft.water")&& ClientItemRegistry.equals(client().player.inventory.getMainHandStack(), new WaterTestingKit())) {
				swingArm(Hand.MAIN_HAND);
				Water.WaterType type = Water.waterType(pos);
				ClientUtils.playSound(SoundEvents.ENTITY_PLAYER_SPLASH, pos, SoundCategory.MASTER, 1, 1);
				ClientUtils.playSound(SoundEvents.ENTITY_HORSE_ARMOR, SoundCategory.MASTER, 1, 0);

				if (ClientFluidManager.instanceOfFluidAt(pos, ClientFluids.CLEANED_WATER)) {
					new StatusMessage(Utils.translate("tough_as_client.message.water_testing_kit.sanitary"), 300, 100, 49151).play();
					((WaterTestingKit) ClientItemRegistry.getItem("Water Testing Kit")).result = Water.WaterType.SANITARY;
					interactWaitTick = 20;
					return;
				}
				((WaterTestingKit) ClientItemRegistry.getItem("Water Testing Kit")).result = type;
				switch (type) {
					case NORMAL: new StatusMessage(Utils.translate("tough_as_client.message.water_testing_kit.normal"), 300, 100, 49151).play();
						break;
					case OCEAN: new StatusMessage(Utils.translate("tough_as_client.message.water_testing_kit.ocean"), 300, 100, 35434).play();
						break;
					case LAKE:
					case SWAMP: new StatusMessage(Utils.translate("tough_as_client.message.water_testing_kit.swamp"), 300, 100, 35434).play();
						break;
				}
				interactWaitTick = 20;
			}
			if (FanItems.isFanItem(client().player.getStackInHand(Hand.MAIN_HAND))) {
				ClientUtils.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.MASTER, 1, 0);
				FanItems.useFan(Hand.MAIN_HAND);
				interactWaitTick = 20;
			}
			else if (FanItems.isFanItem(client().player.getStackInHand(Hand.OFF_HAND))) {
				ClientUtils.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.MASTER, 1, 0);
				FanItems.useFan(Hand.OFF_HAND);
				interactWaitTick = 20;
			}
			fixMouse(m);
		}
	}

	public static void playSound(SoundEvent s, BlockPos b, SoundCategory c, float volume, float pitch) {
		world().playSound(b, s, c, volume, pitch, true);
	}
	public static void playSound(SoundEvent s, SoundCategory c, float volume, float pitch) {
		world().playSound(e().getBlockPos(), s, c, volume, pitch, true);
	}

	static void fixMouse(Mouse m) {
		try {
			Field field = Mouse.class.getDeclaredField("rightButtonClicked");
			field.setAccessible(true); // Force to access the field
			field.set(m, false);
		}
		catch (Exception ignored) {}
	}

	public static void swingArm(Hand h) {
		client().player.swingHand(h);
	}

	public static MinecraftClient client() {
		return MinecraftClient.getInstance();
	}

	public static Entity e() {
		return client().player;
	}

	public static Vec3d loc() {
		return e().getPos();
	}

	public static ClientWorld world() {
		return client().world;
	}

	public static void particle(DefaultParticleType e, double x, double y, double z) {
		world().addParticle(e, x, y, z, 0, 0, 0);
	}

	public static void particleGroup(DefaultParticleType e, double x, double y, double z, double xl, double yl, double zl, int amount) {
		for(int i = 0; i < amount; ++i) {
			particle(e, x+number(xl), y+number(yl), z+number(zl));
		}
	}

	public static void particleInFrontGroup(DefaultParticleType e, double xl, double yl, double zl, int amount, double distance) {
		Vec3d vec = Utils.posInFront(distance);
		ClientUtils.particleGroup(e, vec.x, vec.y, vec.z, xl, yl, zl, amount);
	}

	static double number(double a) {
		return (((Math.random()*a)*2)-(a));
	}
}
