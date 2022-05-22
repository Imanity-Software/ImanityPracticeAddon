package org.imanity.addon.practice.provider;

import dev.imanity.knockback.api.Knockback;
import dev.imanity.knockback.api.KnockbackService;
import org.bukkit.entity.Player;
import org.imanity.addon.practice.ImanityPracticeAddon;

public abstract class PracticeProvider {
    protected ImanityPracticeAddon plugin;

    public PracticeProvider(ImanityPracticeAddon plugin) {
        this.plugin = plugin;
    }

    public abstract String getRequiredPlugin();

    public String getName() {
        return this.getRequiredPlugin();
    }

    public abstract void registerListeners();

    public void pickKitKnockback(Player player, String kitName) {
        String profile = ImanityPracticeAddon.getInstance().getProfileFromKit(kitName);
        KnockbackService knockbackService = this.plugin.getServer().imanity().getKnockbackService();

        Knockback knockback = knockbackService.getKnockbackByName(profile);
        knockbackService.setKnockback(player, knockback);
    }
}
