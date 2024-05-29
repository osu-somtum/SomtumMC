package fur.unityg.somtummc.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class DiscordToMinecraftChat extends ListenerAdapter implements Listener {
    private final String discordChannelId = "1238520224647745568"; // Use final for channel ID
    private JDA jda;

    public DiscordToMinecraftChat(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getId().equals(discordChannelId)) {
            if (event.getMember().getUser().isBot()) {
            } else {
                String message = event.getMessage().getContentDisplay();
                Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "Discord" + ChatColor.GRAY + " » " + ChatColor.GOLD + "[" + event.getMember().getRoles() + "] " + event.getMember().getUser().getName() + ": " + ChatColor.RESET + message);
            }
        }
    }
}