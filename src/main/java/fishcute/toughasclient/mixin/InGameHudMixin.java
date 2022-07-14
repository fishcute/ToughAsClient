package fishcute.toughasclient.mixin;

import fishcute.toughasclient.armor.ClearsightSpectacles;
import fishcute.toughasclient.status_effect.ClientStatusEffects;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import fishcute.toughasclient.util.ClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderStatusBars", at = @At("HEAD"))
    private void injectMethod(MatrixStack matrices, CallbackInfo info) {
        if (StatusEffectManager.getStatusEffectAmplifier(ClientStatusEffects.INSANITY) > 1 && !ClearsightSpectacles.hasAffect)
        ClientUtils.client().getTextureManager().bindTexture(new Identifier("tough_as_client:textures/misc/insane_icons.png"));
    }
}
