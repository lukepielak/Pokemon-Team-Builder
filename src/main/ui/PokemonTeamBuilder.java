package ui;

import model.ListOfTeam;
import model.Pokemon;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Objects.isNull;
import static model.PokemonType.*;

// UI for the pokemon team builder app.
public class PokemonTeamBuilder {
    private static final String JSON_STORE = "./data/pkmnteambuilder.json";
    private Scanner input; // scanner.
    ListOfTeam userList = new ListOfTeam(); // initialize empty ListOfTeam.
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // initialize all pokemon and add to list of available pokemon for the user to choose.
    Pokemon[] allPokemon =
            {new Pokemon("Bulbasaur", GRASS, POISON),
                    new Pokemon("Ivysaur", GRASS, POISON),
                    new Pokemon("Venusaur", GRASS, POISON),
                    new Pokemon("Charmander", FIRE, null),
                    new Pokemon("Charmeleon", FIRE, null),
                    new Pokemon("Charizard", FIRE, null),
                    new Pokemon("Squirtle", WATER, null),
                    new Pokemon("Wartortle", WATER, null),
                    new Pokemon("Blastoise", WATER, null),
                    new Pokemon("Caterpie", BUG, null),
                    new Pokemon("Metapod", BUG, null),
                    new Pokemon("Butterfree", BUG, FLYING),
                    new Pokemon("Weedle", BUG, POISON),
                    new Pokemon("Kakuna", BUG, POISON),
                    new Pokemon("Beedrill", BUG, POISON),
                    new Pokemon("Pidgey", NORMAL, FLYING),
                    new Pokemon("Pidgeotto", NORMAL, FLYING),
                    new Pokemon("Pidgeot", NORMAL, FLYING),
                    new Pokemon("Rattata", NORMAL, null),
                    new Pokemon("Raticate", NORMAL, null),
                    new Pokemon("Spearow", NORMAL, FLYING),
                    new Pokemon("Fearow", NORMAL, FLYING),
                    new Pokemon("Ekans", POISON, null),
                    new Pokemon("Arbok", POISON, null),
                    new Pokemon("Pikachu", ELECTRIC, null),
                    new Pokemon("Raichu", ELECTRIC, null),

                    // commented out for the time being, UI is too cluttered when all pokemon are listed.

//                    new Pokemon("Sandslash", "GROUND", ""),
//                    new Pokemon("Nidoran (female)", "POISON", ""),
//                    new Pokemon("Nidorina", "POISON", ""),
//                    new Pokemon("Nidoqueen", "POISON", "GROUND"),
//                    new Pokemon("Nidoran (male)", "POISON", ""),
//                    new Pokemon("Nidorino", "POISON", ""),
//                    new Pokemon("Nidoking", "POISON", "GROUND"),
//                    new Pokemon("Clefairy", "FAIRY", ""),
//                    new Pokemon("Clefable", "FAIRY", ""),
//                    new Pokemon("Vulpix", "FIRE", ""),
//                    new Pokemon("Ninetales", "FIRE", ""),
//                    new Pokemon("Jigglypuff", "NORMAL", "FAIRY"),
//                    new Pokemon("Wigglytuff", "NORMAL", "FAIRY"),
//                    new Pokemon("Zubat", "POISON", "FLYING"),
//                    new Pokemon("Golbat", "POISON", "FLYING"),
//                    new Pokemon("Oddish", "GRASS", "POISON"),
//                    new Pokemon("Gloom", "GRASS", "POISON"),
//                    new Pokemon("Vileplume", "GRASS", "POISON"),
//                    new Pokemon("Paras", "BUG", "GRASS"),
//                    new Pokemon("Parasect", "BUG", "GRASS"),
//                    new Pokemon("Venonat", "BUG", "POISON"),
//                    new Pokemon("Venomoth", "BUG", "POISON"),
//                    new Pokemon("Diglett", "GROUND", ""),
//                    new Pokemon("Dugtrio", "GROUND", ""),
//                    new Pokemon("Meowth", "NORMAL", ""),
//                    new Pokemon("Persian", "NORMAL", ""),
//                    new Pokemon("Psyduck", "WATER", ""),
//                    new Pokemon("Golduck", "WATER", ""),
//                    new Pokemon("Mankey", "FIGHTING", ""),
//                    new Pokemon("Primeape", "FIGHTING", ""),
//                    new Pokemon("Growlithe", "", ""),
//                    new Pokemon("Arcanine", "", ""),
//                    new Pokemon("Poliwag", "", ""),
//                    new Pokemon("Poliwhirl", "", ""),
//                    new Pokemon("Poliwrath", "", ""),
//                    new Pokemon("Abra", "", ""),
//                    new Pokemon("Kadabra", "", ""),
//                    new Pokemon("Alakazam", "", ""),
//                    new Pokemon("Machop", "", ""),
//                    new Pokemon("Machoke", "", ""),
//                    new Pokemon("Machamp", "", ""),
//                    new Pokemon("Bellsprout", "", ""),
//                    new Pokemon("Weepinbell", "", ""),
//                    new Pokemon("Victreebell", "", ""),
//                    new Pokemon("Tentacool", "", ""),
//                    new Pokemon("Tentacruel", "", ""),
//                    new Pokemon("Geodude", "", ""),
//                    new Pokemon("Graveler", "", ""),
//                    new Pokemon("Golem", "", ""),
//                    new Pokemon("Ponyta", "", ""),
//                    new Pokemon("Rapidash", "", ""),
//                    new Pokemon("Slowpoke", "", ""),
//                    new Pokemon("Slowbro", "", "")
            };

