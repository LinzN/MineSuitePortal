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

import de.linzn.mineSuite.portal.PortalPlugin;
import de.linzn.mineSuite.portal.api.PortalManager;
import de.linzn.mineSuite.portal.object.Portal;
import de.linzn.mineSuite.portal.socket.JClientPortalOutput;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class PlayerMoveListener implements Listener {

    public static HashSet<String> portalPending = new HashSet<>();

    @EventHandler(ignoreCancelled = true)
    public void PlayerMove(PlayerMoveEvent e) {
        if (!PlayerMoveListener.portalPending.contains(e.getPlayer().getName())) {
            if (e.getPlayer().hasMetadata("NPC"))
                return; // Ignore NPCs
            Block t = e.getTo().getBlock();
            Block f = e.getFrom().getBlock();
            if (f.equals(t)) {
                return;
            }
            if (!PortalManager.portalMap.containsKey(t.getWorld())) {
                return;
            }
            for (Portal p : PortalManager.portalMap.get(t.getWorld())) {
                if (p.isBlockInPortal(t)) {
                    PlayerMoveListener.portalPending.add(e.getPlayer().getName());
                    Vector unitVector = e.getFrom().toVector().subtract(e.getTo().toVector()).normalize();
                    Location l = e.getPlayer().getLocation();
                    l.setYaw(l.getYaw() + 180);
                    e.getPlayer().teleport(l);
                    e.getPlayer().setVelocity(unitVector.multiply(0.3));
                    JClientPortalOutput.sendPortalUse(p, e.getPlayer().getUniqueId());
                    removePending(e.getPlayer());
                }
            }
        }

    }

    public void removePending(final Player p) {

        Bukkit.getScheduler().runTaskLaterAsynchronously(PortalPlugin.inst(), () -> PlayerMoveListener.portalPending.remove(p.getName()), 30);
    }

    @EventHandler(ignoreCancelled = true)
    public void PlayerPortal(PlayerPortalEvent e) {
        if (e.getPlayer().hasMetadata("NPC"))
            return; // Ignore NPCs
        Block b;
        Block f = e.getFrom().getBlock();
        if (!PortalManager.portalMap.containsKey(f.getWorld())) {
            return;
        }
        if (f.getRelative(BlockFace.NORTH).getType() == Material.PORTAL
                || f.getRelative(BlockFace.NORTH).getType() == Material.ENDER_PORTAL) {
            b = f.getRelative(BlockFace.NORTH);
        } else if (f.getRelative(BlockFace.EAST).getType() == Material.PORTAL
                || f.getRelative(BlockFace.EAST).getType() == Material.ENDER_PORTAL) {
            b = f.getRelative(BlockFace.EAST);
        } else if (f.getRelative(BlockFace.SOUTH).getType() == Material.PORTAL
                || f.getRelative(BlockFace.SOUTH).getType() == Material.ENDER_PORTAL) {
            b = f.getRelative(BlockFace.SOUTH);
        } else if (f.getRelative(BlockFace.WEST).getType() == Material.PORTAL
                || f.getRelative(BlockFace.WEST).getType() == Material.ENDER_PORTAL) {
            b = f.getRelative(BlockFace.WEST);
        } else {
            return;
        }
        for (Portal p : PortalManager.portalMap.get(f.getWorld())) {
            if (p.isBlockInPortal(b)) {
                e.setCancelled(true);
            }
        }
    }

}
