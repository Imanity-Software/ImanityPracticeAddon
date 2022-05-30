package org.imanity.addon.practice.provider;

import dev.imanity.knockback.api.Knockback;
import dev.imanity.knockback.api.KnockbackService;
import org.bukkit.entity.Player;
import org.imanity.addon.practice.ImanityPracticeAddon;

public abstract class PracticeProvider {
    protected ImanityPracticeAddon plugin;

    public PracticeProvider(final ImanityPracticeAddon plugin) {
        this.plugin = plugin;
    }

    public abstract String getRequiredPlugin();

    public String getName() {
        return this.getRequiredPlugin();
    }

    public abstract void registerKnockbackImplementation();

    public void pickKitKnockback(final Player player, final String kitName) {
        final String profile = this.plugin.getProfileFromKit(kitName);
        final KnockbackService knockbackService = this.plugin.getServer().imanity().getKnockbackService();

        // Use global knockback if profile doesnt exist
        final Knockback knockback = knockbackService.getKnockbackByName(profile);
        knockbackService.setKnockback(player, knockback);
    }
}
