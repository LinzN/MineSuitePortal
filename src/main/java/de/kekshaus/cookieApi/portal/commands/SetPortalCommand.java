package de.kekshaus.cookieApi.portal.commands;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.bukkit.CookieApiBukkit;
import de.kekshaus.cookieApi.bukkit.MessageDB;
import de.kekshaus.cookieApi.portal.Portalplugin;
import de.kekshaus.cookieApi.portal.api.PTStreamInApi;

public class SetPortalCommand implements CommandExecutor {
	public ThreadPoolExecutor executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	public SetPortalCommand(Portalplugin instance) {
	}

	public boolean onCommand(final CommandSender sender, Command cmnd, String label, final String[] args) {
		final Player player = (Player) sender;
		final String server = CookieApiBukkit.getServerName();
		if (player.hasPermission("cookieApi.portal.setportal")) {
			this.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (sender instanceof Player) {
						if (args.length == 3) {
							PTStreamInApi.setPortal(sender, server, args[0], args[1], args[2], "AIR");
							return;
						} else if (args.length == 4) {
							PTStreamInApi.setPortal(sender, server, args[0], args[1], args[2], args[3]);
							return;
						}

						return;
					}
				}
			});
		} else {
			sender.sendMessage(MessageDB.NO_PERMISSIONS);
		}
		return false;
	}
}
