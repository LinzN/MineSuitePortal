/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.mineSuite.portal.commands;

import com.sk89q.worldedit.bukkit.selections.Selection;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.portal.PortalPlugin;
import de.linzn.mineSuite.portal.object.Portal;
import de.linzn.mineSuite.portal.socket.JClientPortalOutput;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SetPortalCommand implements CommandExecutor {
    public ThreadPoolExecutor executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    public SetPortalCommand(PortalPlugin instance) {
    }

    private static void generate(Player player, String server, String name, String type, String dest,
                                 String filltype) {
        Selection sel = PortalPlugin.WORLDEDIT.getSelection(player);
        Location max = sel.getMaximumPoint();
        Location min = sel.getMinimumPoint();

        Portal portal = new Portal(name, filltype, max, min);
        JClientPortalOutput.createPortal(server, player.getUniqueId(), type, dest, portal);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmnd, String label, final String[] args) {
        final Player player = (Player) sender;
        final String server = MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME;
        if (player.hasPermission("mineSuite.portal.setportal")) {
            this.executorServiceCommands.submit(() -> {
                if (args.length >= 3) {
                    if (args.length == 3) {
                        generate(player, server, args[0], args[1], args[2], "AIR");
                    } else if (args.length == 4) {
                        generate(player, server, args[0], args[1], args[2], args[3]);
                    }
                } else {
                    sender.sendMessage("Benutze: /setportal [portalName] [portalType] [destination] <portalMaterial>");
                }
            });
        } else {
            sender.sendMessage(MineSuiteCorePlugin.getInstance().getMineConfigs().generalLanguage.NO_PERMISSIONS);
        }
        return false;
    }
}
