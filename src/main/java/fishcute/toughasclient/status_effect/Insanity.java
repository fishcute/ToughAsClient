package fishcute.toughasclient.status_effect;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.armor.ClearsightSpectacles;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.client.InsanityOverlay;
import fishcute.toughasclient.status_message.StatusMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class Insanity extends IClientStatusEffect {
    int mood = 0;
    int duration = 400;
    public static int noLightMessageTick = 0;
    public Insanity(int ticks, int amplifier) {
        super(ticks, amplifier);
    }
    @Override
    public Identifier getIcon() {
        return new Identifier("tough_as_client:textures/client_effect/insanity.png");
        //new Identifier("tough_as_client:textures/**.png")
    }
    @Override
    public DefaultParticleType particleType() {
        return ClientInit.INSANE_PARTICLE;
    }
    @Override
    public void playParticle() {
        Utils.surroundParticle(particleType(), MinecraftClient.getInstance().player, 0.999, 0.5);
    }
    @Override
    public void tick() {
        if (Math.random() > 0.995 && this.amplifier > 2)
            attemptSwapHands();
        if (Math.random() > 0.99 && this.amplifier > 2)
            hotbarSlot();
        if (!InsanityOverlay.active() && Math.random()>0.9995 && this.amplifier > 2 && !ClearsightSpectacles.hasAffect)
            watch();

        this.mood++;

        if (this.mood >= this.duration) {
            message();
            this.mood = 0;
            this.duration = (int) (2000 + (Math.random()*3000));
            sound();
        }
        if (Math.random()>0.9995)
            sound();
        randomMovement();

        if (noLightMessageTick > 0)
            noLightMessageTick--;
    }
    public void attemptSwapHands() {
        if (ClientUtils.client().player.getStackInHand(Hand.MAIN_HAND).getItem()!= Items.AIR||ClientUtils.client().player.getStackInHand(Hand.OFF_HAND).getItem() != Items.AIR)
            ClientUtils.client().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, ClientUtils.client().player.getBlockPos()
                ,ClientUtils.client().player.getHorizontalFacing()));
}
    public void watch() {
        InsanityOverlay.initiate();
        ClientUtils.client().player.sendMessage(Text.of(Formatting.DARK_PURPLE + generate()), true);
    }
    String generate() {
        String chars = Utils.translate("tough_as_client.message.insane.chars");
        StringBuilder s = new StringBuilder();
        Random rnd = new Random();
        while (s.length() < 18) {
            int index = (int) (rnd.nextFloat() * chars.length());
            s.append(chars.charAt(index));
        }
        return s.toString();
    }
    public static boolean holdingLightObject(Hand h) {
        String i = ClientUtils.client().player.getStackInHand(h).getTranslationKey();
        return (i.contains("torch")||i.contains("lantern")||i.contains("campfire")||i.contains("glowstone")
                ||i.contains("beacon")||i.contains("flint_and_steel")||i.contains("lamp")||i.contains("shroomlight"));
    }
    @Override
    public void onEnd() {
        resetMove();
    }

    //FIX thermometer, fix block placing

    @Override
    public void onStart() {

    }

    @Override
    public String getName() {
        return ClientStatusEffects.INSANITY;
    }

    void message() {
        int a = new Random().nextInt(10);
        switch (a) {
            case 1:
                new StatusMessage(Utils.translate("tough_as_client.message.insane.1"), 400, 100, 4793925).play();
                break;
            case 2:
                new StatusMessage(Utils.translate("tough_as_client.message.insane.2"), 400, 100, 4793925).play();
                break;
            case 3:
                new StatusMessage(Utils.translate("tough_as_client.message.insane.3"), 400, 100, 4793925).play();
                break;
            case 4:
                new StatusMessage(Utils.translate("tough_as_client.message.insane.4"), 400, 100, 4793925).play();
                break;
            case 5:
                new StatusMessage(Utils.translate("tough_as_client.message.insane.5"), 400, 100, 4793925).play();
                break;
            case 6:
                new StatusMessage(Utils.translate("tough_as_client.message.insane.6"), 400, 100, 4793925).play();
                break;
            case 7:
            case 8:
            case 9:
                new StatusMessage(generate(), 400, 100, 4793925).play();
                break;
        }
    }

    void sound() {
        int a = new Random().nextInt(15);
        switch (a) {
            case 1: ClientUtils.playSound(SoundEvents.ENTITY_PHANTOM_DEATH, SoundCategory.MASTER, 1, 0);
            case 2: ClientUtils.playSound(SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.MASTER, 1, 0);
            case 3: ClientUtils.playSound(SoundEvents.ENTITY_PHANTOM_AMBIENT, SoundCategory.MASTER, 1, 0);
            case 4: ClientUtils.playSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, SoundCategory.MASTER, 1, 0);
            case 5: ClientUtils.playSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, SoundCategory.MASTER, 1, 1);
            case 6: ClientUtils.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.MASTER, 0.8F, 1);
            case 7: ClientUtils.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 0.2F, 1);
            case 8: ClientUtils.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, SoundCategory.MASTER, 1F, 1);
            case 9: ClientUtils.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, SoundCategory.MASTER, 1F, 0);
            case 10:
            case 11:
            case 12: ClientUtils.playSound(SoundEvents.AMBIENT_CAVE, SoundCategory.MASTER, 1F, 0);
            case 13:
            case 14: ClientUtils.playSound(SoundEvents.AMBIENT_CAVE, SoundCategory.MASTER, 1F, 1);
        }
    }
    public void hotbarSlot() {
        int slot = ClientUtils.client().player.inventory.selectedSlot;
        if (slot<7&&slot>0) {
            if (Math.random() > 0.5)
                ClientUtils.client().player.inventory.selectedSlot++;
            else ClientUtils.client().player.inventory.selectedSlot--;
        }
        else if (slot==0)
            if (Math.random() > 0.5)
                ClientUtils.client().player.inventory.selectedSlot++;
            else ClientUtils.client().player.inventory.selectedSlot = 8;
        else if (slot==8)
            if (Math.random() > 0.5)
                ClientUtils.client().player.inventory.selectedSlot--;
            else ClientUtils.client().player.inventory.selectedSlot = 0;
    }
    private int moveDirecton = 0;
    private int moveTicks = 0;   
    public static boolean forward = false;
    public static boolean backwards = false;
    public static boolean left = false;
    public static boolean right = false;

    public void randomMovement() {
        if (Math.random() > 0.99 && this.amplifier > 1)
            assignMoveInt();
        if (this.moveTicks>0) {
            this.moveTicks--;
            switch (this.moveDirecton) {
                case 0: forward = true;
                    break;
                case 1: backwards = true;
                    break;
                case 2: left = true;
                    break;
                case 3: right = true;
                    break;
            }
        }
        else resetMove();
    }
    void assignMoveInt() {
        this.moveTicks = (int) (10+Math.random()*30);
        this.moveDirecton = (int) (Math.round(Math.random()*3));
        //TODO: Fix player only moving back & right
    }
    void resetMove() {
        this.moveDirecton = 0;
        forward = false;
        backwards = false;
        left = false;
        right = false;
    }
}
