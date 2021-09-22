package org.imanity.addon.practice;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.imanity.addon.practice.command.AddonCommand;
import org.imanity.addon.practice.config.FileConfig;
import org.imanity.addon.practice.provider.PracticeProvider;
import org.imanity.addon.practice.provider.impl.StrikePracticeProviderImpl;
import org.imanity.addon.practice.provider.impl.ProPracticeProviderImpl;
import org.imanity.addon.practice.provider.impl.mPracticeProviderImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ImanityPracticeAddon extends JavaPlugin {

    private static ImanityPracticeAddon instance;

    public static final Map<String, String> KNOCKBACK_PROFILES = new ConcurrentHashMap<>();
    private static String DEFAULT = "none";

    private PracticeProvider currentProvider;

    public PracticeProvider getCurrentProvider() {
        return this.currentProvider;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;

        this.getCommand("practice-addon").setExecutor(new AddonCommand());
        load();

        Set<PracticeProvider> practiceProviders = new HashSet<>();
        practiceProviders.add(new StrikePracticeProviderImpl(this));
        practiceProviders.add(new mPracticeProviderImpl(this));
        practiceProviders.add(new ProPracticeProviderImpl(this));

        for (PracticeProvider provider : practiceProviders) {
            if (Bukkit.getPluginManager().isPluginEnabled(provider.getRequiredPlugin())) {
                this.currentProvider = provider;
            }
        }

        if (this.currentProvider == null) {
            warn("iSpigot Practice Addon could not find a suitable practice plugin to hook! Please confirm that you have installed one. Disabling plugin now..");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        this.currentProvider.registerListeners();
        long end = System.currentTimeMillis();
        log("iSpigot Practice Addon has been loaded in " + (end - start) + "ms. Current practice plugin: " + this.currentProvider.getRequiredPlugin());
    }

    @Override
    public void onDisable() {
        KNOCKBACK_PROFILES.clear();
        this.configuration.save();
    }

    public String getProfileFromKit(String kitName) {
        return KNOCKBACK_PROFILES.getOrDefault(kitName, null);
    }

    private FileConfig configuration;

    public void load() {
        KNOCKBACK_PROFILES.clear();
        this.configuration = new FileConfig(this, "config.yml");

        ConfigurationSection section = configuration.getConfig().getConfigurationSection("knockback");
        for (String key : section.getKeys(false)) {
            KNOCKBACK_PROFILES.put(key, section.getString(key));
        }

        DEFAULT = configuration.getConfig().getString("default-knockback");
        if (DEFAULT == null) {
            instance.getLogger().warning("The default knockback is unreadable.");
        }
    }

    public void save() {
        this.configuration.save();
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

    public static ImanityPracticeAddon getInstance() {
        return instance;
    }
}
