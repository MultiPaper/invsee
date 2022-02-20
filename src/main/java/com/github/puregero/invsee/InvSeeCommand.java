package com.github.puregero.invsee;

import com.github.puregero.multilib.MultiLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class InvSeeCommand implements CommandExecutor {
    public InvSeeCommand(InvSee invSee) {
        PluginCommand command = invSee.getCommand("invsee");
        if (command != null) {
            command.setExecutor(this);
        } else {
            invSee.getLogger().warning("Could not find command /invsee");
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission("invsee")) {
            player.sendMessage(ChatColor.RED + "Insufficient permissions.");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
            return false;
        }

        Player otherPlayer = Bukkit.getPlayer(args[0]);

        if (otherPlayer == null) {
            player.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
            return false;
        }

        if (MultiLib.isExternalPlayer(otherPlayer)) {
            if (MultiLib.isLocalPlayer(player)) {
                MultiLib.chatOnOtherServers(player, "/" + label + " " + args[0]);
            }
        } else {
            player.openInventory(otherPlayer.getInventory());
        }

        return true;
    }
}
