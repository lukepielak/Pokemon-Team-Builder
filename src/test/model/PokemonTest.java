package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.PokemonType.*;
import static org.junit.jupiter.api.Assertions.*;

// tests for methods in the pokemon class.
public class PokemonTest {
    private Pokemon bulbasaur;
    private Pokemon squirtle;
    private Pokemon charmander;



    @BeforeEach
    void runBefore() {
        bulbasaur = new Pokemon("Bulbasaur", GRASS, POISON);
        squirtle = new Pokemon("Squirtle", WATER, null);
        charmander = new Pokemon("Charmander", FIRE, null);
    }

    @Test
    void testConstructor() {
        // bulbasaur
        assertEquals("Bulbasaur", bulbasaur.getPokemonName());
        assertEquals("GRASS", bulbasaur.getType1());
        assertEquals("POISON", bulbasaur.getType2());

        // squirtle
        assertEquals("Squirtle", squirtle.getPokemonName());
        assertEquals("WATER", squirtle.getType1());
        assertNull(squirtle.getType2());

        // charmander
        assertEquals("Charmander", charmander.getPokemonName());
        assertEquals("FIRE", charmander.getType1());
        assertEquals(null, charmander.getType2());
    }

}
