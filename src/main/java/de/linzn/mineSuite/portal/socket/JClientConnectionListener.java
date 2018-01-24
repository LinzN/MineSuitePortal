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

package de.linzn.mineSuite.portal.socket;

import de.linzn.jSocket.core.ConnectionListener;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.portal.PortalPlugin;
import de.linzn.mineSuite.portal.api.PortalManager;
import org.bukkit.Bukkit;

import java.util.UUID;

public class JClientConnectionListener implements ConnectionListener {

    @Override
    public void onConnectEvent(UUID uuid) {
        PortalPlugin.inst().getServer().getScheduler().runTaskLaterAsynchronously(PortalPlugin.inst(), () -> JClientPortalOutput.requestPortals(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME), 40);
    }

    @Override
    public void onDisconnectEvent(UUID uuid) {
        Bukkit.getScheduler().runTask(PortalPlugin.inst(), PortalManager::disableOnShutdown);
    }
}
