
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


import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class Portal {
    private String name;

    private ArrayList<Loc> blocks = new ArrayList<>();
    private FillType fillType;
    private World world;
    private BlockVector3 min;
    private BlockVector3 max;

    public Portal(String name, String fillType, BlockVector3 max, BlockVector3 min, World world) {
        this.name = name;
        this.world = world;
        this.min = min;
        this.max = max;
        try {
            this.fillType = FillType.valueOf(fillType.toUpperCase());
        } catch (Exception e) {
            this.fillType = FillType.AIR;
            System.out.println("Invalid fill type for the portal, setting to AIR");
        }
        if (this.fillType == null) {
            this.fillType = FillType.AIR;
        }
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    blocks.add(new Loc(world.getName(), x, y, z));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public FillType getFillType() {
        return fillType;
    }

    public void fillPortal() {
        for (Loc locs : blocks) {
            Block b = locs.getBlock();
            if (b.isEmpty()) {
                b.setType(fillType.getBlockMaterial());
            }
        }
    }

    public void clearPortal() {
        for (Loc locs : blocks) {
            Block b = locs.getBlock();
            if (b.getType() == fillType.getBlockMaterial()) {
                b.setType(FillType.AIR.getBlockMaterial());
            } else if (fillType.equals(FillType.LAVA) && b.getType().equals(Material.LAVA)) {
                b.setType(FillType.AIR.getBlockMaterial());
            } else if (fillType.equals(FillType.WATER) && b.getType().equals(Material.WATER)) {
                b.setType(FillType.AIR.getBlockMaterial());
            }

        }
    }

    public ArrayList<Loc> getBlocks() {
        return blocks;
    }

    public boolean isBlockInPortal(Block b) {
        for (Loc l : blocks) {
            if (l.equals(b)) {
                return true;
            }
        }
        return false;
    }

    public World getWorld() {
        return world;
    }

    public BlockVector3 getMin() {
        return this.min;
    }

    public BlockVector3 getMax() {
        return this.max;
    }

    public boolean isLocationInPortal(Location l) {
        for (Loc loc : blocks) {
            if (loc.equals(l)) {
                return true;
            }
        }
        return false;
    }

}