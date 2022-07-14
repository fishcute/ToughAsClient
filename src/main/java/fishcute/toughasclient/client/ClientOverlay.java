
package fishcute.toughasclient.client;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.armor.ClearsightSpectacles;
import fishcute.toughasclient.armor.ClientArmorRegistry;
import fishcute.toughasclient.status_effect.ClientStatusEffects;
import fishcute.toughasclient.util.Sanity;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.Window;
import net.minecraft.client.MinecraftClient;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import fishcute.toughasclient.DataManager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

@Environment(EnvType.CLIENT)
public class ClientOverlay {
	static boolean isWearingGoggles = false;
	public static void render(MatrixStack matrices, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		boolean renderSanity;
		boolean renderWeight;
		if (true) {
			Window w = client.getWindow();
			int midX = w.getScaledWidth() / 2;
			int bottom = w.getScaledHeight();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
			RenderSystem.color3f(1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();

			isWearingGoggles = ClearsightSpectacles.hasAffect;

			if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !isWearingGoggles)
				MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/misc/insane_hud.png"));
			else
				MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/misc/hud.png"));

			int count = 0;
			if (ToughAsClientMod.CONFIG.temperature) {
				count++;
				renderTemperature(matrices, midX, bottom, count);
			}
			if (ToughAsClientMod.CONFIG.stamina) {
				count++;
				renderStamina(matrices, midX, bottom, count);
			}
			if (ToughAsClientMod.CONFIG.thirst) {
				count++;
				renderWater(matrices, midX, bottom, count);
			}

			renderSanity = ToughAsClientMod.CONFIG.sanity&&Sanity.shouldShow();
			renderWeight = false;

			int count2 = 0;
			if (renderSanity) {
				count2++;
				renderSanity(matrices, midX, bottom, count2);
			}
			if (renderWeight) {
				count2++;
				renderWeight(matrices, midX, bottom, count2);
			}

			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	private static void renderSanity(MatrixStack matrices, int midX, int bottom, int count) {
		//Sanity bar

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 184, bottom - (10*count) - 4, 84, 13, 82, 5);
		// actual bar
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 184, bottom - (10*count) - 4, 84, 19, DataManager.sanity, 5);

		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 196, bottom - (10*count) - 5, 34, 33, 8, 6); //1
	}
	private static void renderWeight(MatrixStack matrices, int midX, int bottom, int count) {
		//Weight bar

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 183, bottom - (10*count) - 4, 84, 1, 82, 5);
		// actual bar
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 183, bottom - (10*count) - 4, 84, 7, DataManager.sanity, 5);

	}
	private static void renderWater(MatrixStack matrices, int midX, int bottom, int count) {
		//Water bar
		int water = DataManager.thirst;
		if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !isWearingGoggles)
			water = DataManager.sanity;

		if (StatusEffectManager.contains(ClientStatusEffects.DYSENTERY)) {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 1, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 7, water, 5);
		}
		else if (StatusEffectManager.contains(ClientStatusEffects.HYPERNATREMIA)) {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 13, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 19, water, 5);
		}
		else {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 1, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 7, water, 5);
		}

		if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !isWearingGoggles)
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 1, 34, 33, 8, 6);//15
		else
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 3, 8, 32, 6, 9); //15
	}
	private static void renderStamina(MatrixStack matrices, int midX, int bottom, int count) {
		//Stamina bar
		int stamina = DataManager.stamina;
		if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !isWearingGoggles)
			stamina = DataManager.sanity;

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 13, 82, 5);
		// actual bar
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 19, stamina, 5);

		if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1  && !isWearingGoggles)
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 1, 34, 33, 8, 6); //15
		else
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 184, bottom - (10*count) - 3, 15, 32, 9, 9); //15
	}
	//TODO: Finish changing all the icons for insanity
	private static void renderTemperature(MatrixStack matrices, int midX, int bottom, int count) {
		//Temperature bar
		int temperature = DataManager.temperature;
		if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !isWearingGoggles) {
			temperature = DataManager.sanity;
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 13, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 19, temperature, 5);
		}
		else {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10 * count), 1, 25, 82, 5);
			// indicator
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 97 + temperature, bottom - (10 * count) - 3, 1, 31, 5, 11); //+3
		}
		if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !isWearingGoggles)
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 1, 34, 33, 8, 6);
		else
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 3, 27, 32, 5, 9); //15
	}
}
