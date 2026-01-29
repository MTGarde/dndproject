package org.martagarde;

public class Enemy extends Entity{

    public Enemy() { // default konstruktors
        setName("Default Goblin");
        setMaxHp(5);
        setCurrentHp(getMaxHp());
        setAtk(1);
    }

}
