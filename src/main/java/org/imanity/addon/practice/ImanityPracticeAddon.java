package org.imanity.addon.practice;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.imanity.addon.practice.command.AddonCommand;
import org.imanity.addon.practice.provider.PracticeProvider;
import org.imanity.addon.practice.provider.impl.ProPracticeProviderImpl;
import org.imanity.addon.practice.provider.impl.StrikePracticeProviderImpl;
import org.imanity.addon.practice.provider.impl.YangPracticeProviderImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ImanityPracticeAddon extends JavaPlugin {

    private final Map<String, String> knockbackProfiles = new HashMap<>();
    private PracticeProvider currentProvider;

    @Override
    public void onEnable() {
        final long start = System.currentTimeMillis();

        if (!this.isServerRunningImanityKnockback()) {
            this.warn("This server is not running ImanitySpigot3 or ImanityKnockback Plugin, ImanityPracticeAddon need it to work! " +
                    "Disabling the plugin...");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.loadProfiles();
        this.getCommand("practice-addon").setExecutor(new AddonCommand(this));

        final Set<PracticeProvider> practiceProviders = new HashSet<>();
        practiceProviders.add(new StrikePracticeProviderImpl(this));
        practiceProviders.add(new ProPracticeProviderImpl(this));
        practiceProviders.add(new YangPracticeProviderImpl(this));
        //practiceProviders.add(new mPracticeProviderImpl(this)); // TODO: Waiting API

        for (final PracticeProvider provider : practiceProviders) {
            if (this.getServer().getPluginManager().isPluginEnabled(provider.getRequiredPlugin())) {
                this.currentProvider = provider;
                this.log("ImanityPracticeAddon founded " + provider.getRequiredPlugin() + " using it as provider.");
                break;
            }
        }
        if (this.currentProvider == null) {
            this.warn("ImanityPracticeAddon could not find a suitable practice plugin to hook! Please confirm that you have installed one" +
                    ". Disabling plugin now..");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.currentProvider.registerKnockbackImplementation();

        this.log("ImanityPracticeAddon has been loaded in " + (System.currentTimeMillis() - start) + "ms. Current practice plugin: " + this.currentProvider.getRequiredPlugin());
    }

    @Override
    public void onDisable() {
        this.knockbackProfiles.clear();
    }

    public Map<String, String> getKnockbackProfiles() {
        return this.knockbackProfiles;
    }

    public PracticeProvider getCurrentProvider() {
        return this.currentProvider;
    }

    public String getProfileFromKit(final String kitName) {
        return this.knockbackProfiles.getOrDefault(kitName, null);
    }

    public void loadProfiles() {
        this.getConfig().addDefault("knockback.builduhc", "builduhc");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.knockbackProfiles.clear();

        final ConfigurationSection section = this.getConfig().getConfigurationSection("knockback");
        for (final String key : this.getConfig().getConfigurationSection("knockback").getKeys(false)) {
            this.knockbackProfiles.put(key, section.getString(key));
        }
    }

    private boolean isServerRunningImanityKnockback() {
        try {
            Class.forName("dev.imanity.knockback.api.Knockback");
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    public void log(final String message) {
        this.getLogger().info(message);
    }

    public void warn(final String message) {
        this.getLogger().warning(message);
    }
}
