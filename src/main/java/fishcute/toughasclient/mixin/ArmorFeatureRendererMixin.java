package fishcute.toughasclient.mixin;

import fishcute.toughasclient.custom_armor.CustomArmorRegistry;
import fishcute.toughasclient.custom_armor.ICustomArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

//Credits to Chime for most of this code
//https://github.com/emilyploszaj/chime

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
        extends FeatureRenderer<T, M> {
    @Unique
    private ItemStack cachedStack;

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(at = @At("HEAD"), method = "renderArmor")
    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T livingEntity, EquipmentSlot equipmentSlot, int i,
                             A bipedEntityModel, CallbackInfo info) {
        cachedStack = livingEntity.getEquippedStack(equipmentSlot);
    }
    @Inject(at = @At("HEAD"), method = "getArmorTexture", cancellable = true)
    private void getArmorTexture(ArmorItem armorItem, boolean bl, String string, CallbackInfoReturnable<Identifier> info) {
        if (CustomArmorRegistry.isCustomArmor(cachedStack)&&cachedStack.getTag()!=null) {
            ICustomArmor i = CustomArmorRegistry.valueOf(cachedStack);
            if (bl)
                info.setReturnValue(i.layerTwoIdentifier());
            else
                info.setReturnValue(i.layerOneIdentifier());
        }
    }
}
