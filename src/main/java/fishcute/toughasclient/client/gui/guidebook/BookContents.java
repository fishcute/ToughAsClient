package fishcute.toughasclient.client.gui.guidebook;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.w3c.dom.Text;

import java.awt.*;
import java.awt.print.Book;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class BookContents {
    public BookContents(String title, Identifier background, int backgroundWidth, int backgroundHeight, ArrayList<ButtonWidget> buttonList,  ArrayList<TextElement> textList, ArrayList<PictureElement> imageList) {
        this.title = title;
        this.background = background;
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;
        this.buttonList = buttonList;
        this.textList = textList;
        this.imageList = imageList;
    }
    public BookContents(String title, Identifier background, int backgroundWidth, int backgroundHeight) {
        this.title = title;
        this.background = background;
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;
        this.buttonList = new ArrayList<>();
        this.textList = new ArrayList<>();
        this.imageList = new ArrayList<>();
    }
    public static void addPictureElement(BookContents i, PictureElement e) {
        i.imageList.add(e);
    }
    public static void addTextElement(BookContents i, TextElement e) {
        i.textList.add(e);
    }
    public static void addButtonElement(BookContents i, ButtonWidget e) {
        i.buttonList.add(e);
    }
    public String title;
    public Identifier background;
    public int backgroundWidth;
    public int backgroundHeight;
    public ArrayList<ButtonWidget> buttonList;
    public ArrayList<TextElement> textList;
    public ArrayList<PictureElement> imageList;
    public boolean drawButtonElements() {
        return buttonList.size()>0&&!buttonList.contains(null);
    }
    public boolean drawTextElements() {
        return textList.size()>0&&!textList.contains(null);
    }
    public boolean drawImageElements() {
        return imageList.size()>0&&!imageList.contains(null);
    }
    /*
    public void drawAllImages(GuideBookGui i, MatrixStack ms, int x, int y) {
        if (drawImageElements())
            for (PictureElement e : imageList)
                drawImage(e, i, ms, x, y);
    }
    public void drawImage(PictureElement e, GuideBookGui i, MatrixStack ms, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        MinecraftClient.getInstance().getTextureManager().bindTexture(e.image);
        i.drawTexture(ms, x + e.x, y + e.y, 0, 0, e.width, e.height, e.scaleWidth, e.scaleHeight);
        RenderSystem.disableBlend();

    }
    */
    class TextElement {
        public TextElement(String text, int x, int y, int color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
        }
        String text;
        int x;
        int y;
        int color;
    }
    class PictureElement {
        public PictureElement(Identifier image, int x, int y, int width, int height, int scaleHeight, int scaleWidth) {
            this.image = image;
            this.width = width;
            this.height = height;
            this.scaleHeight = scaleHeight;
            this.scaleWidth = scaleWidth;
            this.x = x;
            this.y = y;
        }
        Identifier image;
        int x;
        int y;
        int width;
        int height;
        int scaleWidth;
        int scaleHeight;
    }
}
