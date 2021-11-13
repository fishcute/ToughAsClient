/*
 *    MCreator note:
 *
 *    If you lock base mod element files, you can edit this file and the proxy files
 *    and they won't get overwritten. If you change your mod package or modid, you
 *    need to apply these changes to this file MANUALLY.
 *
 *
 *    If you do not lock base mod element files in Workspace settings, this file
 *    will be REGENERATED on each build.
 *
 */
package fishcute.toughasclient;

import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class ToughAsClientMod implements ModInitializer {
	/*
	TODO List
	General:
	Wet suit
	Ingame tutorial system:
	- When exhausted, tip will show up as "Sneak to regenerate stamina faster"
	Commands of some sort
	Weight
	Temperature based off of y level
	Internal:
	Remove unnecessary stuff (imports, casts)
	Server to Client networking: implements ServerLoginConnectionEvents.Init
	*/
	public static Config CONFIG = new Config();
	public static final String id = "tough_as_client";
	public static final Logger LOGGER = LogManager.getLogger();
	static final ArrayList<String> importantPeople = new ArrayList<>(
			Arrays.asList(
					"fishcute",
					"block36_"
					//Going to change this to UUIDs eventually
			)
	);
	@Override
	public void onInitialize() {
		log("Initializing ToughAsClient", Level.INFO);
		Config.attemptLoadConfig();
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			//Nether chest
		});
	}

	public static void sendAlert(String message) {
		ClientUtils.client().player.sendMessage(Text.of(Formatting.YELLOW + "[" + Utils.name() + "] " +
				message), false);
	}
	public static String getAlertPrefix() {
		return Formatting.YELLOW + "[" + Utils.name() + "]";
	}

	public static void log(String msg, Level l) {
		LOGGER.log(l, "[" + Utils.name() + "] " + msg);
	}

	public static boolean isImportant(String username) {
		return importantPeople.contains(username);
	}
}
