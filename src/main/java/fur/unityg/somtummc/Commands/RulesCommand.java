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
            player.sendMessage(ChatColor.GREEN + "ฟากเขียนกฎให้หน่อย ไอเอมขก, will change color code later ig");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
        } else if (!(sender instanceof Player)) {
            sender.sendMessage("Hello, You are not the father.");
        }
        return true;
    }
}
