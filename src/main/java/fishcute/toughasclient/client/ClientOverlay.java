
package fishcute.toughasclient.client;

import fishcute.toughasclient.ToughAsClientMod;
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
		//Water bar

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 184, bottom - (10*count) - 4, 84, 13, 82, 5);
		// actual bar
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 184, bottom - (10*count) - 4, 84, 19, DataManager.sanity, 5);

		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 196, bottom - (10*count) - 5, 34, 33, 8, 6); //1
	}
	private static void renderWeight(MatrixStack matrices, int midX, int bottom, int count) {
		//Water bar

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 183, bottom - (10*count) - 4, 84, 1, 82, 5);
		// actual bar
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX - 183, bottom - (10*count) - 4, 84, 7, DataManager.sanity, 5);

	}
	private static void renderWater(MatrixStack matrices, int midX, int bottom, int count) {
		//Water bar
		if (StatusEffectManager.contains(ClientStatusEffects.DYSENTERY)) {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 1, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 7, DataManager.thirst, 5);
		}
		else if (StatusEffectManager.contains(ClientStatusEffects.HYPERNATREMIA)) {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 13, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 167, 19, DataManager.thirst, 5);
		}
		else {
			// background
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 1, 82, 5);
			// actual bar
			MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 7, DataManager.thirst, 5);
		}

		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 3, 8, 32, 6, 9); //15
	}
	private static void renderStamina(MatrixStack matrices, int midX, int bottom, int count) {
		//Stamina bar

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 13, 82, 5);
		// actual bar
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 19, DataManager.stamina, 5);

		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 184, bottom - (10*count) - 3, 15, 32, 9, 9); //15
	}
	private static void renderTemperature(MatrixStack matrices, int midX, int bottom, int count) {
		//Temperature bar

		// background
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 100, bottom - (10*count), 1, 25, 82, 5);
		// indicator
		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 97 + DataManager.temperature, bottom - (10*count) - 3, 1, 31, 5, 11); //+3

		MinecraftClient.getInstance().inGameHud.drawTexture(matrices, midX + 186, bottom - (10*count) - 3, 27, 32, 5, 9);
	}
}
