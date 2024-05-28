package fur.unityg.somtummc.Commands;

import fur.unityg.somtummc.Utils.MusicJoin;
import fur.unityg.somtummc.SomtumMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlaySong implements CommandExecutor, TabCompleter {
    private final SomtumMC plugin;
    private final MusicJoin musicJoin;

    public PlaySong(SomtumMC plugin, MusicJoin musicJoin) {
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
        if (args.length != 1) {
            player.sendMessage("§cUsage: /playsong <song>");
            return true;
        }

        String songName = args[0];
        musicJoin.playMusic(player, songName);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> songList = musicJoin.getSongList();
            completions.addAll(songList);
        }
        return completions;
    }
}
