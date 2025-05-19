package game.engine.dataloader;

import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataLoader {
   
    private static final String TITANS_FILE_NAME = "titans.csv";
    private static final String WEAPONS_FILE_NAME = "weapons.csv";
    
	public static String getTitansFileName() {
		return TITANS_FILE_NAME;
	}
	public static String getWeaponsFileName() {
		return WEAPONS_FILE_NAME;
	}
	//read Titan registry
	public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException {
        HashMap<Integer, TitanRegistry> titanRegistry = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(TITANS_FILE_NAME)); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int code = Integer.parseInt(data[0]);
                int baseHealth = Integer.parseInt(data[1]);
                int baseDamage = Integer.parseInt(data[2]);
                int heightInMeters = Integer.parseInt(data[3]);
                int speed = Integer.parseInt(data[4]);
                int resourcesValue = Integer.parseInt(data[5]);
                int dangerLevel = Integer.parseInt(data[6]);
     titanRegistry.put (code,new TitanRegistry( code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue,
    		  dangerLevel));
            
            }
        br.close();
        return titanRegistry;}
	
    //read weapon registry
        public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException {
            HashMap<Integer, WeaponRegistry> weaponRegistry = new HashMap<>();
            BufferedReader br = new BufferedReader(new FileReader(WEAPONS_FILE_NAME)); 
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    int code = Integer.parseInt(data[0]);
                    int price = Integer.parseInt(data[1]);
                    int damage = Integer.parseInt(data[2]);
                    String name = data[3];
                    
                    if (data.length == 4) {
                       weaponRegistry.put(code,new WeaponRegistry(code,price,damage,name));
                    }
                    else{
                    	 int minRange = Integer.parseInt(data[4]);
                         int maxRange = Integer.parseInt(data[5]);
                         weaponRegistry.put(code,new WeaponRegistry(code,price,damage,name,minRange,maxRange));
                    }
                }
   
            br.close();
            return weaponRegistry;
            }}
        
	
        

    