package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane> {
    private final Wall laneWall;
    private int dangerLevel;
    private final PriorityQueue<Titan> titans;
    private final ArrayList<Weapon> weapons;

    public Lane(Wall laneWall) {
        this.laneWall = laneWall;
        this.dangerLevel = 0;
        this.titans = new PriorityQueue<>();
        this.weapons = new ArrayList<>();
    }

    public Wall getLaneWall() {
        return laneWall;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = Math.max(0, dangerLevel);
    }

    public PriorityQueue<Titan> getTitans() {
        return titans;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void addTitan(Titan titan) {
        titans.add(titan);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void moveLaneTitans() {
    	PriorityQueue<Titan> tempTitans = new PriorityQueue<>(titans);
        
        titans.clear();
        
        for (Titan titan : tempTitans) {
            if (!titan.hasReachedTarget()) {
                titan.move();
                titans.add(titan);
            } else {
                titans.add(titan);
            }
        }
    }

        public int performLaneTitansAttacks() {
            int resourcesGained = 0;
            for (Titan titan : titans) {
                if (titan.hasReachedTarget()) {
                    resourcesGained += titan.attack(laneWall);
                }
            }
            return resourcesGained;
        }

    public int performLaneWeaponsAttacks() {
        int resourcesGained = 0;
        for (Weapon weapon : weapons) {
            resourcesGained += weapon.turnAttack(titans);
        }
        return resourcesGained;
    }

    public boolean isLaneLost() {
        return laneWall.isDefeated();
    }

    public void updateLaneDangerLevel() {
        int totalDangerLevel = 0;
        for (Titan titan : titans) {
            totalDangerLevel += titan.getDangerLevel();
        }
        setDangerLevel(totalDangerLevel);
    }

    @Override
    public int compareTo(Lane o) {
        return Integer.compare(this.dangerLevel, o.dangerLevel);
    }
}
