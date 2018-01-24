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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class Loc {

    private String world;
    private double x;
    private double y;
    private double z;

    public Loc(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public Block getBlock() {
        return getLocation().getBlock();
    }

    public boolean equals(Location loc) {
        return loc.getWorld().getName().equals(world) && !(loc.getBlockX() != x) && !(loc.getBlockY() != y) && !(loc.getBlockZ() != z);
    }

    public boolean equals(Block block) {
        Location loc = block.getLocation();
        return loc.getWorld().getName().equals(world) && !(loc.getBlockX() != x) && !(loc.getBlockY() != y) && !(loc.getBlockZ() != z);
    }
}
