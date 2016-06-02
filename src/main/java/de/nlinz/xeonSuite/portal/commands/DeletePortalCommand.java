package de.nlinz.xeonSuite.portal.commands;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.nlinz.xeonSuite.bukkit.GlobalMessageDB;
import de.nlinz.xeonSuite.portal.Portalplugin;
import de.nlinz.xeonSuite.portal.api.PTStreamInApi;

public class DeletePortalCommand implements CommandExecutor {
	public ThreadPoolExecutor executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	public DeletePortalCommand(Portalplugin instance) {
	}

	public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		final Player player = (Player) sender;
		if (player.hasPermission("cookieApi.portal.delportal")) {
			this.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (sender instanceof Player) {

						if (args.length > 0) {
							PTStreamInApi.deletePortal(args[0]);
							return;
						}
					}
				}
			});
		} else {
			sender.sendMessage(GlobalMessageDB.NO_PERMISSIONS);
		}
		return false;
	}
}
