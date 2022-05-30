package org.imanity.addon.practice.provider.impl;

import ga.strikepractice.events.KitSelectEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.addon.practice.provider.PracticeProvider;

public class StrikePracticeProviderImpl extends PracticeProvider {

    public StrikePracticeProviderImpl(final ImanityPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "StrikePractice";
    }

    @Override
    public void registerKnockbackImplementation() {
        this.plugin.getServer().getPluginManager()
                .registerEvents(new Listener() {
                    @EventHandler
                    public void onKitSelect(KitSelectEvent event) {
                        pickKitKnockback(event.getPlayer(), event.getKit().getName());
                    }
                }, this.plugin);
    }
}
