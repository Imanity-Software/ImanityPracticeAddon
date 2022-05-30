package org.imanity.addon.practice.provider.impl;

import net.pandamc.yang.YangAPI;
import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.addon.practice.provider.PracticeProvider;

public class YangPracticeProviderImpl extends PracticeProvider {

    public YangPracticeProviderImpl(final ImanityPracticeAddon plugin) {
        super(plugin);
    }

    @Override
    public String getRequiredPlugin() {
        return "Yang";
    }

    @Override
    public void registerKnockbackImplementation() {
        YangAPI.setKnockbackProfile(this::pickKitKnockback);
    }
}
