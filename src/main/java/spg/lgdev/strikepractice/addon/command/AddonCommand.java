package spg.lgdev.strikepractice.addon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import spg.lgdev.strikepractice.addon.iSpigotStrikePracticeAddon;

public class AddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        iSpigotStrikePracticeAddon.load();
        commandSender.sendMessage("§aReload success.");
        return false;
    }
}
