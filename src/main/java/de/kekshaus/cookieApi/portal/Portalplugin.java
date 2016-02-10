package de.kekshaus.cookieApi.portal;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import de.kekshaus.cookieApi.portal.commands.DeletePortalCommand;
import de.kekshaus.cookieApi.portal.commands.SetPortalCommand;
import de.kekshaus.cookieApi.portal.database.MinePortalDB;
import de.kekshaus.cookieApi.portal.database.object.PhysicsListener;
import de.kekshaus.cookieApi.portal.database.object.PlayerMoveListener;
import de.kekshaus.cookieApi.portal.database.object.PortalsManager;

public class Portalplugin extends JavaPlugin {
	private static Portalplugin inst;
	public static WorldEditPlugin WORLDEDIT = null;

	public void onEnable() {
		inst = this;

		if (MinePortalDB.create()) {
			loadWorldEdit();
			loadCommands();
			getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
			getServer().getPluginManager().registerEvents(new PhysicsListener(), this);
			if (PortalsManager.loadPortals()) {
				this.getLogger().info(PortalsManager.PORTALS.size() + " Portals loaded!");
			}
		}
	}

	public void onDisable() {
	}

	public static Portalplugin inst() {
		return inst;
	}

	public void loadCommands() {
		getCommand("setportal").setExecutor(new SetPortalCommand(this));
		getCommand("delportal").setExecutor(new DeletePortalCommand(this));
	}

	private void loadWorldEdit() {
		WORLDEDIT = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
	}
}
