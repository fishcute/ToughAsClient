package fishcute.toughasclient;

import fishcute.toughasclient.status_effect.IClientStatusEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class DataManager {
	public static ArrayList<IClientStatusEffect> effects = new ArrayList<>();
	public static int thirst = 82;
	public static int stamina = 82;
	public static int temperature = 40;
	public static int immediateTemperature = 40;
	public static int sanity = 82;
	public static int weight = 0;
}
