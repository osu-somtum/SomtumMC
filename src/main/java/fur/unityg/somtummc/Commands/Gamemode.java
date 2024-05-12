package fur.unityg.somtummc.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Yo Bro You Are Not Player - Fropple");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("somtum.gamemode")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /gamemode <mode> [player]");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        GameMode mode = null;

        String argLower = args[0].toLowerCase();

        switch (argLower) {
            case "0":
            case "survival":
            case "s":
            case "youfuckinglovesurvival":
                mode = GameMode.SURVIVAL;
                break;
            case "1":
            case "creative":
            case "cheating":
            case "c":
                mode = GameMode.CREATIVE;
                break;
            case "2":
            case "adventure":
            case "youhatesurvival":
            case "a":
                mode = GameMode.ADVENTURE;
                break;
            case "3":
            case "spectator":
            case "noclipirl":
            case "sp":
                mode = GameMode.SPECTATOR;
                break;
        }

        if (mode == null) {
            player.sendMessage(ChatColor.RED + "Invalid gamemode specified.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        Player target = args.length > 1 ? Bukkit.getServer().getPlayer(args[1]) : player;

        if (target == null) {
            player.sendMessage(ChatColor.RED + "The player " + args[1] + " is not online.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        if (!player.hasPermission("somtum.gamemode." + mode.name().toLowerCase())) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
            return true;
        }

        target.setGameMode(mode);
        if (!target.equals(player)) {
            target.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.GREEN + "Your gamemode has been changed to " + ChatColor.GOLD + ChatColor.BOLD + target.getGameMode());
            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
        }
        player.sendMessage(ChatColor.LIGHT_PURPLE + "SomtumMC " + ChatColor.GRAY + " » " + ChatColor.GREEN + "You changed " + target.getName() + "'s gamemode to " + ChatColor.GOLD + ChatColor.BOLD + target.getGameMode());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            for (GameMode mode : GameMode.values()) {
                String modeName = mode.name().toLowerCase();
                if (modeName.startsWith(input)) {
                    completions.add(modeName);
                }
            }
        } else if (args.length == 2) {
            String input = args[1].toLowerCase();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String playerName = onlinePlayer.getName();
                if (playerName.toLowerCase().startsWith(input)) {
                    completions.add(playerName);
                }
            }
        }
        return completions;
    }
}