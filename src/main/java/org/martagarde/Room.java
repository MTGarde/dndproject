package org.martagarde;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {
    public String title;
    public String description;
    public Room southRoom = null;
    public Room northRoom = null;
    public Room eastRoom = null;
    public Room westRoom = null;
    public HashMap<String, String> availableActions = new HashMap<>(); // key: action / value: result (kipa atbilde kas tiek izvadita kad izdara darbibu)
                                                                        // no available parvieto uz completed
    public HashMap<String, String> completedActions = new HashMap<>(); // vispirms parbauda no sejienes, vai darbiba jau ir izdarita
    public HashMap<String, Integer> availableItems = new HashMap<>();
    public HashMap<String, Boolean> availableDirections = new HashMap<>(); // ja ir durvis (?un ir valaa?), tad ir true, citadak false
    //barebones: viens enemijs istabaa
    public Enemy enemy;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room getSouthRoom() {
        return southRoom;
    }

    public void setSouthRoom(Room southRoom) {
        if (southRoom != null) {
            this.southRoom = southRoom;
            availableDirections.replace("south", true);
            southRoom.northRoom = this;
            southRoom.availableDirections.replace("north", true);
        }
    }

    public Room getNorthRoom() {
        return northRoom;
    }

    public void setNorthRoom(Room northRoom) {
        if(northRoom != null) {
            this.northRoom = northRoom;
            availableDirections.replace("north", true);
            northRoom.southRoom = this;
            northRoom.availableDirections.replace("south", true);
        }
    }

    public Room getEastRoom() {
        return eastRoom;
    }

    public void setEastRoom(Room eastRoom) {
        if(eastRoom != null) {
            this.eastRoom = eastRoom;
            availableDirections.replace("east", true);
            eastRoom.westRoom = this;
            eastRoom.availableDirections.replace("west", true);
        }
    }

    public Room getWestRoom() {
        return westRoom;
    }

    public void setWestRoom(Room westRoom) {
        if(westRoom != null) {
            this.westRoom = westRoom;
            availableDirections.replace("west", true);
            westRoom.eastRoom = this;
            westRoom.availableDirections.replace("east", true);
        }
    }

    public void getAvailableDirections() {
        for(String i : availableDirections.keySet()) {
            if(availableDirections.get(i) == true) {
                System.out.println("There is a door to the " + i);
            }
        }
    }

    public void setInitialDirections() {
        availableDirections.put("north", false);
        availableDirections.put("south", false);
        availableDirections.put("east", false);
        availableDirections.put("west", false);
    }

    public void getAvailableActions() {
        for(String action : availableActions.keySet()) {
            System.out.println(action + "  |  " + availableActions.get(action));
        }
    }

    public void addAction(String action, String result) {
        availableActions.put(action, result);
    }

    public void getAvailableItems() { // parbaudei
        for(String item : availableItems.keySet()) {
            System.out.println(item + "  |  " + availableItems.get(item));
        }
    }

    public void addItems(String item, int amount) { // pievieno istabaa pieejamos prieksmetus
        availableItems.put(item, amount); // var atkartoties? dazadas vietas istabaa
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Room() { // default konstruktors
        setTitle("Default Room");
        setDescription("You find yourself in a dark room lit by a single torch.\nThere is a door to your left and a small wooden box to your right.");
        // setEnemy(new Enemy());
//        addAction("take torch", "You picked up a torch.");
//        addAction("open box", "There is a key inside the box. Got a silver key.");
//        addItems("torch", 1);
//        addItems("silver key", 1);
//        addItems("wooden stick", 3);
        setInitialDirections();
    }

    public Room(String title, String description) {
        setTitle(title);
        setDescription(description);
        setInitialDirections();
        // actions, items un parejas istabas pec tam, jo nekaa tagad nav?
    }
}
