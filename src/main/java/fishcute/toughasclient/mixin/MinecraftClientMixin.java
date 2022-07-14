package fishcute.toughasclient.mixin;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.items.ClientItemRegistry;
import fishcute.toughasclient.status_effect.Insanity;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import fishcute.toughasclient.status_message.StatusMessage;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void onItemUse(CallbackInfo info) {
        boolean blockItem = ((ClientUtils.client().player.getStackInHand(Hand.MAIN_HAND)).getItem() instanceof BlockItem);
        if (ClientItemRegistry.isCustomItem(ClientUtils.client().player.getStackInHand(Hand.MAIN_HAND))||(ClientItemRegistry.isCustomItem(ClientUtils.client().player.getStackInHand(Hand.OFF_HAND))&&(!blockItem)))
            info.cancel();
        if (Utils.raycastFromPlayerGetBlock(false).getBlock().getTranslationKey().contains("_bed") && DataManager.sanity<40) {
            info.cancel();
            ClientUtils.swingArm(Hand.MAIN_HAND);
            ClientUtils.client().player.sendMessage(Text.of(Utils.translate("tough_as_client.sleep_failure.insane")), true);
        }
        if (StatusEffectManager.contains("Insanity")&&(ClientUtils.client().player.getStackInHand(Hand.MAIN_HAND)).getItem() != Items.AIR&&Insanity.holdingLightObject(Hand.MAIN_HAND)||(Insanity.holdingLightObject(Hand.OFF_HAND)&&(!blockItem))) {
            if (Insanity.noLightMessageTick<=0) {
                new StatusMessage(Utils.translate("tough_as_client.message.insane.no_light"), 400, 100, 10704282).play();
                Insanity.noLightMessageTick = 200;
            }
            info.cancel();
        }
    }
}
