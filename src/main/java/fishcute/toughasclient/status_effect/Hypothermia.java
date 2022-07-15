package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.status_message.StatusMessage;
import fishcute.toughasclient.util.FOVChange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class Hypothermia extends IClientStatusEffect {
    private int lockedSlot = 1;
    private int messageWaitTick = 0;
    private int changeWaitTick = 0;
    public Hypothermia(int ticks, int amplifier) {
        super(ticks, amplifier);
    }
    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/hypothermia.png");
    }
    @Override
    public void tick() {
        if (this.messageWaitTick>0)
            this.messageWaitTick--;
        if (this.changeWaitTick>0)
            this.changeWaitTick--;
        if (this.lockedSlot != ClientUtils.client().player.inventory.selectedSlot) {
            if (this.changeWaitTick>0&&freezable()) {
                Vec3d e = ClientUtils.loc();
                ClientUtils.particleGroup(ClientInit.ICE_CRIT_PARTICLE, e.getX(), e.getY(), e.getZ(), 0.2, 0.2, 0.2, 3);
                ClientUtils.client().player.inventory.selectedSlot = this.lockedSlot;
                ClientUtils.playSound(SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 1F, Utils.fromTo(1.5F, 2F));
                if (this.messageWaitTick<=0) {
                    new StatusMessage(Utils.translate("tough_as_client.message.item_frozen"),
                            200, 200, 56199).play();
                    this.messageWaitTick = 200;
                }
            }
            else if (this.changeWaitTick<=0) {
                this.lockedSlot = ClientUtils.client().player.inventory.selectedSlot;
                this.changeWaitTick = 100 + (int) (Math.random()*200);
            }
        }
        if (DataManager.temperature>22)
            this.ticks--;
        if (DataManager.temperature>42)
            this.ticks--;
        //ClientUtils.client().interactionManager.
    }
    boolean freezable() {
        Item i = ClientUtils.client().player.inventory.getStack(this.lockedSlot).getItem();
        return !(ClientUtils.client().player.inventory.getStack(this.lockedSlot).isEmpty() || i.equals(Items.LAVA_BUCKET) || i.equals(Items.MAGMA_BLOCK)
                || i.equals(Items.MAGMA_CREAM) || i.equals(Items.CAMPFIRE)
                || i.equals(Items.SOUL_CAMPFIRE) || i.equals(Items.FIRE_CHARGE));
    }
    @Override
    public DefaultParticleType particleType() {
        return ClientInit.HYPOTHERMIA_PARTICLE;
    }
    @Override
    public String getName() {
        return ClientStatusEffects.HYPOTHERMIA;
    }
    @Override
    public void onEnd() {
        FOVChange.removeChange("freeze");
    }
    @Override
    public void onStart() {
        this.lockedSlot = ClientUtils.client().player.inventory.selectedSlot;
        if (!FOVChange.containsChange("freeze"))
            FOVChange.addChange(new FOVChange((float) -0.1, "freeze"));
    }
}
