package de.kekshaus.cookieApi.portal.database.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void PlayerMove(PlayerMoveEvent e) {
		if (e.getPlayer().hasMetadata("NPC"))
			return; // Ignore NPCs
		Block t = e.getTo().getBlock();
		Block f = e.getFrom().getBlock();
		if (f.equals(t)) {
			return;
		}
		if (!PortalsManager.PORTALS.containsKey(t.getWorld())) {
			return;
		}
		for (Portal p : PortalsManager.PORTALS.get(t.getWorld())) {
			if (p.isBlockInPortal(t)) {

				PortalsManager.teleportPlayer(e.getPlayer(), p);
				Vector unitVector = e.getFrom().toVector().subtract(e.getTo().toVector()).normalize();
				Location l = e.getPlayer().getLocation();
				l.setYaw(l.getYaw() + 180);
				e.getPlayer().teleport(l);
				e.getPlayer().setVelocity(unitVector.multiply(0.3));
			}
		}

	}

	@EventHandler(ignoreCancelled = true)
	public void PlayerMove(PlayerPortalEvent e) {
		if (e.getPlayer().hasMetadata("NPC"))
			return; // Ignore NPCs
		Block b = null;
		Block f = e.getFrom().getBlock();
		if (!PortalsManager.PORTALS.containsKey(f.getWorld())) {
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
		for (Portal p : PortalsManager.PORTALS.get(f.getWorld())) {
			if (p.isBlockInPortal(b)) {
				e.setCancelled(true);
			}
		}
	}

}
