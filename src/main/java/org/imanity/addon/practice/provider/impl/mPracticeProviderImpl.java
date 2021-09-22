package org.imanity.addon.practice.provider.impl;

import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.addon.practice.provider.PracticeProvider;

public class mPracticeProviderImpl extends PracticeProvider {

    public mPracticeProviderImpl(ImanityPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "mPractice";
    }

    @Override
    public void registerListeners() {
        // TODO: waiting API
        /*Bukkit.getPluginManager()
                .registerEvents(new Listener() {
                    @EventHandler
                    public void onMatchStart(MatchStartEvent event) {
                        Match match = event.getMatch();

                        match.getAllPlayers()
                                .forEach(playerUuid -> {
                                    Player player = Bukkit.getPlayer(playerUuid);
                                    if (player == null) return; // Should not happen but just in case
                                    pickKitKnockback(player, match.getKit().getName());
                                });
                    }

                    @EventHandler
                    public void onMatchEnd(MatchEndEvent event) {
                        Match match = event.getMatch();

                        match.getAllPlayers()
                                .forEach(playerUuid -> {
                                    Player player = Bukkit.getPlayer(playerUuid);
                                    if (player == null) return; // Should not happen but just in case
                                    player.setKnockback(iSpigot.INSTANCE.getKnockbackHandler().getDefaultKnockback());
                                });
                    }
                }, this.plugin);*/
    }
}
