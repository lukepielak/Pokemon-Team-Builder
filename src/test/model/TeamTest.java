package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.PokemonType.*;
import static org.junit.jupiter.api.Assertions.*;

// tests for methods in the team class.
public class TeamTest {
    private Team team1;

    @BeforeEach
    void runBefore() {
        team1 = new Team("team1");
    }

    @Test
    void testConstructor() {
        assertEquals("team1", team1.getTeamName());
        assertEquals(0, team1.getPokemonList().size());
    }

    @Test
    void testAddPokemon1() {
        // add to an empty team.
        Pokemon squirtle = new Pokemon("Squirtle", WATER, null);

        assertTrue(team1.addPokemon(squirtle));

        assertEquals(1, team1.getPokemonList().size());
    }

    @Test
    void testAddPokemon2() {
        // add to a team with one element.
        Pokemon squirtle = new Pokemon("Squirtle", WATER, null);
        Pokemon charmander = new Pokemon("Charmander", FIRE, null);

        assertTrue(team1.addPokemon(squirtle));
        assertTrue(team1.addPokemon(charmander));

        assertEquals(2, team1.getPokemonList().size());
    }

    @Test
    void testAddPokemon3() {
        // add to a team that is full.
        Pokemon charmander = new Pokemon("Charmander", FIRE, null);

        assertTrue(team1.addPokemon(charmander)); //   1
        assertTrue(team1.addPokemon(charmander)); //   2
        assertTrue(team1.addPokemon(charmander)); //   3
        assertTrue(team1.addPokemon(charmander)); //   4
        assertTrue(team1.addPokemon(charmander)); //   5
        assertTrue(team1.addPokemon(charmander)); //   6
        assertFalse(team1.addPokemon(charmander)); //  7

        assertEquals(6, team1.getPokemonList().size());
    }
    @Test
    void testAddPokemon4() {
        // add to a team that is almost full
        Pokemon charmander = new Pokemon("Charmander", FIRE, null);
        assertTrue(team1.addPokemon(charmander)); //   1
        assertTrue(team1.addPokemon(charmander)); //   2
        assertTrue(team1.addPokemon(charmander)); //   3
        assertTrue(team1.addPokemon(charmander)); //   4
        assertTrue(team1.addPokemon(charmander)); //   5
        assertTrue(team1.addPokemon(charmander)); //   6

        assertEquals(6, team1.getPokemonList().size());
    }
}
