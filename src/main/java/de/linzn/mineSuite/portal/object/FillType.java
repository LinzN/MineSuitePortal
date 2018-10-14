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

package de.linzn.mineSuite.portal.object;

import org.bukkit.Material;

public enum FillType {
    AIR(Material.AIR), WATER(Material.WATER), LAVA(Material.LAVA), PORTAL(Material.NETHER_PORTAL);

    private final Material blockMaterial;

    FillType(Material blockId) {
        blockMaterial = blockId;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

}
