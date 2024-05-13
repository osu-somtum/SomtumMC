package fur.unityg.somtummc.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.GREEN + "Welcome to MC!Somtum");
            player.sendMessage(ChatColor.GREEN + "These are rules you need to know!");
            player.sendMessage(ChatColor.GREEN + "  1: " + ChatColor.RED + ChatColor.UNDERLINE + "No Griefing" + ChatColor.GREEN + ", Griefing is not allow. You will be perma banned from the server.");
            player.sendMessage(ChatColor.GREEN + "  2: " + ChatColor.RED + ChatColor.UNDERLINE + "No Racism" + ChatColor.GREEN + ", Racism is not allow. Racism such as saying the N Word is not allowed.");
            player.sendMessage(ChatColor.GREEN + "  3: " + ChatColor.RED + ChatColor.UNDERLINE + "No Hacking" + ChatColor.GREEN + ", No hacks are allow on the server.");
            player.sendMessage(ChatColor.GREEN + "  4: " + ChatColor.RED + ChatColor.UNDERLINE + "Have Common Sense" + ChatColor.GREEN + ", Atleast have some common sense.");
            player.sendMessage(ChatColor.GREEN + "That's all the basic rules you have to know. Thanks for playing and have fun!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
        } else if (!(sender instanceof Player)) {
            sender.sendMessage("Hello, You are not the father.");
        }
        return true;
    }
}
