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

import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.core.configurations.YamlFiles.GeneralLanguage;
import de.linzn.mineSuite.portal.PortalPlugin;
import de.linzn.mineSuite.portal.socket.JClientPortalOutput;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UnsetPortalCommand implements CommandExecutor {
    public ThreadPoolExecutor executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    public UnsetPortalCommand(PortalPlugin instance) {
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        final Player player = (Player) sender;
        final String serverName = MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME;
        if (player.hasPermission("mineSuite.portal.delportal")) {
            this.executorServiceCommands.submit(() -> {
                if (args.length >= 1) {
                    JClientPortalOutput.deletePortal(player.getUniqueId(), args[0], serverName);
                } else {
                    sender.sendMessage(GeneralLanguage.portal_UNSETPORTAL_USAGE);
                }
            });
        } else {
            sender.sendMessage(GeneralLanguage.global_NO_PERMISSIONS);
        }
        return false;
    }
}
