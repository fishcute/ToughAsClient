
package fishcute.toughasclient.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.status_effect.IClientStatusEffect;
import fishcute.toughasclient.status_message.StatusMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class StatusOverlay {
    static int y = 1;
    public static final Identifier BACKGROUND_TEXTURE = new Identifier("tough_as_client:textures/misc/hud.png");
    public static void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (true) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();

            //stuff here

            Window w = client.getWindow();

            renderStatusEffect(matrices, client.getWindow());

            int midX = w.getScaledWidth() / 2;
            int bottom = w.getScaledHeight();

            renderStatusMessageBackground(matrices, midX + 75, bottom - 66);
            renderStatusMessages(matrices, midX + 75, bottom - 66);

            //renderNauseaOverlay(1);

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
    private static void renderStatusMessages(MatrixStack matrices, int i, int j) {
        int a = 0;
        for(StatusMessage var6 : StatusMessage.msg.values()) {
            int color = var6.colorFade();
            if (color!=-99999)
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, var6.message(), (float)(i + 10 + 18), (float)(j + 6 + 10) + a, var6.colorFade());
            a = a - 18;
        }
    }
    private static void renderStatusMessageBackground(MatrixStack matrices, int x, int y) {
        int a = 0;
        for(StatusMessage var6 : StatusMessage.msg.values()) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
            Matrix4f matrix4f = matrices.peek().getModel();
            int i = MinecraftClient.getInstance().textRenderer.getWidth(var6.message());
            int k = x + 28;
            int l = y + 16 + a;
            int n = 8;

            int alpha = 0;
            if (var6.getTick()<=0)
                alpha = (((var6.getEndTick())*255/(var6.getActualFadeTick())) << 24);
            else
                alpha = (((var6.overallEndTick)*255/(var6.overallEndTick())) << 24);

            int c = var6.color() + alpha + 10;

            int o = ((var6.color() & 0xfefefe) >> 1) + alpha + 10;
            int p = c + 25;
            int q = c;


                fillGradient(matrix4f, bufferBuilder, k - 3, l - 4, k + i + 3, l - 3, 400, o, o);
                fillGradient(matrix4f, bufferBuilder, k - 3, l + n + 3, k + i + 3, l + n + 4, 400, o, o);
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l + n + 3, 400, o, o);
                fillGradient(matrix4f, bufferBuilder, k - 4, l - 3, k - 3, l + n + 3, 400, o, o);
                fillGradient(matrix4f, bufferBuilder, k + i + 3, l - 3, k + i + 4, l + n + 3, 400, o, o);
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 3 + 1, k - 3 + 1, l + n + 3 - 1, 400, p, q);
                fillGradient(matrix4f, bufferBuilder, k + i + 2, l - 3 + 1, k + i + 3, l + n + 3 - 1, 400, p, q);
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l - 3 + 1, 400, p, p);
                fillGradient(matrix4f, bufferBuilder, k - 3, l + n + 2, k + i + 3, l + n + 3, 400, q, q);
            RenderSystem.enableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.shadeModel(7425);
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            RenderSystem.shadeModel(7424);
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            a = a - 18;
        }
    }
    private static void fillGradient(Matrix4f matrix, BufferBuilder bufferBuilder, int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd) {
        float f = (float)(colorStart >> 24 & 255) / 255.0F;
        float g = (float)(colorStart >> 16 & 255) / 255.0F;
        float h = (float)(colorStart >> 8 & 255) / 255.0F;
        float i = (float)(colorStart & 255) / 255.0F;
        float j = (float)(colorEnd >> 24 & 255) / 255.0F;
        float k = (float)(colorEnd >> 16 & 255) / 255.0F;
        float l = (float)(colorEnd >> 8 & 255) / 255.0F;
        float m = (float)(colorEnd & 255) / 255.0F;
        bufferBuilder.vertex(matrix, (float)endX, (float)startY, (float)z).color(g, h, i, f).next();
        bufferBuilder.vertex(matrix, (float)startX, (float)startY, (float)z).color(g, h, i, f).next();
        bufferBuilder.vertex(matrix, (float)startX, (float)endY, (float)z).color(k, l, m, j).next();
        bufferBuilder.vertex(matrix, (float)endX, (float)endY, (float)z).color(k, l, m, j).next();
    }
    private static void renderStatusEffect(MatrixStack matrices, Window w) {
        int i = 1;
        ArrayList<IClientStatusEffect> collection = DataManager.effects;
        if (!collection.isEmpty()) {
            for (IClientStatusEffect e : collection) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                int j = 1;
                drawStatusEffectBackgrounds(matrices, i, j, collection);
                drawStatusEffectSprites(matrices, i, j, collection);
                drawStatusEffectDescriptions(matrices, i, j, collection);
            }
        }
    }
    private static void drawStatusEffectBackgrounds(MatrixStack matrices, int i, int j, ArrayList<IClientStatusEffect> iterable) {
        int k = y;
        int a = 0;

        for (IClientStatusEffect var6 : iterable) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            MinecraftClient.getInstance().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
            MinecraftClient.getInstance().inGameHud.drawTexture(matrices, i, k + a, 0, 43, 120, 31);
            a = a + 32;
        }
    }

    private static void renderIcon(Identifier e, int x, int z) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(e);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D + x, 18.0D + z, -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(18.0D + x, 18.0D + z, -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(18.0D + x, 0.0D + z, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D + x, 0.0D + z, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();
    }

    private static void drawStatusEffectSprites(MatrixStack matrices, int i, int j, ArrayList<IClientStatusEffect> iterable) {
        int k = y;

        int a = 0;

        for(IClientStatusEffect var7 : iterable) {
            renderIcon(var7.getIcon(), i + 6, (k + 6)+a);
            a = a + 32;
        }
    }

    private static void drawStatusEffectDescriptions(MatrixStack matrices, int i, int j, ArrayList<IClientStatusEffect> iterable) {
        int k = y;

        int a = 0;
        for (IClientStatusEffect var6 : iterable) {

            String string = I18n.translate(var6.getName(), new Object[0]);
            if (var6.getAmplifier() >= 1 && var6.getAmplifier() <= 9) {
                string = string + ' ' + I18n.translate("enchantment.level." + (var6.getAmplifier() + 1), new Object[0]);
            }

            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, string, (float) (i + 10 + 18), (float) (k + 6) + a, 16777215);
            String string2 = var6.formattedTicksRemaining();
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, string2, (float) (i + 10 + 18), (float) (k + 6 + 10) + a, 8355711);
            a = a + 32;
        }

    }
}
