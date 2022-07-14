package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.items.BlueIcePack;
import fishcute.toughasclient.items.ClientItemRegistry;
import fishcute.toughasclient.items.IcePack;
import fishcute.toughasclient.items.PackedIcePack;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.status_message.StatusMessage;
import fishcute.toughasclient.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class Hyperthermia extends IClientStatusEffect {
    private int messageTick = 0;
    private int dropTick = 200;
    private int previousSlot = 0;
    public Hyperthermia(int ticks, int amplifier) {
        super(ticks, amplifier);
    }
    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/hyperthermia.png");
    }
    @Override
    public DefaultParticleType particleType() {
        return ClientInit.NONE;
    }
    @Override
    public void tick() {
        if (ClientUtils.client().player.inventory.selectedSlot!=this.previousSlot)
            this.dropTick = 200;
        if (this.messageTick>0)
            this.messageTick--;
        if (this.dropTick>0&&shouldDropItem())
            this.dropTick--;
        if (shouldDropItem()&&this.messageTick<=0&&this.dropTick<=0&&Math.random()>0.8) {
            ClientUtils.client().player.dropSelectedItem(true);
            ClientUtils.swingArm(Hand.MAIN_HAND);
            new StatusMessage(Utils.translate("tough_as_client.message.drop_item"),
                    200, 200, 16718080).play();
            this.messageTick = 40;
            this.dropTick = 200;
        }
        this.previousSlot = ClientUtils.client().player.inventory.selectedSlot;
        if (DataManager.temperature<60)
            this.ticks--;
        if (DataManager.temperature<40)
            this.ticks--;
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public String getName() {
        return ClientStatusEffects.HYPERTHERMIA;
    }
    static boolean shouldDropItem() {
        ItemStack i = (ItemStack) ((ArrayList) ClientUtils.e().getItemsHand()).get(0);
        return !(i.equals(Items.SNOW) || i.equals(Items.SNOW_BLOCK) || i.equals(Items.SNOWBALL)
                || i.equals(Items.ICE) || i.equals(Items.PACKED_ICE) || i.equals(Items.BLUE_ICE)
                || ClientItemRegistry.isHoldingItem(new IcePack())
                || ClientItemRegistry.isHoldingItem(new PackedIcePack())
                || ClientItemRegistry.isHoldingItem(new BlueIcePack()));
    }
}
