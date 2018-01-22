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

package de.linzn.mineSuite.portal;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.portal.api.PortalManager;
import de.linzn.mineSuite.portal.commands.SetPortalCommand;
import de.linzn.mineSuite.portal.commands.UnsetPortalCommand;
import de.linzn.mineSuite.portal.listener.PhysicsListener;
import de.linzn.mineSuite.portal.listener.PlayerMoveListener;
import de.linzn.mineSuite.portal.socket.JClientPortalListener;
import de.linzn.mineSuite.portal.socket.JClientPortalOutput;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalPlugin extends JavaPlugin {
    public static WorldEditPlugin WORLDEDIT = null;
    private static PortalPlugin inst;

    public static PortalPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        loadWorldEdit();
        loadCommands();
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PhysicsListener(), this);
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.registerIncomingDataListener("mineSuitePortal", new JClientPortalListener());
        this.getServer().getScheduler().runTaskLaterAsynchronously(this, () -> JClientPortalOutput.requestPortals(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME), 40);
    }

    @Override
    public void onDisable() {
        PortalManager.disableOnShutdown();
    }

    private void loadCommands() {
        getCommand("setportal").setExecutor(new SetPortalCommand(this));
        getCommand("unsetportal").setExecutor(new UnsetPortalCommand(this));
    }

    private void loadWorldEdit() {
        WORLDEDIT = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
    }
}
