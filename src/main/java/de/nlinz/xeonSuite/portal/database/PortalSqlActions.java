package de.nlinz.xeonSuite.portal.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.nlinz.xeonSuite.bukkit.database.XeonConnectionManager;
import de.nlinz.xeonSuite.portal.api.PTStreamInApi;

public class PortalSqlActions {

	public static boolean setPortal(Player p, String server, String name, String type, String dest, String fill,
			Location min, Location max) {

		String pname = name;
		String pserver = server;
		String ptype = type;
		String pdest = dest;
		String material = fill;
		String worldMax = max.getWorld().getName();
		double maxX = max.getX();
		double maxY = max.getY();
		double maxZ = max.getZ();
		String worldMin = min.getWorld().getName();
		double minX = min.getX();
		double minY = min.getY();
		double minZ = min.getZ();
		XeonConnectionManager manager = XeonConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("XeonSuite");
			PreparedStatement sql = conn
					.prepareStatement("SELECT portalname FROM portals WHERE portalname = '" + pname + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn.prepareStatement(
						"UPDATE portals SET server = '" + pserver + "', type = '" + ptype + "', destination = '" + pdest
								+ "', world = '" + worldMax + "', filltype = '" + material + "', xmax = '" + maxX
								+ "', xmin = '" + minX + "', ymax = '" + maxY + "', ymin = '" + minY + "', zmax = '"
								+ maxZ + "', zmin = '" + minZ + "' WHERE portalname = '" + pname + "';");
				update.executeUpdate();
				update.close();
			} else {
				PreparedStatement insert = conn.prepareStatement(
						"INSERT INTO portals (portalname, server, type, destination, world, filltype, xmax, xmin, ymax, ymin, zmax, zmin) VALUES('"
								+ pname + "', '" + pserver + "', '" + ptype + "', '" + pdest + "', '" + worldMin
								+ "', '" + material + "', '" + maxX + "', '" + minX + "', '" + maxY + "', '" + minY
								+ "', '" + maxZ + "', '" + minZ + "');");

				insert.executeUpdate();
				insert.close();
			}
			result.close();
			sql.close();
			manager.release("XeonSuite", conn);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean delPortal(String name) {
		String pname = name;
		XeonConnectionManager manager = XeonConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("XeonSuite");
			PreparedStatement sql = conn
					.prepareStatement("SELECT portalname FROM portals WHERE portalname = '" + pname + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn
						.prepareStatement("DELETE FROM portals WHERE portalname = '" + pname + "';");
				update.executeUpdate();
				update.close();
			}
			result.close();
			sql.close();
			manager.release("XeonSuite", conn);
			return true;

		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean getPortals(String server) {
		XeonConnectionManager manager = XeonConnectionManager.DEFAULT;
		try {

			Connection conn = manager.getConnection("XeonSuite");
			PreparedStatement sel = conn.prepareStatement("SELECT * FROM portals WHERE server = '" + server + "';");

			ResultSet result = sel.executeQuery();
			if (result != null) {

				while (result.next()) {
					String name = result.getString("portalname");
					String type = result.getString("type");
					String dest = result.getString("destination");
					String filltype = result.getString("filltype");
					Location min = new Location(Bukkit.getWorld(result.getString("world")), result.getInt("xmin"),
							result.getInt("ymin"), result.getInt("zmin"));
					Location max = new Location(Bukkit.getWorld(result.getString("world")), result.getInt("xmax"),
							result.getInt("ymax"), result.getInt("zmax"));
					PTStreamInApi.addPortal(name, type, dest, filltype, max, min);
				}
			}
			result.close();
			sel.close();
			manager.release("XeonSuite", conn);
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
