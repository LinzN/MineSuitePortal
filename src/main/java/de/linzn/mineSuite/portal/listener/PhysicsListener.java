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

package de.linzn.mineSuite.portal.listener;

import de.linzn.mineSuite.portal.api.PortalManager;
import de.linzn.mineSuite.portal.object.Portal;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PhysicsListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (!(e.getBlock().isLiquid() || e.getBlock().getType() == Material.NETHER_PORTAL
                || e.getBlock().getType() == Material.END_PORTAL
                || e.getBlock().getType() == Material.SUGAR_CANE)) {
            return;
        }
        if (!PortalManager.portalMap.containsKey(e.getBlock().getWorld())) {
            return;
        }

        for (Portal p : PortalManager.portalMap.get(e.getBlock().getWorld())) {
            if (p.isBlockInPortal(e.getBlock())) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockPhysics(BlockFromToEvent e) {
        if (!(e.getBlock().isLiquid() || e.getBlock().getType() == Material.NETHER_PORTAL
                || e.getBlock().getType() == Material.END_PORTAL
                || e.getBlock().getType() == Material.SUGAR_CANE)) {
            return;
        }
        if (!PortalManager.portalMap.containsKey(e.getBlock().getWorld())) {
            return;
        }

        for (Portal p : PortalManager.portalMap.get(e.getBlock().getWorld())) {
            if (p.isBlockInPortal(e.getBlock())) {
                e.setCancelled(true);
            }
        }

    }
}
