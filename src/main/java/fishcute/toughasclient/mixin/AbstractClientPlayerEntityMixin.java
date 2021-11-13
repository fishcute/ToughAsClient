package fishcute.toughasclient.mixin;

import fishcute.toughasclient.ToughAsClientMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    private void capeTexture(CallbackInfoReturnable<Identifier> info) {
        AbstractClientPlayerEntity a = (((AbstractClientPlayerEntity)(Object)this));
        if (ToughAsClientMod.isImportant(a.getName().getString()))
            info.setReturnValue(new Identifier("tough_as_client:textures/misc/supporter_cape.png"));
    }
}
