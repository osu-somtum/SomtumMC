package fur.unityg.somtummc.Utils;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import fur.unityg.somtummc.SomtumMC;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicJoin {
    private final SomtumMC plugin;
    private boolean musicEnabled = true;
    private RadioSongPlayer songPlayer; // Add this field to keep track of the currently playing song

    public MusicJoin(SomtumMC plugin) {
        this.plugin = plugin;
    }

    public void playMusic(Player player, String songName) {
        if (musicEnabled) {
            File songFile = new File(plugin.getDataFolder() + File.separator + "songs", songName);
            Song song = NBSDecoder.parse(songFile);
            RadioSongPlayer rsp = new RadioSongPlayer(song);
            rsp.setPlaying(false);
            if (song != null) {
                rsp.addPlayer(player);
                rsp.setPlaying(true);
                player.sendMessage("§aNow playing: §e" + song.getTitle());
                songPlayer = rsp; // Store the reference to the song player
            } else {
                player.sendMessage("§cInvalid NBS File");
            }
        }
    }

    public void stopMusic(Player player) {
        if (songPlayer != null) {
            songPlayer.setPlaying(false);
            player.sendMessage("Music stopped.");
        } else {
            player.sendMessage("No music is currently playing.");
        }
    }

    public List<String> getSongList() {
        List<String> songList = new ArrayList<>();
        File songsFolder = new File(plugin.getDataFolder(), "songs");
        if (songsFolder.exists() && songsFolder.isDirectory()) {
            File[] songFiles = songsFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".nbs"));
            if (songFiles != null) {
                for (File songFile : songFiles) {
                    songList.add(songFile.getName());
                }
            }
        }
        return songList;
    }

    public boolean toggleMusic() {
        musicEnabled = !musicEnabled;
        return musicEnabled;
    }
}
