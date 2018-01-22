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

import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.portal.object.Portal;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientPortalOutput {

    public static void sendPortalUse(Portal portal, UUID playerUUID) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF("client_portal_use-portal");
            dataOutputStream.writeUTF(portal.getName());
            dataOutputStream.writeUTF(playerUUID.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineSuitePortal", byteArrayOutputStream.toByteArray());
    }

    public static void createPortal(String server, UUID playerUUID, String type, String destination, Portal portal) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("client_portal_create-portal");
            dataOutputStream.writeUTF(playerUUID.toString());
            /* Portal target destination and type */
            dataOutputStream.writeUTF(portal.getName()); // Portal name
            dataOutputStream.writeUTF(type); // Warp or Server
            dataOutputStream.writeUTF(destination); // Warpname or Servername
            dataOutputStream.writeUTF(portal.getFillType().getBlockMaterial().name());
            /* Location of the portal */
            dataOutputStream.writeUTF(server);
            dataOutputStream.writeUTF(portal.getWorld().getName());
            /* Cords of min side minX, minY, minZ */
            dataOutputStream.writeDouble(portal.getMin().getX());
            dataOutputStream.writeDouble(portal.getMin().getY());
            dataOutputStream.writeDouble(portal.getMin().getZ());

            /* Cords of min side maxX, maxY, maxZ */
            dataOutputStream.writeDouble(portal.getMin().getX());
            dataOutputStream.writeDouble(portal.getMin().getY());
            dataOutputStream.writeDouble(portal.getMin().getZ());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineSuitePortal", byteArrayOutputStream.toByteArray());
    }

    public static void deletePortal(UUID playerUUID, String portalName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("client_portal_remove-portal");
            dataOutputStream.writeUTF(playerUUID.toString());
            /* Portal name */
            dataOutputStream.writeUTF(portalName); // Portal name

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineSuitePortal", byteArrayOutputStream.toByteArray());
    }

}
