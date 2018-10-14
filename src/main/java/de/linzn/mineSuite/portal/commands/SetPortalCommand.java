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

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.Region;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.core.configurations.YamlFiles.GeneralLanguage;
import de.linzn.mineSuite.portal.PortalPlugin;
import de.linzn.mineSuite.portal.object.Portal;
import de.linzn.mineSuite.portal.socket.JClientPortalOutput;
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

    private static void generate(Player player, String server, String name, String type, String dest,
                                 String filltype) {
        Region region = null;
        try {
            region = PortalPlugin.WORLDEDIT.getSession(player).getSelection(new BukkitWorld(player.getWorld()));
        } catch (IncompleteRegionException e) {
            e.printStackTrace();
        }

        if (region == null) {
            player.sendMessage(GeneralLanguage.portal_NO_SELECTION);
            return;
        }
        Vector max = region.getMaximumPoint();
        Vector min = region.getMinimumPoint();

        Portal portal = new Portal(name, filltype, max, min, player.getWorld());
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
                    sender.sendMessage(GeneralLanguage.portal_SETPORTAL_USAGE);
                }
            });
        } else {
            sender.sendMessage(GeneralLanguage.global_NO_PERMISSIONS);
        }
        return false;
    }
}
