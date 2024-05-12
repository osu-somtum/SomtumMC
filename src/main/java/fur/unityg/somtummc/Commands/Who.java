package fur.unityg.somtummc.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Who implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.RED + "Yo Bro You Are Not Player - Fropple");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("somtum.who")) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.RED + "You don't have permission to use this command.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.RED + "Usage: /who [player]");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]); // Changed args[1] to args[0] to get the first argument

        if (target == null) {
            player.sendMessage(ChatColor.RED + "The player " + args[0] + " is not online.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        player.sendMessage(ChatColor.GOLD + "you no allow dox!");
        return true;
    }
}
