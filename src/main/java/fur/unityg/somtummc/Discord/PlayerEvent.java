package fur.unityg.somtummc.Discord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerEvent implements Listener {
    private JDA jda;

    public PlayerEvent(JDA jda) {
        this.jda = jda;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(event.getPlayer().getName() + " joined the server!", "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
        embed.setColor(new Color(64, 176, 56));
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(252, 68, 35));
        embed.setAuthor(event.getPlayer().getName() + " left the server!", "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent event) {
        TextComponent advancementName = (TextComponent) event.getAdvancement().displayName();
        String plainTextAdvancementName = PlainTextComponentSerializer.plainText().serialize((net.kyori.adventure.text.Component) advancementName);
        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(64, 176, 56));
        embed.setAuthor(event.getPlayer().getName() + " has made the advancement " + plainTextAdvancementName, "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
    @EventHandler
    public void onPlayerDied(PlayerDeathEvent event) {
        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(252, 68, 35));
        embed.setAuthor(event.getDeathMessage(), "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        String[] blacklistwords = {"nigga", "nigger", "@everyone", "@here"};
        boolean blacklist = false;
        for (int i = 0; i <= blacklistwords.length - 1; i++){
            if (message.contains(blacklistwords[i])){
                blacklist = true;
            }
        }
        if (blacklist){
            event.setCancelled(true);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + "» " + ChatColor.RED + "You are not allowed to type this in the chat...");
            return;
        }
        if (channel != null) {
            channel.sendMessage(event.getPlayer().getName() + " » " + event.getMessage()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
}
