package org.imanity.addon.practice.provider;

import org.bukkit.entity.Player;
import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.imanityspigot.knockback.Knockback;
import org.imanity.imanityspigot.knockback.KnockbackService;

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
        KnockbackService knockbackService = this.plugin.getServer().imanity().getKnockbackService();

        Knockback knockback = knockbackService.getKnockbackByName(profile);
        knockbackService.setKnockback(player, knockback);
    }
}
