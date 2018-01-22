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
    AIR(Material.AIR), WATER(Material.STATIONARY_WATER), LAVA(Material.STATIONARY_LAVA), WEB(Material.WEB), SUGAR_CANE(
            Material.SUGAR_CANE_BLOCK), END_PORTAL(Material.ENDER_PORTAL), PORTAL(Material.PORTAL);

    private final Material BlockMaterial;

    FillType(Material blockId) {
        BlockMaterial = blockId;
    }

    public Material getBlockMaterial() {
        return BlockMaterial;
    }

}
