package fishcute.toughasclient.mixin;

import fishcute.toughasclient.data.SaveEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin {
    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V", at = @At("RETURN"))
    public void connect(ServerInfo entry, CallbackInfo info) {
        SaveEvents.serverName = convertIp(entry.address);
    }
    String convertIp(String i) {
        return i.replace(':', '#');
    }
}
