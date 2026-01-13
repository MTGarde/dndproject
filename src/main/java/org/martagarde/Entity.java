package org.martagarde;

public abstract class Entity {
    private String name = "";
    private int hp;
    private int atk;

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    Entity() {
        setName("Entity");
        setHp(20);
        setAtk(5);
    }

    Entity(String name, int hp, int atk) {
        setName(name);
        setHp(hp);
        setAtk(atk);
    }
}
