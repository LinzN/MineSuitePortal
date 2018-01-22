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

package de.linzn.mineSuite.portal.api;


import de.linzn.mineSuite.portal.PortalPlugin;
import de.linzn.mineSuite.portal.object.Portal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;

public class PortalManager {

    public static HashMap<World, ArrayList<Portal>> portalMap = new HashMap<>();


    private static Portal getPortal(String name) {
        for (ArrayList<Portal> list : portalMap.values()) {
            for (Portal p : list) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }
        return null;
    }

    public static void enablePortalFrame(String portalName, String type, Location max, Location min) {
        if (max.getWorld() == null) {
            Bukkit.getConsoleSender()
                    .sendMessage(ChatColor.RED + "World does not exist portal " + portalName + " will not load :(");
            return;
        }
        Portal portal = new Portal(portalName, type, max, min);
        ArrayList<Portal> worldPortals = portalMap.computeIfAbsent(max.getWorld(), k -> new ArrayList<>());
        worldPortals.add(portal);
        System.out.println("Enable portal " + portalName);
        PortalPlugin.inst().getServer().getScheduler().runTask(PortalPlugin.inst(), portal::fillPortal);
    }

    public static void disablePortalFrame(String portalName) {
        Portal portal = getPortal(portalName);
        System.out.println("Disable portal " + portalName);
        if (portal != null) {
            portalMap.get(portal.getWorld()).remove(portal);
            PortalPlugin.inst().getServer().getScheduler().runTask(PortalPlugin.inst(), portal::clearPortal);

        }
    }
}
