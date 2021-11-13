package fishcute.toughasclient.mixin;

import fishcute.toughasclient.status_effect.ClientStatusEffects;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.status_effect.Refreshed;
import fishcute.toughasclient.status_effect.StatusEffectManager;
import fishcute.toughasclient.util.Water;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    public void eat(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> callbackInfo) {
        if (stack.isFood())
            DataManager.thirst+=thirstValue(stack.getTranslationKey());
        if (stack.getTranslationKey().contains("potion")) {
            DataManager.thirst=82;
            StatusEffectManager.addStatusEffect(new Refreshed(Utils.secondsToTicks(61), 0));
        }
    }
    @Inject(method = "use", at = @At("HEAD"))
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> callbackInfo) {
        if (((Item)(Object)this).isFood()&&StatusEffectManager.contains(ClientStatusEffects.THIRST)&&thirstValue(((Item)(Object)this).getTranslationKey())<=5) {
            ClientUtils.client().interactionManager.stopUsingItem(user);
            Water.sendThirstMessage();
        }
    }
    private int thirstValue(String j) {
        if (j.contains("melon_slice")) return 5;
        else if (j.contains("stew")||j.contains("soup")) return 50;
        else if (j.contains("honey_bottle")||j.contains("sweet_berries")||j.contains("apple")) return 2;
        else if (j.contains("dried_kelp")||j.contains("golden_carrot")) return -2;
        else if (j.contains("rotten_flesh")||j.contains("spider_eye")
                ||j.contains("poisonous_potato")) return -5;
        return 0;
    }
}

