package de.kekshaus.cookieApi.portal;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import de.kekshaus.cookieApi.portal.Listener.BukkitSockPortalListener;
import de.kekshaus.cookieApi.portal.Listener.PhysicsListener;
import de.kekshaus.cookieApi.portal.Listener.PlayerMoveListener;
import de.kekshaus.cookieApi.portal.api.PTStreamInApi;
import de.kekshaus.cookieApi.portal.commands.DeletePortalCommand;
import de.kekshaus.cookieApi.portal.commands.SetPortalCommand;
import de.kekshaus.cookieApi.portal.database.MinePortalDB;

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
			getServer().getPluginManager().registerEvents(new BukkitSockPortalListener(), this);
			if (PTStreamInApi.loadPortals()) {
				this.getLogger().info(PTStreamInApi.PORTALS.size() + " Portals loaded!");
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
