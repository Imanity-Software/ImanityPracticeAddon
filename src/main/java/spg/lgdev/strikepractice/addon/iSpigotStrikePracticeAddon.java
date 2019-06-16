package spg.lgdev.strikepractice.addon;

import ga.strikepractice.battlekit.BattleKit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import spg.lgdev.strikepractice.addon.command.AddonCommand;
import spg.lgdev.strikepractice.addon.listener.StrikePracticeListener;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class iSpigotStrikePracticeAddon extends JavaPlugin {

    private static iSpigotStrikePracticeAddon instance;

    private static final Map<String, String> KNOCKBACK_PROFILES = new ConcurrentHashMap<>();
    private static String DEFAULT = "noone";

    private static FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getPluginManager().registerEvents(new StrikePracticeListener(), this);
        this.getCommand("reloadspaddon").setExecutor(new AddonCommand());
        load();

        log("iSpigot StrikePractice addon loaded up");
    }

    @Override
    public void onDisable() {
        KNOCKBACK_PROFILES.clear();
    }

    public static boolean isProfileFromKitExists(String kitName) {
        return KNOCKBACK_PROFILES.containsKey(kitName);
    }

    public static String  getProfileFromKit(String kitName) {
        return KNOCKBACK_PROFILES.get(kitName);
    }

    public static void load() {
        KNOCKBACK_PROFILES.clear();

        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }

        File file = new File(instance.getDataFolder(), "config.yml");

        if (!file.exists()) {
            instance.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = config.getConfigurationSection("knockback");
        for (String key : section.getKeys(false)) {

            KNOCKBACK_PROFILES.put(key, section.getString(key));

        }

        DEFAULT = config.getString("default-knockback");
        if (DEFAULT == null) {
            instance.getLogger().warning("The default knockback is unreadable.");
        }

        instance.getLogger().info("StrikePractice addon iSpigot loaded.");
    }

    public static void log(String message) {
        instance.getLogger().info(message);
    }

    public static void warn(String message) {
        instance.getLogger().warning(message);
    }

    public static String getDefault() {
        return DEFAULT;
    }
}
