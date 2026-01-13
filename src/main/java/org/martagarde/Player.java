package org.martagarde;

import java.util.HashMap;

public class Player extends Entity{
    public HashMap<String, Integer> inventory = new HashMap<>();

    public void getInventory() {
        // TODO: maybe uztaisit lai smuki tabula izprintejas
        System.out.println("item    |    amount");
        for(String item : inventory.keySet()) {
            System.out.println(item + "  |  " + inventory.get(item));
        }
    }

    public void addToInventory(String item, int amount) {
        // TODO: amount jabut >= 1
        if(inventory.containsKey(item)) { // ja items jau ir, tad pieskaita skaitu
            inventory.replace(item, inventory.get(item) + amount);
        } else { // ja items nav inventory, tad pievieno
            inventory.put(item, amount);
        }
    }

    public void addToInventory(String item) {// pievieno tikai vienu itemu inventory
        // ja items jau ir inventory, tad pieskaita 1 pie skaita
        if(inventory.containsKey(item)) {
            inventory.replace(item, inventory.get(item) + 1);
        } else { // ja items nav inventory, tad pievieno 1
            inventory.put(item, 1);
        }
    }

    public void removeFromInventory(String item) {
        // ja daudzums ir tikai viens, tad izdzes no inventory
        if(inventory.get(item) == 1) {
            inventory.remove(item);
        } else { // ja daudzums nav viens, tad nomaina skaitu uz vienu mazak
            inventory.replace(item, inventory.get(item)-1);
        }
    }

    public void removeFromInventory(String item, int amount) {
        // TODO: parbaude par to vai nemegina iznemt vairak ka ir invenotry
        // ja daudzums sakrit ar skaitu inventory tad izdzes itemu
        if(inventory.get(item) <= amount) { // TODO: pagaidam ja ievada lielaku skaitu neka ir inventorijaa, tad vnk iznem no inventory
            inventory.remove(item);         // TODO: ta nevajadzetu but!!!!!! velak nomainit <= uz ==
        } else { // ja ir vairak, tad no skaita atnem amount
            inventory.replace(item, inventory.get(item) - amount);
        }
    }

    public Player() { // default konstruktors
        setName("Default Player");
        setHp(20);
        setAtk(5);
    }

    public Player(String name, int hp, int atk) {
        setName(name);
        setHp(hp);
        setAtk(atk);
    }
}
