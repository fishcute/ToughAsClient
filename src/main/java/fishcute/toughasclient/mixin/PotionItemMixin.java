package fishcute.toughasclient.mixin;

import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.status_effect.Refreshed;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PotionItem.class)
public class PotionItemMixin {
    @Inject(method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    public void drink(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> callbackInfo) {
        DataManager.thirst=82;
        StatusEffectManager.addStatusEffect(new Refreshed(Utils.secondsToTicks(61), 0));
    }
}