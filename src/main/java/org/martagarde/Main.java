package org.martagarde;

// import java.awt.List;
import java.util.*;

public class Main {
    static String input = "";
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();
    static Player player = new Player("TestDummy", 50, 10);
    static Room currentRoom;
    static Boolean roomChange = true;
    static Boolean running = true;

    public static void main(String[] args) {
        Room startingRoom = new Room();
        Room leftRoom = new Room("Cold stone room", "You walk into a dark, cold room. There is a small goblin sitting in the middle of the room.");
        Room northRoom = new Room("Green room", "The room is oozing with vines and other flora you can't identify.\nSuddenly, a skeleton crawls out from the far corner of the room.");
        Room southRoom = new Room("Empty room", "The room is empty. Nothing but a few bricks and stones lay on the floor. \nYou hear a faint cracking sound...");
        Room exitRoom = new Room("Dungeon entrance", "You find yourself at the entrance of a dungeon.\nThe sun is shining on your weary face as you look around the vast field.\nYou finally escaped.");

        startingRoom.setWestRoom(leftRoom);
        leftRoom.setNorthRoom(northRoom);
        leftRoom.setSouthRoom(southRoom);
        northRoom.setNorthRoom(exitRoom);

        leftRoom.setEnemy(new Enemy("Goblin", 20, 3));
        northRoom.setEnemy(new Enemy("Skeleton", 30, 4));

        startingRoom.addItems("torch", 1);

        /*
        Action action1 = new Action("open box", "You opened the box. There is a key.");
        Action action2 = new Action("take key", "You picked up a key");
        Action action3 = new Action("unlock door", "You unlocked the door.");

        action1.setNextAction(action2);
        action2.setNextAction(action3);
        */

        currentRoom = startingRoom;

        while (running) { // game loop
            if (roomChange) {
                System.out.println(currentRoom.getTitle());
                System.out.println(currentRoom.getDescription());
                roomChange = false;
            }
            if (currentRoom == exitRoom) {
                System.out.println("You have won.");
                break;
            }
            if (currentRoom == southRoom) { // pirms sada eventa vajadzetu ability check
                System.out.println("The floor caves in beneath your feet and you fall to your untimely demise. \nYou have died.");
                break;
            }

            System.out.print("What will you do? >");
            input = sc.nextLine();
            input = input.trim();

            doAction(divideInput(input));

        }
    }

    public static List<String> divideInput(String input) {
        char[] inputArray = input.toCharArray();
        List<String> words = new ArrayList<>();
        int startIndex = 0;
        int endIndex;
        int counter = -1; // -1 jo cikla sakuma +1 un pirmajam jabut 0
        String substr;

        for (char i : inputArray) { // iet cauri katram simbolam virknee
            counter++; // skaita indexu

            if (i == ' ') { // ja atrod atstarpi
                endIndex = counter; // atstarpes index = varda beigu index
                substr = input.substring(startIndex, endIndex).trim();

                if (!substr.isEmpty()) { // ja string nav tukss tad pievieno sarakstam
                    words.add(substr); // tas ir gadijumos, ja ir vairakas atstarpes un vins atstarpi uztver ka vardu
                }

                startIndex = endIndex; // parvieto sakuma index uz nakama varda sakumu

            } else if (counter == inputArray.length - 1) { // ja ir pedejais char sarakstaa
                // te nevar buut tukss string jo funkcijai sakuma tiek padots jau trimots strings
                words.add(input.substring(startIndex).trim()); // pievieno atlikuso substring bez endIndex
            }
        }

        // iznem ara filler vardus
        words.removeIf(w -> w.equals(FillerWords.the.toString()));

        return words;
    }

    public static void doAction(List<String> inputWords) {
        // funkcija sanem sadalitu user inputu un izpilda darbibu
        switch (inputWords.getFirst()) {
            case "go":
                goFunction(inputWords);
                break;
            case "look":
                System.out.println("You look around.");
                lookFunction(inputWords);
                break;
            case "take":
                takeFunction(inputWords);
                break;
            case "inventory":
                System.out.println("You look in your inventory, you have: ");
                player.getInventory();
                break;
            case "use":
                useFunction(inputWords);
                break; // TODO: citu actionu izpildisana
            case "fight":
                fightFunction();
                break;
            case "exit":
                System.out.println("You have chosen to exit the game.");
                running = false;
                break;
            default:
                System.out.println("You can't do that. Here are the possible actions:");
                System.out.println("go [direction]  |  look [somewhere]  |  take [something]\ninventory  |  use [something]  |  exit");
                break;
        }

    }

