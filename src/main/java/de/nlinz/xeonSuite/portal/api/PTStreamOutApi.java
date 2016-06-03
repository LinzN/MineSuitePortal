package de.nlinz.xeonSuite.portal.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.nlinz.javaSocket.client.api.XeonSocketClientManager;
import de.nlinz.xeonSuite.portal.Listener.XeonPortal;

public class PTStreamOutApi {

	public static void sendOtherServer(String player, String server) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream out = XeonSocketClientManager.createChannel(bytes, XeonPortal.channelName);
		try {
			out.writeUTF("TeleportToServer");
			out.writeUTF(player);
			out.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}

		XeonSocketClientManager.sendData(bytes);
	}

}
