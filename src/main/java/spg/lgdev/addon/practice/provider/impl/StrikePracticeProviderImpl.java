package spg.lgdev.addon.practice.provider.impl;

import ga.strikepractice.events.KitSelectEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import spg.lgdev.addon.practice.iSpigotPracticeAddon;
import spg.lgdev.addon.practice.provider.PracticeProvider;

public class StrikePracticeProviderImpl extends PracticeProvider {
    public StrikePracticeProviderImpl(iSpigotPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "StrikePractice";
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager()
                .registerEvents(new Listener() {
                    @EventHandler
                    public void onKitSelect(KitSelectEvent event) {
                        pickKitKnockback(event.getPlayer(), event.getKit().getName());
                    }
                }, this.plugin);
    }
}
