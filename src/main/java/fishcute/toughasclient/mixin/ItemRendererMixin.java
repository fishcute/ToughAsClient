package fishcute.toughasclient.mixin;

import fishcute.toughasclient.items.ClientItemRegistry;
import fishcute.toughasclient.items.IClientItem;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    //TODO: Find a better place to do this stuff
    //Nah, I'm too lazy to do that. Anyways, this works so why do I care.
    @Inject(method = "getHeldItemModel", cancellable = true, at = @At("HEAD"))
    public void getItemModel(ItemStack stack, World world, LivingEntity entity, CallbackInfoReturnable<BakedModel> callbackInfo) {
        if (ClientItemRegistry.isCustomItem(stack)&&stack.getTag()!=null) {
            IClientItem i = ClientItemRegistry.valueOf(stack);
            NbtList nbtList = new NbtList();
            int k = 0;
            if (i.lore()!=null&&i.lore().size()>0) {
                for (String j : i.lore()) {
                    nbtList.add(k, NbtString.of(Text.Serializer.toJson(Text.of(j))));
                    k++;
                }
            }
            nbtList.add(k, NbtString.of(Text.Serializer.toJson(Text.of(Formatting.GREEN + "" + Formatting.ITALIC + Utils.name()))));
            NbtCompound compound = stack.getTag().getCompound("display");
            compound.put("Lore", nbtList);
            compound.putString("Name", Text.Serializer.toJson(Text.of(i.nameColor + i.identifier())));
            stack.getTag().putInt("CustomModelData", i.modelData());
        }
    }
}