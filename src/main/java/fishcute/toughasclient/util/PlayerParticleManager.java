package fishcute.toughasclient.util;

import fishcute.toughasclient.ClientInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PlayerParticleManager {
    static int breathDuration = 0;
    static int breathWait = 240;
    public static void tickBreath() {
        if (Temperature.biomeTemperature()*100>35)
            return;
        if (breathDuration>0) {
            breathDuration--;
            if ((breathDuration & 1) == 0)
                ClientUtils.particleInFrontGroup(ClientInit.BREATH_PARTICLE,0.05, 0.05, 0.05, 1, 0.6);
        }
        else if (breathWait>0) {
            breathWait--;
        }
        else {
            breathDuration = 40;
            breathWait = 240;
        }
    }
    static int sickDuration = 0;
    static int sickWait = 2200;
    public static void tickPurge() {
        if (sickDuration>0) {
            sickDuration--;
            if ((sickDuration & 1) == 0) {
                ClientUtils.particleInFrontGroup(ClientInit.SICK_PARTICLE, 0.2, 0.2, 0.2, (int) (Math.random() * 5), 0.6);
                ClientUtils.client().player.input.sneaking = true;
            }
            }
        else if (sickWait>0) {
            sickWait--;
        }
        else {
            sickDuration = (int) (100+(Math.random()*200));
            sickWait = (int) (2000+(Math.random()*1000));
        }
    }
}
