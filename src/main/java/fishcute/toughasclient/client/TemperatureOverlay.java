
package fishcute.toughasclient.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Sanity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TemperatureOverlay {

	public static void render(MatrixStack matrices, float tickDelta) {
		ClientUtils.tick();
		if (true) {
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableAlphaTest();
			RenderSystem.enableBlend();
			float status = ClientUtils.overlayStatus();

			if (status<0)
				renderFreezeOverlay(-status);
			else if (status>0)
				renderHeatOverlay(status);

			float sanityStatus = Sanity.sanityOverlayStatus();
			if (sanityStatus<0)
				renderSanityOverlay(-sanityStatus);

			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	private static void renderHeatOverlay(float alpha) {
		Window w = MinecraftClient.getInstance().getWindow();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/overlay/heat_overlay.png"));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(0.0D, w.getScaledHeight(), -90.0D).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex(w.getScaledWidth(), w.getScaledHeight(), -90.0D).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(w.getScaledWidth(), 0.0D, -90.0D).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
		tessellator.draw();
	}
	private static void renderFreezeOverlay(float alpha) {
		Window w = MinecraftClient.getInstance().getWindow();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/overlay/freeze_overlay.png"));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(0.0D, w.getScaledHeight(), -90.0D).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex(w.getScaledWidth(), w.getScaledHeight(), -90.0D).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(w.getScaledWidth(), 0.0D, -90.0D).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
		tessellator.draw();
	}
	private static void renderSanityOverlay(float alpha) {
		Window w = MinecraftClient.getInstance().getWindow();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/overlay/insanity_overlay.png"));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(0.0D, w.getScaledHeight(), -90.0D).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex(w.getScaledWidth(), w.getScaledHeight(), -90.0D).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(w.getScaledWidth(), 0.0D, -90.0D).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
		tessellator.draw();
	}
	//tough_as_client:textures/overlay/freeze_overlay.png
}
