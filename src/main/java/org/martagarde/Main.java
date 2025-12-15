package org.martagarde;
import java.awt.*;
import java.util.Scanner;

public class Main {
    static String input = "";
    static Scanner sc = new Scanner(System.in);
    static Player player = new Player("TestDummy", 50, 10);
    static Room currentRoom;
    static Boolean roomChange = true;
    static Boolean running = true;

    /*
    public static void doAction (String untrimmedAction) {
        String action = untrimmedAction.trim(); // varbut pirma varda parbaudee trimot sakumu vvvvvvv nevis .contains()
        if (action.contains("look") || action.contains("look") && action.contains("around") || action.contains("look around")) {
            currentRoom.getAvailableDirections();
        } else if (action.contains("go")) {
            if (currentRoom.availableDirections.containsKey(action.substring(2).trim()) && currentRoom.availableDirections.get(action.substring(2).trim())) {
                switch (action.substring(2).trim()) {
                    case "south" :
                        currentRoom = currentRoom.getSouthRoom();
                        roomChange = true;
                        break;
                    case "north" :
                        currentRoom = currentRoom.getNorthRoom();
                        roomChange = true;
                        break;
                    case "east" :
                        currentRoom = currentRoom.getEastRoom();
                        roomChange = true;
                        break;
                    case "west" :
                        currentRoom = currentRoom.getWestRoom();
                        roomChange = true;
                        break;
                    default :
                        System.out.println("You didn't go anywhere.");
                        break;
                }
            } else {
                System.out.println("You can't go in that direction.");
            }
        }else if (action.contains("take")) {
            if (currentRoom.availableItems.containsKey(action.substring(4).trim())) {
                player.addToInventory(action.substring(4).trim());
                currentRoom.availableItems.remove(action.substring(4).trim());
                System.out.println("You have picked up " + action.substring(4).trim());
            } else {
                System.out.println("The item you're trying to pick up is not there...\nare you okay??");
            }
        } else if (action.contains("inventory")) {
            player.getInventory();
//            System.out.println("Items in this room:");
//            currentRoom.getAvailableItems();
        } else if (action.contains("use")) {
            if (player.inventory.containsKey(action.substring(4).trim())) {
                player.removeFromInventory(action.substring(4).trim());
                System.out.println("You have used " + action.substring(4).trim());
            } else {
                System.out.println("You don't have this item in your inventory.");
            }
//        } else if (currentRoom.availableActions.containsKey(action)) {
//            if (!currentRoom.completedActions.containsKey(action)) {
//                System.out.println("You " + action);
//                currentRoom.completedActions.put(action, "");
//            } else {
//                System.out.println("You have already done that.");
//            }
        }
            else {
            System.out.println("That is not a valid action.");
        }
    }
    */

    public static void main(String[] args) {
        Room startingRoom = new Room();
        Room leftRoom = new Room("Cold stone room", "You walk into a dark, cold room. You can't see anything.");
        Room northRoom = new Room("Green room", "The room is oozing with vines and other flora you can't identify.\nYou can make out the silhouette of a door on the opposite side of the room.");
        Room southRoom = new Room("Empty room", "The room is empty. Nothing but a few bricks and stones lay on the floor. \nYou hear a faint cracking sound...");
        Room exitRoom = new Room("Dungeon entrance", "You find yourself at the entrance of a dungeon.\nThe sun is shining on your weary face as you look around the vast field.\nYou finally escaped.");

        startingRoom.setWestRoom(leftRoom);
        leftRoom.setNorthRoom(northRoom);
        leftRoom.setSouthRoom(southRoom);
        northRoom.setNorthRoom(exitRoom);

        startingRoom.addItems("torch", 1);

        // TODO izdomat actionus
        /*
        Action action1 = new Action("open box", "You opened the box. There is a key.");
        Action action2 = new Action("take key", "You picked up a key");
        Action action3 = new Action("unlock door", "You unlocked the door.");

        action1.setNextAction(action2);
        action2.setNextAction(action3);
        */

        currentRoom = startingRoom;

        while (running) { // game loop
            if(roomChange) {
                System.out.println(currentRoom.getTitle());
                System.out.println(currentRoom.getDescription());
                roomChange = false;
            }
            if(currentRoom == exitRoom){
                System.out.println("You have won.");
                break;
            }
            if(currentRoom == southRoom){ // pirms sada eventa vajadzetu ability check
                System.out.println("The floor caves in beneath your feet and you fall to your untimely demise. \nYou have died.");
                break;
            }

            System.out.print("What will you do? >");
            input = sc.nextLine();
            input = input.trim();

            doAction(divideInput(input));

        }
    }

