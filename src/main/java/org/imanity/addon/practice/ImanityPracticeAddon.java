package org.imanity.addon.practice;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.imanity.addon.practice.command.AddonCommand;
import org.imanity.addon.practice.config.FileConfig;
import org.imanity.addon.practice.provider.PracticeProvider;
import org.imanity.addon.practice.provider.impl.ProPracticeProviderImpl;
import org.imanity.addon.practice.provider.impl.StrikePracticeProviderImpl;
import org.imanity.addon.practice.provider.impl.YangPracticeProviderImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ImanityPracticeAddon extends JavaPlugin {

    private static ImanityPracticeAddon instance;
    public static final Map<String, String> KNOCKBACK_PROFILES = new ConcurrentHashMap<>();

    private PracticeProvider currentProvider;

    public PracticeProvider getCurrentProvider() {
        return this.currentProvider;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        if (!this.isServerRunningImanityKnockback()) {
            warn("This server is not running ImanitySpigot3 or ImanityKnockback Plugin, ImanityPracticeAddon need it to work! Disabling the plugin...");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;

        this.getCommand("practice-addon").setExecutor(new AddonCommand(this));
        this.load();

        Set<PracticeProvider> practiceProviders = new HashSet<>();
        practiceProviders.add(new StrikePracticeProviderImpl(this));
        practiceProviders.add(new ProPracticeProviderImpl(this));
        practiceProviders.add(new YangPracticeProviderImpl(this));
        //practiceProviders.add(new mPracticeProviderImpl(this)); // TODO: Waiting API

        for (PracticeProvider provider : practiceProviders) {
            if (this.getServer().getPluginManager().isPluginEnabled(provider.getRequiredPlugin())) {
                this.currentProvider = provider;
                log("ImanityPracticeAddon founded " + provider.getRequiredPlugin() + " using it as provider.");
                break;
            }
        }
        if (this.currentProvider == null) {
            warn("ImanityPracticeAddon could not find a suitable practice plugin to hook! Please confirm that you have installed one. Disabling plugin now..");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.currentProvider.registerListeners();

        log("ImanityPracticeAddon has been loaded in " + (System.currentTimeMillis() - start) + "ms. Current practice plugin: " + this.currentProvider.getRequiredPlugin());
    }

    @Override
    public void onDisable() {
        KNOCKBACK_PROFILES.clear();
    }

    public String getProfileFromKit(String kitName) {
        return KNOCKBACK_PROFILES.getOrDefault(kitName, null);
    }

    private FileConfig configuration;

    public void load() {
        KNOCKBACK_PROFILES.clear();
        this.configuration = new FileConfig(this, "config.yml");

        ConfigurationSection section = this.configuration.getConfig().getConfigurationSection("knockback");
        for (String key : section.getKeys(false)) {
            KNOCKBACK_PROFILES.put(key, section.getString(key));
        }
    }

    public void save() {
        this.configuration.save();
    }

    private boolean isServerRunningImanityKnockback() {
        try {
            Class.forName("dev.imanity.imanityspigot.knockback.Knockback");
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    public static ImanityPracticeAddon getInstance() {
        return instance;
    }

    public static void log(String message) {
        instance.getLogger().info(message);
    }

    public static void warn(String message) {
        instance.getLogger().warning(message);
    }
}
