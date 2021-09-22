package org.imanity.addon.practice.util;

import org.bukkit.ChatColor;

public class CC {
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
