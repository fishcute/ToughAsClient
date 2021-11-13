package fishcute.toughasclient.mixin;

import fishcute.toughasclient.util.Tick;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerMixin {
    @Inject(method = "reload", at = @At("RETURN"))
    private void reload(CallbackInfoReturnable<ResourceReload> i) {
        Tick.reload = i.getReturnValue();
    }
}