    public static List divideInput(String input) {
        char[] inputArray = input.toCharArray();
        List words = new List();
        int startIndex = 0;
        int endIndex;
        int counter = -1; // -1 jo cikla sakuma +1 un pirmajam jabut 0

        /*
        do{
            System.out.println("start = " + startIndex);
            endIndex = input.substring(startIndex).trim().indexOf(" "); // atrod index atstarpei
            System.out.println("end = " + endIndex);
            if(endIndex == -1){ // -1 nozime, ka nav atrasta atstarpe
                System.out.println("atstarpes nav.");
                endIndex = input.length(); // - 1;
                System.out.println("pedejais index = " + endIndex);
            } else {
                System.out.println("atstarpe ir.");
                // endIndex --; // atstarpes index - 1, jo tas bus varda beigas
                System.out.println("varda beigu index = " + endIndex);
            }
            // !!!!!!!! problema ir ka vins sak skaitit atstarpes index no startIndex pozicijas un tad nav pareizs endIndex
            System.out.println("vards, ko pievieno sarakstam: " + input.substring(startIndex, endIndex));
            words.add(input.substring(startIndex, endIndex + startIndex).trim()); // trim just in case prieksaa palikusi atstarpe
            startIndex = endIndex; // + 1; // end + 1 drosi vien bus atstarpe, tapec sakuma trim() substringu
                                       // tas ir lai gadijuma ja lietotajs ievada vairakas atstarpes taas netraucetu

        }while(startIndex < input.length() - 1); // -1 jo length atgriez simbolu skaitu nevis pedejo indeksu
                                               // cikls beidzas kad endIndex sakrit ar ievadita teksta pedejo indeksu
        */

        /*
        do {
            System.out.println("----------------");

            if (endIndex == -1) {
                System.out.println("No space");
                System.out.println("Word: " + input.substring(startIndex).trim());
                words.add(input.substring(startIndex).trim());
                go = false;
            } else {
                System.out.println("Yes space");
                System.out.println("Word: " + input.substring(startIndex, endIndex + startIndex).trim());
                endIndex += startIndex;
                words.add(input.substring(startIndex, endIndex).trim());
                startIndex = endIndex;
            }
        } while (go);
        */

        for(char i : inputArray) { // iet cauri katram simbolam virknee
            counter++; // skaita indexu

            if(i == ' '){ // ja atrod atstarpi
                endIndex = counter; // atstarpes index = varda beigu index

                if(!input.substring(startIndex, endIndex).trim().isEmpty()) { // ja string nav tukss tad pievieno sarakstam
                    words.add(input.substring(startIndex, endIndex).trim()); // tas ir gadijumos, ja ir vairakas atstarpes un vins atstarpi uztver ka vardu
                }

                startIndex = endIndex; // parvieto sakuma index uz nakama varda sakumu

            } else if (counter == inputArray.length - 1) { // ja ir pedejais char sarakstaa
                                                           // te nevar buut tukss string jo funkcijai sakuma tiek padots jau trimots strings
                words.add(input.substring(startIndex).trim()); // pievieno atlikuso substring bez endIndex
            }
        }

        return words;
    }

    public static void doAction(List inputWords){
        // funkcija sanem sadalitu user inputu un izpilda darbibu
        switch(inputWords.getItem(0)){
            case "go" :
                goFunction(inputWords);
                break;
            case "look" :
                System.out.println("You look around.");
                lookFunction(inputWords);
                break;
            case "take" :
                takeFunction(inputWords);
                break;
            case "inventory" :
                System.out.println("You look in your inventory, you have: ");
                player.getInventory();
                break;
            case "use" :
                useFunction(inputWords);
                break; // TODO: citu actionu izpildisana
            case "exit" :
                System.out.println("You have chosen to exit the game.");
                running = false;
                break;
            default :
                System.out.println("You can't do that. Here are the possible actions:");
                System.out.println("go [direction]  |  look [somewhere]  |  take [something]\ninventory  |  use [something]  |  exit");
                break;
        }

    }

    public static void goFunction (List dividedInput) {
        if (currentRoom.availableDirections.containsKey(dividedInput.getItem(dividedInput.getItemCount()-1)) // ja eksistee tads virziens
                && currentRoom.availableDirections.get(dividedInput.getItem(dividedInput.getItemCount()-1))) { // un tas ir true (atverts)
            switch (dividedInput.getItem(dividedInput.getItemCount()-1)) {
                case "south" :
                    currentRoom = currentRoom.getSouthRoom();
                    roomChange = true;
                    break;
                case "north" :
                    currentRoom = currentRoom.getNorthRoom();
                    roomChange = true;
                    break;
                case "east" :
                    currentRoom = currentRoom.getEastRoom();
                    roomChange = true;
                    break;
                case "west" :
                    currentRoom = currentRoom.getWestRoom();
                    roomChange = true;
                    break;
                default :
                    System.out.println("You didn't go anywhere.");
                    break;
            }
        } else {
            System.out.println("You can't go in that direction.");
        }
    }

    public static void lookFunction (List dividedInput) {
        // TODO: uztaisiit ka var apskatiitiess prieksmetus
        // ja dividedInput pedejais strings ir kkads objekts tad izvada taa objekta aprakstu
        currentRoom.getAvailableDirections();
    }

    public static void takeFunction (List dividedInput) {
        // ja istaba eksiste tads prieksmets
        if (currentRoom.availableItems.containsKey(dividedInput.getItem(dividedInput.getItemCount()-1))) {
            // tad pievieno speletajam inventory
            player.addToInventory(dividedInput.getItem(dividedInput.getItemCount()-1));
            // izdzes no istabas to prieksmetu
            currentRoom.availableItems.remove(dividedInput.getItem(dividedInput.getItemCount()-1));

            System.out.println("You have picked up " + dividedInput.getItem(dividedInput.getItemCount()-1));
        } else {
            System.out.println("The item you're trying to pick up is not there.");
        }
    }

    public static void useFunction (List dividedInput) {
        // TODO: izdomat lietu izmantosanas logiku
        // pagaidam izmantosana nozime tikai to ka iznem no inventory un items pazud
        if (player.inventory.containsKey(dividedInput.getItem(dividedInput.getItemCount()-1))) {
            player.removeFromInventory(dividedInput.getItem(dividedInput.getItemCount()-1));
            System.out.println("You have used " + dividedInput.getItem(dividedInput.getItemCount()-1));
        } else {
            System.out.println("You don't have this item in your inventory.");
        }
    }


}