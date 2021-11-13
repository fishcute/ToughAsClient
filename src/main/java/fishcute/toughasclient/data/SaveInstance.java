package fishcute.toughasclient.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import fishcute.toughasclient.DataManager;
import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.status_effect.IClientStatusEffect;
import fishcute.toughasclient.status_message.StatusMessage;
import fishcute.toughasclient.util.ClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class SaveInstance {
    public static SaveData fromCurrent() {
        SaveData d = new SaveData();
        d.effects = DataUtils.toStringArrayList(DataManager.effects);
        d.thirst = DataManager.thirst;
        d.stamina = DataManager.stamina;
        d.temperature = DataManager.temperature;
        d.sanity = DataManager.sanity;
        d.weight = DataManager.weight;
        return d;
    }
    public SaveInstance(ArrayList<IClientStatusEffect> effects, int thirst, int stamina, int temperature, int sanity, int weight) {
        this.effects = effects;
        this.thirst = thirst;
        this.stamina = stamina;
        this.temperature = temperature;
        this.sanity = sanity;
        this.weight = weight;
    }
    public static void load(SaveData d) {
        DataManager.effects = DataUtils.fromStringArrayList(d.effects);
        if (ToughAsClientMod.CONFIG.thirst)
            DataManager.thirst = d.thirst;
        else
            DataManager.thirst = 82;

        if (ToughAsClientMod.CONFIG.stamina)
            DataManager.stamina = d.stamina;
        else
            DataManager.stamina = 82;

        if (ToughAsClientMod.CONFIG.temperature)
            DataManager.temperature = d.temperature;
        else
            DataManager.temperature = 40;

        if (ToughAsClientMod.CONFIG.sanity)
            DataManager.sanity = d.sanity;
        else
            DataManager.sanity = 82;

        DataManager.weight = d.weight;
    }
    static File pathFile(boolean server, String id) {
        String addon;
        if (server)
            addon = "server";
        else addon = "world";
        Path filePath = FabricLoader.getInstance().getGameDir().resolve("tough_as_client/" + addon + "/" + id + ".json");
        File f = filePath.toFile();

        if (!doesPathExist(server, id))
        try {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        catch (Exception e) {
            sendErrorStatusToast("Could not save data", "Something went wrong");
            ToughAsClientMod.log("Something went wrong while saving data!", Level.FATAL);
            e.printStackTrace();
        }
        return filePath.toFile();
    }
    public static void sendStatusToast(boolean save, boolean server) {
        String side;
        if (server)
            side = "server";
        else side = "world";
        if (save) {
            DataUtils.sendStatus(DataUtils.StatusToastType.SAVE_DATA, side);
        }
        else {
            DataUtils.sendStatus(DataUtils.StatusToastType.LOAD_DATA, side);
        }
    }
    public static void sendErrorStatusToast(String status, String name) {
        ClientUtils.client().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_ACCESS_FAILURE, Text.of(name), Text.of(status)));
    }
    public static boolean doesPathExist(boolean server, String id) {
        String addon;
        if (server)
            addon = "server";
        else addon = "world";
        Path filePath = FabricLoader.getInstance().getGameDir().resolve("tough_as_client/" + addon + "/" + id + ".json");
        File f = filePath.toFile();
        return f.exists();
    }
    public static void resetCurrentStats() {
        DataManager.effects = new ArrayList<>();
        DataManager.thirst = 82;
        DataManager.stamina = 82;
        DataManager.temperature = 40;
        DataManager.sanity = 82;
        DataManager.weight = 0;
        StatusMessage.statusMessageData = new HashMap<>();
    }
    public static void saveData(String name, boolean server) {
        saveToFile(pathFile(server, name), fromCurrent());
        sendStatusToast(true, server);
    }
    public static void loadData(String name, boolean server) {
        load(loadDataFile(pathFile(server, name)));
        sendStatusToast(false, server);
    }
    public static void saveToFile(File configFile, SaveData data) {
        try {
            FileWriter writer = null;
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                writer = new FileWriter(configFile);
                writer.write(gson.toJson(data));
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        catch (Exception e) {
            DataUtils.sendAlert(DataUtils.StatusToastType.FAILURE_SAVE_DATA);
            e.printStackTrace();
        }
    }
    public static SaveData loadDataFile(File configFile) {
        try {
            FileReader reader = null;
            try {
                Gson gson = new Gson();
                reader = new FileReader(configFile);
                return gson.fromJson(reader, SaveData.class);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        catch (Exception e) {
            DataUtils.sendAlert(DataUtils.StatusToastType.FAILURE_LOAD_DATA);
            e.printStackTrace();
            resetCurrentStats();
        }
        return new SaveData();
    }
    ArrayList<IClientStatusEffect> effects;
    int thirst;
    int stamina;
    int temperature;
    int sanity;
    int weight;

    public static class SaveData implements Serializable {
        @Expose
        int thirst = 82;
        @Expose
        int stamina = 82;
        @Expose
        int temperature = 40;
        @Expose
        int sanity = 82;
        @Expose
        int weight = 0;
        @Expose
        ArrayList<String> effects = new ArrayList<>();
    }
}