    public static void goFunction(List<String> dividedInput) {
        if (currentRoom.availableDirections.containsKey(dividedInput.getLast()) // ja eksistee tads virziens
                && currentRoom.availableDirections.get(dividedInput.getLast())) { // un tas ir true (atverts)
            switch (dividedInput.getLast()) { // getLast, jo ievada [darbiba] [prieksmets] un pedejais ir tas, pec ka izpilda darbibu
                case "south":
                    currentRoom = currentRoom.getSouthRoom();
                    roomChange = true;
                    break;
                case "north":
                    currentRoom = currentRoom.getNorthRoom();
                    roomChange = true;
                    break;
                case "east":
                    currentRoom = currentRoom.getEastRoom();
                    roomChange = true;
                    break;
                case "west":
                    currentRoom = currentRoom.getWestRoom();
                    roomChange = true;
                    break;
                default:
                    System.out.println("You didn't go anywhere.");
                    break;
            }
        } else {
            System.out.println("You can't go in that direction.");
        }
    }

    public static void lookFunction(List<String> dividedInput) {
        // TODO: uztaisiit ka var apskatiitiess prieksmetus
        // ja dividedInput pedejais strings ir kkads objekts tad izvada taa objekta aprakstu
        currentRoom.getAvailableDirections();
    }

    public static void takeFunction(List<String> dividedInput) { // TODO uzlabot, jo te vins strada tikai ar viena varda prieksmetiem
        // ja istaba eksiste tads prieksmets
        if (currentRoom.availableItems.containsKey(dividedInput.getLast())) {
            // tad pievieno speletajam inventory
            player.addToInventory(dividedInput.getLast());
            // izdzes no istabas to prieksmetu
            currentRoom.availableItems.remove(dividedInput.getLast());

            System.out.println("You have picked up " + dividedInput.getLast());
        } else {
            System.out.println("The item you're trying to pick up is not there.");
        }
    }

    public static void useFunction(List<String> dividedInput) {
        // TODO: izdomat lietu izmantosanas logiku
        // pagaidam izmantosana nozime tikai to ka iznem no inventory un items pazud
        if (player.inventory.containsKey(dividedInput.getLast())) {
            player.removeFromInventory(dividedInput.getLast());
            System.out.println("You have used " + dividedInput.getLast());
        } else {
            System.out.println("You don't have this item in your inventory.");
        }
    }

    public static void fightFunction() {
        TreeMap<Integer, Entity> initiative = new TreeMap<>();// uzglaba katra cinas dalibnieka uzmesto initiative bez duplikatiem
        // TODO ! Initiative ir d20 + DEX modifiers

        if (currentRoom.getEnemy() != null) { // ja istaba ir ar ko cinities
            // TODO uztaisit normalu loop lai var but vairaki speletaji un enemiji
            // for each cinas dalibnieks
            // uzmet skaitli
            // ja tads jau ir sarakstaa tad uzmet citu
            // atkarto ieprieksejo lidz ir jauns skaitlis
            // pievieno TreeMapaa

            int dice = diceRoll(20); // uzmet skaitli
            initiative.put(dice, player); // pievieno
            dice = diceRoll(20); // uzmet nakamo
            if (initiative.containsKey(dice)) dice = diceRoll(20); // ja tads skaitlis jau ir tad uzmet velreiz- uztaisit ka ciklu lai turpina mest kamer nav jauns skaitlis
            initiative.put(dice, currentRoom.getEnemy());// pievieno

            sortInitiative(initiative); // izveido sarakstu ar entity kas piedalas battle
            battleLoop();
        } else { // ja istaba nav enemy
            System.out.println("You can't fight anyone here.");
        }
    }

    static java.util.List<Entity> battleQueue = new ArrayList<>();

    public static void sortInitiative(TreeMap<Integer, Entity> initiative) {
        for(Integer i : initiative.keySet()) { // TreeMap automatiski sakarto pec key bet no mazaka uz lielako v
            battleQueue.add(initiative.get(i)); // uztaisa sarakstu tikai ar entity                            v
        }                                       //                                                             v
        Collections.reverse(battleQueue); // tapec te ir reverse <--------------------------------------------<
    }

