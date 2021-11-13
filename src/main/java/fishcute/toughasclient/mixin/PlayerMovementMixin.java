package fishcute.toughasclient.mixin;

import fishcute.toughasclient.client.InsanityOverlay;
import fishcute.toughasclient.status_effect.Insanity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.KeyboardInput;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(KeyboardInput.class)
public class PlayerMovementMixin {
    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingForward:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingForward(KeyboardInput input) {
        input.pressingForward = input.pressingForward||Insanity.forward;
        if (InsanityOverlay.active())
            input.pressingForward = false;
        return input.pressingForward;
    }

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingBack:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingBack(KeyboardInput input) {
        input.pressingBack = input.pressingBack||Insanity.backwards;
        if (InsanityOverlay.active())
            input.pressingBack = false;
        return input.pressingBack;
    }

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingLeft:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingLeft(KeyboardInput input) {
        input.pressingLeft = input.pressingLeft||Insanity.left;
        if (InsanityOverlay.active())
            input.pressingLeft = false;
        return input.pressingLeft;
    }

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingRight:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingRight(KeyboardInput input) {
        input.pressingRight = input.pressingRight||Insanity.right;
        if (InsanityOverlay.active())
            input.pressingRight = false;
        return input.pressingRight;
    }
}
