package fur.unityg.somtummc.Discord;

import fur.unityg.somtummc.SomtumMC;
import fur.unityg.somtummc.Utils.MusicJoin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
    private final SomtumMC plugin;
    private final LuckPerms luckPerms;
    private final MusicJoin musicJoin;

    public PlayerEvent(JDA jda, SomtumMC plugin, LuckPerms luckPerms, MusicJoin musicJoin) {
        this.jda = jda;
        this.plugin = plugin;
        this.luckPerms = luckPerms;
        this.musicJoin = musicJoin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPlayedBefore()) {
            Player player = event.getPlayer();
            musicJoin.playMusic(player, "fnafsong.nbs");
            CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
            String prefixWC = metaData.getPrefix();
            String prefix = removeColorCodes(prefixWC);
            TextChannel channel = jda.getTextChannelById("1238520224647745568");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(prefix + event.getPlayer().getName() + " joined the server!", "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            embed.setColor(new Color(64, 176, 56));
            if (channel != null) {
                channel.sendMessageEmbeds(embed.build()).queue();
            } else {
                Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
            }
        }
        else {
            Player player = event.getPlayer();
            musicJoin.playMusic(player, "fnafsong.nbs");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "This is your first time playing!" + ChatColor.GREEN + " Please do /rules before playing! :3");
            TextChannel channel = jda.getTextChannelById("1238520224647745568");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(event.getPlayer().getName() + " joined the server for the first time ever!", "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            embed.setColor(new Color(64, 176, 56));
            if (channel != null) {
                channel.sendMessageEmbeds(embed.build()).queue();
            } else {
                Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
        String prefixWC = metaData.getPrefix();
        String prefix = removeColorCodes(prefixWC);
        TextChannel channel = jda.getTextChannelById("1238520224647745568");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(252, 68, 35));
        embed.setAuthor(prefix + event.getPlayer().getName() + " left the server!", "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
    @EventHandler
    public void onPlayerAR(PlayerAdvancementDoneEvent event) {
        TextChannel channel = jda.getTextChannelById("1238520224647745568");

        if (channel != null) {
            if (event.getAdvancement().getDisplay() != null) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(64, 176, 56)); // Thank 719505873877205082 for the fix
                String advancement = PlainTextComponentSerializer.plainText().serialize(event.getAdvancement().getDisplay().displayName());
                embed.setAuthor(event.getPlayer().getName() + " has made the advancement " + advancement, "https://example.com", "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                channel.sendMessageEmbeds(embed.build()).queue();
            }
            else {
                return;
                /* Bukkit.getServer().getLogger().warning("advancement is null - not sending an bedem"); */
            }
        } else {
            Bukkit.getServer().getLogger().warning("Discord channel not found with ID: 1238726409942470749");
        }
    }
    @EventHandler
    public void onPlayerDied(PlayerDeathEvent event) {
        TextChannel channel = jda.getTextChannelById("1238520224647745568");
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
        TextChannel channel = jda.getTextChannelById("1238520224647745568");
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        String[] blacklistwords = {"nigga", "nigger", "@everyone", "@here", "faggot", "fag", "shutcreisthebest"};
        boolean blacklist = false;
        for (int i = 0; i <= blacklistwords.length - 1; i++){
            if (message.contains(blacklistwords[i])){
                blacklist = true;
            }
        }
        if (blacklist){
            event.setCancelled(true);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + "» " + ChatColor.RED + "You are not allowed to type this in the chat...");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return;
        }
        if (channel != null) {
            CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
            String prefixWC = metaData.getPrefix();
            String prefix = removeColorCodes(prefixWC);
            channel.sendMessage("**" + prefix + "**" + event.getPlayer().getName() + " » " + event.getMessage()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
    private String removeColorCodes(String input) {
        Pattern pattern = Pattern.compile("&[0-9a-fklmnor]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
