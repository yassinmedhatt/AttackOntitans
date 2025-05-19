package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class PiercingCannon extends Weapon {
    public static final int WEAPON_CODE = 1;

    public PiercingCannon(int baseDamage) {
        super(baseDamage);
    }
    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;
        int remainingTargets = 5;

        // Create a copy of the original queue
        PriorityQueue<Titan> titansCopy = new PriorityQueue<>(laneTitans);

        while (!titansCopy.isEmpty() && remainingTargets > 0) {
            Titan target = titansCopy.poll();
            int resources = target.takeDamage(getBaseDamage());
            resourcesGained += resources;
            remainingTargets--; 

            if (target.isDefeated()) {
                laneTitans.remove(target);
            }
        }

        return resourcesGained;
    }

}
