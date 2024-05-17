package fur.unityg.somtummc;

import fur.unityg.somtummc.Commands.Gamemode;
import fur.unityg.somtummc.Commands.RulesCommand;
import fur.unityg.somtummc.Commands.Sethome;
import fur.unityg.somtummc.Discord.DiscCommand;
import fur.unityg.somtummc.Discord.DiscordToMinecraftChat;
import fur.unityg.somtummc.Discord.PlayerEvent;
import fur.unityg.somtummc.Utils.LocationUtils;

import me.lucko.spark.api.Spark;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SomtumMC extends JavaPlugin implements Listener {
    private Map<String, Location> homes = new HashMap<>();
    String token = "MTIzODUyMzc1NTk0NTUyNTMwOQ.GcELdS.E6k52w1TLclAlPGX32iXrxwNWPsjXueeBAzTwo";
    private ShardManager shardManager;
    public ShardManager getShardManager() {
        return shardManager;
    }
    private File homesFile;
    private FileConfiguration homesConfig;
    public Map<String, Location> getHomes() {
        return homes;
    }
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        homesFile = new File(getDataFolder(), "homes.yml");
        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        loadHomes();
        getLogger().info("============================================");
        getLogger().info("Hello World! Somtum Bot is starting up....");
        getLogger().info("I'M ABOUT TO FC BLUEZENITH HD HR 727pp OMG OMG cOMg");
        getLogger().info("============================================");

        // Registering Spark... and LUCKY PERMISSION

        RegisteredServiceProvider<Spark> provider = Bukkit.getServicesManager().getRegistration(Spark.class);
        if (provider != null) {
            Spark spark = provider.getProvider();
        }
        this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        // Registering Discord Bot

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.playing("Among US"));
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        shardManager = builder.build();

        // Embed
        shardManager.addEventListener(new ListenerAdapter() {
            @Override
            public void onReady(ReadyEvent event) {
                // JDA has finished loading
                TextChannel channel = shardManager.getTextChannelById("1238726409942470749");
                if (channel != null) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor("The server has successfully started up!");
                    embed.setColor(new Color(64, 176, 56));
                    channel.sendMessageEmbeds(embed.build()).queue();
                } else {
                    Bukkit.getServer().getLogger().warning("Relay channel not found.");
                }
            }
        });

        // Registering Slash Command and Minecraft Events

        this.getCommand("rules").setExecutor(new RulesCommand());
        this.getCommand("gamemode").setExecutor(new Gamemode());
        this.getCommand("gamemode").setTabCompleter(new Gamemode());
        this.getCommand("sethome").setExecutor(new Sethome(this));
        this.getCommand("home").setExecutor(new Sethome(this));
        this.getCommand("delhome").setExecutor(new Sethome(this));

        PlayerEvent joinMSG = new PlayerEvent(shardManager.getShards().get(0), this, luckPerms);
        getServer().getPluginManager().registerEvents(joinMSG, this);
        shardManager.addEventListener(new DiscCommand());
        shardManager.addEventListener(new DiscordToMinecraftChat(shardManager.getShards().get(0)));
        getServer().getPluginManager().registerEvents(new DiscordToMinecraftChat(shardManager.getShards().get(0)), this);
    }

    @Override
    public void onDisable() {
        saveHomes();
        TextChannel channel = shardManager.getTextChannelById("1238726409942470749");
        if (channel != null) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("The server has successfully stopped!");
            embed.setColor(new Color(100, 5, 18));
            channel.sendMessageEmbeds(embed.build()).queue();
        } else {
            Bukkit.getServer().getLogger().warning("Relay channel not found.");
        }
    }
    public void setHome(Player player) {
        homes.put(player.getUniqueId().toString(), player.getLocation());
        player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC" + ChatColor.GRAY + " » " + ChatColor.GOLD + "You have successfully set your home!");
        saveHomes(); // save home when it set bnecause i no lose atafadat
    }

    private void loadHomes() {
        if (homesFile.exists()) {
            ConfigurationSection homesSection = homesConfig.getConfigurationSection("homes");
            if (homesSection != null) {
                for (String playerId : homesSection.getKeys(false)) {
                    String locationString = homesSection.getString(playerId);
                    Location location = LocationUtils.fromString(locationString);
                    if (location != null) {
                        homes.put(playerId, location);
                    }
                }
            }
        }
    }
    private void saveHomes() {
        homesConfig.set("homes", null);
        for (Map.Entry<String, Location> entry : homes.entrySet()) {
            homesConfig.set("homes." + entry.getKey(), LocationUtils.toString(entry.getValue()));
        }
        try {
                homesConfig.save(homesFile);
        } catch (IOException e) {
                getLogger().warning("Error saving homes file: " + e.getMessage());
        }
    }
}