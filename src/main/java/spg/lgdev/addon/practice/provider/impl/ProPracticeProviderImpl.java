package spg.lgdev.addon.practice.provider.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import spg.lgdev.addon.practice.iSpigotPracticeAddon;
import spg.lgdev.addon.practice.provider.PracticeProvider;
import sv.file14.propractice.api.events.PlayerSelectKitEvent;

public class ProPracticeProviderImpl extends PracticeProvider {

    public ProPracticeProviderImpl(iSpigotPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "ProPractice";
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager()
                .registerEvents(new Listener() {
                    @EventHandler
                    public void onSelectKit(PlayerSelectKitEvent event) {
                        pickKitKnockback(event.getPlayer(), event.getKit());
                    }
                }, this.plugin);
    }
}
