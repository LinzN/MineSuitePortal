/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.mineSuite.portal.socket;

import com.sk89q.worldedit.Vector;
import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.portal.api.PortalManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientPortalListener implements IncomingDataListener {


    @Override
    public void onEvent(String channel, UUID clientUUID, byte[] dataInBytes) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(dataInBytes));
        String subChannel;
        try {
            String serverName = in.readUTF();

            if (!serverName.equalsIgnoreCase(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME)) {
                return;
            }
            subChannel = in.readUTF();
            if (subChannel.equals("server_portal_enable-portal")) {
                String portalName = in.readUTF();
                String fillType = in.readUTF();
                /* Location of the portal */
                World world = Bukkit.getWorld(in.readUTF());
                /* Cords of min side minX, minY, minZ */
                double minX = in.readDouble();
                double minY = in.readDouble();
                double minZ = in.readDouble();
                Vector min = new Vector(minX, minY, minZ);
                /* Cords of min side maxX, maxY, maxZ */
                double maxX = in.readDouble();
                double maxY = in.readDouble();
                double maxZ = in.readDouble();
                Vector max = new Vector(maxX, maxY, maxZ);

                PortalManager.enablePortalFrame(portalName, fillType, min, max, world);
            }

            if (subChannel.equals("server_portal_disable-portal")) {
                String portalName = in.readUTF();
                PortalManager.disablePortalFrame(portalName);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
