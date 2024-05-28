package fur.unityg.somtummc.Utils;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import fur.unityg.somtummc.SomtumMC;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Random;

public class MusicJoin {
    private final SomtumMC plugin;
    private boolean musicEnabled = true;

    public MusicJoin(SomtumMC plugin) {
        this.plugin = plugin;
    }

    public void playMusic(Player player) {
        if (musicEnabled) {
            String randomSongFile = getRandomSongFile();
            Song song = NBSDecoder.parse(new File(plugin.getDataFolder() + File.separator + "songs", randomSongFile));
            if (song != null) {
                RadioSongPlayer rsp = new RadioSongPlayer(song);
                rsp.addPlayer(player);
                rsp.setPlaying(true);
                player.sendMessage("§aNow playing: §e" + song.getTitle());
            } else {
                player.sendMessage("§cInvalid NBS File");
            }
        }
    }

    private String getRandomSongFile() {
        Random random = new Random();
        final String[] songFiles = {"Bad_Apple.nbs", "Smells_Like_Teen_Spirit.nbs", "Viva_La_Vida.nbs"};
        int index = random.nextInt(songFiles.length);
        return songFiles[index];
    }

    public boolean toggleMusic() {
        musicEnabled = !musicEnabled;
        return musicEnabled;
    }
}
