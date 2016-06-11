package de.nlinz.xeonSuite.portal.Listener;

import de.nlinz.javaSocket.client.events.SocketDataEvent;
import de.nlinz.javaSocket.client.interfaces.IDataListener;

public class XeonPortal implements IDataListener {

	@Override
	public String getChannel() {
		// TODO Auto-generated method stub
		return channelName;
	}

	public static String channelName = "xeonPortal";

	@Override
	public void onDataRecieve(SocketDataEvent event) {
		/* Nothing yet */
	}

}
