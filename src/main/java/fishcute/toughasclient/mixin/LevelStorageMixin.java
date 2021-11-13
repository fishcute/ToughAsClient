package fishcute.toughasclient.mixin;

import fishcute.toughasclient.data.SaveEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LevelStorage.class)
public class LevelStorageMixin {
    @Inject(method = "createSession", at = @At("RETURN"))
    public void session(CallbackInfoReturnable<LevelStorage.Session> info) {
        SaveEvents.worldName = info.getReturnValue().getDirectoryName();
    }
}
