package de.nlinz.xeonSuite.portal.Listener;

import java.io.DataInputStream;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import de.keks.socket.bukkit.events.plugin.BukkitSockPortalEvent;
import de.keks.socket.core.ByteStreamConverter;
import de.nlinz.xeonSuite.bukkit.XeonSuiteBukkit;

public class BukkitSockPortalListener implements Listener {

	@EventHandler
	public void onBukkitSockBanEvent(final BukkitSockPortalEvent e) {

		DataInputStream in = ByteStreamConverter.toDataInputStream(e.readBytes());
		String task = null;
		String servername = null;
		try {
			servername = in.readUTF();

			if (!servername.equalsIgnoreCase(XeonSuiteBukkit.getServerName())) {
				return;
			}

			task = in.readUTF();
			Bukkit.getLogger().info(task);
			if (task.equalsIgnoreCase("some")) {

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
