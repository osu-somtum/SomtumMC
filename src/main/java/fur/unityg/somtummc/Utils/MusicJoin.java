package fur.unityg.somtummc.Utils;

import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import fur.unityg.somtummc.SomtumMC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicJoin {
    private final SomtumMC plugin;
    private boolean musicEnabled = true;
    private RadioSongPlayer songPlayer; // Add this field to keep track of the currently playing song
    private SongEndListener songEndListener;

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
                player.sendMessage("§aNow playing: §e" + song.getPath());
                songPlayer = rsp;
                // Register the song end listener when starting the song
                songEndListener = new SongEndListener(player, this);
                plugin.getServer().getPluginManager().registerEvents(songEndListener, plugin);
            } else {
                player.sendMessage("§cInvalid NBS File");
            }
        }
    }

    public void stopMusic(Player player) {
        if (songPlayer != null) {
            songPlayer.setPlaying(false);
            // Unregister the song end listener when stopping the song
            if (songEndListener != null) {
                HandlerList.unregisterAll(songEndListener);
            }
        } else {
            player.sendMessage("No music is currently playing.");
        }
    }
    public boolean isPlaying(Player player) {
        return songPlayer != null && songPlayer.getPlayerUUIDs().contains(player.getUniqueId());
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
    public static class SongEndListener implements Listener {
        private final Player player;
        private final MusicJoin musicJoin;

        public SongEndListener(Player player, MusicJoin musicJoin) {
            this.player = player;
            this.musicJoin = musicJoin;
        }

        @EventHandler
        public void onSongEnd(com.xxmicloxx.NoteBlockAPI.event.SongEndEvent event) {
            SongPlayer songPlayer = event.getSongPlayer();
            if (songPlayer.equals(musicJoin.songPlayer)) {
                player.sendMessage("§aThe song has ended.");
                // Unregister the listener after handling the event
                HandlerList.unregisterAll(this);
            }
        }
    }
}
