package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class WallTrap extends Weapon {
    public static final int WEAPON_CODE = 4;

    public WallTrap(int baseDamage) {
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;

        if (!laneTitans.isEmpty()) {
            Titan target = laneTitans.peek();
            if (target.getDistance() <= 0) {
                resourcesGained += target.takeDamage(getBaseDamage());
            }
        }

        return resourcesGained;
    }
}
