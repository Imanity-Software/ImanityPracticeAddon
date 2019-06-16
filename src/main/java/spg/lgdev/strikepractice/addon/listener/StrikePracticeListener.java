package spg.lgdev.strikepractice.addon.listener;

import ga.strikepractice.battlekit.BattleKit;
import ga.strikepractice.events.FightStartEvent;
import ga.strikepractice.fights.duel.Duel;
import ga.strikepractice.fights.party.partyfights.PartyFFA;
import ga.strikepractice.fights.party.partyfights.PartySplit;
import ga.strikepractice.fights.party.partyfights.PartyVsBots;
import ga.strikepractice.fights.party.partyfights.PartyVsParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import spg.lgdev.iSpigot;
import spg.lgdev.knockback.Knockback;
import spg.lgdev.strikepractice.addon.iSpigotStrikePracticeAddon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StrikePracticeListener implements Listener {

    @EventHandler
    public void onKitSelect(FightStartEvent e) {

        if (e.getFight() instanceof Duel) {
            this.select(e.getFight().getKit(), Arrays.asList(
                    Bukkit.getPlayer(((Duel) e.getFight()).getP1()),
                    Bukkit.getPlayer(((Duel) e.getFight()).getP2())
            ));
        }
        else if (e.getFight() instanceof PartyFFA) {
            this.select(e.getFight().getKit(), ((PartyFFA)e.getFight()).getAlive().stream()
            .map(Bukkit::getPlayer).collect(Collectors.toList()));
        }
        else if (e.getFight() instanceof PartySplit) {
            this.select(e.getFight().getKit(), Stream.of(((PartySplit)e.getFight()).getAlive1(), ((PartySplit)e.getFight()).getAlive2()).flatMap(Collection::stream)
                    .map(Bukkit::getPlayer).collect(Collectors.toList()));
        }
        else if (e.getFight() instanceof PartyVsParty) {
            this.select(e.getFight().getKit(), Stream.of(((PartyVsParty)e.getFight()).getPartyAlive1(), ((PartyVsParty)e.getFight()).getPartyAlive2()).flatMap(Collection::stream)
                    .map(Bukkit::getPlayer).collect(Collectors.toList()));
        }
        else if (e.getFight() instanceof PartyVsBots) {
            this.select(e.getFight().getKit(), Stream.of(((PartyVsBots)e.getFight()).getPlayersAlive()).flatMap(Collection::stream)
                    .map(Bukkit::getPlayer).collect(Collectors.toList()));
        }
    }

    private void select(BattleKit kit, List<Player> playerList) {
        String kitName = kit.getName();

        if (iSpigotStrikePracticeAddon.isProfileFromKitExists(kitName)) {
            String profile = iSpigotStrikePracticeAddon.getProfileFromKit(kitName);

            Knockback knockback = iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(profile);
            if (knockback == null) {
                iSpigotStrikePracticeAddon.warn("The knockback profile called " + profile + " is not exists.");
            } else {
                for (Player player : playerList) {
                    player.setKnockback(knockback);
                }
                return;
            }
        }

        Knockback knockback = iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(iSpigotStrikePracticeAddon.getDefault());

        if (knockback == null) {
            iSpigotStrikePracticeAddon.warn("The default knockback called " + iSpigotStrikePracticeAddon.getDefault() + " is null. switching to default knockback from iSpigot");
            knockback = iSpigot.INSTANCE.getKnockbackHandler().getDefaultKnockback();
        }

        for (Player player : playerList) {
            player.setKnockback(knockback);
        }
    }

}
