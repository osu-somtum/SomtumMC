package fur.unityg.somtummc.Discord;

import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class DiscCommand extends ListenerAdapter {
    Spark spark = SparkProvider.get();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equalsIgnoreCase("serverinfo")) {
            // getting playercount
            int online = Bukkit.getServer().getOnlinePlayers().size();
            int maxonline = Bukkit.getServer().getMaxPlayers();
            // Get server cpu usage and tps
            DoubleStatistic<StatisticWindow.TicksPerSecond> tps = spark.tps();

            int tpsLast10Secs = (int) tps.poll(StatisticWindow.TicksPerSecond.SECONDS_10);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Somtum Server Stats");
            embed.setColor(new Color(64, 176, 56));
            embed.setDescription(":exploding_head: Online Players: **" + online + "**/**" + maxonline + "**\n" +
                    ":zap: Server TPS: **" + tpsLast10Secs + "**\n");
            embed.setFooter("Among US Simulator 2024");
            event.replyEmbeds(embed.build()).queue();
        }
    }



    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("serverinfo", "Shows the server stats, Such as CPU Usage, TPS and Online Players!!"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("serverinfo", "Shows the server stats, Such as CPU Usage, TPS and Online Players!!"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
