package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class VolleySpreadCannon extends Weapon {
    public static final int WEAPON_CODE = 3;
    private final int minRange;
    private final int maxRange;

    public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
        super(baseDamage);
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;
        ArrayList<Titan> titansToRemove = new ArrayList<>();

        for (Titan target : laneTitans) {
            if (target.getDistance() >= minRange && target.getDistance() <= maxRange) {
                int damageDealt = getDamage();
                int resources = target.takeDamage(damageDealt);
                resourcesGained += resources;
                if (resources > 0) {
                    titansToRemove.add(target); // Add defeated titan to the removal list
                }
            }
        }

        for (Titan titanToRemove : titansToRemove) {
            laneTitans.remove(titanToRemove);
        }

        return resourcesGained;
    }
}