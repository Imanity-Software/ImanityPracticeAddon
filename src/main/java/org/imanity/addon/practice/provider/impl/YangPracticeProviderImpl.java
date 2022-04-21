package org.imanity.addon.practice.provider.impl;

import net.pandamc.yang.YangAPI;
import net.pandamc.yang.knockback.KnockbackProfiler;
import org.bukkit.entity.Player;
import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.addon.practice.provider.PracticeProvider;

public class YangPracticeProviderImpl extends PracticeProvider implements KnockbackProfiler {

    public YangPracticeProviderImpl(ImanityPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "Yang";
    }

    @Override
    public void registerListeners() {
        YangAPI.setKnockbackProfile(this);
    }

    @Override
    public void setKnockback(Player player, String kitName) {
        this.pickKitKnockback(player, kitName);
    }
}
