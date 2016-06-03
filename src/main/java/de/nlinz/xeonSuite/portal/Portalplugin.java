package de.nlinz.xeonSuite.portal;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import de.nlinz.javaSocket.client.api.XeonSocketClientManager;
import de.nlinz.xeonSuite.portal.Listener.PhysicsListener;
import de.nlinz.xeonSuite.portal.Listener.PlayerMoveListener;
import de.nlinz.xeonSuite.portal.Listener.XeonPortal;
import de.nlinz.xeonSuite.portal.api.PTStreamInApi;
import de.nlinz.xeonSuite.portal.commands.DeletePortalCommand;
import de.nlinz.xeonSuite.portal.commands.SetPortalCommand;
import de.nlinz.xeonSuite.portal.database.MinePortalDB;

public class Portalplugin extends JavaPlugin {
	private static Portalplugin inst;
	public static WorldEditPlugin WORLDEDIT = null;

	@Override
	public void onEnable() {
		inst = this;

		if (MinePortalDB.create()) {
			loadWorldEdit();
			loadCommands();
			getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
			getServer().getPluginManager().registerEvents(new PhysicsListener(), this);
			XeonSocketClientManager.registerDataListener(new XeonPortal());
			if (PTStreamInApi.loadPortals()) {
				this.getLogger().info(PTStreamInApi.PORTALS.size() + " Portals loaded!");
			}
		}
	}

	@Override
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
