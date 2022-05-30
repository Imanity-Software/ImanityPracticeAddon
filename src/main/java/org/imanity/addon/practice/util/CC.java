package org.imanity.addon.practice.util;

import org.bukkit.ChatColor;

public final class CC {

    private CC() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
