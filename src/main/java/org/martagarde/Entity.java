package org.martagarde;

public abstract class Entity {
    private String name = "";
    private int maxHp;
    private int currentHp;
    private int atk;
    private boolean isShielding = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public boolean isShielding() {
        return isShielding;
    }

    public void setShielding(boolean shielding) {
        isShielding = shielding;
    }

    public void heal(int amount) {
        currentHp += amount;
        if (currentHp > maxHp) currentHp = maxHp; // current hp nevar parsniegt max hp
    }

    public void takeDamage(int amount) {
        if (isShielding) currentHp -= amount/2;
        else currentHp -= amount;
    }

    Entity() {
        setName("Entity");
        setMaxHp(20);
        setCurrentHp(maxHp);
        setAtk(5);
    }

    Entity(String name, int hp, int atk) {
        setName(name);
        setMaxHp(hp);
        setCurrentHp(maxHp);
        setAtk(atk);
    }
}