    public static void battleLoop() {
        boolean active = true;
        while (active) {
            for (Entity entity : battleQueue) { // iet cauri katram cinas dalibniekam
                if (entity.getCurrentHp() <= 0) { // parbauda vai ir miris
                    System.out.println(entity.getName() + " has died.");
                    battleQueue.remove(entity);// iznem miruso no saraksta
                } else {
                    System.out.println(entity.getName() + " | " + entity.getCurrentHp() + "/" + entity.getMaxHp() + " HP");
                }
            }

            // parbauda ka jabut vismaz vienam speletajam un enemy lai cina turpinatos
            // ja saraksta nav neviena enemy vai player
            if (battleQueue.stream().noneMatch(e -> e instanceof Enemy) || battleQueue.stream().noneMatch(e -> e instanceof Player)) {
                active = false;
                continue;
            }

            if(battleQueue.getFirst().getClass().equals(Player.class)) {
                System.out.println("What will you do?");
            } else {
                System.out.println( battleQueue.getFirst().getName() + " is making a move ...");
            }
            handleBattleAction(battleQueue.getFirst());// pirmais sarakstaa izdara darbiibu

            battleQueue.add(battleQueue.getFirst());// pirmais saraksta tiek parvietots uz beigam
            battleQueue.removeFirst();
        }
        // izvada kurs ir miris- ja speletajs tad izvada zinu un running = false (parstaj game loop)
        if(player.getCurrentHp() <= 0) {
            System.out.println("You have died.");
            running = false;
        } else {
            System.out.println("You are victorious!");
        }
        battleQueue.clear();
    }

    public static int diceRoll(int faceCount) { // faceCount ir skaldnu skaits metamajam kaulinam d20 d15 utt
        // rand.nextInt(max - min + 1) + min
        // max = faceCount  min = 1  jo metamais kaulins var uzmest vertibu no 1 -> skaldnu skaits
        return rand.nextInt(faceCount - 1 + 1) + 1;
    }

    public static HashMap<String, Integer> battleActions = new HashMap<>();
    static {
        battleActions.put("attack", 1);
        battleActions.put("heal", 2);
        battleActions.put("shield", 3);
        battleActions.put("run", 4);
    } // TODO actioni katram entity pašam savi something something
    // kipa enemy var tikai noteiktas darbibas no kuram vins izvelas un tad nevajag tos skaitlus

    public static int actionToNumber(String action) {
        if(battleActions.containsKey(action)) return battleActions.get(action); // ja ir tada darbiba tad atgriez attiecigo skaitli
        return 0; // ja nav tad ir default
    }

    public static void handleBattleAction(Entity currentDoer) {
        // varbut darbibu pielidzinat skaitlim lai enemy var uzrollot random skaiti darbibai
        // kipa hashmapaa attack - 1, heal - 2, guard - 3, utt
        // un tad enemy var dice rollot 1-3 un izdarit darbibu
        int action;
        boolean repeat = false;
        do {

            if(currentDoer.getClass().equals(Player.class)) { // ja sobrid gajiens ir speletajam
                input = sc.nextLine(); // speletajs ievada darbibu
                action = actionToNumber(input);
            } else { // ja gajiens ir pretiniekam
                action = diceRoll(3); // izvelas random darbibu
            }

            switch (action) {
                case 1: // attack // TODO: attack funkcija atseviski? jo tas repeat tiek izmantots vairakos
                    if(currentDoer.getClass().equals(Player.class)) {// ja uzbruk speletajs tad jaizvelas, kuram enemy uzbrukt
                        repeat = true;
                        do {
                            System.out.println("Who will you attack?");// izvelas kuram enemy uzbrukt
                            input = sc.nextLine();
                            // parbauda vai tads enemy eksiste
                            for (Entity e : battleQueue) {
                                if (e.getName().equals(input)) {
                                    System.out.println("You attack " + input);
                                    e.takeDamage(currentDoer.getAtk()); // damage funkcija jau ir shield parbaude
                                    // maybe parbaude vai neuzbruk citam playerim- nelaut ta darit
                                    repeat = false;
                                    break;
                                }
                            }
                            if (repeat) System.out.println("This enemy doesn't exist");
                        } while(repeat);
                    } else {
                        // TODO: pagaidam ir tikai viens players bet vajag lai izvelas randoma ja vairaki piedalas cina
                        System.out.println(currentDoer.getName() + " attacks!");
                        player.takeDamage(currentDoer.getAtk());
                    }
                    repeat = false;

                    break;
                case 2: // heal
                    // velak implementot itemus un spellus
                    System.out.println(currentDoer.getName() + " heals!");
                    currentDoer.heal(5); // iebāzt random lai ir random daudzums healam
                    break;
                case 3: // shield
                    System.out.println(currentDoer.getName() + " shields!");
                    currentDoer.setShielding(true); // uz pusi samazina sanemto damage
                    break;
                case 4: // run
                    System.out.println("Your enemy is preventing you from running away.");
                    break;
                default:
                    System.out.println("You can't do that");
                    repeat = true;
            }
        } while(repeat);
    }

}