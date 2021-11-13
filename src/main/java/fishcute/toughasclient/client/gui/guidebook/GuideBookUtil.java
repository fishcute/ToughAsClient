package fishcute.toughasclient.client.gui.guidebook;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GuideBookUtil {
    //This is work in progress, probably going to get removed because the wiki exists
    //tough_as_client\textures\gui
    //String title, Identifier background, int widthHeight, int backgroundWidth, int backgroundHeight
    public static BookContents defaultContents() {
        return new BookContents("Tough As Client Guidebook", new Identifier("tough_as_client:textures/gui"), 256, 256);
    }
    public static void openGui() {
    }
}
