package org.imanity.addon.practice.provider.impl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.addon.practice.provider.PracticeProvider;
import sv.file14.propractice.api.events.PlayerSelectKitEvent;

public class ProPracticeProviderImpl extends PracticeProvider {

    public ProPracticeProviderImpl(final ImanityPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "ProPractice";
    }

    @Override
    public void registerKnockbackImplementation() {
        this.plugin.getServer().getPluginManager()
                .registerEvents(new Listener() {
                    @EventHandler
                    public void onPlayerSelectKit(PlayerSelectKitEvent event) {
                        pickKitKnockback(event.getPlayer(), event.getKit());
                    }
                }, this.plugin);
    }
}
