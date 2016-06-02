package de.nlinz.xeonSuite.portal.api;

import de.keks.socket.bukkit.BukkitPlugin;
import de.keks.socket.core.Channel;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PTStreamOutApi {

	public static void sendOtherServer(String player, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = Channel.teleportChannel(b);
		try {
			out.writeUTF("TeleportToServer");
			out.writeUTF(player);
			out.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BukkitPlugin.instance().sendBytesOut(b);
	}

}
