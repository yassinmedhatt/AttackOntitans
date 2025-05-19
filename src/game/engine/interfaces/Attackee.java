package game.engine.interfaces;

public interface Attackee {

	int getCurrentHealth();
	
	void setCurrentHealth(int health);
	
	int getResourcesValue();
	
	public default boolean isDefeated(){
		if(getCurrentHealth()==0){
			return true;
		}
		return false;
	}
	public default int takeDamage(int damage) {
        int currentHealth = getCurrentHealth();
        currentHealth -= damage;
        setCurrentHealth(currentHealth);
        
        if (currentHealth <= 0) {
            return getResourcesValue();
        } else {
            return 0;
        }
    }

	}

