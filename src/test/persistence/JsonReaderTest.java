package persistence;

import model.ListOfTeam;
import model.Pokemon;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.PokemonType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    // CITATION: code taken from provided sample, JasonSerializationDemo, and appropriate adjustments were made.

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/Dad.json");
        try {
            ListOfTeam lot = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyListOfTeam() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfTeam.json");
        try {
            ListOfTeam lot = reader.read();
            assertEquals(0, lot.getTeams().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListOfTeam() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfTeam.json");
        try {
            ListOfTeam lot = reader.read();

            ArrayList<Team> teams = lot.getTeams();
            assertEquals(3, teams.size());

            checkTeam("Fire Team", 3, lot.getTeams().get(0));
            checkTeam("Bug Team", 4, lot.getTeams().get(1));
            checkTeam("Water Team", 3, lot.getTeams().get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



}
