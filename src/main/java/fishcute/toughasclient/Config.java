package fishcute.toughasclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class Config {
    public static void attemptLoadConfig() {
        Path configFilePath = FabricLoader.getInstance().getConfigDir().resolve("tough_as_config.json");
        File configFile = configFilePath.toFile();
        if (configFile.exists()) {
            try {
                ConfigData data = Config.loadConfigFile(configFile);
                ToughAsClientMod.CONFIG = new Config(data);
                Config.writeConfigFile(configFile, data);
            } catch (Exception ex) {
                ToughAsClientMod.LOGGER.error("Something went wrong while loading the config file, using default config file");
            }
        } else {
            try {
                Config.writeConfigFile(configFile, new ConfigData());
            } catch (Exception ex) {
                ToughAsClientMod.LOGGER.error("Something went wrong while creating a default config. Please report this to the mod author");
            }
        }
    }

    public boolean thirst;
    public boolean temperature;
    public boolean stamina;
    public boolean sanity;
    public boolean dangerousWater;

    public Config(ConfigData confileFileFormat) {
        this.thirst = confileFileFormat.enableThirst;
        this.temperature = confileFileFormat.enableTemperature;
        this.stamina = confileFileFormat.enableStamina;
        this.sanity = confileFileFormat.enableSanity;
        this.dangerousWater = confileFileFormat.canWaterBeHarmful;
    }

    public Config() {
        this(new ConfigData());
    }

    public static ConfigData loadConfigFile(File configFile) throws IOException {
        FileReader reader = null;
        try {
            Gson gson = new Gson();
            reader = new FileReader(configFile);
            return gson.fromJson(reader, ConfigData.class);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void writeConfigFile(File configFile, ConfigData data) throws IOException {
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

    public static class ConfigData implements Serializable {
        @Expose
        private boolean enableThirst = true;
        @Expose
        private boolean enableTemperature = true;
        @Expose
        private boolean enableStamina = true;
        @Expose
        private boolean enableSanity = true;
        @Expose
        private boolean canWaterBeHarmful = true;
    }
}