    // EFFECTS: runs the pokemon team builder app.
    public PokemonTeamBuilder() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBuilder();
    }

    // MODIFIES: this.
    // EFFECTS: processes user input.
    private void runBuilder() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee ya!");
    }

    // MODIFIES: this.
    // EFFECTS: processes user command for menu.
    private void processCommand(String command) {
        if (command.equals("v")) {
            viewTeams();
        } else if (command.equals("m")) {
            makeTeam();
        } else if (command.equals("s")) {
            saveListOfTeam();
        } else if (command.equals("l")) {
            loadListOfTeam();
        } else {
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this.
    // EFFECTS: initializes scanner.
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user.
    private void displayMenu() {
        System.out.println("\n----------MENU----------");
        System.out.println("Select from:");
        System.out.println("\tv -> View teams");
        System.out.println("\tm -> Make a new team");
        System.out.println("\ts -> Save");
        System.out.println("\tl -> Load");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this.
    // EFFECTS: allows user to choose and view a pokemon team they created.
    private void viewTeams() {
        if (0 == userList.getTeams().size()) {
            System.out.print("You have no teams.");
        } else {
            System.out.print("\nYou have the following teams: \n\n");
            int i = 1;
            for (Team t : userList.getTeams()) {
                System.out.print("\t" + i + " -> " + t.getTeamName() + "  ");
                i++;
            }

            System.out.print("\n\nInput the corresponding number of the team you want to view. \n");
            String teamChoice = input.next();

            try {
                Integer.parseInt(teamChoice);
            } catch (Exception e) {
                teamChoice = "0";
            }

            int teamChoiceInt = Integer.parseInt(teamChoice);
            processTeamChoice(teamChoiceInt);

        }
    }

    // MODIFIES: this.
    // EFFECTS: processes user command for choosing a team.
    private void processTeamChoice(int teamChoiceInt) {
        if ((teamChoiceInt > 0) && (teamChoiceInt <= getTeams().size())) {
            System.out.println("\n\n" + getTeams().get(teamChoiceInt - 1).getTeamName() + " contains: \n");
            for (int j = 0; (j < getTeams().get(teamChoiceInt - 1).getPokemonList().size()); j++) {
                System.out.println("\t" + getNameAtIndex(teamChoiceInt, j) + " | " + getTypesAtIndex(teamChoiceInt, j));
            }
        } else {
            System.out.println("\t\nInvalid input, returned to menu.");
        }
    }

    // EFFECTS: returns name of pokemon in chosen team at specified index.
    private String getNameAtIndex(int teamChoiceInt, int j) {
        return getTeams().get(teamChoiceInt - 1).getPokemonList().get(j).getPokemonName();
    }

    // EFFECTS: returns first type of pokemon in chosen team at specified index.
    private String getType1AtIndex(int teamChoiceInt, int j) {
        if (isNull(getTeams().get(teamChoiceInt - 1).getPokemonList().get(j).getType1())) {
            return "";
        }
        return getTeams().get(teamChoiceInt - 1).getPokemonList().get(j).getType1();
    }

    // EFFECTS: returns second type of pokemon in chosen team at specified index.
    private String getType2AtIndex(int teamChoiceInt, int j) {
        if (isNull(getTeams().get(teamChoiceInt - 1).getPokemonList().get(j).getType2())) {
            return "";
        }
        return getTeams().get(teamChoiceInt - 1).getPokemonList().get(j).getType2();
    }

    // EFFECTS: returns both pokemon types in chosen team at specified index
    private String getTypesAtIndex(int teamChoiceInt, int j) {
        return getType1AtIndex(teamChoiceInt, j) + " " + getType2AtIndex(teamChoiceInt, j);
    }

    // MODIFIES: this.
    // EFFECTS: allows user to create a new pokemon team.
    private void makeTeam() {
        System.out.println("Choose a name for your new team.");
        String nameChoice = input.next();
        processNameChoice(nameChoice);

        boolean keepGoing2 = !nameChoice.equals("");

        while (keepGoing2) {
            System.out.println("\nAdd a Pokemon to your team by inputting the corresponding number. ");
            System.out.println("Input 'q' when you are finished.\n");

            displayPokemonChoices();

            String pokemonChoice = input.next();

            if ((pokemonChoice.equals("q")) && getTeams().get(getTeams().size() - 1).getPokemonList().size() == 0) {
                System.out.println("Please add at least one Pokemon to your team.");
            } else if (pokemonChoice.equals("q")) {
                keepGoing2 = false;
            } else {
                processPokemonChoice(pokemonChoice);
            }
        }
    }

    // MODIFIES: this.
    // EFFECTS: processes user command for naming a team.
    private void processNameChoice(String nameChoice) {
        if (nameChoice.equals("")) {
            System.out.println("Insufficient characters, returned to menu.");
        } else {
            userList.addTeam(new Team(nameChoice));
        }
    }

    // EFFECTS: processes user command for choosing a pokemon.
    private void processPokemonChoice(String pokemonChoice) {
        try {
            Integer.parseInt(pokemonChoice);
        } catch (Exception e) {
            pokemonChoice = "0";
        }
        int pokemonChoiceInt = Integer.parseInt(pokemonChoice);

        if ((pokemonChoiceInt > 0) && pokemonChoiceInt <= allPokemon.length) {
            getNewTeam().addPokemon(allPokemon[pokemonChoiceInt - 1]);
        } else {
            System.out.println("Please choose a valid number.");
        }
    }

    // EFFECTS: displays all available pokemon to choose from.
    private void displayPokemonChoices() {
        int i = 1;
        for (Pokemon p : allPokemon) {
            System.out.println("\t" + i + " -> " + p.getPokemonName());
            i++;
        }
    }

    // EFFECTS: gets teams from userList.
    private ArrayList<Team> getTeams() {
        return userList.getTeams();
    }

    // EFFECTS: getter for pokemon choice, returns the most recently created team.
    private Team getNewTeam() {
        return getTeams().get(getTeams().size() - 1);
    }

    // EFFECTS: saves the workroom to file
    private void saveListOfTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(userList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadListOfTeam() {
        try {
            userList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

