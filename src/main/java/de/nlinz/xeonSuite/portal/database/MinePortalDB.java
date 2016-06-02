package de.nlinz.xeonSuite.portal.database;

import java.sql.Connection;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.nlinz.xeonSuite.bukkit.XeonSuiteBukkit;
import de.nlinz.xeonSuite.portal.Portalplugin;

public class MinePortalDB {
	public static boolean create() {
		return mysql();

	}

	public static boolean mysql() {
		String db = XeonSuiteBukkit.getDataBase();
		String port = XeonSuiteBukkit.getPort();
		String host = XeonSuiteBukkit.getHost();
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = XeonSuiteBukkit.getUsername();
		String password = XeonSuiteBukkit.getPassword();
		ConnectionFactory factory = new ConnectionFactory(url, username, password);
		ConnectionManager manager = ConnectionManager.DEFAULT;
		ConnectionHandler handler = manager.getHandler("mineportal", factory);

		try {
			Connection connection = handler.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS portals (portalname VARCHAR(100), server VARCHAR(100), type VARCHAR(20), destination VARCHAR(100), world VARCHAR(100), filltype VARCHAR(100) DEFAULT 'AIR', xmax INT(11), xmin INT(11), ymax INT(11), ymin INT(11), zmax INT(11), zmin INT(11), CONSTRAINT pk_portalname PRIMARY KEY (portalname));";
			Statement action = connection.createStatement();
			action.executeUpdate(sql);
			action.close();
			handler.release(connection);
			Portalplugin.inst().getLogger().info("[Module] Database loaded!");
			return true;

		} catch (Exception e) {
			Portalplugin.inst().getLogger().info("[Module] Database error!");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "==================PORTAL-ERROR================");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Unable to connect to database.");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Pls check you mysql connection in config.yml.");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "==================PORTAL-ERROR================");
			if (XeonSuiteBukkit.isDebugmode()) {
				e.printStackTrace();
			}
			return false;
		}

	}

}
