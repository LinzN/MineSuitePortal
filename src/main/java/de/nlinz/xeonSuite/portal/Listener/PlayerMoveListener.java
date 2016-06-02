package de.nlinz.xeonSuite.portal.Listener;

import java.util.HashSet;

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

import de.nlinz.xeonSuite.portal.Portalplugin;
import de.nlinz.xeonSuite.portal.api.PTStreamInApi;
import de.nlinz.xeonSuite.portal.database.object.Portal;

public class PlayerMoveListener implements Listener {

	public static HashSet<String> portalPending = new HashSet<String>();

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
			if (!PTStreamInApi.PORTALS.containsKey(t.getWorld())) {
				return;
			}
			for (Portal p : PTStreamInApi.PORTALS.get(t.getWorld())) {
				if (p.isBlockInPortal(t)) {
					PlayerMoveListener.portalPending.add(e.getPlayer().getName());
					Vector unitVector = e.getFrom().toVector().subtract(e.getTo().toVector()).normalize();
					Location l = e.getPlayer().getLocation();
					l.setYaw(l.getYaw() + 180);
					e.getPlayer().teleport(l);
					e.getPlayer().setVelocity(unitVector.multiply(0.3));
					PTStreamInApi.teleportPlayer(e.getPlayer(), p);
					removePending(e.getPlayer());
				}
			}
		}

	}

	public void removePending(final Player p) {

		Bukkit.getScheduler().runTaskLaterAsynchronously(Portalplugin.inst(), new Runnable() {
			public void run() {
				PlayerMoveListener.portalPending.remove(p.getName());
			}
		}, 30);
	}

	@EventHandler(ignoreCancelled = true)
	public void PlayerPortal(PlayerPortalEvent e) {
		if (e.getPlayer().hasMetadata("NPC"))
			return; // Ignore NPCs
		Block b = null;
		Block f = e.getFrom().getBlock();
		if (!PTStreamInApi.PORTALS.containsKey(f.getWorld())) {
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
		for (Portal p : PTStreamInApi.PORTALS.get(f.getWorld())) {
			if (p.isBlockInPortal(b)) {
				e.setCancelled(true);
			}
		}
	}

}
