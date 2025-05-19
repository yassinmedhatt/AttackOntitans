package game.engine.weapons;

public class WeaponRegistry {
    private final int code;
    private int price;
    private int damage;
    private String name;
    private int minRange;
    private int maxRange;

    public WeaponRegistry(int code, int price) {
        this.code = code;
        this.price = price;
    }

    public WeaponRegistry(int code, int price, int damage, String name) {
        this.code = code;
        this.price = price;
        this.damage = damage;
        this.name = name;
    }

    public WeaponRegistry(int code, int price, int damage, String name, int minRange, int maxRange) {
        this.code = code;
        this.price = price;
        this.damage = damage;
        this.name = name;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public WeaponRegistry(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public Weapon buildWeapon() {
        switch (code) {
            case PiercingCannon.WEAPON_CODE:
                return new PiercingCannon(damage);
            case SniperCannon.WEAPON_CODE:
                return new SniperCannon(damage);
            case VolleySpreadCannon.WEAPON_CODE:
                return new VolleySpreadCannon(damage, minRange, maxRange);
            case WallTrap.WEAPON_CODE:
                return new WallTrap(damage);
            default:
                throw new IllegalArgumentException("Invalid weapon code: " + code);
        }
    }
}
