package spg.lgdev.addon.practice.provider.impl;

import me.abhi.practice.event.impl.MatchEndEvent;
import me.abhi.practice.event.impl.MatchStartEvent;
import me.abhi.practice.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import spg.lgdev.addon.practice.iSpigotPracticeAddon;
import spg.lgdev.addon.practice.provider.PracticeProvider;
import spg.lgdev.iSpigot;

public class mPracticeProviderImpl extends PracticeProvider {

    public mPracticeProviderImpl(iSpigotPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "mPractice";
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager()
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
                }, this.plugin);
    }
}
