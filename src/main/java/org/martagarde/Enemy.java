package org.martagarde;

public class Enemy {
    public String name;
    public int attack;
    public int hp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Enemy() { // default konstruktors
        setName("Default Goblin");
        setHp(5);
        setAttack(1);
    }

    public Enemy(String name, Integer hp, Integer attack) {
        setName(name);
        setHp(hp);
        setAttack(attack);
    }
}
