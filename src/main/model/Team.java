package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// a team having a name and a list of 0 - 6 pokemon.
public class Team implements Writable {
    private String teamName; // the name of a pokemon team.
    private ArrayList<Pokemon> pokemonList; // the pokemon in a team (0 - 6 elements).
    
    // MODIFIES: this.
    // REQUIRES: teamName has a non-zero length, pokemonList.size() < 6.
    // EFFECTS: creates a team with a team name and an empty list of pokemon initially.
    public Team(String teamName) {
        this.teamName = teamName;
        this.pokemonList = new ArrayList<Pokemon>();
    }

    // MODIFIES: this.
    // EFFECTS: adds a pokemon to a team if team.pokemonList.size() < 6
    public boolean addPokemon(Pokemon pokemon) {
        if (pokemonList.size() < 6) {
            pokemonList.add(pokemon);
            EventLog.getInstance().logEvent(new Event(pokemon.getPokemonName() + " was added to a team."));
            return true;
        } else {
            return false;
        }
    }

    public String getTeamName() {
        return this.teamName;
    }

    public ArrayList<Pokemon> getPokemonList() {
        return this.pokemonList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamName", teamName);
        json.put("pokemonList", pokemonToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : pokemonList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
