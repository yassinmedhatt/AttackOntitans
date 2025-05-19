package game.engine.weapons.factory;

import game.engine.dataloader.DataLoader;
import game.engine.weapons.WeaponRegistry;
import game.engine.exceptions.InsufficientResourcesException;

import java.io.IOException;
import java.util.HashMap;

public class WeaponFactory {
    private final HashMap<Integer, WeaponRegistry> weaponShop;

    public WeaponFactory() throws IOException {
        this.weaponShop = DataLoader.readWeaponRegistry();
    }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }

    public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException {
        WeaponRegistry weapon = weaponShop.get(weaponCode);
        if (weapon == null) {
            throw new IllegalArgumentException("Invalid weapon code: " + weaponCode);
        }

        int price = weapon.getPrice();
        if (resources >= price) {
            int remainingResources = resources - price;
            return new FactoryResponse(weapon.buildWeapon(), remainingResources);
        } else {
            throw new InsufficientResourcesException("Not enough resources to buy weapon", resources);
        }
    }

    public void addWeaponToShop(int code, int price) {
        weaponShop.put(code, new WeaponRegistry(code, price));
    }

    public void addWeaponToShop(int code, int price, int damage, String name) {
        weaponShop.put(code, new WeaponRegistry(code, price, damage, name));
    }

    public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange) {
        weaponShop.put(code, new WeaponRegistry(code, price, damage, name, minRange, maxRange));
    }
}

	