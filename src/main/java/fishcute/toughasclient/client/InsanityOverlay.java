
package fishcute.toughasclient.client;

import fishcute.toughasclient.util.ClientUtils;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.Window;
import net.minecraft.client.MinecraftClient;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class InsanityOverlay {
    static int confusionTime = -1;
    static int goalAlpha = 0;
    static int currentAlpha = 0;
    static int maxTime = 0;
    static float watcherAnimation = 0;
    static boolean animationDirection = false;
    public static void render(MatrixStack matrices, float tickDelta) {
        if (active()) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();

            boolean ending = confusionTime>maxTime;

            if (!ClientUtils.client().isPaused()) {
                confusionTime++;
                if (Math.random()>0.95&&(confusionTime%5)==0)
                    ClientUtils.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, SoundCategory.MASTER, 1F, 1);

                if (currentAlpha<goalAlpha)
                    currentAlpha+=Math.random()*3;
                else if (currentAlpha>goalAlpha)
                    currentAlpha-=Math.random()*3;

                if (!ending&&(goalAlpha==0||Math.random()>0.9))
                    goalAlpha = (int) (Math.random()*100);
                else if (ending&&!ClientUtils.client().isPaused())
                    goalAlpha = 0;

                if (animationDirection)
                    watcherAnimation+=step();
                else
                    watcherAnimation-=step();
            }
            //renderOverlayDistortion();

            float alpha = currentAlpha/10;

            renderDistortionOverlay(alpha/10);
            /*
            5 or -5

            */

            if (watcherAnimation<=-5)
                animationDirection = true;
            else if (watcherAnimation>=5)
                animationDirection = false;

            renderWatcher(alpha/10);

            if (ending&&currentAlpha<=0)
                end();


            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
    static float step() {
        //Professional coding at its best
        float current = Math.abs(watcherAnimation);
        int i = Math.round(current);
        if (i>5)
            i=5;
        switch (i) {
            case 0: return 0.01F;
            case 1: return 0.008F;
            case 2: return 0.006F;
            case 3: return 0.004F;
            case 4: return 0.002F;
            case 5: return 0.0008F;
            default: return 0;
        }
    }
    private static void renderDistortionOverlay(float alpha) {
        Window w = MinecraftClient.getInstance().getWindow();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
        MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/overlay/insane_effect.png"));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D, w.getScaledHeight(), -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(w.getScaledWidth(), w.getScaledHeight(), -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(w.getScaledWidth(), 0.0D, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();
    }
    static void renderWatcher(float alpha) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
        MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/overlay/eyes.png"));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        double size = 240;
        int x = 200;
        int z = 130;
        GL11.glPushMatrix();
        float rotation = watcherAnimation;
        GL11.glRotatef(rotation, 0.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glRotatef(rotation, 0.0F, 1.0F, 1.0F);
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D + x, size + z, -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(size + x, size + z, -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(size + x, 0.0D + z, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D + x, 0.0D + z, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();
        GL11.glPopMatrix();
    }
    public static void initiate() {
        confusionTime = 0;
        currentAlpha = 0;
        maxTime = (int) (100 + Math.random()*400);
    }
    static void end() {
        confusionTime = -1;
        currentAlpha = 0;
        maxTime = 0;
    }
    public static boolean active() {
        return maxTime!=0&&confusionTime!=-1;
    }
}
