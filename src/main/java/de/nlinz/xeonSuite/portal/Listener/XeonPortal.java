package de.nlinz.xeonSuite.portal.Listener;

import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;

import de.nlinz.javaSocket.client.api.XeonSocketClientManager;
import de.nlinz.javaSocket.client.events.SocketDataEvent;
import de.nlinz.javaSocket.client.interfaces.IDataListener;
import de.nlinz.xeonSuite.bukkit.XeonSuiteBukkit;

public class XeonPortal implements IDataListener {

	@Override
	public String getChannel() {
		// TODO Auto-generated method stub
		return channelName;
	}

	public static String channelName = "xeonPortal";

	@Override
	public void onDataRecieve(SocketDataEvent event) {
		// TODO Auto-generated method stub
		DataInputStream in = XeonSocketClientManager.readDataInput(event.getStreamBytes());
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
