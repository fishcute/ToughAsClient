package fishcute.toughasclient.block;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import fishcute.toughasclient.custom_item.CustomItemRegistry;
import fishcute.toughasclient.custom_item.UVSterilizer;
import fishcute.toughasclient.status_message.StatusMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class CleanedWaterManager {
    public static int filterCounts = 0;
    public static BlockPos filterSpot;
    public static void filterTick() {
        if (!isFocused()) {
            reset();
            return;
        }
        ClientUtils.particleGroup(ParticleTypes.SPLASH, filterSpot.getX(), filterSpot.getY(), filterSpot.getZ(),
                1, 1, 1, 5);
        if ((filterCounts%10)==0) {
            ClientUtils.swingArm(Hand.MAIN_HAND);
            ClientUtils.particleGroup(ClientInit.CLEAN_PARTICLE, filterSpot.getX()+0.5, filterSpot.getY()+1, filterSpot.getZ()+0.5,
                    1, 0.1, 1, 2);
        }
        if ((filterCounts%20)==0) {
            ClientUtils.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, filterSpot, SoundCategory.MASTER, 0.5F, Utils.fromTo(1, 2));
        }
        filterCounts++;
        if (filterCounts>300)
            newFilterSpot();
    }
    public static void newFilterSpot() {
        new CleanedWater(filterSpot, 3).assign();
        new StatusMessage(Utils.translate("tough_as_client.message.uv_sterilizer.water_cleaned"), 300, 100, 49151).play();
        reset();
    }
    public static void reset() {
        filterSpot = null;
        filterCounts = 0;
        CustomItemRegistry.getItem("UV Sterilizer").setOverride("uv_sterilizer");
    }
    public static boolean isFocused() {
        return ClientUtils.e().getBlockPos().isWithinDistance(filterSpot,  2) && CustomItemRegistry.equals(ClientUtils.client().player.inventory.getMainHandStack(), new UVSterilizer());
    }
    public static boolean isFiltering() {
        return filterCounts>0&&filterSpot!=null;
    }
    public static void prepare(BlockPos b) {
        filterSpot = b;
        filterCounts = 1;
        CustomItemRegistry.getItem("UV Sterilizer").setOverride("uv_sterilizer_on");
    }
}
