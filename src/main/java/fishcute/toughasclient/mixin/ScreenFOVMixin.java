package fishcute.toughasclient.mixin;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.FOVChange;
import fishcute.toughasclient.util.Stamina;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.GameRenderer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class ScreenFOVMixin {
    private double currentValue = 1;
    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void fovLevel(CallbackInfoReturnable<Double> callbackInfo) {
        float target = (float) (Stamina.destinedFOVValue + FOVChange.overallChange());
        if (!ClientUtils.client().isPaused())
            if (currentValue < target)
                currentValue = currentValue + 0.001;
            else if (currentValue > target)
                currentValue = currentValue - 0.001;
        double fov = callbackInfo.getReturnValue();
        callbackInfo.setReturnValue((fov * currentValue));
    }
}
