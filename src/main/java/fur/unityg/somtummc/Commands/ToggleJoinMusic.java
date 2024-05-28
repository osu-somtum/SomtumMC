package fur.unityg.somtummc.Commands;

import fur.unityg.somtummc.SomtumMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleJoinMusic implements CommandExecutor {
    private final SomtumMC plugin;

    public ToggleJoinMusic(SomtumMC plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            boolean musicEnabled = plugin.getMusicJoin().toggleMusic();
            sender.sendMessage("You have successfully " + (musicEnabled ? "enabled" : "disabled" + " join music!"));
        } else {
            sender.sendMessage("Only player can execute this command.");
        }
        return true;
    }
}
