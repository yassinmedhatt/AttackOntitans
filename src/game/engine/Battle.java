package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;


public class Battle {
	
	private static final int[][] PHASES_APPROACHING_TITANS = new int [][]{{1,1,1,2,1,3,4},{2,2,2,1,3,3,4},{4,4,4,4,4,4,4}};

    private static final int WALL_BASE_HEALTH = 10000;
    private int numberOfTurns;
    private int resourcesGathered;
    private BattlePhase battlePhase;
    private int numberOfTitansPerTurn;
    private int score;
    private int titanSpawnDistance;
    private final WeaponFactory weaponFactory;
    private final HashMap<Integer, TitanRegistry> titansArchives;
    private final ArrayList<Titan> approachingTitans;
    private final PriorityQueue<Lane> lanes;
    private final ArrayList<Lane> originalLanes;

    public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
                  int initialResourcesPerLane) throws IOException {
        this.numberOfTurns = numberOfTurns;
        this.score = score;
        this.titanSpawnDistance = titanSpawnDistance;
        this.resourcesGathered = initialNumOfLanes * initialResourcesPerLane;
        this.battlePhase = BattlePhase.EARLY;
        this.numberOfTitansPerTurn = 1;
        this.weaponFactory = new WeaponFactory();
        this.titansArchives = DataLoader.readTitanRegistry();
        this.approachingTitans = new ArrayList<>();
        this.lanes = new PriorityQueue<>(initialNumOfLanes);
        this.originalLanes = new ArrayList<>();

        initializeLanes(initialNumOfLanes);
    }

    private void initializeLanes(int numOfLanes) {
        for (int i = 0; i < numOfLanes; i++) {
            Wall wall = new Wall(WALL_BASE_HEALTH);
            Lane lane = new Lane(wall);
            lanes.add(lane);
            originalLanes.add(lane);
        }
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
        if(numberOfTurns<0)
    		this.numberOfTurns = 0;
    	else
    		this.numberOfTurns = numberOfTurns;
    }

    public int getResourcesGathered() {
        return resourcesGathered;
    }

    public void setResourcesGathered(int resourcesGathered) {
        this.resourcesGathered = resourcesGathered;
        if(resourcesGathered<0)
    		this.resourcesGathered = 0;
    	else
    		this.resourcesGathered = resourcesGathered;
    }

    public BattlePhase getBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(BattlePhase battlePhase) {
        this.battlePhase = battlePhase;
        
    }

    public int getNumberOfTitansPerTurn() {
        return numberOfTitansPerTurn;
    }

    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
        this.numberOfTitansPerTurn = numberOfTitansPerTurn;
        if(numberOfTitansPerTurn<0)
    		this.numberOfTitansPerTurn = 0;
    	else
    		this.numberOfTitansPerTurn = numberOfTitansPerTurn;

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if(score<0)
    		this.score = 0;
    	else
    		this.score = score;

    }

    public int getTitanSpawnDistance() {
        return titanSpawnDistance;
    }

    public void setTitanSpawnDistance(int titanSpawnDistance) {
        this.titanSpawnDistance = titanSpawnDistance;
        if(titanSpawnDistance<0)
    		this.titanSpawnDistance = 0;
    	else
    		this.titanSpawnDistance = titanSpawnDistance;

    }

    public WeaponFactory getWeaponFactory() {
        return weaponFactory;
    }

    public HashMap<Integer, TitanRegistry> getTitansArchives() {
        return titansArchives;
    }

    public ArrayList<Titan> getApproachingTitans() {
        return approachingTitans;
    }

    public PriorityQueue<Lane> getLanes() {
        return lanes;
    }

    public ArrayList<Lane> getOriginalLanes() {
        return originalLanes;
    }
    public void refillApproachingTitans() {
        if (!approachingTitans.isEmpty()) {
            return;
        }

        approachingTitans.clear();
        int[] phaseTitans = PHASES_APPROACHING_TITANS[battlePhase.ordinal()];        
        for (int titanCode : phaseTitans) {
            TitanRegistry titanRegistry = titansArchives.get(titanCode);          
            if (titanRegistry != null) {
                Titan titan = titanRegistry.spawnTitan(titanSpawnDistance);
                approachingTitans.add(titan);
            } 
        }
    }

    public void purchaseWeapon(int weaponCode, Lane lane) throws InvalidLaneException, InsufficientResourcesException {
        if (lane == null || !originalLanes.contains(lane) || lane.isLaneLost()||!lanes.contains(lane) ) {
            throw new InvalidLaneException("Invalid lane provided for weapon purchase.");
        }

        FactoryResponse response = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
        Weapon weapon = response.getWeapon();
        lane.addWeapon(weapon);

        setResourcesGathered(response.getRemainingResources());
        performTurn();
    }


    public void passTurn() {
    	 moveTitans();
         performTitansAttacks();
         performWeaponsAttacks();
         addTurnTitansToLane();
         updateLanesDangerLevels();
         finalizeTurns();
        
    }
    
    
    private void addTurnTitansToLane() {
        
        Lane currentLane=lanes.poll();
        for (int i = 0; i < getNumberOfTitansPerTurn(); i++) {
            refillApproachingTitans();
        	 currentLane.addTitan(approachingTitans.remove(0));
        }       
        currentLane.updateLaneDangerLevel();
        lanes.offer(currentLane);
    }

    private void moveTitans() {
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                lane.moveLaneTitans();
                }
            }
        }
  
    private int performWeaponsAttacks() {
        int resourcesGained = 0;
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                resourcesGained += lane.performLaneWeaponsAttacks();
            }
        }
        for (Titan titan : approachingTitans) {
            if (titan.isDefeated()) {
                resourcesGained += titan.getResourcesValue();
            }
        }
        approachingTitans.removeIf(Titan::isDefeated);
        setResourcesGathered(getResourcesGathered() + resourcesGained);
        setScore(getScore() + resourcesGained);
        return resourcesGained;
    }
    
    private int performTitansAttacks() {
        int resourcesGained = 0;

        List<Lane> lanesToRemove = new ArrayList<>();
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                resourcesGained += lane.performLaneTitansAttacks();
            } else {
                lanesToRemove.add(lane);
            }
        }

        lanes.removeIf(Lane::isLaneLost);

        return resourcesGained;
    }

    private void updateLanesDangerLevels() {
        PriorityQueue<Lane> sortedLanes = new PriorityQueue<>(lanes);

        lanes.clear();
        for (Lane lane : sortedLanes) {
            if (!lane.isLaneLost()) {
                lane.updateLaneDangerLevel();
                lanes.add(lane);
            }
        }
    }
    
    private void finalizeTurns() {
    	numberOfTurns++;  

            if(numberOfTurns<15){
            setBattlePhase(BattlePhase.EARLY);
            }
            else if(numberOfTurns<30){
                setBattlePhase(BattlePhase.INTENSE);
                }
            else if(numberOfTurns>=30){
                setBattlePhase(BattlePhase.GRUMBLING);
             }
                if (numberOfTurns > 30 && numberOfTurns % 5 == 0) {
                    setBattlePhase(BattlePhase.GRUMBLING);
                    numberOfTitansPerTurn *= 2; 
                }
                
    }
    private void performTurn() {
        if (!isGameOver()) {
            moveTitans();
            performWeaponsAttacks();
            performTitansAttacks();
            addTurnTitansToLane();
            updateLanesDangerLevels();
            finalizeTurns();
        } else {
            System.out.println("Game over! The battle has ended.");
        }
    }

    public boolean isGameOver() {
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                return false; 
            }
        }
        return true; 
    }
    }
 