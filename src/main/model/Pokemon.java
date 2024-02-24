package model;

import org.json.JSONObject;
import persistence.Writable;

import static java.util.Objects.isNull;


// a pokemon with a name and up to two types.
public class Pokemon implements Writable {
    private String pokemonName; // the name of a pokemon.
    private PokemonType type1; // the first type of a pokemon.
    private PokemonType type2; // the second type of a pokemon if applicable.

    // REQUIRES: type1 is a PokemonType
    //           type2 is a PokemonType or null
    // MODIFIES: this.
    // EFFECTS: creates a pokemon with a name and 1-2 types.
    public Pokemon(String pokemonName, PokemonType type1, PokemonType type2) {
        this.pokemonName = pokemonName;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public String getType1() {

        return this.type1.name();
    }

    public String getType2() {
        if (isNull(this.type2)) {
            return null;
        }
        return this.type2.name();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pokemonName", pokemonName);
        json.put("type1", type1);
        json.put("type2", type2);
        return json;
    }

}
