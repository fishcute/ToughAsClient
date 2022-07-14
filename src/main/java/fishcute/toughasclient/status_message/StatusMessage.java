package fishcute.toughasclient.status_message;

import fishcute.toughasclient.ToughAsClientMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Iterator;

@Environment(EnvType.CLIENT)
public class StatusMessage {
    public static HashMap<Integer, StatusMessage> msg = new HashMap<>();
    public static HashMap<String, Boolean> statusMessageData = new HashMap<>();
    public static boolean dataContains(String value) {
        return statusMessageData.containsKey(value);
    }
    public static void dataSetValue(String data, boolean value) {
        statusMessageData.put(data, value);
    }
    public static boolean dataGetValue(String data) {
        if (dataContains(data))
            return statusMessageData.get(data);
        return false;
    }
    public StatusMessage(String msg, int duration, int fadeTicks, int color) {
        this.duration = duration;
        this.message = msg;
        this.color = color;
        this.fadeTick = fadeTicks;
        this.overallFadeTick = fadeTicks;
        this.totalDuration = duration;
    }
    public void play() {
        assign();
    }
    public static void tickAll() {
        if (msg.size()>0) {
            //ArrayList<StatusMessage> toRemove = new ArrayList<StatusMessage>();
            for (Iterator<StatusMessage> iterator = msg.values().iterator(); iterator.hasNext(); ) {
                StatusMessage b = iterator.next();
                if (b.getFadeTick() <= 1) {
                    iterator.remove();
                } else b.tick();
            }
        }
    }
    public int duration;
    public int fadeTick;
    public int overallFadeTick;
    public int endTick = -99;
    public int overallEndTick = -99;
    int totalDuration;
    int id = 0;
    String message;
    int color;
    public String message() {
        return this.message;
    }
    public int overallEndTick() {
        return this.overallEndTick;
    }
    public int color() {
        return this.color;
    }
    public int getFadeTick() {
        if (this.endTick == -99)
            return this.fadeTick;
        return this.endTick;
    }
    public int getActualFadeTick() {
        return this.fadeTick;
    }
    public int getTick() {
        return this.duration;
    }
    public int getEndTick() {
        return this.endTick;
    }
    public void tick() {
        this.duration--;
        if (this.duration<=0) {
            if (this.endTick==-99)
                this.endTick = this.fadeTick;
            this.duration = 0;
            this.endTick--;
            if (this.endTick<=0)
                this.remove();
        }
    }
    private void assign() {
        this.id = msg.size() + 1;
        msg.put(this.id, this);
    }
    public int colorFade() {
        if (this.duration!=0)
            return this.color();
        if (this.endTick<=this.totalDuration/50+1)
            return -99999;
        int color = this.color();

        color += ((this.endTick*255/this.fadeTick) << 24);

        return color;
    }
    public void remove() {
        if (this.id>0)
            msg.remove(this.id);
    }
}
