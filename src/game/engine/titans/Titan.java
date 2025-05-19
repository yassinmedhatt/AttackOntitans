package game.engine.titans;

import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;


public abstract class Titan implements Comparable<Titan> ,Mobil,Attackee,Attacker{
    private final int baseHealth;
    private int currentHealth;
    private final int baseDamage;
    private final int heightInMeters;
    private int distanceFromBase;
    private int speed;
    private final int resourcesValue;
    private final int dangerLevel;

    public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
                 int speed, int resourcesValue, int dangerLevel) {
        this.baseHealth = baseHealth;
        this.currentHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.heightInMeters = heightInMeters;
        this.distanceFromBase = distanceFromBase;
        this.speed = speed;
        this.resourcesValue = resourcesValue;
        this.dangerLevel = dangerLevel;
    }

    @Override
    public int compareTo(Titan otherTitan) {
        return Integer.compare(this.distanceFromBase, otherTitan.distanceFromBase);
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getHeightInMeters() {
        return heightInMeters;
    }

    public int getResourcesValue() {
        return resourcesValue;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
    	if(currentHealth<0)
    		this.currentHealth = 0;
    	else
    		this.currentHealth = currentHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        if(speed<0)
    		this.speed = 0;
    	else
    		this.speed = speed;
    }
    
	public int getDistance(){
		return distanceFromBase;
	}
	
	public void setDistance(int distance){
		this.distanceFromBase = distance;
		 if(distance<0)
	    		this.distanceFromBase = 0;
	    	else
	    		this.distanceFromBase = distance;
	    }
	
    public int getDamage(){
	    return baseDamage;
}

}
