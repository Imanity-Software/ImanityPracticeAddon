package org.imanity.addon.practice.provider;

import org.bukkit.entity.Player;
import org.imanity.addon.practice.ImanityPracticeAddon;
import spg.lgdev.iSpigot;
import spg.lgdev.knockback.Knockback;

public abstract class PracticeProvider {
    protected ImanityPracticeAddon plugin;

    public PracticeProvider(ImanityPracticeAddon plugin) {
        this.plugin = plugin;
    }

    public abstract String getRequiredPlugin();

    public String getName() {
        return getRequiredPlugin();
    }

    public abstract void registerListeners();

    public void pickKitKnockback(Player player, String kitName) {
        String profile = ImanityPracticeAddon.getInstance().getProfileFromKit(kitName);
        boolean defaultKnockback = profile == null;

        Knockback knockback = defaultKnockback ? iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(ImanityPracticeAddon.getDefault()) : iSpigot.INSTANCE.getKnockbackHandler().getKnockbackProfile(profile);
        if (knockback == null) {
            ImanityPracticeAddon.warn("The " + (defaultKnockback ? "default" : "") + "knockback profile named " + profile + " does not exist." + (defaultKnockback ? " Switching to default knockback iSpigot's default knockback profile." : ""));
            if (defaultKnockback) knockback = iSpigot.INSTANCE.getKnockbackHandler().getDefaultKnockback();
        }

        player.setKnockback(knockback);
    }
}
