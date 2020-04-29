package spg.lgdev.addon.practice.provider;

import org.bukkit.entity.Player;
import spg.lgdev.addon.practice.iSpigotPracticeAddon;
import spg.lgdev.iSpigot;
import spg.lgdev.knockback.Knockback;

public abstract class PracticeProvider {
    protected iSpigotPracticeAddon plugin;

    public PracticeProvider(iSpigotPracticeAddon plugin) {
        this.plugin = plugin;
    }

    public abstract String getRequiredPlugin();

    public String getName() {
        return getRequiredPlugin();
    }

    public abstract void registerListeners();

    public void pickKitKnockback(Player player, String kitName) {
        String profile = iSpigotPracticeAddon.getInstance().getProfileFromKit(kitName);
        boolean defaultKnockback = profile == null;

        Knockback knockback = defaultKnockback ? iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(iSpigotPracticeAddon.getDefault()) : iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(profile);
        if (knockback == null) {
            iSpigotPracticeAddon.warn("The " + (defaultKnockback ? "default" : "") + "knockback profile named " + profile + " does not exist." + (defaultKnockback ? " Switching to default knockback iSpigot's default knockback profile." : ""));
            if (defaultKnockback) knockback = iSpigot.INSTANCE.getKnockbackHandler().getDefaultKnockback();
        }

        player.setKnockback(knockback);
    }
}
