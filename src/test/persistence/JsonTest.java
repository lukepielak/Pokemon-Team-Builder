package persistence;

import model.Pokemon;
import model.Team;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    // CITATION: code taken from provided sample, JasonSerializationDemo, and appropriate adjustments were made.

    protected void checkTeam(String teamName, int size, Team team) {
        assertEquals(teamName, team.getTeamName());
        assertEquals(size, team.getPokemonList().size());
    }
}
