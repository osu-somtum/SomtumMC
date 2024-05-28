package fur.unityg.somtummc.Commands;

import fur.unityg.somtummc.Utils.MusicJoin;
import fur.unityg.somtummc.SomtumMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopSong implements CommandExecutor {
    private final SomtumMC plugin;
    private final MusicJoin musicJoin;

    public StopSong(SomtumMC plugin, MusicJoin musicJoin) {
        this.plugin = plugin;
        this.musicJoin = musicJoin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        musicJoin.stopMusic(player);
        player.sendMessage("§aMusic stopped.");
        return true;
    }
}
