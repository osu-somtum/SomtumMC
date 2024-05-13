package fur.unityg.somtummc.Commands;

import fur.unityg.somtummc.SomtumMC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sethome implements CommandExecutor {
    private SomtumMC plugin; // Reference to the main plugin class

    public Sethome(SomtumMC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("sethome")) {
            plugin.setHome(player);
            return true;
        }
        if (command.getName().equalsIgnoreCase("home")) {
            if (plugin.getHomes().containsKey(player.getUniqueId().toString())) {
                player.teleport(plugin.getHomes().get(player.getUniqueId().toString()));
                player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.GOLD + "You have teleported to your house :)");
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.RED + "You don't have any home set. Do /sethome to set a home.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("delhome")) {
            if (plugin.getHomes().containsKey(player.getUniqueId().toString())) {
                plugin.getHomes().remove(player.getUniqueId().toString());
                player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.RED + "You deleted your only home.");
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.RED + "You don't have a home set.");
            }
            return true;
        }

        return false;
    }
}
