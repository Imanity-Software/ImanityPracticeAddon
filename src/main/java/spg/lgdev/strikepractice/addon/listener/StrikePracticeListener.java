package spg.lgdev.strikepractice.addon.listener;

import ga.strikepractice.battlekit.BattleKit;
import ga.strikepractice.events.KitSelectEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import spg.lgdev.iSpigot;
import spg.lgdev.knockback.Knockback;
import spg.lgdev.strikepractice.addon.iSpigotStrikePracticeAddon;

public class StrikePracticeListener implements Listener {

    @EventHandler
    public void onKitSelect(KitSelectEvent event) {
        this.select(event.getPlayer(), event.getKit());
    }

    private void select(Player player, BattleKit kit) {
        String kitName = kit.getName();

        if (iSpigotStrikePracticeAddon.isProfileFromKitExists(kitName)) {
            String profile = iSpigotStrikePracticeAddon.getProfileFromKit(kitName);

            Knockback knockback = iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(profile);
            if (knockback == null) {
                iSpigotStrikePracticeAddon.warn("The knockback profile called " + profile + " is not exists.");
            } else {
                player.setKnockback(knockback);
                return;
            }
        }

        Knockback knockback = iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(iSpigotStrikePracticeAddon.getDefault());

        if (knockback == null) {
            iSpigotStrikePracticeAddon.warn("The default knockback called " + iSpigotStrikePracticeAddon.getDefault() + " is null. switching to default knockback from iSpigot");
            knockback = iSpigot.INSTANCE.getKnockbackHandler().getDefaultKnockback();
        }

        player.setKnockback(knockback);
    }

}
