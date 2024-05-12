package fur.unityg.somtummc;

import fur.unityg.somtummc.Commands.RulesCommand;
import fur.unityg.somtummc.Discord.DiscCommand;
import fur.unityg.somtummc.Discord.PlayerEvent;
import me.lucko.spark.api.Spark;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class SomtumMC extends JavaPlugin implements Listener {
    String token = "MTIzODUyMzc1NTk0NTUyNTMwOQ.GcELdS.E6k52w1TLclAlPGX32iXrxwNWPsjXueeBAzTwo";
    private ShardManager shardManager;
    public ShardManager getShardManager() {
        return shardManager;
    }
    private JDA jda;

    public JDA getJDA() {
        return jda;
    }

    @Override
    public void onEnable() {

        getLogger().info("============================================");
        getLogger().info("Hello World! Somtum Bot is starting up....");
        getLogger().info("I'M ABOUT TO FC BLUEZENITH HD HR OMG OMG cOMg");
        getLogger().info("============================================");

        // Registering Spark...

        RegisteredServiceProvider<Spark> provider = Bukkit.getServicesManager().getRegistration(Spark.class);
        if (provider != null) {
            Spark spark = provider.getProvider();
        }

        // Registering Discord Bot

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.playing("Among US"));
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_PRESENCES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        shardManager = builder.build();

        // Starting Embed

        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(64, 176, 56));
        embed.setAuthor("The server started up succesfully!!");
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }

        // Registering Slash Command and Minecraft Events

        this.getCommand("rules").setExecutor(new RulesCommand());

        PlayerEvent joinMSG = new PlayerEvent(shardManager.getShards().get(0));
        getServer().getPluginManager().registerEvents(joinMSG, this);
        shardManager.addEventListener(new DiscCommand());
    }

    @Override
    public void onDisable() {
        TextChannel channel = jda.getTextChannelById("1238726409942470749");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(128, 5, 5));
        embed.setAuthor("The server shutdownned succesfully!!");
        if (channel != null) {
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("MOTHA FAKER IRT NO FOUND AHHAHASFHASUIBFAQojasfhjknaefwjkhaefwj g");
        }
    }
}
