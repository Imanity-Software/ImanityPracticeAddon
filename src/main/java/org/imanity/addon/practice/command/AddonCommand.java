package org.imanity.addon.practice.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.imanity.addon.practice.ImanityPracticeAddon;
import org.imanity.addon.practice.util.CC;

import java.util.function.Consumer;

public class AddonCommand implements CommandExecutor {

    private final ImanityPracticeAddon plugin;

    public AddonCommand(final ImanityPracticeAddon plugin) {
        this.plugin = plugin;
    }

    private static final String PREFIX = CC.translate("&a[ImanityPracticeAddon]");
    private static final String PREFIX_ERROR = CC.translate("&c[ImanityPracticeAddon]");

    private static final Consumer<CommandSender> HELP_MESSAGE = (sender -> {
        sender.sendMessage(CC.translate(PREFIX + "&f Command Usages:"));
        sender.sendMessage(CC.translate("  &7/practice-addon reload &a- Reloads the addon configuration"));
        sender.sendMessage(CC.translate("  &7/practice-addon status &a- Shows the current addon status, including what practice plugin it had hooked and what knockback profiles are loaded"));
        sender.sendMessage(CC.translate("  &7/practice-addon link [kit] [knockback] &a- Links a practice kit's knockback profile with an ImanitySpigot's knockback profile"));
        sender.sendMessage(CC.translate("  &7/practice-addon unlink [kit] &a- Unlinks a kit from its linked knockback profile"));
    });

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!sender.hasPermission("imanity.practiceaddon.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have the permission to perform this command.");
            return false;
        }
        if (args.length <= 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            HELP_MESSAGE.accept(sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            final long start = System.currentTimeMillis();
            this.plugin.reloadConfig();
            final long end = System.currentTimeMillis();

            sender.sendMessage(CC.translate(PREFIX + "&f Reloaded addon in " + (end - start) + "ms."));
            return true;

        } else if (args[0].equalsIgnoreCase("status")) {
            sender.sendMessage(CC.translate(PREFIX + "&f Current Status:"));
            sender.sendMessage(CC.translate("&f  Provider: &a" + this.plugin.getCurrentProvider().getName()));
            sender.sendMessage(CC.translate("&f  Default Knockback: &aIf the profile to be applied does not exist, or if it is not defined, a knockback profile will be chosen automatically from those that have the \"default=true\" option"));
            sender.sendMessage(CC.translate("&f  Loaded Knockbacks (Linked with specific kits): "));
            this.plugin.getKnockbackProfiles()
                    .forEach((kit, profile) -> sender.sendMessage(CC.translate("&7    -  &fPractice Kit &a" + kit + "&f has been linked with &a" + profile)));
            return true;

        } else if (args[0].equalsIgnoreCase("link")) {
            if (args.length < 3) {
                sender.sendMessage(CC.translate(PREFIX_ERROR + " Usage: /practice-addon link [kitName] [profileName]"));
                return true;
            }
            final String kitName = args[1];
            final String profileName = args[2];

            if (this.plugin.getServer().imanity().getKnockbackService().getKnockbackByName(profileName) == null) {
                sender.sendMessage(CC.translate(PREFIX_ERROR + " The specified knockback profile does not exist in ImanitySpigot."));
                return true;
            }
            if (this.plugin.getProfileFromKit(kitName) != null) {
                this.plugin.getKnockbackProfiles().replace(kitName, profileName);
            } else {
                this.plugin.getKnockbackProfiles().put(kitName, profileName);
            }
            this.plugin.saveConfig();

            sender.sendMessage(CC.translate(PREFIX + "&f You have linked the kit &a" + kitName + "&f with a knockback profile named &a" + profileName + "&f."));
            return true;

        } else if (args[0].equalsIgnoreCase("unlink")) {
            if (args.length < 2) {
                sender.sendMessage(CC.translate(PREFIX_ERROR + " Usage: /practice-addon unlink [kitName]"));
                return true;
            }
            final String kitName = args[1];

            if (this.plugin.getProfileFromKit(kitName) == null) {
                sender.sendMessage(CC.translate(PREFIX_ERROR + " The kit " + kitName + " does not have a knockback profile linked yet."));
                return true;
            }
            this.plugin.getKnockbackProfiles().remove(kitName);
            this.plugin.saveConfig();

            sender.sendMessage(CC.translate(PREFIX + "&f You have unlinked the kit &a" + kitName + "&f's knockback profile"));
            return true;

        } else {
            HELP_MESSAGE.accept(sender);
            return true;
        }
    }

}
