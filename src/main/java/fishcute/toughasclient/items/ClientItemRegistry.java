package fishcute.toughasclient.items;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.mixin.PlayerInventoryMixin;
import fishcute.toughasclient.util.ClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientItemRegistry {
    public static ArrayList<IClientItem> registry = new ArrayList<>();
    public static boolean equals(ItemStack i, IClientItem e) {
        try {
            NbtCompound nbtCompound = i.getSubTag("display");
            String text = Formatting.strip(i.getName().asString());
            String translationKey = i.getItem().getTranslationKey().contains(".") ? i.getItem().getTranslationKey().split("\\.")[i.getItem().getTranslationKey().split("\\.").length - 1] : i.getItem().getTranslationKey();

            return (translationKey.equals(e.itemType()) && e.identifier().equals((text)));
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isHoldingItem(IClientItem i) {
        return ClientItemRegistry.equals(ClientUtils.getItemInMainHand(), i) || ClientItemRegistry.equals(ClientUtils.getItemInOffHand(), i);
    }
    public static boolean isCustomItem(ItemStack i) {
        for (IClientItem e : registry)
            if (i.hasCustomName()&&equals(i, e))
                return true;
        return false;
    }
    public static IClientItem valueOf(ItemStack i) {
        for (IClientItem e : registry)
            if (equals(i, e))
                return e;
            return null;
    }
    public static void register(IClientItem i) {
        i.init();
        registry.add(i);
    }
    public static IClientItem getItem(String i) {
        for (IClientItem j : registry) {
            if (j.identifier().equals(i))
                return j;
        }
        return null;
    }
    public static void tickCustomItems() {
        for (IClientItem i : registry) {
            i.tickBefore();
            if (isHoldingItem(i))
                i.handTick();
        }
    }

    public static boolean doesPlayerInventoryContain(IClientItem item) {
            Iterator var2 = ((PlayerInventoryMixin) (ClientUtils.client().player.inventory)).getCombinedInventory().iterator();

            while(var2.hasNext()) {
                List<ItemStack> list = (List)var2.next();
                Iterator var4 = list.iterator();

                while(var4.hasNext()) {
                    ItemStack i = (ItemStack)var4.next();
                    if (equals(i, item))
                        return true;
                }
            }
            return false;
    }
}
