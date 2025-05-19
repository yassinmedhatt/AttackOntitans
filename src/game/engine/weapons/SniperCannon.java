package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class SniperCannon extends Weapon {
    public static final int WEAPON_CODE = 2;

    public SniperCannon(int baseDamage) {
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;
        if (!laneTitans.isEmpty()) {
            Titan target = laneTitans.peek();
            resourcesGained += target.takeDamage(getBaseDamage());
            if (target.getCurrentHealth() <= 0) {
                laneTitans.poll();
            }
        }
        return resourcesGained;
    }
}
