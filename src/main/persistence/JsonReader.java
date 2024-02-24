package persistence;

import model.PokemonType;
import model.Pokemon;
import model.Team;
import model.ListOfTeam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import static java.util.Objects.isNull;

// Represents a reader that reads ListOfTeam from JSON data stored in file
public class JsonReader {
    // CITATION: code taken from provided sample, JasonSerializationDemo, and appropriate adjustments were made.

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfTeam from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfTeam read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ListOfTeam from JSON object and returns it
    private ListOfTeam parseListOfTeam(JSONObject jsonObject) {
        ListOfTeam lot = new ListOfTeam();
        addTeams(lot, jsonObject);
        return lot;
    }

    // MODIFIES: lot
    // EFFECTS: parses teams from JSON object and adds them to ListOfTeam
    private void addTeams(ListOfTeam lot, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(lot, nextTeam);
        }
    }

    // MODIFIES: lot
    // EFFECTS: parses team from JSON object and adds it to ListOfTeam
    private void addTeam(ListOfTeam lot, JSONObject jsonObject) {
        String teamName = jsonObject.getString("teamName");
        Team team = new Team(teamName);
        addTeam2(team, jsonObject);
        lot.addTeam(team);
    }

    // MODIFIES: team
    // EFFECTS: parses thingies from JSON object and adds them to ListOfTeam
    private void addTeam2(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pokemonList");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemon(team, nextPokemon);
        }
    }

    // MODIFIES: team
    // EFFECTS: parses pokemon from JSON object and adds it to team
    private void addPokemon(Team team, JSONObject jsonObject) {
        String pokemonName = jsonObject.getString("pokemonName");
        PokemonType type1 = PokemonType.valueOf(jsonObject.getString("type1"));
        PokemonType type2;
        try {
            type2 = PokemonType.valueOf(jsonObject.getString("type2"));
        } catch (JSONException e) {
            type2 = null;
        }
        Pokemon pokemon = new Pokemon(pokemonName, type1, type2);
        team.addPokemon(pokemon);
    }

}

