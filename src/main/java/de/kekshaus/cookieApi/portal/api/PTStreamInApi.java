package de.kekshaus.cookieApi.portal.api;

import com.sk89q.worldedit.bukkit.selections.Selection;

import de.kekshaus.cookieApi.bukkit.CookieApiBukkit;
import de.kekshaus.cookieApi.portal.Portalplugin;
import de.kekshaus.cookieApi.portal.database.ConnectionInject;
import de.kekshaus.cookieApi.portal.database.object.Portal;
import de.kekshaus.cookieApi.warp.commands.WarpPortalApi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PTStreamInApi {

	public static HashMap<World, ArrayList<Portal>> PORTALS = new HashMap<>();

	public static void deletePortal(String name) {

		if (ConnectionInject.delPortal(name)) {
			removePortal(name);
		}
	}

	public static boolean loadPortals() {
		final String server = CookieApiBukkit.getServerName();
		if (ConnectionInject.getPortals(server)) {
			return true;
		}
		return false;
	}

	public static void removePortal(String name) {
		Portal p = getPortal(name);
		System.out.println("removing portal " + name);
		if (p != null) {
			PORTALS.get(p.getWorld()).remove(p);
			p.clearPortal();
		}
	}

	public static Portal getPortal(String name) {
		for (ArrayList<Portal> list : PORTALS.values()) {
			for (Portal p : list) {
				if (p.getName().equals(name)) {
					return p;
				}
			}
		}
		return null;
	}

	public static void setPortal(CommandSender sender, String server, String name, String type, String dest,
			String fill) {

		Player p = (Player) sender;
		Selection sel = Portalplugin.WORLDEDIT.getSelection(p);
		Location max = sel.getMaximumPoint();
		Location min = sel.getMinimumPoint();

		if (ConnectionInject.setPortal(p, server, name, type, dest, fill, min, max)) {
			addPortal(name, type, dest, fill, max, min);
		}
	}

	public static void addPortal(String name, String type, String dest, String filltype, Location max, Location min) {
		if (max.getWorld() == null) {
			Bukkit.getConsoleSender()
					.sendMessage(ChatColor.RED + "World does not exist portal " + name + " will not load :(");
			return;
		}
		final Portal portal = new Portal(name, type, dest, filltype, max, min);
		ArrayList<Portal> ps = PORTALS.get(max.getWorld());
		if (ps == null) {
			ps = new ArrayList<>();
			PORTALS.put(max.getWorld(), ps);
		}
		ps.add(portal);

		Portalplugin.inst().getServer().getScheduler().runTask(Portalplugin.inst(), new Runnable() {

			public void run() {
				portal.fillPortal();
			}
		});
	}

	public static void teleportPlayer(Player player, Portal p) {
		if (p.getType().equalsIgnoreCase("server")) {
			PTStreamOutApi.sendOtherServer(player.getName(), p.getDestination());
		} else if (p.getType().equalsIgnoreCase("warp")) {
			WarpPortalApi.warp(player, p.getDestination());
		}
	}

}
